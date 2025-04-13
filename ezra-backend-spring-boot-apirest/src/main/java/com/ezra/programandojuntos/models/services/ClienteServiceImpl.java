package com.ezra.programandojuntos.models.services;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezra.programandojuntos.dto.ParametrosPageable;
import com.ezra.programandojuntos.errors.ClienteMapErrors;
import com.ezra.programandojuntos.exceptions.ClienteExceptions;
import com.ezra.programandojuntos.exceptions.PedidoExceptions;
import com.ezra.programandojuntos.models.dao.IClienteDao;
import com.ezra.programandojuntos.models.entity.Cliente;
import com.ezra.programandojuntos.models.entity.Producto;
import com.ezra.programandojuntos.models.entity.TipoDocumento;

@Service
public class ClienteServiceImpl implements IClienteService {
	
	Logger log = LoggerFactory.getLogger(ClienteServiceImpl.class);

	@Autowired
	private IClienteDao clienteDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findAll() {
		return (List<Cliente>) clienteDao.findAll();
	}
	
	
//	
//    @Override
//    public PageableEntity<UserSecurityEntity> findByProfilePageable(
//            PagingParameters<UserSecurityPageableActiveEnum> pagingParameters, List<Integer> authorizedProfiles) {
//        log.debug("UserSecurityRepositoryImpl.findByProfilePageable(pagingParameters: {}) ", pagingParameters);
//        Pageable pageRequest = PageRequest.of(pagingParameters.getPageNumber(), pagingParameters.getPageSize(),
//                Sort.by(directionSIAMMapper.asDirection(pagingParameters.getDirection()),
//                        pagingParameters.getActive().getValue()));
//        Page<UserSecuritySIAMEntity> userSecuritySIAMEntity = userSecuritySIAMRepository
//                .findByProfileAndPageable(pagingParameters.getQuery(), authorizedProfiles, pageRequest);
//
//        return MapperPageableUtil.asPageToPageableEntity(userSecuritySIAMEntity,
//                userSecuritySIAMMapper.asUserSecurityEntityByUsers(userSecuritySIAMEntity.getContent()));
//    }
//    
//    public Pageable<Cliente> cualqueira() {
//    	
//        Pageable pageRequest = PageRequest.of(pagingParameters.getPageNumber(), pagingParameters.getPageSize(),
//                Sort.by(directionSIAMMapper.asDirection(pagingParameters.getDirection()),
//                        pagingParameters.getActive().getValue()));
//    	
//    	clienteDao.findAllClientePageable(null, null)
//    }
	
	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findClienteByNomApellRz (String term) {
		return clienteDao.findByNomApellRzContainingIgnoreCase(term);
	}
	

	@Override
	@Transactional(readOnly = true)
	public Page<Cliente> findAllClientePageable(String query, Pageable pageRequest) {
		
		log.info("findAllClientePageable pageRequest= {}",pageRequest);
	
		return clienteDao.findAllClientePageable(query, pageRequest);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Cliente findById(Long id) {
		return clienteDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Cliente insertar(Cliente cliente) {
		Cliente clienteActual = null;
		clienteActual = clienteDao.findByCelular(cliente.getCelular()).orElse(null);
			
		if (clienteActual != null) {
			throw new ClienteExceptions(
					ClienteMapErrors.getErrorString(ClienteMapErrors.CODE_CEL_DUPLICADO, clienteActual.getCelular())
			);
		}
		
		clienteActual = clienteDao.findByNumeroDocumento(cliente.getNumeroDocumento()).orElse(null);

		if (clienteActual != null) {
			throw new ClienteExceptions(
					ClienteMapErrors.getErrorString(ClienteMapErrors.CODE_NUM_DOC_DUPLICADO, clienteActual.getNumeroDocumento())
			);
		}
		return clienteDao.save(cliente);
	}
	
	@Override
	@Transactional
	public Cliente actualizar(Cliente cliente, Long clienteId) {
		
		Cliente clienteActual = clienteDao.findById(clienteId)
				.orElseThrow( ()-> new ClienteExceptions(
						ClienteMapErrors.getErrorString(ClienteMapErrors.MSJ_NO_CLIENTE_ID, clienteId)
				));
		
//		clienteActual.setApellidos(cliente.getApellidos());
//		clienteActual.setNombres(cliente.getNombres());
		clienteActual.setNomApellRz(cliente.getNomApellRz());
		clienteActual.setCelular(cliente.getCelular());
		clienteActual.setTipoDocumento(cliente.getTipoDocumento());
		clienteActual.setNumeroDocumento(cliente.getNumeroDocumento());
		
		return clienteDao.save(clienteActual);
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
