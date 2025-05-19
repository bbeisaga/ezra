package com.ezra.programandojuntos.models.repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ezra.programandojuntos.constants.DataJdbcContants;
import com.ezra.programandojuntos.dto.CajaUsuarioReporte;
import com.ezra.programandojuntos.dto.PedidoReporte;
import com.ezra.programandojuntos.dto.report.Report;
import com.ezra.programandojuntos.dto.report.ReportArray;
import com.ezra.programandojuntos.models.entity.Pedido;
import com.ezra.programandojuntos.util.DateUtil;

@Repository
public class CajaUsuarioRepositoryImpl extends GenericRepository implements CajaUsuarioRepository {
	

	@Override
	public List<CajaUsuarioReporte> listarCajaUsuarioConFiltros(ReportArray reporte){
		String sql = "SELECT cj.nombre AS nombreCaja, us.apellidos AS apellidoUsuario, us.nombres AS nombreUsuario, "
				+ "cu.fecha_apertura, cu.fecha_actualizacion, cu.fecha_cierre, cu.ingreso_esperado, cu.egreso_esperado, "
				+ "cu.saldo_caja,cu.saldo_por_conteo "
				+ "FROM caja_usuarios cu "
				+ "INNER JOIN cajas cj ON cj.id = cu.caja_id "
				+ "INNER JOIN usuarios us ON us.id = cu.usuario_id WHERE 1=1";
		String filtros = this.armarFiltroSql(reporte.getParameters(), "cu");
		sql +=filtros;
        return jdbc.query(sql, Map.of(), new BeanPropertyRowMapper<> (CajaUsuarioReporte.class));
	}
	
	

}
