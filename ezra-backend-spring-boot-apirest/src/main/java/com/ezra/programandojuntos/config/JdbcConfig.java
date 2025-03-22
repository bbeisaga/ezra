package com.ezra.programandojuntos.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.ezra.programandojuntos.constants.DataJdbcContants;


@Configuration
public class JdbcConfig {
	
    @Bean(DataJdbcContants.JDBC_TEMPLATE_MYSQL)
    //@Primary -- ESTO SOLO SE USA SI SE HABRE OTRA CONEXION
    public NamedParameterJdbcTemplate jdbcTemplate(DataSource dataSource) {//Datasource es un BEAN
        var jdbc = new NamedParameterJdbcTemplate(dataSource);
        jdbc.getJdbcTemplate().setQueryTimeout(30);
        return jdbc;
    }
    
     
//    @Bean(Constantes.JDBC_TEMPLATE_ORACLE)
//    public NamedParameterJdbcTemplate jdbcTemplateMeta4(
//            @Value("${oracle.datasource.url:}") String url,
//            @Value("${oracle.datasource.username:}") String user,
//            @Value("${oracle.datasource.password:}") String password) {
//        if (url.isBlank() || user.isBlank() || password.isBlank()) {
//            log.info("No está configurada la conexión a la BD de Oracle");
//            return null;
//        } else {
//            var ds = new DriverManagerDataSource(url, user, password);
//            var jdbcTemplate = new NamedParameterJdbcTemplate(ds);
//            jdbcTemplate.getJdbcTemplate().setQueryTimeout(60);
//            log.info("Conexión configurada a la BD de Oracle!");
//            return jdbcTemplate;
//        }
//    }
//
//    @Bean(Constantes.JDBC_TEMPLATE_SQL_SERVER)
//    public NamedParameterJdbcTemplate jdbcTemplateSimple(
//            @Value("${orq.dbsimple.url:}") String url,
//            @Value("${orq.dbsimple.user:}") String user,
//            @Value("${orq.dbsimple.password:}") String password) {
//        if (url.isBlank() || user.isBlank() || password.isBlank()) {
//            log.info("No está configurada la conexión a la BD +Simple");
//            return null;
//        } else {
//            var ds = new DriverManagerDataSource(url, user, password);
//            var jdbcTemplate = new NamedParameterJdbcTemplate(ds);
//            jdbcTemplate.getJdbcTemplate().setQueryTimeout(5);
//            log.info("Conexión configurada a la BD +Simple!");
//            return jdbcTemplate;
//        }
//    }

}
