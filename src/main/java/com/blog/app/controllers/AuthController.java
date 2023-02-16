package com.blog.app.controllers;

import com.blog.app.payload.LoginDto;
import com.blog.app.payload.RegisterDto;
import com.blog.app.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	private final AuthenticationManager authenticationManager;
	private final AuthService authService;

	public AuthController(AuthenticationManager authenticationManager, AuthService authService) {
		this.authenticationManager = authenticationManager;
		this.authService = authService;
	}

	/**
	 * authenticate user
	 *
	 * @param loginDto loginDto
	 * @return {@link ResponseEntity}
	 * @see ResponseEntity
	 * @see String
	 */
	@PostMapping(value = {"/login", "/signin"})
	public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto) {
		return ResponseEntity.ok(authService.login(loginDto));
	}

	/**
	 * register user
	 *
	 * @param registerDto registerDto
	 * @return {@link ResponseEntity}
	 * @see ResponseEntity
	 * @see String
	 */
	@PostMapping(value = {"/register", "/signup"})
	public ResponseEntity<String> registerUser(@RequestBody RegisterDto registerDto) {
		return ResponseEntity.ok(authService.register(registerDto));
	}
}
