package com.raju.admin.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.google.common.collect.ImmutableList;

@Configuration
public class SecurityConfig {

	@Autowired
	private JwtAuthenticationFilter jwtAuthFilter;

	// Swagger endpoints that should be public
	private static final String[] SWAGGER_WHITELIST = { 
			"/v3/api-docs/**",
			"/swagger-ui/**", 
			"/swagger-ui.html",
			"/swagger-resources/**", 
			"/webjars/**" 
	};

	private static final String[] ADMIN_AUTH_LIST = {
			"/user/**"
	};

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()).cors(Customizer.withDefaults()).authorizeHttpRequests(auth -> auth
				// Allow Swagger and login endpoint without authentication
				.requestMatchers(SWAGGER_WHITELIST).permitAll()
//                    .requestMatchers("/**").permitAll()
				// allow login and OTP APIs without token
				.requestMatchers(ADMIN_AUTH_LIST).permitAll()
				// All other requests require authentication
				.anyRequest().authenticated())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		final CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOriginPatterns(List.of("*"));
		configuration.setAllowedMethods(ImmutableList.of("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
		configuration.setAllowCredentials(true);
		configuration.setAllowedHeaders(ImmutableList.of("Authorization", "companyId", "companyid", "Cache-Control",
				"Content-Type", "token", "x-auth-token", "captcha", "companyName", "X-Requested-With", "Accept",
				"rememberMe", "rememberMeChecked", "responseType", "access-control-expose-headers",
				"Access-Control-Allow-Headers", "*"));
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
