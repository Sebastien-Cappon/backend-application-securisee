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

import com.nnk.poseidon.service.UserDetailsServiceImpl;

/**
 * A configuration class that contains 5 Beans for configuring SpringSecurity to
 * encode passwords with BCrypt and route users according to their level of
 * accreditation.
 *
 * @author Sébastien Cappon
 * @version 1.0
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

	/**
	 * A Bean linking SpringSecurity to the application's Service layer
	 * authentication class.
	 * 
	 * @return A <code>UserDetailsServiceImpl</code>
	 */
	@Bean
	public UserDetailsServiceImpl userDetailsService() {
		return new UserDetailsServiceImpl();
	}

	/**
	 * A Bean which tells SpringSecurity that the hash method used will be BCrypt.
	 * 
	 * @return A <code>BCryptPasswordEncoder</code>
	 */
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * A Bean that matches (no kidding...) the path to the request and extracts the
	 * variables required for the request to works correctly.
	 * 
	 * @return A <code>MvcRequestMatcher.Builder()</code>
	 */
	@Bean
	MvcRequestMatcher.Builder requestMatcher(HandlerMappingIntrospector handlerMappingIntrospector) {
		return new MvcRequestMatcher.Builder(handlerMappingIntrospector);
	}

	/**
	 * A Bean that validates the password from user details using the password
	 * encoder defined beforehand
	 * 
	 * @return A <code>MvcRequestMatcher.Builder()</code>
	 */
	@Bean
	public DaoAuthenticationProvider daoAuthProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

		daoAuthenticationProvider.setUserDetailsService(userDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

		return daoAuthenticationProvider;
	}

	/**
	 * A Bean that connects precedents together in order to connect, disconnect and
	 * route users according to their role.
	 * 
	 * @return A <code>SecurityFilterChain()</code>
	 */
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity, MvcRequestMatcher.Builder mvcRequestMatcher) throws Exception {
		return httpSecurity
			.authorizeHttpRequests(auth -> auth
				.requestMatchers(mvcRequestMatcher.pattern("/css/**")).permitAll()
				.requestMatchers(mvcRequestMatcher.pattern("/user/**")).hasRole("ADMIN")
				.anyRequest().authenticated()
			)
			.formLogin(formLogin -> formLogin
				.defaultSuccessUrl("/bid/list")
				.permitAll()
			)
			.logout(logout -> logout
				.logoutUrl("/app-logout")
			)
			.authenticationProvider(daoAuthProvider())
			.build();
	}
}