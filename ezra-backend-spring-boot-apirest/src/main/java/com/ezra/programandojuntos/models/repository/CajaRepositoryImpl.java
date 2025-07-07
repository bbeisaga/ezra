package com.ezra.programandojuntos.models.repository;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.ezra.programandojuntos.dto.CajaDto;

@Repository
public class CajaRepositoryImpl extends GenericRepository implements CajaRepository {
	

	@Override
	public List<CajaDto> listarCajasPorAsignar(){
		String sql = "SELECT * FROM (SELECT id AS id, nombre AS nombre, ubicacion AS ubicacion FROM cajas "
				+ "EXCEPT SELECT cu.caja_id AS id, cj.nombre AS nombre, cj.ubicacion AS ubicacion  "
				+ "FROM caja_usuarios cu INNER JOIN cajas cj ON cu.caja_id = cj.id WHERE activa = 1) cajas_por_asignar";
        return jdbc.query(sql, Map.of(), new BeanPropertyRowMapper<> (CajaDto.class));
	}
	
	

}
