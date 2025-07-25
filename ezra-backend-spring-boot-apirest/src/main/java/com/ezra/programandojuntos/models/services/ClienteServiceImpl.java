package com.ezra.programandojuntos.models.services;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezra.programandojuntos.dto.EmailDto;
import com.ezra.programandojuntos.dto.ParametrosPageable;
import com.ezra.programandojuntos.errors.ClienteMapErrors;
import com.ezra.programandojuntos.exceptions.ClienteExceptions;
import com.ezra.programandojuntos.exceptions.PedidoExceptions;
import com.ezra.programandojuntos.models.dao.IClienteDao;
import com.ezra.programandojuntos.models.dao.IRoleDao;
import com.ezra.programandojuntos.models.dao.IUsuarioDao;
import com.ezra.programandojuntos.models.entity.Cliente;
import com.ezra.programandojuntos.models.entity.Producto;
import com.ezra.programandojuntos.models.entity.Role;
import com.ezra.programandojuntos.models.entity.TipoDocumento;
import com.ezra.programandojuntos.models.entity.Usuario;

@Service
public class ClienteServiceImpl implements IClienteService {

	Logger log = LoggerFactory.getLogger(ClienteServiceImpl.class);

	@Autowired
	private IClienteDao clienteDao;

	@Autowired
	private IUsuarioDao usuarioDao;

	@Autowired
	private IRoleDao roleDao;
	
	@Autowired
	private IEmailService emailService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

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
	public List<Cliente> findClienteByNomApellRz(String term) {
		return clienteDao.findByNomApellRzContainingIgnoreCase(term);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Cliente> findAllClientePageable(String query, Pageable pageRequest) {

		log.info("findAllClientePageable pageRequest= {}", pageRequest);

		return clienteDao.findAllClientePageable(query, pageRequest);
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente findById(Long id) {
		return clienteDao.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente findByNumeroDocumento(String numero) {
		return clienteDao.findByNumeroDocumento(numero).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente findByNumeroCelular(String celular) {
		return clienteDao.findByCelular(celular).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public Usuario findUsuarioByUsername(String username) {
		return clienteDao.findUsuarioByUsername(username);
	}
	

	@Override
	public Cliente findClienteByUsuarioId(Long usuarioId) {
		return clienteDao.findByUsuarioId(usuarioId).orElse(null);
	}

	@Override
	@Transactional
	public Cliente guardarCliente(Cliente cliente) {
		
		Cliente clienteSave = null;
		 var clienteActual = clienteDao.findByNumeroDocumento(cliente.getNumeroDocumento()).orElse(null);

		if ((clienteActual != null && clienteActual.getUsuarioId() != null)) {
			//ya esta registrado en el sistema olvido su contraseña
			throw new ClienteExceptions(
					ClienteMapErrors.getErrorString(ClienteMapErrors.CODE_CLI_USR_DUPLICADO, clienteActual.getNomApellRz()));
		}

		if (clienteActual != null && clienteActual.getUsuarioId() == null) {
			clienteSave = this.actualizar(cliente, clienteActual.getId());
		}
		
		if (clienteActual == null ) {
			clienteSave = this.insertar(cliente);
		}
		
		return clienteSave;
	}

	@Override
	@Transactional
	public Cliente insertar(Cliente cliente) {
		cliente.setUsuarioId(null);
		Usuario newUsuario = null;
		if (cliente.getClave() != null) {
			List<Role> rolesDefault = roleDao.findAll().stream().filter(a -> a.getNombre().equals("ROLE_LIST_VENTAS")
					|| a.getNombre().equals("ROLE_REPORT_VENTA") || a.getNombre().equals("ROLE_CREATE_VENTA"))
					.collect(Collectors.toList());

			Usuario usuario = new Usuario();
			usuario.setPassword(passwordEncoder.encode(cliente.getClave()));
			usuario.setNomApellRz(cliente.getNomApellRz());
			usuario.setActivo(true);
			usuario.setBloqueado(false);
			usuario.setReintentos(0);
			usuario.setUsername(cliente.getNumeroDocumento());
			usuario.setRoles(rolesDefault);
			newUsuario = usuarioDao.save(usuario);
			
			sendEmailNuevasCredencialesCliente(cliente);
			cliente.setUsuarioId(newUsuario.getId());
			cliente.setClave(null);
		}
		return clienteDao.save(cliente);
	}

	@Override
	public void sendEmailNuevasCredencialesCliente(Cliente cliente) {
		EmailDto email = new EmailDto();
		email.setEmailDestino(cliente.getEmail());
		email.setAsunto("Bienvenido! Credenciales de acceso a importaciones GRAFIYA");
		email.setSaludo("Hola " + cliente.getNomApellRz());
		email.setMensaje(ClienteMapErrors.getErrorString(ClienteMapErrors.CODE_NEW_CREDENCIALES_ACCESO, cliente.getNumeroDocumento(), cliente.getClave()));
		email.setDespedida("Atentamente.");
		
		this.emailService.sendMailWithTymeleaf(email);
	}
	
	

	@Override
	@Transactional
	public Cliente actualizar(Cliente cliente, Long clienteId) {
		Cliente clienteActual = clienteDao.findById(clienteId).orElseThrow(() -> new ClienteExceptions(
				ClienteMapErrors.getErrorString(ClienteMapErrors.MSJ_NO_CLIENTE_ID, clienteId)));


		clienteActual.setNomApellRz(cliente.getNomApellRz());
		clienteActual.setCelular(cliente.getCelular());
		clienteActual.setTipoDocumento(cliente.getTipoDocumento());
		clienteActual.setNumeroDocumento(cliente.getNumeroDocumento());
		clienteActual.setDireccion(cliente.getDireccion());
		clienteActual.setEmail(cliente.getEmail());
		
		if (cliente.getClave() != null && clienteActual.getUsuarioId()==null) {
			List<Role> rolesDefault = roleDao.findAll().stream().filter(a -> a.getNombre().equals("ROLE_LIST_VENTAS")
					|| a.getNombre().equals("ROLE_REPORT_VENTA") || a.getNombre().equals("ROLE_CREATE_VENTA"))
					.collect(Collectors.toList());

			Usuario usuario = new Usuario();
			usuario.setPassword(passwordEncoder.encode(cliente.getClave()));
			usuario.setNomApellRz(cliente.getNomApellRz());
			usuario.setActivo(true);
			usuario.setBloqueado(false);
			usuario.setReintentos(0);
			usuario.setUsername(cliente.getNumeroDocumento());
			usuario.setRoles(rolesDefault);
			var newUsuario = usuarioDao.save(usuario);
			sendEmailNuevasCredencialesCliente(cliente);

			clienteActual.setUsuarioId(newUsuario.getId());
			clienteActual.setClave(null);
		}

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
