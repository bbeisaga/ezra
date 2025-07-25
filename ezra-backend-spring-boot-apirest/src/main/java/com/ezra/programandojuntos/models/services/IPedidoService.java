package com.ezra.programandojuntos.models.services;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ezra.programandojuntos.dto.report.Report;
import com.ezra.programandojuntos.dto.report.ReportArray;
import com.ezra.programandojuntos.models.entity.Cliente;
import com.ezra.programandojuntos.models.entity.EstadoPedido;
import com.ezra.programandojuntos.models.entity.Pedido;
import com.ezra.programandojuntos.models.entity.Producto;
import com.ezra.programandojuntos.models.entity.TipoPedido;

public interface IPedidoService {
	
	public static final Long TIPO_PEDIDO_VENTA = 1L;
	public static final Long TIPO_PEDIDO_COMPRA = 2L;
	
	public List<Pedido> findPedidoAll();
	
	public Page<Pedido> findAllPedidoPageable(String query, Long tipoPedidoId, Pageable pagebale);
	
	public Page<Pedido> findPedidoClientePageable(String query, Long clienteId, Pageable pagebale);

	
	//public Page<Cliente> findAll(Pageable pageable);
	
	//public Cliente findById(Long id);
	
	//public Cliente save(Cliente cliente);
	
	//public void delete(Long id);
	
	//public List<Region> findAllRegiones();
	
	public List<EstadoPedido> findAllEstadoPedido();
	
	public List<TipoPedido> listarTipoPedidoAll();
	
	public TipoPedido tipoPedidoById(Long id);
	
	public Pedido findPedidoById(Long id);
	
	public Pedido registrarPedido(Pedido pedido);
	
	public Pedido updatePedido(Pedido pedido, Long id);

	public void deletePedidoById(Long id);
	
	public ByteArrayInputStream createReportPedidos(Report reporte);
	
	public ByteArrayInputStream downloadOrderClienteToPdf(Pedido pedido);
	
	public Map<String, BigDecimal> movimientoPorPedido(Long pedidoId);


}
