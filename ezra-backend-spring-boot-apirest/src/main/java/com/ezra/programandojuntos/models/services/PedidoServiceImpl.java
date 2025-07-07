package com.ezra.programandojuntos.models.services;

import java.awt.Color;
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
import com.ezra.programandojuntos.dto.report.ReportArray;
import com.ezra.programandojuntos.enums.TipoPedidoEnum;
import com.ezra.programandojuntos.errors.PedidoMapErrors;
import com.ezra.programandojuntos.exceptions.PedidoExceptions;
import com.ezra.programandojuntos.models.dao.IClienteDao;
import com.ezra.programandojuntos.models.dao.IPedidoDao;
import com.ezra.programandojuntos.models.dao.IProductoDao;
import com.ezra.programandojuntos.models.entity.Cliente;
import com.ezra.programandojuntos.models.entity.EstadoPedido;
import com.ezra.programandojuntos.models.entity.ItemPedido;
import com.ezra.programandojuntos.models.entity.Movimiento;
import com.ezra.programandojuntos.models.entity.Pedido;
import com.ezra.programandojuntos.models.entity.Producto;
import com.ezra.programandojuntos.models.entity.TipoPedido;
import com.ezra.programandojuntos.models.repository.PedidoRepository;
import com.ezra.programandojuntos.util.ExcelUtil;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Service
public class PedidoServiceImpl implements IPedidoService {

	Logger log = LoggerFactory.getLogger(PedidoServiceImpl.class);

	public static final Long PEDIDO_PENDIENTE = 1L;
	public static final Long PEDIDO_VENCIDO = 2L;
	public static final Long PEDIDO_DEVUELTO = 3L;
	public static final Long PEDIDO_CANCELADO = 4L;
	public static final Long PEDIDO_ENTREGADO = 5L;
	public static final Long PEDIDO_ADQUIRIDO = 6L;

//	public static final Long TIPO_PEDIDO_VENTA = 1L;
//	public static final Long TIPO_PEDIDO_COMPRA = 2L;
	public static final BigDecimal IGV = new BigDecimal(0.18);

	@Autowired
	private IClienteDao clienteDao;
	
	@Autowired
	private IClienteService clienteService;

	@Autowired
	private IPedidoDao pedidoDao;

	@Autowired
	private IProductoDao productoDao;
	
	@Autowired
	private ProductoService productoService;

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

		log.info("findAllPedidoPageable pageable= {}", pageable);

