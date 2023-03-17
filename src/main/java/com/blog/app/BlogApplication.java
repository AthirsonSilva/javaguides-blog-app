package com.blog.app;

import com.blog.app.entities.Role;
import com.blog.app.repositories.RoleRepository;
import com.blog.app.repositories.UserRepository;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Spring Boot 3 Blog API",
				version = "1.0",
				description = "Spring Boot 3 Blog API REST APIs Documentation",
				contact = @Contact(
						name = "Athirson",
						url = "https://athirsonsilva.github.io/personal-portfolio/",
						email = "athirsonarceus@gmail.com"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://www.apache.org/licenses/LICENSE-2.0.html"
				),
				termsOfService = "https://swagger.io/terms/",
				extensions = {
						@Extension(name = "x-logo", properties = {
								@ExtensionProperty(name = "url", value = "https://athirsonsilva.github.io/personal-portfolio/assets/img/logo.png"),
								@ExtensionProperty(name = "altText", value = "Spring Boot 3 Blog API")
						})
				}
		),
		externalDocs = @ExternalDocumentation(
				description = "Spring Boot 3 Blog API REST APIs Documentation",
				url = "https://athirsonsilva.github.io/personal-portfolio/"
		)
)
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
		if (this.roleRepository.count() != 0) {
			return;
		}

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
