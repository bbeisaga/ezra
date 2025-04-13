package com.ezra.programandojuntos.models.repository;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import com.ezra.programandojuntos.dto.MovimientoEnCajaReporte;
import com.ezra.programandojuntos.dto.report.ReportArray;

@Service
public class MovimientoRepositoryImpl extends GenericRepository implements MovimientoRepository {
	
//	@Qualifier(DataJdbcContants.JDBC_TEMPLATE_MYSQL)
//	@Autowired
//	NamedParameterJdbcTemplate jdbc;

	@Override
	public List<MovimientoEnCajaReporte> listarMovimientoEnCajaConFiltros(ReportArray reporte) {
			
		String sql = "SELECT caja, usuario, cod_pedido as codPedido, cliente_proveedor AS clienteProveedor, tipo_documento AS tipoDocumento, numero_documento AS numDocumento," 
		+"fecha_transaccion AS fechaTransaccion , ingreso, egreso, tipo_movimiento AS tipoMovimiento, movimiento, modulo "
		+"FROM ("
		+"	SELECT cu.caja_id, cj.nombre AS caja, cu.usuario_id, CONCAT(us.apellido,' ',  us.nombre) AS usuario, pd.id AS cod_pedido, pd.cliente_id, cl.nom_apell_rz AS cliente_proveedor, td.acronimo AS tipo_documento," 
		+"	cl.numero_documento, mv.create_at AS fecha_transaccion,"
		+"	ingreso_dinero AS ingreso, egreso_dinero AS egreso, (CASE  WHEN  tm.tipo = 'I' THEN 'Ingreso' WHEN tm.tipo= 'E' THEN 'Egreso' ELSE '' END) AS tipo_movimiento, tm.movimiento AS movimiento, 'Pedidos' AS modulo "
		+"	FROM movimientos mv "
		+"	INNER JOIN caja_usuarios cu ON  cu.id = mv.caja_usuario_id "
		+"	INNER JOIN cajas cj ON cj.id = cu.caja_id "
		+"  INNER JOIN usuarios us ON us.id = cu.usuario_id "
		+"	INNER JOIN pedidos pd ON pd.id = mv.pedido_id "
		+"	INNER JOIN clientes cl ON cl.id = pd.cliente_id "
		+"	INNER JOIN tipo_documentos td ON td.id = cl.tipo_documentos_id "
		+"	INNER JOIN tipo_movimientos_pedido tm ON tm.id = mv.tipo_movimiento_pedido_id "
		+"	UNION all "
		+"	SELECT cu.caja_id, cj.nombre, cu.usuario_id,CONCAT(us.apellido,' ',  us.nombre) AS usuario, 0,0, '','','',mc.create_at, ingreso_dinero, egreso_dinero, CASE  WHEN  tm.tipo = 'I' THEN 'Ingreso' WHEN tm.tipo= 'E' THEN 'Egreso' ELSE '' END, tm.movimiento, 'Caja' AS modulo "
		+"	FROM movimientos_caja mc "
		+"	INNER JOIN caja_usuarios cu ON  cu.id = mc.caja_usuario_id "
		+"	INNER JOIN cajas cj ON cj.id = cu.caja_id "
		+"  INNER JOIN usuarios us ON us.id = cu.usuario_id "
		+"	INNER JOIN tipo_movimientos_caja tm ON tm.id = mc.tipo_movimiento_caja_id) movimientos_all "
		+"WHERE 1 = 1";
		
		String filtros = this.armarFiltroSql(reporte.getParameters(), "movimientos_all");
		sql +=filtros;
        return jdbc.query(sql, Map.of(), new BeanPropertyRowMapper<> (MovimientoEnCajaReporte.class));	}

}