		return pedidoDao.findAllPedidoPageable(query, tipoPedidoId, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Pedido> findPedidoClientePageable(String query, Long clienteId, Pageable pageable) {

		log.info("findAllPedidoPageable pageable= {}", pageable);

		return pedidoDao.findPedidoClientePageable(query, clienteId, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Pedido findPedidoById(Long id) {
		return pedidoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Pedido registrarPedido(Pedido pedido) throws PedidoExceptions {
		clienteService.actualizar(pedido.getCliente(), pedido.getCliente().getId());	

		TipoPedido tipoPedido = pedidoDao.findTipoPedidoById(pedido.getTipoPedido().getId());
		
		pedido.setPagado(false);
		pedido.setPrecioBrutoTotal(new BigDecimal(0));
		pedido.setPagoTotal(new BigDecimal(0));
		pedido.setFlujoEfectivoTotal(new BigDecimal(0));
		pedido.setEstadoPedido(pedidoDao.findAllEstadoPedido().stream().filter(e -> e.getId() == PEDIDO_PENDIENTE)
				.collect(Collectors.toList()).get(0));
		if (tipoPedido.getId() == TipoPedidoEnum.VENTA.getValue()) {
			validarPedidoTipoVenta(pedido);
			pedido.setPrecioNetoTotal(pendienteImporte(pedido.getItems(), new BigDecimal(0)));
			pedido.setPrecioBrutoTotal(
					pedido.getPrecioNetoTotal().divide(IGV.add(new BigDecimal(1)), 2, RoundingMode.HALF_UP));
			pedido.setSaldoPedido(pedido.getPrecioNetoTotal());

		} else if (tipoPedido.getId() == TipoPedidoEnum.COMPRA.getValue()) {
			validarPedidoTipoCompra(pedido);
			pedido.setCostoNetoTotal(pendienteImporte(pedido.getItems(), new BigDecimal(0)));
			pedido.setCostoBrutoTotal(
					pedido.getCostoNetoTotal().divide(IGV.add(new BigDecimal(1)), 2, RoundingMode.HALF_UP));
			// pedido.setSaldoBrutoPedido(pedido.getCostoBrutoTotal());
			pedido.setSaldoPedido(pedido.getCostoNetoTotal());
		}

		if ((pedido.getEntregadoEn() != null && pedido.getEntregadoEn().before(new Date()))
				|| (pedido.getAdquiridoEn() != null && pedido.getAdquiridoEn().before(new Date()))) {
			pedido.setEstadoPedido(pedidoDao.findAllEstadoPedido().stream()
					.filter(estado -> estado.getId() == PEDIDO_VENCIDO).collect(Collectors.toList()).get(0));
		}

		pedido.setTipoPedido(tipoPedido);

		if (pedido.getSaldoPedido().intValue() < 1) {
			pedido.setPagado(true);
		}

		return pedidoDao.save(pedido);
	}

	private void validarPedidoTipoCompra(Pedido pedido) {
		if (pedido.getAdquiridoEn() == null) {
			throw new PedidoExceptions(PedidoMapErrors.getErrorString(PedidoMapErrors.CODE_FECHA_ADQUISICION));
		}
		
		if (pedido.getAdquiridoEn().after(new Date())) {
			throw new PedidoExceptions(PedidoMapErrors.getErrorString(PedidoMapErrors.CODE_FECHA_ADQUISICION_FUTURA));
		}
	}

	private void validarPedidoTipoVenta(Pedido pedido) {
		if (pedido.getEntregadoEn() == null) {
			throw new PedidoExceptions(PedidoMapErrors.getErrorString(PedidoMapErrors.CODE_FECHA_VENTA));
		}
		
		if (pedido.getEntregadoEn().before(new Date())) {
			throw new PedidoExceptions(PedidoMapErrors.getErrorString(PedidoMapErrors.CODE_FECHA_VENTA_PASADA));
		}

		pedido.getItems().forEach(item -> {
			if (item.getCantidad() > item.getProducto().getCantidadStock()) {
				throw new PedidoExceptions(PedidoMapErrors.getErrorString(PedidoMapErrors.CODE_STOCK_INSUFICIENTE,
						item.getCantidad(), item.getProducto().getNombre()));
			}

			if (item.getProducto().getMargenGanancia().intValue() == 0) {
				throw new PedidoExceptions(PedidoMapErrors.getErrorString(PedidoMapErrors.CODE_SIN_MARGEN_GANANCIA,
						item.getProducto().getNombre()));
			}

			if (item.getProducto().getImpuestoIgv().intValue() == 0) {
				throw new PedidoExceptions(PedidoMapErrors.getErrorString(PedidoMapErrors.CODE_SIN_IGV,
						item.getProducto().getNombre()));
			}
		});
	}
	
	
	

	@Override
	@Transactional
	public Pedido updatePedido(Pedido pedido, Long id) {
		Pedido pedidoActual = findPedidoById(id);
		List<EstadoPedido> lstEstadosPedido = findAllEstadoPedido();
		if (pedidoActual != null) {
			pedidoActual.setPagado(false);
			// pedidoActual.setDevuelto(pedido.isDevuelto());
			if ((pedidoActual.getEntregadoEn() != null && pedidoActual.getEntregadoEn().before(new Date()))
					|| (pedidoActual.getAdquiridoEn() != null && pedidoActual.getAdquiridoEn().before(new Date()))) {
				pedidoActual.setEstadoPedido(pedidoDao.findAllEstadoPedido().stream()
						.filter(estado -> estado.getId() == PEDIDO_VENCIDO).collect(Collectors.toList()).get(0));

			}
			Map<String, BigDecimal> movimientosPedido = null;
			movimientosPedido = movimientoPorPedido(id);
			pedidoActual.setFlujoEfectivoTotal(movimientosPedido.get("flujoEfectivo"));
			pedidoActual
					.setSaldoPedido(pendienteImporte(pedidoActual.getItems(), movimientosPedido.get("flujoEfectivo")));

			if (pedidoActual.getTipoPedido().getId() == TipoPedidoEnum.VENTA.getValue()) {
				validarPedidoTipoVenta(pedido);
				clienteService.actualizar(pedido.getCliente(), pedido.getCliente().getId());	
				pedidoActual.setPagoTotal(movimientosPedido.get("ingreso"));
				pedidoActual.setVueltoTotal(movimientosPedido.get("egreso"));
				if (pedidoActual.getSaldoPedido().intValue() < 1 && pedidoActual.getPagoTotal().intValue() > 0) {
					pedidoActual.setEstadoPedido(lstEstadosPedido.stream().filter(e -> e.getId() == PEDIDO_ENTREGADO)
							.collect(Collectors.toList()).get(0));
				}
				pedidoActual.getItems().forEach(item -> {
					Producto producto = productoDao.findById(item.getProducto().getId()).orElseThrow(null);
					producto = productoService.actualizarExistenciasEstado(producto, item, TipoPedidoEnum.VENTA.getValue());
					producto.setFchUltRealVenta(new Date());
					productoDao.save(producto);
				});
			} else if (pedidoActual.getTipoPedido().getId() == TipoPedidoEnum.COMPRA.getValue()) {
				validarPedidoTipoCompra(pedido);
				pedidoActual.setPagoTotal(movimientosPedido.get("egreso"));
				pedidoActual.setVueltoTotal(movimientosPedido.get("ingreso"));
				if (pedidoActual.getSaldoPedido().intValue() < 1 && pedidoActual.getPagoTotal().intValue() > 0) {
					pedidoActual.setEstadoPedido(lstEstadosPedido.stream().filter(e -> e.getId() == PEDIDO_ADQUIRIDO)
							.collect(Collectors.toList()).get(0));
				}

				pedidoActual.getItems().forEach(item -> {
					Producto producto = productoDao.findById(item.getProducto().getId()).orElseThrow(null);
					producto = productoService.actualizarExistenciasEstado(producto, item, TipoPedidoEnum.COMPRA.getValue());
					producto.setFchUltRealCompra(new Date());
					producto = productoService.actualizarCostoPrecio(producto, item);
					productoDao.save(producto);
				});

			}

			if (pedidoActual.getSaldoPedido().intValue() > 0 && pedidoActual.getPagoTotal().intValue() < 1) {
				pedidoActual.setEstadoPedido(lstEstadosPedido.stream().filter(e -> e.getId() == PEDIDO_CANCELADO)
						.collect(Collectors.toList()).get(0));
			}

			if (pedidoActual.getSaldoPedido().intValue() < 1 && pedidoActual.getPagoTotal().intValue() > 0) {
				pedidoActual.setPagado(true);
			}

			if (pedido.isDevuelto()) {
				pedidoActual.setDevuelto(pedido.isDevuelto());
				pedidoActual.setEstadoPedido(pedidoDao.findAllEstadoPedido().stream()
						.filter(estado -> estado.getId() == PEDIDO_DEVUELTO).collect(Collectors.toList()).get(0));
			}

		}
		return pedidoDao.save(pedidoActual);
	}
	
	



	@Override
	@Transactional(readOnly = true)
	public Map<String, BigDecimal> movimientoPorPedido(Long pedidoId) {
		Pedido pedido = findPedidoById(pedidoId);
		List<Movimiento> items = pedido.getMovimientos();
//		BigDecimal saldoPedido = new BigDecimal(0);
		BigDecimal ingresoPedido = new BigDecimal(0);
		BigDecimal egresoPedido = new BigDecimal(0);

		for (Movimiento item : items) {
			ingresoPedido = ingresoPedido.add(item.getIngresoDinero()); // +
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

	public BigDecimal pendienteImporte(List<ItemPedido> itemPedido, BigDecimal pago) {
		BigDecimal total = new BigDecimal(0);
		for (ItemPedido item : itemPedido) {
			if (item.getImporte() == null || item.getImporte().floatValue() <= 0) {
				throw new PedidoExceptions(PedidoMapErrors.getErrorString(PedidoMapErrors.CODE_IMPORTE_ITEMS_PEDIDO,
						item.getProducto().getNombre()));
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
	public List<EstadoPedido> findAllEstadoPedido() {
		return pedidoDao.findAllEstadoPedido();
	}

	@Override
	@Transactional(readOnly = true)
	public List<TipoPedido> listarTipoPedidoAll() {
		return pedidoDao.findAllTipoPedido();
	}

	@Override
	@Transactional(readOnly = true)
	public TipoPedido tipoPedidoById(Long id) {
		return pedidoDao.findTipoPedidoById(id);
	}

	@Override
	public ByteArrayInputStream createReportPedidos(Report reporte) {
		String[] cabeceraReporte = null;
		if (reporte.getTipoPedido() == TipoPedidoEnum.VENTA.getValue()) {
			cabeceraReporte = new String[] { "Cliente", "Código pedido", "Fecha creación", "Fecha entrega",
					"Estado pedido", "Descripción estado", "Precio Total Bruto", "Precio Total Neto(IGV)",
					"Saldo pedido", "Pago total", "Vuelto total", "Es pagado", "Tipo pedido", };
		}

		if (reporte.getTipoPedido() == TipoPedidoEnum.COMPRA.getValue()) {
			cabeceraReporte = new String[] { "Proveedor", "Código pedido", "Fecha creación", "Fecha adquisición",
					"Estado pedido", "Descripción estado", "Costo Total Bruto", "Costo Total Neto(IGV)", "Saldo pedido",
					"Pago total", "Vuelto total", "Es pagado", "Tipo pedido", };
		}

		XSSFWorkbook excelbook = new XSSFWorkbook();
		XSSFSheet excelHoja = excelbook.createSheet("data");
		ExcelUtil.generarCabecera(excelbook, excelHoja, cabeceraReporte);

		// DataFormat fmt = excelbook.createDataFormat();
		XSSFCellStyle style = ExcelUtil.crearStyle(excelbook, HorizontalAlignment.CENTER,
				HSSFColorPredefined.GREY_25_PERCENT.getIndex(), (short) 10, HSSFColorPredefined.BLACK.getIndex());
		// style.setDataFormat(fmt.getFormat("@"));
		// dataFormat.GetFormat("M/D/YYYY");

		int numFila = 1;
		List<PedidoReporte> pedidos = pedidoRepository.listarPedidosConFiltros(reporte);
		for (PedidoReporte p : pedidos) {
			short numColumn = 0;
			XSSFRow fila = excelHoja.createRow(numFila);
			ExcelUtil.insertarDataCelda(fila, numColumn++, p.getNomApellRz(), style);
			ExcelUtil.insertarDataCelda(fila, numColumn++, p.getCodigoPedido(), style);
			ExcelUtil.insertarDataCelda(fila, numColumn++, p.getFechaCreacion(), style);
			if (reporte.getTipoPedido() == TipoPedidoEnum.VENTA.getValue()) {
				ExcelUtil.insertarDataCelda(fila, numColumn++, p.getFechaEntrega(), style);
			}
			if (reporte.getTipoPedido() == TipoPedidoEnum.COMPRA.getValue()) {
				ExcelUtil.insertarDataCelda(fila, numColumn++, p.getAdquiridoEn(), style);
			}

			ExcelUtil.insertarDataCelda(fila, numColumn++, p.getEstadoPedido(), style);
			ExcelUtil.insertarDataCelda(fila, numColumn++, p.getDescripcionPedido(), style);

			if (reporte.getTipoPedido() == TipoPedidoEnum.VENTA.getValue()) {
				ExcelUtil.insertarDataCelda(fila, numColumn++, p.getPrecioTotalBruto().doubleValue(), style);
			}
			if (reporte.getTipoPedido() == TipoPedidoEnum.COMPRA.getValue()) {
				ExcelUtil.insertarDataCelda(fila, numColumn++, p.getCostoBrutoTotal().doubleValue(), style);
			}
			if (reporte.getTipoPedido() == TipoPedidoEnum.COMPRA.getValue()) {
				ExcelUtil.insertarDataCelda(fila, numColumn++, p.getPrecioTotalNeto().doubleValue(), style);
			}
			if (reporte.getTipoPedido() == TipoPedidoEnum.COMPRA.getValue()) {
				ExcelUtil.insertarDataCelda(fila, numColumn++, p.getCostoNetoTotal().doubleValue(), style);
			}

			ExcelUtil.insertarDataCelda(fila, numColumn++, p.getSaldoPedido().doubleValue(), style);
			ExcelUtil.insertarDataCelda(fila, numColumn++, p.getPagoTotal().doubleValue(), style);
			ExcelUtil.insertarDataCelda(fila, numColumn++, p.getVueltoTotal().doubleValue(), style);

			ExcelUtil.insertarDataCelda(fila, numColumn++, p.getEsPagado(), style);
			ExcelUtil.insertarDataCelda(fila, numColumn++, p.getTipoPedido(), style);
			numFila++;
		}
		;

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			excelbook.write(out);
		} catch (IOException e) {
			e.getCause();
			// throw new RuntimeException("Error al descargar plantilla baja",e);
		}
		return new ByteArrayInputStream(out.toByteArray());
	}

	@Override
	public ByteArrayInputStream downloadOrderClienteToPdf(Pedido pedido) {
		// 1. Create the PDF document
		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			// 2. Set up the writer to output to the response
			PdfWriter.getInstance(document, out);
			document.setPageSize(PageSize.A4);
			document.open();

			// Step 4: Add content
			Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
			Font fontBody = FontFactory.getFont(FontFactory.HELVETICA, 11);
			Font fontFooter = FontFactory.getFont(FontFactory.HELVETICA, 11);
			Font fontTableHeadItems = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11);


			Paragraph paragraphTitulo = new Paragraph("Pedido del cliente", fontTitle);
			paragraphTitulo.setAlignment(Paragraph.ALIGN_CENTER);
			document.add(paragraphTitulo);
			document.add(new Paragraph("\n")); // Add spacing
			document.add(new Paragraph("Cliente: ".concat(pedido.getCliente().getNomApellRz()), fontBody));
			document.add(new Paragraph("Documento: ".concat(pedido.getCliente().getTipoDocumento().getAcronimo())
					.concat("-").concat(pedido.getCliente().getNumeroDocumento()), fontBody));
			document.add(new Paragraph("Direccion: ".concat(pedido.getCliente().getDireccion()), fontBody));
			document.add(new Paragraph("Celular: ".concat(pedido.getCliente().getCelular()), fontBody));
			//document.add(new Paragraph("Email: ".concat(pedido.getCliente().getEmail()), bodyFont));
			document.add(new Paragraph("\n")); // Add spacing

			PdfPTable tableItems = new PdfPTable(4); // 4 columns
			tableItems.setWidthPercentage(100); // Table takes full width
			tableItems.setWidths( new float[] {30f,40f,10f,20f });

			PdfPCell cell = new PdfPCell();
			cell.setPadding(5);
			cell.setBackgroundColor(Color.GRAY);
			cell.setPhrase(new Phrase("PRODUCTO", fontTableHeadItems));
			tableItems.addCell(cell);
			cell.setPhrase(new Phrase("DESCRIPCION", fontTableHeadItems));
			tableItems.addCell(cell);
			cell.setPhrase(new Phrase("CANT", fontTableHeadItems));
			tableItems.addCell(cell);
			cell.setPhrase(new Phrase("IMPORTE", fontTableHeadItems));
			tableItems.addCell(cell);

			// Inside the try block
			// PdfPTable tBodyTable = new PdfPTable(4); // 4 columns
			cell.setBackgroundColor(Color.WHITE);
			pedido.getItems().forEach(item -> {
				cell.setPhrase(new Phrase(item.getProducto().getCodigo().concat("-").concat(item.getProducto().getNombre()), fontBody));
				tableItems.addCell(cell);
				cell.setPhrase(new Phrase(item.getDescripcion(), fontBody));
				tableItems.addCell(cell);
				cell.setPhrase(new Phrase(item.getCantidad().toString(), fontBody));
				tableItems.addCell(cell);
				cell.setPhrase(new Phrase("S/. ".concat(item.getImporte().toString()), fontBody));
				tableItems.addCell(cell);
			});
			tableItems.setSpacingAfter(5);
			
			PdfPTable tableFooter1 = new PdfPTable(4); // 4 columns
			tableFooter1.setWidthPercentage(100); // Table takes full width
			tableFooter1.setWidths( new float[] {25f,25f,25f,25f});
			cell.setPadding(5);
			cell.setBackgroundColor(Color.GRAY);
			cell.setPhrase(new Phrase("PAGO:", fontTableHeadItems));
			tableFooter1.addCell(cell);
			cell.setBackgroundColor(Color.WHITE);
			cell.setPhrase(new Phrase("S/. ".concat(pedido.getPagoTotal().toString()), fontBody));
			tableFooter1.addCell(cell);
			
			cell.setBackgroundColor(Color.GRAY);
			cell.setPhrase(new Phrase("SALDO:", fontTableHeadItems));
			tableFooter1.addCell(cell);
			cell.setBackgroundColor(Color.WHITE);
			cell.setPhrase(new Phrase("S/. ".concat(pedido.getSaldoPedido().toString()), fontBody));
			tableFooter1.addCell(cell);
			
			PdfPTable tableFooter2 = new PdfPTable(4); // 4 columns
			tableFooter2.setWidthPercentage(100); // Table takes full width

			cell.setBackgroundColor(Color.GRAY);
			cell.setPhrase(new Phrase("VUELTO:", fontTableHeadItems));
			tableFooter2.addCell(cell);
			cell.setBackgroundColor(Color.WHITE);
			cell.setPhrase(new Phrase("S/. ".concat(pedido.getVueltoTotal().toString()), fontBody));
			tableFooter2.addCell(cell);
			

			cell.setBackgroundColor(Color.GRAY);
			cell.setPhrase(new Phrase("TOTAL:", fontTableHeadItems));
			tableFooter2.addCell(cell);
			cell.setBackgroundColor(Color.WHITE);
			cell.setPhrase(new Phrase("S/. ".concat(pedido.getPrecioNetoTotal().toString()), fontBody));
			tableFooter2.addCell(cell);


			// 3. Add content to the PDF
			document.add(tableItems);
			document.add(tableFooter1);
			document.add(tableFooter2);

			
			Paragraph paragraphFooter = new Paragraph("Grafiya RUC: 10466628269 - Calle Pizarro 214 f - Cercado - Arequipa ", fontFooter);
			paragraphFooter.setAlignment(Paragraph.ALIGN_CENTER);
			
			document.add(paragraphFooter);
			document.close();

		} catch (DocumentException e) {
			e.getStackTrace();
		} 
		return new ByteArrayInputStream(out.toByteArray());
	}

}
