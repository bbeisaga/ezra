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
import com.ezra.programandojuntos.dto.CajaUsuarioReporte;
import com.ezra.programandojuntos.dto.PedidoReporte;
import com.ezra.programandojuntos.dto.report.Report;
import com.ezra.programandojuntos.dto.report.ReportArray;
import com.ezra.programandojuntos.models.entity.Pedido;
import com.ezra.programandojuntos.util.DateUtil;

@Repository
public class CajaUsuarioRepositoryImpl implements CajaUsuarioRepository {
	
	@Qualifier(DataJdbcContants.JDBC_TEMPLATE_MYSQL)
	@Autowired
	NamedParameterJdbcTemplate jdbc;
		
	public String armarFiltroSql(Map<String, List<String>> parametros, String aliasTablaPrincipal) {
		 String filtroSql = ""; //local variable no defined inclosed escope
	      if (parametros == null) { 
	    	  return filtroSql;
	      }
	      
	      for (Map.Entry<String, List<String>> p : parametros.entrySet()) {
	           if (p.getValue()!=null && p.getValue().size() > 0) {
	        	   
	        	   //var valueReal = p.getValue();
	        	   boolean isDate = DateUtil.isDate(p.getValue().get(0), DateUtil.FORMATO_YYYY_MM_DD);
	        	   char ultimo	= p.getKey().charAt(p.getKey().length()-1);
	        	   boolean isDigit =  Character.isDigit(ultimo);//1->verdadero Si hay numero al final de la cadena
	        	   
	        	   String newKey = null;
	        	   if(isDigit) {
	        		   newKey = p.getKey().replaceAll("[0-9]", "");
	        	   }
	        	     
	        	   if(isDate  && ultimo =='1') {//Si hay numero al final de la cadena
		            	filtroSql += " and " + aliasTablaPrincipal+"."+newKey + ">='" + p.getValue().get(0) +"'";
	        	   }else 
	        		if(isDate && ultimo =='2') {
		            	filtroSql += " and " + aliasTablaPrincipal+"."+newKey + "<='" + p.getValue().get(0) +"'";
	        	   }else 
	        		if(ultimo =='1') {
		            	filtroSql += " and " + aliasTablaPrincipal+"."+newKey + ">=" + p.getValue().get(0);
	        	   }else 
	        		if(ultimo =='2') {
		            	filtroSql += " and " + aliasTablaPrincipal+"."+newKey + "<=" + p.getValue().get(0);
	        	   } else 
	        	   	if(p.getValue().size() > 1){
		            	filtroSql += " and " + aliasTablaPrincipal+"."+p.getKey() + " IN (" + String.join(",", p.getValue()) +")";
	        	   } 
	        		else {
		            	filtroSql += " and " + aliasTablaPrincipal+"."+p.getKey() + "=" + p.getValue().get(0);
	        	   }
	            }
	      }
	        
		return filtroSql;
	}
	
	@Override
	public List<CajaUsuarioReporte> listarCajaUsuarioConFiltros(ReportArray reporte){
		String sql = "SELECT cj.nombre AS nombreCaja, us.apellido AS apellidoUsuario, us.nombre AS nombreUsuario, "
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
