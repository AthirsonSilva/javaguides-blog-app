package com.blog.app.services;

import com.blog.app.payload.LoginDto;
import com.blog.app.payload.RegisterDto;

public interface AuthService {
	String login(LoginDto loginDto);
	String register(RegisterDto registerDto);
}
