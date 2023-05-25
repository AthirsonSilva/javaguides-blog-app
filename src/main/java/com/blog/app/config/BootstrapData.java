package com.blog.app.config;

import java.util.List;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.blog.app.entities.Role;
import com.blog.app.entities.User;
import com.blog.app.repositories.RoleRepository;
import com.blog.app.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BootstrapData {
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;

	public void bootstrap() {
		if (roleRepository.count() != 0) {
			return;
		}

		Role roleUser = new Role();
		roleUser.setName("ROLE_USER");

		Role roleAdmin = new Role();
		roleAdmin.setName("ROLE_ADMIN");

		roleRepository.saveAll(List.of(roleUser, roleAdmin));

		User user = new User();
		user.setUsername("user");
		user.setName("user");
		user.setPassword(passwordEncoder.encode("user"));
		user.setEmail("user@user.com");
		user.setRoles(Set.of(roleUser));

		User admin = new User();
		admin.setUsername("admin");
		admin.setName("admin");
		admin.setPassword(passwordEncoder.encode("admin"));
		admin.setEmail("admin@admin.com");
		admin.setRoles(Set.of(roleUser, roleAdmin));

		userRepository.saveAll(List.of(user, admin));
	}
}
