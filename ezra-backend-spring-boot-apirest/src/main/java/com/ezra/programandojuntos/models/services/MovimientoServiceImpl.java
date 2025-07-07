package com.ezra.programandojuntos.models.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezra.programandojuntos.dto.CajaUsuarioReporte;
import com.ezra.programandojuntos.dto.MovimientoEnCajaReporte;
import com.ezra.programandojuntos.dto.report.ReportArray;
import com.ezra.programandojuntos.enums.TipoPedidoEnum;
import com.ezra.programandojuntos.errors.MovimientoMapError;
import com.ezra.programandojuntos.exceptions.MovimientoExceptions;
import com.ezra.programandojuntos.models.dao.IMovimientoDao;
import com.ezra.programandojuntos.models.entity.CajaUsuario;
import com.ezra.programandojuntos.models.entity.Movimiento;
import com.ezra.programandojuntos.models.entity.Pedido;
import com.ezra.programandojuntos.models.entity.TipoMovimientoPedido;
import com.ezra.programandojuntos.models.entity.TipoPago;
import com.ezra.programandojuntos.models.repository.MovimientoRepository;
import com.ezra.programandojuntos.util.ExcelUtil;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

@Service
public class MovimientoServiceImpl implements IMovimientoService {

	Logger log = LoggerFactory.getLogger(MovimientoServiceImpl.class);

	@Autowired
	IMovimientoDao movimientoDao;

	@Autowired
	MovimientoRepository movimientoRepository;

	@Autowired
	IPedidoService pedidoService;

	@Autowired
	ICajaUsuarioService cajaUsuarioService;

//	
//	@Autowired
//	ICajaUsuarioDao cajaUsuarioDao;

	@Override
	@Transactional(readOnly = true)
	public List<TipoPago> lstAllTipoPagos() {
		return movimientoDao.findAllTipoPagos();
	}

	@Override
	@Transactional(readOnly = true)
	public List<TipoMovimientoPedido> lstAllTipoMovimientosPedido() {
		return movimientoDao.findAllTipoMovimientosPedido();
	}

	@Override
	@Transactional
	public Movimiento saveMovimiento(Movimiento movimiento) {
		Movimiento newMovimiento = null;
		CajaUsuario cajaUsuario = cajaUsuarioService.findCajaUsuarioByUserIdAndCajaId(
				movimiento.getCajaUsuario().getUsuario().getId(), movimiento.getCajaUsuario().getCaja().getId());

		if (cajaUsuario == null || !cajaUsuario.isActiva()) {
			return null;
		}

		if (movimiento.getTipoMovimientoPedido().getTipo().equals("E")
				&& cajaUsuario.getSaldoCaja().subtract(movimiento.getEgresoDinero()).doubleValue() < 0) {
			throw new MovimientoExceptions(
					MovimientoMapError.getErrorString(MovimientoMapError.CODE_SALDO_INSUFICIENTE_CJU));
		}

		valdiarMovimientoPedido(movimiento);

		Pedido pedido = pedidoService.findPedidoById(movimiento.getPedido().getId());
		log.info("MovimientoServiceImpl.saveMovimiento... estadoPedido={}", pedido.getEstadoPedido().getEstado());

		// BigDecimal newSaldoBruto = BigDecimal.valueOf(0);
		BigDecimal newSaldo = BigDecimal.valueOf(0);
		if (movimiento.getPedido().getTipoPedido().getId() == TipoPedidoEnum.VENTA.getValue()) {
			if (movimiento.getTipoMovimientoPedido().getTipo().equals("I")) {
				newSaldo = pedido.getSaldoPedido().subtract(movimiento.getIngresoDinero());
				if (newSaldo.intValue() >= 0) {
					movimiento.setEgresoDinero(BigDecimal.valueOf(0));
				}
				if (newSaldo.intValue() < 0) {
					movimiento.setEgresoDinero(newSaldo.abs());// - con abs cambiamos a +
				}
			}
			if (movimiento.getTipoMovimientoPedido().getTipo().equals("E")) {
				newSaldo = pedido.getPagoTotal().subtract(movimiento.getEgresoDinero());
				if (newSaldo.intValue() >= 0) {
					movimiento.setIngresoDinero(BigDecimal.valueOf(0));
				}
				if (newSaldo.intValue() < 0) {
					movimiento.setIngresoDinero(newSaldo.abs());// - con abs cambiamos a +
				}
				pedido.setDevuelto(true);
			}
		}
		if (movimiento.getPedido().getTipoPedido().getId() == TipoPedidoEnum.COMPRA.getValue()) {
			if (movimiento.getTipoMovimientoPedido().getTipo().equals("I")) {
				newSaldo = pedido.getPagoTotal().subtract(movimiento.getIngresoDinero());
				if (newSaldo.intValue() >= 0) {
					movimiento.setEgresoDinero(BigDecimal.valueOf(0));
				}
				if (newSaldo.intValue() < 0) {
					movimiento.setEgresoDinero(newSaldo.abs());
				}
				pedido.setDevuelto(true);
			}
			if (movimiento.getTipoMovimientoPedido().getTipo().equals("E")) {
				newSaldo = pedido.getSaldoPedido().subtract(movimiento.getEgresoDinero());
				if (newSaldo.intValue() >= 0) {
					movimiento.setIngresoDinero(BigDecimal.valueOf(0));
				}
				if (newSaldo.intValue() < 0) {
					movimiento.setIngresoDinero(newSaldo.abs());
				}
			}
		}
		movimiento.setCajaUsuario(cajaUsuario);
		movimiento.setPedido(pedido);
		newMovimiento = movimientoDao.save(movimiento);
		newMovimiento.setPedido(pedidoService.updatePedido(newMovimiento.getPedido(), movimiento.getPedido().getId()));
		newMovimiento.setCajaUsuario(cajaUsuarioService.updateCajaUsuario(newMovimiento.getCajaUsuario(),
				movimiento.getCajaUsuario().getId()));
		return newMovimiento;
	}

