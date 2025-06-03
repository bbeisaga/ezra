package com.ezra.programandojuntos.auth;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.ezra.programandojuntos.auth.filter.JwtAuthenticationFilter;
import com.ezra.programandojuntos.auth.filter.JwtValidationFilter;

@Configuration
public class SpringSecurityConfig {
	

//	@Autowired
//	private UserDetailsService usuarioService;
	
	@Autowired
	private AuthenticationConfiguration authenticationConfiguration;
	
	@Bean("authenticationManager")
	AuthenticationManager authenticationManager() throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

//	@Override
//	@Autowired
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(this.usuarioService).passwordEncoder(passwordEncoder());
//	}


	
//	@Override
//	public void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests()
//		.anyRequest().authenticated()
//		.and()
//		.csrf().disable()
//		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//	}
	
	   @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		   return http.authorizeHttpRequests(authz -> 
			   authz
			   //Permisos par alas URLs de clientes
			   .requestMatchers(HttpMethod.GET,"api/clientes").permitAll() // aqui dejar las URL que siempre deben estar abiertas para autenticarnos
			   .requestMatchers(HttpMethod.GET,"api/clientes/documentos").permitAll()
			   .requestMatchers(HttpMethod.GET,"api/clientes/{id}").permitAll()
			  // .requestMatchers(HttpMethod.GET,"api/clientes/filtrar-cliente/{term}").permitAll()
			   .requestMatchers(HttpMethod.GET,"api/clientes/pageable").hasRole("LIST_CLIENTES")
			   .requestMatchers(HttpMethod.POST,"api/clientes").hasRole("REGISTER_CLIENTE") 
			   .requestMatchers(HttpMethod.PUT,"api/clientes/{id}").hasRole("UPDATE_CLIENTE")
			   .requestMatchers(HttpMethod.DELETE,"api/clientes/{id}").hasRole("DELETE_CLIENTE")

			 //Permisos par alas URLs de pedido
			   //.requestMatchers(HttpMethod.GET,"api/pedidos").permitAll() 
			   .requestMatchers(HttpMethod.GET,"api/pedidos/estado-pedido").permitAll()
			   .requestMatchers(HttpMethod.GET,"api/pedidos/tipo-pedido").permitAll()
			   .requestMatchers(HttpMethod.GET,"api/pedidos/tipo-pedido/{tipoPedidoId}").permitAll()
			   .requestMatchers(HttpMethod.GET,"api/pedidos/{tipoPedidoId}/pageable").hasAnyRole("LIST_VENTAS", "LIST_COMPRAS")
			   .requestMatchers(HttpMethod.POST,"api/pedidos").hasRole("CREATE_PEDIDO") 
			   .requestMatchers(HttpMethod.POST,"api/pedidos/reporte/tipo-pedido").hasAnyRole("REPORT_VENTA", "REPORT_COMPRA") 
			   //.requestMatchers(HttpMethod.PUT,"api/pedidos/{id}").hasAnyRole("USER","ADMIN")
			   //.requestMatchers(HttpMethod.DELETE,"api/pedidos/{id}").hasRole("ADMIN")

			 //Permisos par alas URLs de MOVIMIENTO CAJA - PEDIDOS
			   .requestMatchers(HttpMethod.GET,"api/movimientos/tipoPagos").permitAll() 
			   .requestMatchers(HttpMethod.GET,"api/movimientos/tipoMovimientosPedido").permitAll() 
			   .requestMatchers(HttpMethod.POST,"api/movimientos").hasRole("REGISTER_PAGO_PEDIDO")
			   
			 //Permisos par alas URLs de MOVIMIENTO CAJA - CAJA CHICA
			   .requestMatchers(HttpMethod.GET,"api/movimientos/tipoMovimientosCaja").permitAll() 
			   .requestMatchers(HttpMethod.POST,"api/movimientos/caja").hasRole("REGISTER_MOVCAJA")
			   .requestMatchers(HttpMethod.POST,"api/movimientos/reporte/mov-en-caja").hasAnyRole("REPORT_MOVCAJA","REPORT_MOVCAJA_USUARIO")

			 //Permisos par alas URLs de CAJAS USUARIOS
			   .requestMatchers(HttpMethod.GET,"api/cajas/usuarios/{userName}").permitAll()
			   .requestMatchers(HttpMethod.GET,"api/cajas/{cajaId}/usuarios/{userId}").permitAll()
			   .requestMatchers(HttpMethod.GET,"api/cajas/{cajaId}/usuarios/{username}").permitAll()
			   .requestMatchers(HttpMethod.POST,"api/cajas/usuarios").hasRole("OPEN_CJU")
			   .requestMatchers(HttpMethod.PUT,"api/cajas/usuarios/{id}").hasRole("CLOSE_CJU")
			   .requestMatchers(HttpMethod.POST,"api/cajas/reporte/cierre-caja").hasAnyRole("REPORT_CJU","REPORT_USUARIO_CJU")

				 //Permisos par alas URLs ROLES
			   .requestMatchers(HttpMethod.GET,"api/roles").permitAll()
			   .requestMatchers(HttpMethod.GET,"api/roles/modulos").permitAll()
   
			   //Permisos par alas URLs de CAJAS
			   .requestMatchers(HttpMethod.GET,"api/cajas").permitAll() 
			   
				 //Permisos par alas URLs de PRODUCTOS
			   .requestMatchers(HttpMethod.GET,"api/producto/colores").permitAll()
			   .requestMatchers(HttpMethod.GET,"api/producto/materiales").permitAll()
			   .requestMatchers(HttpMethod.GET,"api/producto/categorias").permitAll()
			   .requestMatchers(HttpMethod.GET,"api/producto/usos").permitAll()	
			   .requestMatchers(HttpMethod.GET,"api/producto/filtrar-productos/{term}").permitAll()
			   .requestMatchers(HttpMethod.GET,"api/producto/pageable").hasRole("LIST_PRODUCTOS")
			   .requestMatchers(HttpMethod.POST,"api/producto").hasRole("REGISTER_PRODUCTO") //este se va ir
			   .requestMatchers(HttpMethod.POST,"api/producto/imagen").hasRole("REGISTER_PRODUCTO") 
			   .requestMatchers(HttpMethod.PUT,"api/producto/{id}").hasRole("UPDATE_PRODUCTO")
			   .requestMatchers(HttpMethod.DELETE,"api/producto/{id}").hasRole("DELETE_PRODUCTO")

			   //Permisos para las URLs de USUARIOS
			   .requestMatchers(HttpMethod.GET,"api/usuarios").permitAll()
			   .requestMatchers(HttpMethod.GET,"api/usuarios/{username}").permitAll()
			   .requestMatchers(HttpMethod.GET,"api/usuarios/pageable").hasRole("LIST_USUARIOS")
			   .requestMatchers(HttpMethod.PUT,"api/usuarios-roles/update/{usuarioId}").hasRole("ASIGNAR_ROL_USUARIO")
			   .requestMatchers(HttpMethod.PUT,"api/usuarios-roles/delete/{usuarioId}").hasRole("ASIGNAR_ROL_USUARIO")

			   ///////////////////////////////////////////////////////////////////////
			   .anyRequest().authenticated())
	        .cors(cors -> cors.configurationSource(configurationSource()))
			.addFilter(new JwtAuthenticationFilter(this.authenticationManager()))
			.addFilter(new JwtValidationFilter(this.authenticationManager()))
			.csrf(config -> config.disable())
			.sessionManagement(m -> m.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.build();   
	    }
	   
	    @Bean
	    CorsConfigurationSource configurationSource() {
	        CorsConfiguration config = new CorsConfiguration();
	        config.setAllowedOriginPatterns(Arrays.asList("*"));
	        config.setAllowedOrigins(Arrays.asList("http://localhost:4200"));//al pasar a produccion cambiar
	        config.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE"));
	        //config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Content-Disposition", "Access-Control-Allow-Headers", "Access-Control-Expose-Headers"));
	        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Content-Disposition"));
	        config.addExposedHeader("Content-Disposition");// sirve para download archivos xls en cliente
	        config.setAllowCredentials(true);

	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        source.registerCorsConfiguration("/**", config);
	        return source;
	    }

	    @Bean
	    FilterRegistrationBean<CorsFilter> corsFilter() {
	        FilterRegistrationBean<CorsFilter> corsBean = new FilterRegistrationBean<CorsFilter>(
	                new CorsFilter(this.configurationSource()));
	        corsBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
	        return corsBean;
	    }


}
