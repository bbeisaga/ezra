package com.ezra.programandojuntos.models.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Path;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezra.programandojuntos.dto.PedidoReporte;
import com.ezra.programandojuntos.dto.report.Report;
import com.ezra.programandojuntos.errors.PedidoMapErrors;
import com.ezra.programandojuntos.exceptions.PedidoExceptions;
import com.ezra.programandojuntos.models.dao.IPedidoDao;
import com.ezra.programandojuntos.models.dao.IProductoDao;
import com.ezra.programandojuntos.models.entity.EstadoPedido;
import com.ezra.programandojuntos.models.entity.ItemPedido;
import com.ezra.programandojuntos.models.entity.Movimiento;
import com.ezra.programandojuntos.models.entity.Pedido;
import com.ezra.programandojuntos.models.entity.TipoPedido;
import com.ezra.programandojuntos.models.repository.PedidoRepository;
import com.ezra.programandojuntos.util.ExcelUtil;

@Service
public class PedidoServiceImpl implements IPedidoService {
	
	Logger log = LoggerFactory.getLogger(PedidoServiceImpl.class);
	
	public static final Long PEDIDO_PENDIENTE = 1L;
	public static final Long PEDIDO_VENCIDO = 2L;
	public static final Long PEDIDO_DEVUELTO = 3L;
	public static final Long PEDIDO_CANCELADO = 4L;
	public static final Long PEDIDO_ENTREGADO = 5L;
	public static final Long PEDIDO_ADQUIRIDO = 6L;
	
	public static final Long TIPO_PEDIDO_VENTA = 1L;
	public static final Long TIPO_PEDIDO_COMPRA = 2L;
	public static final BigDecimal IGV =new BigDecimal(0.18);



	@Autowired
	private IPedidoDao pedidoDao;
		
