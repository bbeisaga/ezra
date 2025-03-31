package com.ezra.programandojuntos.models.dao;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.ezra.programandojuntos.models.entity.CajaUsuario;

public interface ICajaUsuarioDao extends CrudRepository<CajaUsuario, Long>{
	
	@Query (value = "SELECT cu.* FROM caja_usuarios cu INNER JOIN usuarios u ON cu.usuario_id = u.id WHERE u.username = ?1 ORDER BY cu.fecha_apertura DESC LIMIT 1" ,nativeQuery = true)
	CajaUsuario findCajaUsuarioByUserName(String userName);

	
	@Query (value = "SELECT cu.* FROM caja_usuarios cu INNER JOIN usuarios u ON cu.usuario_id = u.id WHERE cu.usuario_id = ?1 AND cu.caja_id = ?2 ORDER BY cu.fecha_apertura DESC LIMIT 1" ,nativeQuery = true)
	CajaUsuario findCajaUsuarioByUserIdAndCajaId(Long userId, Byte cajaId);
	
	@Query (value = "SELECT cu.* FROM caja_usuarios cu INNER JOIN usuarios u ON cu.usuario_id = u.id WHERE u.username = ?1 AND cu.caja_id = ?2 ORDER BY cu.fecha_apertura DESC LIMIT 1" ,nativeQuery = true)
	CajaUsuario findCajaUsuarioByUsernameAndCajaId(String username, Byte cajaId);
	
	@Transactional
	@Modifying
	@Query (value = "INSERT INTO caja_usuarios (fecha_apertura, saldo_caja, saldo_por_conteo, ingreso_esperado, egreso_esperado, activa, caja_id, usuario_id) VALUES (?1, ?2, 0, 0, 0, ?3, ?4, ?5);" ,nativeQuery = true)
	void persistCajaUsuario(Date fechaApertura,  BigDecimal saldoCaja, BigDecimal saldoPorConteo, boolean activa, Byte cajaId, Long usuarioId );
}
