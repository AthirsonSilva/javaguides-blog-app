package com.blog.app.services;

import com.blog.app.payload.LoginDto;

public interface AuthService {
	String login(LoginDto loginDto);
}
