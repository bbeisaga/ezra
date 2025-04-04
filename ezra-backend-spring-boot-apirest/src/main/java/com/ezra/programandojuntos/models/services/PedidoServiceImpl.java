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
	
	/* Estados del pedido 
			Pagado	entrega(vencido)		aceptado
Registrado	-		no						no
Vencido		-		si						no
Devuelto 	-		si						no
Entregado	si		-						si*/
	
	public static final Long PEDIDO_REGISTRADO = 1L;
	public static final Long PEDIDO_VENCIDO = 2L;
	public static final Long PEDIDO_DEVUELTO = 3L;
	public static final Long PEDIDO_ENTREGADO = 4L;
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
	public Page<Pedido> findAllPedidoPageable(String query, Pageable pageable) {
		
		log.info("findAllPedidoPageable pageable= {}",pageable);
	
		return pedidoDao.findAllPedidoPageable(query, pageable);
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
		pedido.setAceptado(false);
		pedido.setVencido(false);
		pedido.setPrecioBrutoTotal(new BigDecimal(0));
		pedido.setPagoTotal(new BigDecimal(0));
		if(tipoPedido.getId()==TIPO_PEDIDO_VENTA){
			if(pedido.getEntregadoEn()==null){
				throw new PedidoExceptions(PedidoMapErrors.getErrorString(PedidoMapErrors.CODE_FECHA_VENTA));
			} 
			
			if(pedido.getEntregadoEn().before(new Date())){
				pedido.setVencido(true);
			}
			pedido.setPrecioNetoTotal(pendienteImporte(pedido.getItems(), new BigDecimal(0)));
			pedido.setPrecioBrutoTotal(pedido.getPrecioNetoTotal().divide(IGV.add(new BigDecimal(1)),2, RoundingMode.HALF_UP));
			//pedido.setSaldoBrutoPedido(pedido.getPrecioBrutoTotal());
			pedido.setSaldoPedido(pedido.getPrecioNetoTotal());

		} else if(tipoPedido.getId()==TIPO_PEDIDO_COMPRA) {
			if(pedido.getAdquiridoEn()==null){
				throw new PedidoExceptions(PedidoMapErrors.getErrorString(PedidoMapErrors.CODE_FECHA_ADQUISICION));
			} 
			if(pedido.getAdquiridoEn().before(new Date())){
				pedido.setVencido(true);
			}
			
			pedido.setCostoNetoTotal(pendienteImporte(pedido.getItems(), new BigDecimal(0)));
			pedido.setCostoBrutoTotal(pedido.getCostoNetoTotal().divide(IGV.add(new BigDecimal(1)),2, RoundingMode.HALF_UP));
			//pedido.setSaldoBrutoPedido(pedido.getCostoBrutoTotal());
			pedido.setSaldoPedido(pedido.getCostoNetoTotal());
		}
		
		pedido.setTipoPedido(tipoPedido);

		if(pedido.getSaldoPedido().intValue() < 1) {
			pedido.setPagado(true);
		}		
		if(pedido.isVencido() && !pedido.isAceptado()) {
			pedido.setEstadoPedido(pedidoDao.findAllEstadoPedido()
					.stream()
					.filter(estado -> estado.getId()== PEDIDO_VENCIDO)
					.collect(Collectors.toList())
					.get(0));
		} else {
			pedido.setEstadoPedido(pedidoDao.findAllEstadoPedido()
					.stream()
					.filter(estado -> estado.getId()== PEDIDO_REGISTRADO)
					.collect(Collectors.toList())
					.get(0));
			
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
			
			//pedido.setVencido(false);
		if((pedidoActual.getEntregadoEn()!=null && pedidoActual.getEntregadoEn().before(new Date())) || 
			   (pedidoActual.getAdquiridoEn()!=null && pedidoActual.getAdquiridoEn().before(new Date()))
		){
			pedidoActual.setVencido(true);
		}
		Map<String, BigDecimal> movimientosPedido = null;	
		if(pedidoActual.getTipoPedido().getId()==TIPO_PEDIDO_VENTA){
			movimientosPedido = movimientoPorPedido(id, TIPO_PEDIDO_VENTA);
			//pedidoActual.setPrecioNetoTotal(pendienteImporte(pedidoActual.getItems(), new BigDecimal(0)));
			//pedidoActual.setPrecioBrutoTotal(pedidoActual.getPrecioBrutoTotal().divide(IGV.add(new BigDecimal(1))));
			pedidoActual.setPagoTotal(movimientosPedido.get("ingresoPedido"));
			pedidoActual.setVueltoTotal(movimientosPedido.get("egresoPedido"));	
		}
		else if(pedidoActual.getTipoPedido().getId()==TIPO_PEDIDO_COMPRA) {
			movimientosPedido = movimientoPorPedido(id, TIPO_PEDIDO_COMPRA);
			//pedidoActual.setCostoNetoTotal(pendienteImporte(pedidoActual.getItems(), new BigDecimal(0)));
			//pedidoActual.setCostoBrutoTotal(pedidoActual.getCostoBrutoTotal().divide(IGV.add(new BigDecimal(1))));
			pedidoActual.setPagoTotal(movimientosPedido.get("egresoPedido"));
			pedidoActual.setVueltoTotal(movimientosPedido.get("ingresoPedido"));
		}
		pedidoActual.setSaldoPedido(pendienteImporte(pedidoActual.getItems(), movimientosPedido.get("saldoPedido")));
		if(pedidoActual.getSaldoPedido().intValue() < 1) {
			pedidoActual.setPagado(true);
		}
		
		pedidoActual.setAceptado(pedido.isAceptado());
		pedidoActual.setEstadoPedido(
				lstEstadosPedido.stream()
				.filter(e->e.getId()==PEDIDO_REGISTRADO)
				.collect(Collectors.toList())
				.get(0));
	
		if(pedidoActual.isVencido() && !pedido.isAceptado()) {
			pedidoActual.setEstadoPedido(
					lstEstadosPedido.stream()
					.filter(e->e.getId()==PEDIDO_VENCIDO)
					.collect(Collectors.toList())
					.get(0));			}  
		
		if(pedidoActual.isPagado() && pedido.isAceptado()) {
				pedidoActual.setAceptado(true);
				pedidoActual.setEntregadoEn(new Date());
				pedidoActual.setEstadoPedido(
						lstEstadosPedido.stream()
						.filter(e->e.getId()==PEDIDO_ENTREGADO)
						.collect(Collectors.toList())
						.get(0));			} 			
		} 
		return pedidoDao.save(pedidoActual);
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
	@Transactional(readOnly = true)
	public Map<String, BigDecimal> movimientoPorPedido(Long pedidoId, Long tipoPedido){
		Pedido pedido = findPedidoById(pedidoId);
		List<Movimiento> items= pedido.getMovimientos();
		BigDecimal saldoPedido = new BigDecimal(0);
		BigDecimal ingresoPedido = new BigDecimal(0);
		BigDecimal egresoPedido = new BigDecimal(0);

		for (Movimiento item : items) {
			ingresoPedido = ingresoPedido.add(item.getIngresoDinero()); //+
			egresoPedido = egresoPedido.add(item.getEgresoDinero()); // -
		}
		
		if(tipoPedido==TIPO_PEDIDO_VENTA){
			saldoPedido = ingresoPedido.subtract(egresoPedido);
		} else if(tipoPedido==TIPO_PEDIDO_COMPRA){
			saldoPedido = egresoPedido.subtract(ingresoPedido);
		}
		
        Map<String, BigDecimal> mapa = new HashMap<String, BigDecimal>();
        mapa.put("ingresoPedido", ingresoPedido);
        mapa.put("egresoPedido", egresoPedido);
        mapa.put("saldoPedido", saldoPedido);
		return mapa;
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
				"Apellidos", "Nombres", "Razón Social", "Código pedido", "Fecha creación", "Fecha entrega", "Estado pedido", 
				"Descripción estado", "Precio Total Bruto", "Precio Total Neto(IGV)", "Saldo pedido","Es pagado","Tipo pedido",};
		}
		
		if(reporte.getTipoPedido()==TIPO_PEDIDO_COMPRA) {
			cabeceraReporte = new String[] {
				"Apellidos", "Nombres", "Razón Social", "Código pedido", "Fecha creación", "Fecha adquisición", "Estado pedido", 
				"Descripción estado", "Costo Total Bruto", "Costo Total Neto(IGV)", "Saldo pedido", "Es pagado","Tipo pedido",};}
		
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
			ExcelUtil.insertarDataCelda(fila, numColumn ++, p.getApellidos(), style);
			ExcelUtil.insertarDataCelda(fila, numColumn ++, p.getNombres(), style);
			ExcelUtil.insertarDataCelda(fila, numColumn ++, p.getRazonSocial(), style);
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
