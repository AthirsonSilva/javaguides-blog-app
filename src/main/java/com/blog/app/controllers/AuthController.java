package com.blog.app.controllers;

import com.blog.app.payload.LoginDto;
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

	@PostMapping(value = {"/login", "/signin"})
	public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto) {
		return ResponseEntity.ok(authService.login(loginDto));
	}
}
