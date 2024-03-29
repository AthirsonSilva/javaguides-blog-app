package com.blog.app.controllers;

import com.blog.app.payload.JwtAuthenticationResponse;
import com.blog.app.payload.LoginDto;
import com.blog.app.payload.RegisterDto;
import com.blog.app.services.implementation.AuthenticationServiceImplementation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(
		name = "CRUD REST API for Authentication resource",
		description = "CRUD REST API for Authentication resource"
)
public class AuthenticationController {
	private final AuthenticationManager authenticationManager;
	private final AuthenticationServiceImplementation authenticationService;

	public AuthenticationController(AuthenticationManager authenticationManager, AuthenticationServiceImplementation authenticationService) {
		this.authenticationManager = authenticationManager;
		this.authenticationService = authenticationService;
	}

	/**
	 * authenticate user
	 *
	 * @param loginDto loginDto
	 * @return {@link ResponseEntity}
	 * @see ResponseEntity
	 * @see String
	 */
	@Operation(summary = "Authenticate user")
	@ApiResponse(responseCode = "200", description = "User authenticated")
	@PostMapping(value = {"/login", "/signin"})
	public ResponseEntity<JwtAuthenticationResponse> authenticateUser(@RequestBody LoginDto loginDto) {
		String accessToken = authenticationService.login(loginDto);

		return ResponseEntity.ok(new JwtAuthenticationResponse(accessToken));
	}

	/**
	 * register user
	 *
	 * @param registerDto registerDto
	 * @return {@link ResponseEntity}
	 * @see ResponseEntity
	 * @see String
	 */
	@Operation(summary = "Authenticate user")
	@ApiResponse(responseCode = "200", description = "User authenticated")
	@PostMapping(value = {"/register", "/signup"})
	public ResponseEntity<String> registerUser(@RequestBody RegisterDto registerDto) {
		return ResponseEntity.ok(authenticationService.register(registerDto));
	}
}
