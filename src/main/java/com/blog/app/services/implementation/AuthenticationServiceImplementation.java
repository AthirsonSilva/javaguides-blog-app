package com.blog.app.services.implementation;

import com.blog.app.entities.Role;
import com.blog.app.entities.User;
import com.blog.app.exceptions.BlogAPIException;
import com.blog.app.payload.LoginDto;
import com.blog.app.payload.RegisterDto;
import com.blog.app.repositories.RoleRepository;
import com.blog.app.repositories.UserRepository;
import com.blog.app.security.JwtTokenProvider;
import com.blog.app.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthenticationServiceImplementation implements AuthenticationService {

	private final AuthenticationManager authenticationManager;
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;

	@Autowired
	public AuthenticationServiceImplementation(
		AuthenticationManager authenticationManager,
		UserRepository userRepository,
		RoleRepository roleRepository,
		PasswordEncoder passwordEncoder,
		JwtTokenProvider jwtTokenProvider) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	/**
	 * login
	 *
	 * @param loginDto loginDto
	 * @return {@link String}
	 * @see String
	 */
	@Override
	public String login(LoginDto loginDto) {
		// Authenticate user with username or email and password
		Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(
				loginDto.getUsernameOrEmail(),
				loginDto.getPassword()
			));

		// Set authentication in context
		SecurityContextHolder.getContext().setAuthentication(authentication);

		return jwtTokenProvider.generateJwtToken(authentication);
	}

	/**
	 * register
	 *
	 * @param registerDto registerDto
	 * @return {@link String}
	 * @see String
	 */
	@Override
	public String register(RegisterDto registerDto) {
		// Check if username, email, or name is already taken
		if (userRepository.existsByUsername(registerDto.getUsername()))
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Username is already taken");

		if (userRepository.existsByEmail(registerDto.getEmail()))
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Email is already taken");

		if (userRepository.existsByName(registerDto.getName()))
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Name is already taken");

		User user = new User();

		user.setName(registerDto.getName());
		user.setUsername(registerDto.getUsername());
		user.setEmail(registerDto.getEmail());
		user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

		Set<Role> roles = new HashSet<>();

		Role role = roleRepository.findByName("ROLE_USER")
			.orElseThrow(() -> new BlogAPIException(HttpStatus.BAD_REQUEST, "Role not found"));

		roles.add(role);

		user.setRoles(roles);

		userRepository.save(user);

		return "User registered successfully";
	}
}
