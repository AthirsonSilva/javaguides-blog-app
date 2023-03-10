package com.blog.app.security;

import com.blog.app.exceptions.BlogAPIException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {
	@Value("${app.jwt-secret}")
	private String jwtSecret;
	@Value("${app.jwt-expiration-milliseconds}")
	private long jwtExpirationDate;

	/**
	 * generate jwt token
	 *
	 * @param authentication authentication
	 * @return {@link String}
	 * @see String
	 */
	public String generateJwtToken(Authentication authentication) {
		// get username from authentication
		String username = authentication.getName();
		// get current date
		Date currentDate = new Date();
		// set expiry date
		Date expiryDate = new Date(currentDate.getTime() + jwtExpirationDate);

		// generate and return jwt token with username, current date, expiry date and decoded secret key
		return Jwts.builder()
			.setSubject(username)
			.setIssuedAt(currentDate)
			.setExpiration(expiryDate)
			.signWith(key())
			.compact();
	}

	/**
	 * key
	 *
	 * @return {@link Key}
	 * @see Key
	 */
	private Key key() {
		// decode secret key and return it
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	}

	/**
	 * get username from jwt token
	 *
	 * @param token token
	 * @return {@link String}
	 * @see String
	 */
	public String getUsernameFromJwtToken(String token) {
		// parse jwt token and return username
		return Jwts.parserBuilder()
			.setSigningKey(key())
			.build()
			.parseClaimsJws(token)
			.getBody()
			.getSubject();
	}

	/**
	 * validate jwt token
	 *
	 * @param authToken authToken
	 * @return {@link Boolean}
	 * @see Boolean
	 */
	public boolean validateJwtToken(String authToken) {
		// parse jwt token and return true if it is valid
		try {
			Jwts.parserBuilder()
				.setSigningKey(key())
				.build()
				.parseClaimsJws(authToken);

			return true;
		} catch (MalformedJwtException e) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Invalid JWT token");
		} catch (ExpiredJwtException e) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Expired JWT token");
		} catch (UnsupportedJwtException e) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Unsupported JWT token");
		} catch (IllegalArgumentException e) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "JWT claims string is empty");
		}
	}
}
