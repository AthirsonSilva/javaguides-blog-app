package com.blog.app.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {
	@NotEmpty(message = "Username or email is required")
	@Size(min = 3, max = 40, message = "Username or email must be between 3 and 40 characters")
	private String usernameOrEmail;
	@NotEmpty(message = "Password is required")
	@Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
	private String password;
}
