package com.ezra.programandojuntos.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezra.programandojuntos.models.dao.ICajaDao;
import com.ezra.programandojuntos.models.dao.ICajaUsuarioDao;
import com.ezra.programandojuntos.models.dao.IClienteDao;
import com.ezra.programandojuntos.models.entity.Caja;
import com.ezra.programandojuntos.models.entity.CajaUsuario;
import com.ezra.programandojuntos.models.entity.Cliente;
import com.ezra.programandojuntos.models.entity.TipoDocumento;

@Service
public class CajaServiceImpl implements ICajaService {

	@Autowired
	private ICajaDao cajaDao;
	
	@Autowired
	private ICajaUsuarioDao cajaUsuarioDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Caja> findAll() {
		return (List<Caja>) cajaDao.findAll();
	}
	
	@Override
	@Transactional
	public CajaUsuario guardar(CajaUsuario cajaUsuario) {
		return cajaUsuarioDao.save(cajaUsuario);
	}
	
	public void persistCajaUsuario ( CajaUsuario cajaUsuario) {
		 cajaUsuarioDao.persistCajaUsuario(cajaUsuario.getFechaApertura(), cajaUsuario.getSaldoCaja(), cajaUsuario.isActiva(), cajaUsuario.getCaja().getId(), cajaUsuario.getUsuario().getId());
	}


	@Override
	@Transactional(readOnly = true)
	public CajaUsuario findUltimaCajaUsuarioByUserName(String userName) {
		return cajaUsuarioDao.findCajaUsuarioByUserName(userName);
	}

	@Override
	@Transactional(readOnly = true)
	public CajaUsuario findCajaUsuarioByUserIdAndCajaId(Long userId, Byte cajaId) {
		return cajaUsuarioDao.findCajaUsuarioByUserIdAndCajaId(userId, cajaId);
	}

	
	@Override
	@Transactional(readOnly = true)
	public CajaUsuario findById(Long id) {
		return cajaUsuarioDao.findById(id).orElse(null);
	}


}
