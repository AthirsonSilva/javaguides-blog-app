package com.blog.app.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordGeneratorEncoder {
	public static void main(String[] args) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		// Generate encoded password for admin and user
		System.out.println(passwordEncoder.encode("admin"));
		System.out.println(passwordEncoder.encode("user"));
	}
}
