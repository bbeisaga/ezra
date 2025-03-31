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
			   .requestMatchers(HttpMethod.POST,"api/clientes").hasAnyRole("USER","ADMIN") 
			   .requestMatchers(HttpMethod.GET,"api/clientes/pageable").permitAll()
			   .requestMatchers(HttpMethod.GET,"api/clientes/documentos").permitAll()
			   .requestMatchers(HttpMethod.GET,"api/clientes/{id}").permitAll()
			   .requestMatchers(HttpMethod.PUT,"api/clientes/{id}").hasAnyRole("USER","ADMIN")
			   .requestMatchers(HttpMethod.DELETE,"api/clientes/{id}").hasRole("ADMIN")
			 //Permisos par alas URLs de pedido
			   .requestMatchers(HttpMethod.GET,"api/pedidos").permitAll() 
			   .requestMatchers(HttpMethod.GET,"api/pedidos/pageable").permitAll()
			   .requestMatchers(HttpMethod.GET,"api/pedidos/estado-pedido").permitAll()
			   .requestMatchers(HttpMethod.GET,"api/pedidos/tipo-pedido").permitAll()
			   .requestMatchers(HttpMethod.GET,"api/pedidos/tipo-pedido/{tipoPedidoId}").permitAll()
			   .requestMatchers(HttpMethod.POST,"api/pedidos").hasAnyRole("USER","ADMIN") 
			   .requestMatchers(HttpMethod.PUT,"api/pedidos/{id}").hasAnyRole("USER","ADMIN")
			   .requestMatchers(HttpMethod.DELETE,"api/pedidos/{id}").hasRole("ADMIN")
			   .requestMatchers(HttpMethod.POST,"api/pedidos/reporte/tipo-pedido").permitAll() 

			 //Permisos par alas URLs de movimientos VENTAS
			   .requestMatchers(HttpMethod.GET,"api/movimientos/tipoPagos").permitAll() 
			   .requestMatchers(HttpMethod.GET,"api/movimientos/tipoMovimientos").permitAll() 
			   .requestMatchers(HttpMethod.POST,"api/movimientos").hasAnyRole("USER","ADMIN")
			 //Permisos par alas URLs de movimientos CAJAS
			   .requestMatchers(HttpMethod.POST,"api/movimientos/caja").hasAnyRole("USER","ADMIN")			   
			 //Permisos par alas URLs de CAJAS USUARIOS
			   .requestMatchers(HttpMethod.GET,"api/cajas/usuarios/{userName}").hasAnyRole("USER","ADMIN")	
			   .requestMatchers(HttpMethod.GET,"api/cajas/{cajaId}/usuarios/{userId}").hasAnyRole("USER","ADMIN")
			   .requestMatchers(HttpMethod.GET,"api/cajas/{cajaId}/usuarios/{username}").hasAnyRole("USER","ADMIN")
			   .requestMatchers(HttpMethod.POST,"api/cajas/usuarios").hasAnyRole("USER","ADMIN")
			   .requestMatchers(HttpMethod.PUT,"api/cajas/usuarios/{id}").hasAnyRole("USER","ADMIN")
			   .requestMatchers(HttpMethod.POST,"api/cajas/reporte/cierre-caja").permitAll()

			   //Permisos par alas URLs de CAJAS
			   .requestMatchers(HttpMethod.GET,"api/cajas").hasAnyRole("USER","ADMIN")
			   //Permisos para las URLs de Usuarios
			   .requestMatchers(HttpMethod.GET,"api/usuarios").hasAnyRole("USER","ADMIN")
			   .requestMatchers(HttpMethod.GET,"api/usuarios/{username}").hasAnyRole("USER","ADMIN")
				 //Permisos par alas URLs de PRODUCXTOS
			   .requestMatchers(HttpMethod.GET,"api/producto/colores").permitAll()
			   .requestMatchers(HttpMethod.GET,"api/producto/materiales").permitAll()
			   .requestMatchers(HttpMethod.GET,"api/producto/categorias").permitAll()
			   .requestMatchers(HttpMethod.GET,"api/producto/usos").permitAll()
			   .requestMatchers(HttpMethod.POST,"api/producto").hasAnyRole("USER","ADMIN") 
			   .requestMatchers(HttpMethod.PUT,"api/producto/{id}").hasAnyRole("USER","ADMIN")

			   //.requestMatchers(HttpMethod.GET,"api/productos/generico").permitAll()
			   .requestMatchers(HttpMethod.GET,"api/producto/filtrar-productos/{term}").permitAll()
			   //.requestMatchers(HttpMethod.GET,"api/genericos-de-producto").permitAll()

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
