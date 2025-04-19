package com.ezra.programandojuntos.models.services;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezra.programandojuntos.errors.ClienteMapErrors;
import com.ezra.programandojuntos.exceptions.ClienteExceptions;
import com.ezra.programandojuntos.models.dao.IUsuarioDao;
import com.ezra.programandojuntos.models.entity.Cliente;
import com.ezra.programandojuntos.models.entity.Role;
import com.ezra.programandojuntos.models.entity.Usuario;

@Service
public class UsuarioService implements IUsuarioService, UserDetailsService{
	
	private Logger logger = LoggerFactory.getLogger(UsuarioService.class);

	@Autowired
	private IUsuarioDao usuarioDao;
	
	@Override
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuario usuario = usuarioDao.findByUsername(username);
		
		if(usuario == null) {
			logger.error("Error en el login: no existe el usuario '"+username+"' en el sistema!");
			throw new UsernameNotFoundException("Error en el login: no existe el usuario '"+username+"' en el sistema!");
		}
		
		List<GrantedAuthority> authorities = usuario.getRoles()
				.stream()
				.map(role -> new SimpleGrantedAuthority(role.getNombre()))
				.peek(authority -> logger.info("Role: " + authority.getAuthority()))
				.collect(Collectors.toList());
		
		return new User(usuario.getUsername(), usuario.getPassword(), usuario.getActivo(), true, true, true, authorities);
	}

	@Override
	@Transactional(readOnly=true)
	public Usuario findByUsername(String username) {
		return usuarioDao.findByUsername2(username);
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Usuario> getAllUsuarios(){
		return (List<Usuario>) usuarioDao.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Page<Usuario> findAllUsuarioPageable(String query, Pageable pageRequest) {
			
		return usuarioDao.findAllUsuarioPageable(query, pageRequest);
	}

	@Override
	@Transactional
	public Usuario updateRolUsuario(Usuario usuario, Long usuarioId) throws SQLException  {		
	
//		List<Long> idsDelete = usuario.getRoles().stream().filter(r -> !r.isActivated()).map(r -> r.getId()).collect(Collectors.toList());
//		if(!idsDelete.isEmpty()) {
//			deleteRolUsuarioForGroup(usuarioId, idsDelete);
//		}
		Usuario usuarioNew = usuarioDao.findById(usuarioId).orElse(null);
		List<Role> rolesInsert = usuario.getRoles().stream().filter(r -> r.isActivated()).collect(Collectors.toList());
		usuarioNew.setRoles(rolesInsert);
		return usuarioDao.save(usuarioNew);
	}
	
	
	@Override
	@Transactional
	public void deleteRolUsuarioForGroup(Usuario usuario, Long usuarioId) throws SQLException {
		List<Long> idsDelete = usuario.getRoles().stream().filter(r -> !r.isActivated()).map(r -> r.getId()).collect(Collectors.toList());
//		if(!idsDelete.isEmpty()) {
//			deleteRolUsuarioForGroup(usuarioId, idsDelete);
//		}
		
		usuarioDao.deleteRolUsuario(usuarioId, idsDelete);
	}
	

}
