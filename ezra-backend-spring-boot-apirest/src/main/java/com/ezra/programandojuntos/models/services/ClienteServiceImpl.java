package com.ezra.programandojuntos.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezra.programandojuntos.models.dao.IClienteDao;
import com.ezra.programandojuntos.models.entity.Cliente;
import com.ezra.programandojuntos.models.entity.TipoDocumento;

@Service
public class ClienteServiceImpl implements IClienteService {

	@Autowired
	private IClienteDao clienteDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findAll() {
		return (List<Cliente>) clienteDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Cliente> findAll(Pageable pageable) {
		return clienteDao.findAll(pageable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Cliente findById(Long id) {
		return clienteDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Cliente save(Cliente cliente) {
		return clienteDao.save(cliente);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		clienteDao.deleteById(id);
	}

//	@Override
//	@Transactional(readOnly = true)
//	public List<Region> findAllRegiones() {
//		return clienteDao.findAllRegiones();
//	}
	
	@Override
	@Transactional(readOnly = true)
	public List<TipoDocumento> findAllTipoDocumento() {
		return clienteDao.findAllDocumentos();
	}

//	@Override
//	@Transactional(readOnly = true)
//	public Pedido findPedidoById(Long id) {
//		return pedidoDao.findById(id).orElse(null);
//	}
//
//	@Override
//	@Transactional
//	public Pedido savePedido(Pedido pedido) {
//		
//		List<ItemPedido> items= pedido.getItems();
//		Double total = 0.00;
//		for (ItemPedido item : items) {
//			total += item.getImporte();
//		}	
//		pedido.setTotal(total);
//		pedido.setSaldo(total - pedido.getAcuenta());
//		return pedidoDao.save(pedido);
//	}
//
//	@Override
//	@Transactional
//	public void deletePedidoById(Long id) {
//		pedidoDao.deleteById(id);
//	}
//
//	@Override
//	@Transactional(readOnly = true)
//	public List<Producto> findProductoByNombre(String term) {
//		return productoDao.findByNombreContainingIgnoreCase(term);
//	}

}
