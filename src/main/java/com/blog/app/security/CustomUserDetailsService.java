package com.blog.app.security;

import com.blog.app.entities.Role;
import com.blog.app.entities.User;
import com.blog.app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	private final UserRepository userRepository;

	@Autowired
	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/**
	 * load user by username
	 *
	 * @param usernameOrEmail usernameOrEmail
	 * @return {@link UserDetails}
	 * @see UserDetails
	 * @throws UsernameNotFoundException org.springframework.security.core.userdetails. username not found exception
	 */
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
		// Let people login with either username or email
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
            .orElseThrow(() ->
                new UsernameNotFoundException("User not found with username or email:" + usernameOrEmail));

		// return a new user with the username, password, and authorities
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
            user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection< ? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles){
		// convert the roles to a list of authorities
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
