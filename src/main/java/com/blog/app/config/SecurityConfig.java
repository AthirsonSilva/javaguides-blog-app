package com.blog.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * security config
 */
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

	private final UserDetailsService userDetailsService;

	@Autowired
	public SecurityConfig(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * authentication manager bean
	 *
	 * @param configuration configuration
	 * @return {@link AuthenticationManager}
	 * @throws Exception java.lang. exception
	 * @see AuthenticationManager
	 */
	@Bean
	public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	/**
	 * security filter chain
	 *
	 * @param http http
	 * @return {@link SecurityFilterChain}
	 * @throws Exception java.lang. exception
	 * @see SecurityFilterChain
	 */
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		// http basic authentication
		http
			.csrf().disable() // disable csrf
			.authorizeHttpRequests((authorize) ->
				authorize.requestMatchers(HttpMethod.GET, "/api/v1/**").permitAll() // permit all get requests
					.requestMatchers("/api/v1/auth/**").permitAll() // permit all auth requests
					.anyRequest().authenticated() // all other requests must be authenticated
			);

		return http.build();
	}
}
