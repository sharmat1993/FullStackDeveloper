package com.stackroute.keepnote.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.keepnote.exception.UserNotFoundException;
import com.stackroute.keepnote.model.User;
import com.stackroute.keepnote.service.UserAuthenticationService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;

/*
 * As in this assignment, we are working on creating RESTful web service, hence annotate
 * the class with @RestController annotation. A class annotated with the @Controller annotation
 * has handler methods which return a view. However, if we use @ResponseBody annotation along
 * with @Controller annotation, it will return the data directly in a serialized 
 * format. Starting from Spring 4 and above, we can use @RestController annotation which 
 * is equivalent to using @Controller and @ResposeBody annotation
 */
@RestController
@CrossOrigin
public class UserAuthenticationController {

	/*
	 * Autowiring should be implemented for the UserAuthenticationService. (Use
	 * Constructor-based autowiring) Please note that we should not create an object
	 * using the new keyword
	 */

	UserAuthenticationService userAuthenticationService;

	@Autowired
	public UserAuthenticationController(UserAuthenticationService userAuthenticationService) {
		this.userAuthenticationService = userAuthenticationService;
	}

	/*
	 * Define a handler method which will create a specific user by reading the
	 * Serialized object from request body and save the user details in the
	 * database. This handler method should return any one of the status messages
	 * basis on different situations: 1. 201(CREATED) - If the user created
	 * successfully. 2. 409(CONFLICT) - If the userId conflicts with any existing
	 * user
	 * 
	 * This handler method should map to the URL "/api/v1/auth/register" using HTTP
	 * POST method
	 */

	@PostMapping(value = "/api/v1/auth/register", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> createUser(@RequestBody User user) {
		try {
			userAuthenticationService.saveUser(user);
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
	}

	/*
	 * Define a handler method which will authenticate a user by reading the
	 * Serialized user object from request body containing the username and
	 * password. The username and password should be validated before proceeding
	 * ahead with JWT token generation. The user credentials will be validated
	 * against the database entries. The error should be return if validation is not
	 * successful. If credentials are validated successfully, then JWT token will be
	 * generated. The token should be returned back to the caller along with the API
	 * response. This handler method should return any one of the status messages
	 * basis on different situations: 1. 200(OK) - If login is successful 2.
	 * 401(UNAUTHORIZED) - If login is not successful
	 * 
	 * This handler method should map to the URL "/api/v1/auth/login" using HTTP
	 * POST method
	 */

	@PostMapping(value = "/api/v1/auth/login", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> authenticateUser1(@RequestBody User user) {
		HttpHeaders headers = new HttpHeaders();
		String jwsToken = null;
		try {
			if (userAuthenticationService.findByUserIdAndPassword(user.getUserId(), user.getUserPassword()) != null) {

				jwsToken = getToken(user.getUserId(), user.getUserPassword());
				headers.add("bearerToken", jwsToken);
				return new ResponseEntity<String>("{\"bearerToken\": \"" + jwsToken + "\"}", headers, HttpStatus.OK);

			} else {
				return new ResponseEntity<String>("{\"bearerToken\": \"" + jwsToken + "\"}", headers, HttpStatus.UNAUTHORIZED);
			}
		} catch (UserNotFoundException e) {
			return new ResponseEntity<String>("{\"bearerToken\": \"" + jwsToken + "\"}", headers, HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			return new ResponseEntity<String>("{\"bearerToken\": \"" + jwsToken + "\"}", headers, HttpStatus.UNAUTHORIZED);
		}
	}

	// Generate JWT token
	public String getToken(String username, String password) throws Exception {

		String jws = Jwts.builder().setSubject(username + password).signWith(SignatureAlgorithm.HS256,
				TextCodec.BASE64.decode("Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=")).compact();

		return jws;

	}
	
	@PostMapping(value = "/api/v1/auth/isAuthenticated", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> isAuthenticatedUser1(HttpServletRequest request) {
		
		HttpHeaders headers = new HttpHeaders();
		String token = request.getHeader("Authorization");
		headers.add("bearerToken", token);
		
		if(!token.equals("null")) {
			return new ResponseEntity<String>("{\"isAuthenticated\": true }", headers, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("{\"isAuthenticated\":  false }", headers, HttpStatus.UNAUTHORIZED);
		}
	}

}