	private void valdiarMovimientoPedido(Movimiento movimiento) {
		if (movimiento.getTipoPago() == null) {
			throw new MovimientoExceptions(
					MovimientoMapError.getErrorString(MovimientoMapError.CODE_SELECCIONAR_TIPO_PAGO));
		}

		if (movimiento.getPedido().getTipoPedido().getId() == IPedidoService.TIPO_PEDIDO_VENTA) {
			if (movimiento.getTipoMovimientoPedido().getTipo().equals("I")) {
				if (movimiento.getIngresoDinero().intValue() < 0) {
					throw new MovimientoExceptions(
							MovimientoMapError.getErrorString(MovimientoMapError.CODE_PAGO_DEL_CLIENTE));
				}
			}
			if (movimiento.getTipoMovimientoPedido().getTipo().equals("E")) {
				if (movimiento.getPedido().getFlujoEfectivoTotal().intValue() < 1) {
					throw new MovimientoExceptions(
							MovimientoMapError.getErrorString(MovimientoMapError.CODE_DEVOL_DINERO_CLIENTE_SIN_PAGO));
				}
				if (movimiento.getEgresoDinero().intValue() < 0) {
					throw new MovimientoExceptions(
							MovimientoMapError.getErrorString(MovimientoMapError.CODE_DEVOL_DINERO_CLIENTE));
				}
			}
		}
		if (movimiento.getPedido().getTipoPedido().getId() == IPedidoService.TIPO_PEDIDO_COMPRA) {
			if (movimiento.getTipoMovimientoPedido().getTipo().equals("E")) {
				if (movimiento.getPedido().getSaldoPedido().intValue() < 1) {
					throw new MovimientoExceptions(
							MovimientoMapError.getErrorString(MovimientoMapError.CODE_COMPRA_SIN_SALDO));
				}
				if (movimiento.getEgresoDinero().intValue() < 0) {
					throw new MovimientoExceptions(
							MovimientoMapError.getErrorString(MovimientoMapError.CODE_COMPRA_AL_PROVEEDOR));
				}
			}
			if (movimiento.getTipoMovimientoPedido().getTipo().equals("I")) {
				if (movimiento.getPedido().getFlujoEfectivoTotal().intValue() < 1) {
					throw new MovimientoExceptions(
							MovimientoMapError.getErrorString(MovimientoMapError.CODE_DEVOL_COMPRA_PROVEEDOR_SIN_PAGO));
				}
				if (movimiento.getIngresoDinero().intValue() < 0) {
					throw new MovimientoExceptions(
							MovimientoMapError.getErrorString(MovimientoMapError.CODE_DEVOL_COMPRA));
				}
			}
		}
	};

	public ByteArrayInputStream createReportMovimientoEnCaja(ReportArray reporte) {
		String[] cabeceraReporte = null;

		cabeceraReporte = new String[] { "Caja", "Usuario", "Cod. Pedido", "Cliente o proveedor", "Tipo documento",
				"Número documento", "Fecha transacción", "Ingresos S/.", "Egresos S/.", "Tipo movimiento", "movimiento",
				"modulo" };

		XSSFWorkbook excelbook = new XSSFWorkbook();
		XSSFSheet excelHoja = excelbook.createSheet("data");
		ExcelUtil.generarCabecera(excelbook, excelHoja, cabeceraReporte);

		XSSFCellStyle style = ExcelUtil.crearStyle(excelbook, HorizontalAlignment.CENTER,
				HSSFColorPredefined.GREY_25_PERCENT.getIndex(), (short) 10, HSSFColorPredefined.BLACK.getIndex());

		int numFila = 1;
		List<MovimientoEnCajaReporte> mvs = movimientoRepository.listarMovimientoEnCajaConFiltros(reporte);

		for (MovimientoEnCajaReporte p : mvs) {
			short numColumn = 0;
			XSSFRow fila = excelHoja.createRow(numFila);
			ExcelUtil.insertarDataCelda(fila, numColumn++, p.getCaja(), style);
			ExcelUtil.insertarDataCelda(fila, numColumn++, p.getUsuario(), style);
			ExcelUtil.insertarDataCelda(fila, numColumn++, p.getCodPedido(), style);
			ExcelUtil.insertarDataCelda(fila, numColumn++, p.getClienteProveedor(), style);
			ExcelUtil.insertarDataCelda(fila, numColumn++, p.getTipoDocumento(), style);
			ExcelUtil.insertarDataCelda(fila, numColumn++, p.getNumDocumento(), style);
			ExcelUtil.insertarDataCelda(fila, numColumn++, p.getFechaTransaccion(), style);
			ExcelUtil.insertarDataCelda(fila, numColumn++, p.getIngreso().doubleValue(), style);
			ExcelUtil.insertarDataCelda(fila, numColumn++, p.getEgreso().doubleValue(), style);
			ExcelUtil.insertarDataCelda(fila, numColumn++, p.getTipoMovimiento(), style);
			ExcelUtil.insertarDataCelda(fila, numColumn++, p.getMovimiento(), style);
			ExcelUtil.insertarDataCelda(fila, numColumn++, p.getModulo(), style);
			numFila++;
		}
		;

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			excelbook.write(out);
		} catch (IOException e) {
			e.getCause();
		}
		return new ByteArrayInputStream(out.toByteArray());
	}



}
