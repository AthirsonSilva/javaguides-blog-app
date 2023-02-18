package com.blog.app;

import com.blog.app.entities.Role;
import com.blog.app.repositories.RoleRepository;
import com.blog.app.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BlogApplication implements CommandLineRunner {

	@Autowired
	private final PasswordEncoder passwordEncoder;
	@Autowired
	private final UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;

	public BlogApplication(RoleRepository roleRepository, PasswordEncoder passwordEncoder, UserRepository userRepository) {
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		// Automates the mapping between Java bean types based on a convention over configuration approach.
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		// Create user role
		Role roleUser = new Role();
		roleUser.setName("ROLE_USER");
		this.roleRepository.save(roleUser);

		// Create admin role
		Role roleAdmin = new Role();
		roleAdmin.setName("ROLE_ADMIN");
		this.roleRepository.save(roleAdmin);
	}
}
