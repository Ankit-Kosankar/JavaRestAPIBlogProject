package com.blog.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payload.LoginDto;
import com.blog.security.JwtTokenProvider;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	
	//This will automatically verify if the username or password is correct or Not
	//If not then the login activity will stop here 
	//authenticate method will take UserNamePasswordAuthenticationTOken Object
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@RequestMapping("signin")
	public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto){
		Authentication authenticate = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						loginDto.getUsernameOrEmail(),
						loginDto.getPassword()
						)
				);
		//if uname and password is wrong code below this will not execute test by
		//sysOut somethinf givng wrong credintials  
		SecurityContextHolder.getContext().setAuthentication(authenticate);
		
		return new ResponseEntity<>("User Signed In Succesfully",HttpStatus.OK);
	}
	
	@RequestMapping("login")
	public ResponseEntity<?> authenticateUserJwt(@RequestBody LoginDto loginDto){
		
		String usernameOrEmail = loginDto.getUsernameOrEmail();
		String password = loginDto.getPassword();
		
		Authentication authenticate = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						loginDto.getUsernameOrEmail(),
						loginDto.getPassword()
						)
				);
		
		String token = jwtTokenProvider.generateToken(authenticate);
		Map<Object, Object> model = new HashMap<>();
		model.put("username", usernameOrEmail);
		model.put("password", password);
		return new ResponseEntity<>(model,HttpStatus.OK);
	}
	
}
