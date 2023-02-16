package com.blog.app.payload;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
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
public class RegisterDto {

	@NotEmpty(message = "Name field is required")
	@Min(value = 3, message = "Name must be at least 3 characters long")
	private String name;
	@NotEmpty(message = "Username field is required")
	@Min(value = 3, message = "Username must be at least 3 characters long")
	private String username;
	@NotEmpty(message = "Email field is required")
	@Size(min = 3, message = "Email must be at least 3 characters long")
	@Email(message = "Email must be valid")
	private String email;
	@NotEmpty(message = "Password field is required")
	@Size(min = 6, message = "Password must be at least 6 characters long")
	private String password;
}
