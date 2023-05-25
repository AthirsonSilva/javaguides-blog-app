package com.blog.app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.blog.app.config.BootstrapData;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import lombok.RequiredArgsConstructor;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Spring Boot 3 Blog API", version = "1.0", description = "Spring Boot 3 Blog API REST APIs Documentation", contact = @Contact(name = "Athirson", url = "https://athirsonsilva.github.io/personal-portfolio/", email = "athirsonarceus@gmail.com"), license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0.html"), termsOfService = "https://swagger.io/terms/", extensions = {
		@Extension(name = "x-logo", properties = {
				@ExtensionProperty(name = "url", value = "https://athirsonsilva.github.io/personal-portfolio/assets/img/logo.png"),
				@ExtensionProperty(name = "altText", value = "Spring Boot 3 Blog API")
		})
}), externalDocs = @ExternalDocumentation(description = "Spring Boot 3 Blog API REST APIs Documentation", url = "https://athirsonsilva.github.io/personal-portfolio/"))
@RequiredArgsConstructor
public class BlogApplication implements CommandLineRunner {
	private final BootstrapData bootstrapData;

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		bootstrapData.bootstrap();
	}
}
