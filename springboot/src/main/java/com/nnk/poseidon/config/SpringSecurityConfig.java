package com.nnk.poseidon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import com.nnk.poseidon.service.AuthenticationServiceImpl;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

	@Bean
	public AuthenticationServiceImpl authService() {
		return new AuthenticationServiceImpl();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	MvcRequestMatcher.Builder requestMatcher(HandlerMappingIntrospector handlerMappingIntrospector) {
	    return new MvcRequestMatcher.Builder(handlerMappingIntrospector);
	}
	
	@Bean
	public DaoAuthenticationProvider daoAuthProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

		daoAuthenticationProvider.setUserDetailsService(authService());
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

		return daoAuthenticationProvider;
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity, MvcRequestMatcher.Builder mvcRequestMatcher) throws Exception {
		return httpSecurity.authorizeHttpRequests(auth -> auth
				.requestMatchers(mvcRequestMatcher.pattern("/css/**")).permitAll()
				.requestMatchers(mvcRequestMatcher.pattern("/user/add")).hasRole("ADMIN")
				.requestMatchers(mvcRequestMatcher.pattern("/user/update")).hasRole("ADMIN")
				.anyRequest().authenticated()
			)
			.formLogin(formLogin -> formLogin
				.defaultSuccessUrl("/bidList/list")
				.permitAll()
			)
			.logout(logout -> logout
				.logoutUrl("/app-logout")		
			)
			.authenticationProvider(daoAuthProvider())
			.build();
	}
}