	@Autowired
	private IProductoDao productoDao;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<Pedido> findPedidoAll() {
		return (List<Pedido>) pedidoDao.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Page<Pedido> findAllPedidoPageable(String query, Long tipoPedidoId, Pageable pageable) {
		
		log.info("findAllPedidoPageable pageable= {}",pageable);
	
		return pedidoDao.findAllPedidoPageable(query,tipoPedidoId, pageable);
	}
	

	@Override
	@Transactional(readOnly = true)
	public Pedido findPedidoById(Long id) {
		return pedidoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Pedido registrarPedido(Pedido pedido) throws PedidoExceptions {	
		TipoPedido tipoPedido = pedidoDao.findTipoPedidoById(pedido.getTipoPedido().getId());
		pedido.setPagado(false);
		pedido.setPrecioBrutoTotal(new BigDecimal(0));
		pedido.setPagoTotal(new BigDecimal(0));
		pedido.setFlujoEfectivoTotal(new BigDecimal(0));
		pedido.setEstadoPedido(pedidoDao.findAllEstadoPedido()
				.stream()
				.filter(e->e.getId()==PEDIDO_PENDIENTE) 
				.collect(Collectors.toList())
				.get(0));
		if(tipoPedido.getId()==TIPO_PEDIDO_VENTA){
			if(pedido.getEntregadoEn()==null){
				throw new PedidoExceptions(PedidoMapErrors.getErrorString(PedidoMapErrors.CODE_FECHA_VENTA));
			} 
			
//			if(pedido.getEntregadoEn().before(new Date())){
//				pedido.setEstadoPedido(pedidoDao.findAllEstadoPedido()
//						.stream()
//						.filter(estado -> estado.getId()== PEDIDO_VENCIDO)
//						.collect(Collectors.toList())
//						.get(0));
//			}
			pedido.setPrecioNetoTotal(pendienteImporte(pedido.getItems(), new BigDecimal(0)));
			pedido.setPrecioBrutoTotal(pedido.getPrecioNetoTotal().divide(IGV.add(new BigDecimal(1)),2, RoundingMode.HALF_UP));
			//pedido.setSaldoBrutoPedido(pedido.getPrecioBrutoTotal());
			pedido.setSaldoPedido(pedido.getPrecioNetoTotal());

		} else if(tipoPedido.getId()==TIPO_PEDIDO_COMPRA) {
			if(pedido.getAdquiridoEn()==null){
				throw new PedidoExceptions(PedidoMapErrors.getErrorString(PedidoMapErrors.CODE_FECHA_ADQUISICION));
			} 
//			if(pedido.getAdquiridoEn().before(new Date())){
//				pedido.setEstadoPedido(pedidoDao.findAllEstadoPedido()
//						.stream()
//						.filter(estado -> estado.getId()== PEDIDO_VENCIDO)
//						.collect(Collectors.toList())
//						.get(0));			
//			}
			
			pedido.setCostoNetoTotal(pendienteImporte(pedido.getItems(), new BigDecimal(0)));
			pedido.setCostoBrutoTotal(pedido.getCostoNetoTotal().divide(IGV.add(new BigDecimal(1)),2, RoundingMode.HALF_UP));
			//pedido.setSaldoBrutoPedido(pedido.getCostoBrutoTotal());
			pedido.setSaldoPedido(pedido.getCostoNetoTotal());
		}
		
		if((pedido.getEntregadoEn()!=null && pedido.getEntregadoEn().before(new Date())) || 
				   (pedido.getAdquiridoEn()!=null && pedido.getAdquiridoEn().before(new Date()))
			){			
			pedido.setEstadoPedido(pedidoDao.findAllEstadoPedido()
						.stream()
						.filter(estado -> estado.getId()== PEDIDO_VENCIDO)
						.collect(Collectors.toList())
						.get(0));	
			}
		
		pedido.setTipoPedido(tipoPedido);

		if(pedido.getSaldoPedido().intValue() < 1) {
			pedido.setPagado(true);
		}		

		return pedidoDao.save(pedido);
	}
	
	@Override
	@Transactional
	public Pedido updatePedido(Pedido pedido, Long id) {	
		Pedido pedidoActual = findPedidoById(id);
		//TipoPedido tipoPedido = pedidoDao.findTipoPedidoById(pedido.getTipoPedido().getId());
		List<EstadoPedido> lstEstadosPedido = findAllEstadoPedido();
		if (pedidoActual!=null) {		
			pedidoActual.setPagado(false);	
			//pedidoActual.setDevuelto(pedido.isDevuelto());
			if((pedidoActual.getEntregadoEn()!=null && pedidoActual.getEntregadoEn().before(new Date())) || 
				   (pedidoActual.getAdquiridoEn()!=null && pedidoActual.getAdquiridoEn().before(new Date()))
			){			
				pedidoActual.setEstadoPedido(pedidoDao.findAllEstadoPedido()
						.stream()
						.filter(estado -> estado.getId()== PEDIDO_VENCIDO)
						.collect(Collectors.toList())
						.get(0));	
				
			}
			Map<String, BigDecimal> movimientosPedido = null;	
			//movimientosPedido = movimientoPorPedido(id, pedidoActual.getTipoPedido().getId());
			movimientosPedido = movimientoPorPedido(id);
			pedidoActual.setFlujoEfectivoTotal(movimientosPedido.get("flujoEfectivo"));
			pedidoActual.setSaldoPedido(pendienteImporte(pedidoActual.getItems(), movimientosPedido.get("flujoEfectivo")));	
			
			if(pedidoActual.getTipoPedido().getId()==TIPO_PEDIDO_VENTA){
				pedidoActual.setPagoTotal(movimientosPedido.get("ingreso"));
				pedidoActual.setVueltoTotal(movimientosPedido.get("egreso"));				
				if(pedidoActual.getSaldoPedido().intValue() < 1 && pedidoActual.getPagoTotal().intValue() > 0 ) {
					pedidoActual.setEstadoPedido(
							lstEstadosPedido.stream()
							.filter(e->e.getId()==PEDIDO_ENTREGADO)
							.collect(Collectors.toList())
							.get(0));	
				}
			}else if(pedidoActual.getTipoPedido().getId()==TIPO_PEDIDO_COMPRA) {
				pedidoActual.setPagoTotal(movimientosPedido.get("egreso"));
				pedidoActual.setVueltoTotal(movimientosPedido.get("ingreso"));
				if(pedidoActual.getSaldoPedido().intValue() < 1 && pedidoActual.getPagoTotal().intValue() > 0 ) {
					pedidoActual.setEstadoPedido(
							lstEstadosPedido.stream()
							.filter(e->e.getId()==PEDIDO_ADQUIRIDO)
							.collect(Collectors.toList())
							.get(0));	
				}
			}
	
			if(pedidoActual.getSaldoPedido().intValue() > 0 && pedidoActual.getPagoTotal().intValue() < 1) {
				pedidoActual.setEstadoPedido(
						lstEstadosPedido.stream()
						.filter(e->e.getId()==PEDIDO_CANCELADO)
						.collect(Collectors.toList())
						.get(0));		
			}
			
			if(pedidoActual.getSaldoPedido().intValue() < 1 && pedidoActual.getPagoTotal().intValue() > 0 ) {
				pedidoActual.setPagado(true);	
			}		
			
			if(pedido.isDevuelto() ) {
				pedidoActual.setDevuelto(pedido.isDevuelto());
				pedidoActual.setEstadoPedido(pedidoDao.findAllEstadoPedido()
						.stream()
						.filter(estado -> estado.getId()== PEDIDO_DEVUELTO)
						.collect(Collectors.toList())
						.get(0));
			}
		
		} 
		return pedidoDao.save(pedidoActual);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Map<String, BigDecimal> movimientoPorPedido(Long pedidoId){
		Pedido pedido = findPedidoById(pedidoId);
		List<Movimiento> items= pedido.getMovimientos();
//		BigDecimal saldoPedido = new BigDecimal(0);
		BigDecimal ingresoPedido = new BigDecimal(0);
		BigDecimal egresoPedido = new BigDecimal(0);

		for (Movimiento item : items) {
			ingresoPedido = ingresoPedido.add(item.getIngresoDinero()); //+
			egresoPedido = egresoPedido.add(item.getEgresoDinero()); // -
		}
		
//		if(tipoPedido==TIPO_PEDIDO_VENTA){
//			saldoPedido = ingresoPedido.subtract(egresoPedido);
//		} else if(tipoPedido==TIPO_PEDIDO_COMPRA){
//			saldoPedido = egresoPedido.subtract(ingresoPedido);
//		}
		
        Map<String, BigDecimal> mapa = new HashMap<String, BigDecimal>();
        mapa.put("ingreso", ingresoPedido);
        mapa.put("egreso", egresoPedido);
//        mapa.put("saldoPedido", saldoPedido);
        mapa.put("flujoEfectivo", ingresoPedido.subtract(egresoPedido).abs());

		return mapa;
	}
	
	
	public BigDecimal pendienteImporte (List<ItemPedido> itemPedido, BigDecimal pago) {
		BigDecimal total = new BigDecimal(0);
		for (ItemPedido item : itemPedido) {
			 if(item.getImporte()== null || item.getImporte().floatValue()<=0)  {
				 throw new PedidoExceptions(
						 PedidoMapErrors.getErrorString(
								PedidoMapErrors.CODE_IMPORTE_ITEMS_PEDIDO, 
						 		item.getProducto().getNombre()
						 ));
			 }
			total = total.add(item.getImporte());
		}
		return (total.subtract(pago));
	}
	


	@Override
	@Transactional
	public void deletePedidoById(Long id) {
		pedidoDao.deleteById(id);
	}
	

	
	@Override
	@Transactional(readOnly = true)
	public List<EstadoPedido> findAllEstadoPedido(){
		return pedidoDao.findAllEstadoPedido();
	}

	
	@Override
	@Transactional(readOnly = true)
	public List<TipoPedido> listarTipoPedidoAll(){
		return pedidoDao.findAllTipoPedido();
	}
	
	@Override
	@Transactional(readOnly = true)
	public TipoPedido tipoPedidoById(Long id){
		return pedidoDao.findTipoPedidoById(id);
	}
	
	@Override
	public ByteArrayInputStream createReportPedidos(Report reporte) {
		String[] cabeceraReporte = null;
		if(reporte.getTipoPedido()==TIPO_PEDIDO_VENTA) {
			cabeceraReporte = new String[] {
				"Cliente", "Código pedido", "Fecha creación", "Fecha entrega", "Estado pedido", 
				"Descripción estado", "Precio Total Bruto", "Precio Total Neto(IGV)", "Saldo pedido","Pago total","Vuelto total","Es pagado","Tipo pedido",};
		}
		
		if(reporte.getTipoPedido()==TIPO_PEDIDO_COMPRA) {
			cabeceraReporte = new String[] {
				"Proveedor", "Código pedido", "Fecha creación", "Fecha adquisición", "Estado pedido", 
				"Descripción estado", "Costo Total Bruto", "Costo Total Neto(IGV)", "Saldo pedido","Pago total","Vuelto total", "Es pagado","Tipo pedido",};}
		
		XSSFWorkbook excelbook = new XSSFWorkbook();
		XSSFSheet excelHoja = excelbook.createSheet("data");
		ExcelUtil.generarCabecera(excelbook, excelHoja, cabeceraReporte);
		
		//DataFormat fmt =  excelbook.createDataFormat();
		XSSFCellStyle style =ExcelUtil.crearStyle(excelbook, 
							HorizontalAlignment.CENTER, 
							HSSFColorPredefined.GREY_25_PERCENT.getIndex(), 
							(short) 10, 
							HSSFColorPredefined.BLACK.getIndex());
		//style.setDataFormat(fmt.getFormat("@"));
		//dataFormat.GetFormat("M/D/YYYY");
		
		int numFila = 1;
		List<PedidoReporte> pedidos = pedidoRepository.listarPedidosConFiltros(reporte);
		for(PedidoReporte p: pedidos) {
			short numColumn = 0;
			XSSFRow fila = excelHoja.createRow(numFila);
			ExcelUtil.insertarDataCelda(fila, numColumn ++, p.getNomApellRz(), style);
			ExcelUtil.insertarDataCelda(fila, numColumn ++, p.getCodigoPedido(), style);
			ExcelUtil.insertarDataCelda(fila, numColumn ++, p.getFechaCreacion(), style);
			if(reporte.getTipoPedido()==TIPO_PEDIDO_VENTA) {
				ExcelUtil.insertarDataCelda(fila, numColumn ++, p.getFechaEntrega(), style);}
			if(reporte.getTipoPedido()==TIPO_PEDIDO_COMPRA) {
				ExcelUtil.insertarDataCelda(fila, numColumn ++, p.getAdquiridoEn(), style);}
			
			ExcelUtil.insertarDataCelda(fila, numColumn ++, p.getEstadoPedido(), style);
			ExcelUtil.insertarDataCelda(fila, numColumn ++, p.getDescripcionPedido(), style);
			
			if(reporte.getTipoPedido()==TIPO_PEDIDO_VENTA) {
				ExcelUtil.insertarDataCelda(fila, numColumn ++, p.getPrecioTotalBruto().doubleValue(), style);}
			if(reporte.getTipoPedido()==TIPO_PEDIDO_COMPRA) {
				ExcelUtil.insertarDataCelda(fila, numColumn ++, p.getCostoBrutoTotal().doubleValue(), style);}
			if(reporte.getTipoPedido()==TIPO_PEDIDO_VENTA) {
				ExcelUtil.insertarDataCelda(fila, numColumn ++, p.getPrecioTotalNeto().doubleValue(), style);}
			if(reporte.getTipoPedido()==TIPO_PEDIDO_COMPRA) {
				ExcelUtil.insertarDataCelda(fila, numColumn ++, p.getCostoNetoTotal().doubleValue(), style);}
			
			ExcelUtil.insertarDataCelda(fila, numColumn ++, p.getSaldoPedido().doubleValue(), style);	
			ExcelUtil.insertarDataCelda(fila, numColumn ++, p.getPagoTotal().doubleValue(), style);			
			ExcelUtil.insertarDataCelda(fila, numColumn ++, p.getVueltoTotal().doubleValue(), style);			

			ExcelUtil.insertarDataCelda(fila, numColumn ++, p.getEsPagado(), style);			
			ExcelUtil.insertarDataCelda(fila, numColumn ++, p.getTipoPedido(), style);			
			numFila++;
		};
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			excelbook.write(out);
		} catch (IOException e) {
			e.getCause();
			//throw new RuntimeException("Error al descargar plantilla baja",e);
		}
		return new ByteArrayInputStream(out.toByteArray());
	}

}
