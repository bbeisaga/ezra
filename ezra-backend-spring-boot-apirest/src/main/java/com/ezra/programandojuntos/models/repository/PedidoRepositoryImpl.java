package com.ezra.programandojuntos.models.repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ezra.programandojuntos.constants.DataJdbcContants;
import com.ezra.programandojuntos.dto.PedidoReporte;
import com.ezra.programandojuntos.dto.report.Report;
import com.ezra.programandojuntos.models.entity.Pedido;
import com.ezra.programandojuntos.util.DateUtil;

@Repository
public class PedidoRepositoryImpl implements PedidoRepository {
	
	@Qualifier(DataJdbcContants.JDBC_TEMPLATE_MYSQL)
	@Autowired
	NamedParameterJdbcTemplate jdbc;
		
	public String armarFiltroSql(Map<String, String> parametros, String aliasTablaPrincipal) {
		 String filtroSql = ""; //local variable no defined inclosed escope
	      if (parametros == null) { 
	    	  return filtroSql;
	      }
	      
	      for (Map.Entry<String, String> p : parametros.entrySet()) {
	           if (p.getValue()!=null && !p.getValue().equals("-1")) {
	        	   
	        	   boolean isDate = DateUtil.isDate(p.getValue(), DateUtil.FORMATO_YYYY_MM_DD);
	        	   char ultimo	= p.getKey().charAt(p.getKey().length()-1);
	        	   boolean isDigit =  Character.isDigit(ultimo);//1->verdadero Si hay numero al final de la cadena
	        	   
	        	   String newKey = null;
	        	   if(isDigit) {
	        		   newKey = p.getKey().replaceAll("[0-9]", "");
	        	   }
	        	     
	        	   if(isDate  && ultimo =='1') {//Si hay numero al final de la cadena
		            	filtroSql += " and " + aliasTablaPrincipal+"."+newKey + ">='" + p.getValue() +"'";
	        	   }else 
	        		if(isDate && ultimo =='2') {
		            	filtroSql += " and " + aliasTablaPrincipal+"."+newKey + "<='" + p.getValue() +"'";
	        	   }else 
	        		if(ultimo =='1') {
		            	filtroSql += " and " + aliasTablaPrincipal+"."+newKey + ">=" + p.getValue();
	        	   }else 
	        		if(ultimo =='2') {
		            	filtroSql += " and " + aliasTablaPrincipal+"."+newKey + "<=" + p.getValue();
	        	   } else {
		            	filtroSql += " and " + aliasTablaPrincipal+"."+p.getKey() + "=" + p.getValue();
	        	   }
	            }
	      }
	        
		return filtroSql;
	}
	
	@Override
	public List<PedidoReporte> listarPedidosConFiltros(Report reporte){
		String sql = "SELECT cl.nom_apell_rz AS nomApellRz, pe.id AS codigoPedido,"
				+ "pe.create_at AS fechaCreacion, pe.entregado_en AS fechaEntrega, pe.adquirido_en as adquiridoEn,"
				+ "ep.estado AS estadoPedido,ep.descripcion AS descripcionPedido, pe.costo_bruto_total as costoBrutoTotal,"
				+ "pe.costo_neto_total as costoNetoTotal, pe.precio_bruto_total AS precioTotalBruto,"
				+ "pe.precio_neto_total AS precioTotalNeto, pe.saldo_pedido AS saldoPedido, pe.pago_total AS pagoTotal, pe.vuelto_total as vueltoTotal,"
				+ "(CASE pe.pagado when 0 then 'NO' when 1 then 'SI' END) AS esPagado, tp.nombre AS tipoPedido "
				+ "FROM pedidos pe " //--> alias tabla principal
				+ "INNER JOIN clientes cl ON pe.cliente_id = cl.id "
				+ "INNER JOIN estado_pedido ep ON ep.id = pe.estado_pedido_id "
				+ "INNER JOIN tipo_pedido tp ON tp.id = pe.tipo_pedido_id "
				+ "WHERE pe.tipo_pedido_id = :tipoPedidoId ";
		String filtros = this.armarFiltroSql(reporte.getParameters(), "pe");
		sql +=filtros;
        return jdbc.query(sql, Map.of("tipoPedidoId",reporte.getTipoPedido()), new BeanPropertyRowMapper<> (PedidoReporte.class));
	}
	
	

}
