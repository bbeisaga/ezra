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
import com.ezra.programandojuntos.dto.report.Report;
import com.ezra.programandojuntos.models.entity.Pedido;
import com.ezra.programandojuntos.util.DateUtil;

@Repository
public class PedidoRepositoryImpl implements PedidoRepository {
	
	@Qualifier(DataJdbcContants.JDBC_TEMPLATE_MYSQL)
	@Autowired
	NamedParameterJdbcTemplate jdbc;
		
	public String armarFiltroSql(Map<String, String> parametros) {
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
		            	filtroSql += " and " + newKey + ">='" + p.getValue() +"'";
	        	   }else 
	        		if(isDate && ultimo =='2') {
		            	filtroSql += " and " + newKey + "<='" + p.getValue() +"'";
	        	   }else 
	        		if(ultimo =='1') {
		            	filtroSql += " and " + newKey + ">=" + p.getValue();
	        	   }else 
	        		if(ultimo =='2') {
		            	filtroSql += " and " + newKey + "<=" + p.getValue();
	        	   } else {
		            	filtroSql += " and " + p.getKey() + "=" + p.getValue();
	        	   }
	            }
	      }
	        
		return filtroSql;
	}
	
	@Override
	public List<Pedido> listarPedidoConFiltros(Report reporte){
		String filtros = this.armarFiltroSql(reporte.getParameters());
		String sql = "SELECT * FROM pedidos WHERE 1=1";
		sql +=filtros;
        return jdbc.query(sql, Map.of(), new BeanPropertyRowMapper<> (Pedido.class));
	}
	
	

}
