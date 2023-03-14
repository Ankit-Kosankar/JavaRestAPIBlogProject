package com.blog.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.entity.Role;
import com.blog.entity.User;
import com.blog.payload.LoginDto;
import com.blog.payload.SignUpDto;
import com.blog.repository.RoleRepository;
import com.blog.repository.UserRepository;
import com.blog.security.JwtTokenProvider;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	
	//This will automatically verify if the username or password is correct or Not
	//If not then the login activity will stop here 
	//authenticate method will take UserNamePasswordAuthenticationTOken Object
	//authnticate method  is present in authentication manager
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepository roleRepository;
	
	//for authentication we will need authentication token
	@RequestMapping("signin")
	public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto){
		
		String usernameOrEmail = loginDto.getUsernameOrEmail();
		String password = loginDto.getPassword();
		if(password == null || password.isEmpty()) {
			password = "123456";
		}
		Authentication authenticate = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						usernameOrEmail,
						password
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
	
	@PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto){

        // add check for username exists in a DB
        if(userRepository.existsByUsername(signUpDto.getUsername())){
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        // add check for email exists in DB
        if(userRepository.existsByEmail(signUpDto.getEmail())){
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        String password = "123456";
        //hardcoding the user as admin by default 
        // create user object
        User user = new User();
        user.setName(signUpDto.getName());
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(password));

        Role roles = roleRepository.findByName("ROLE_USER");
        user.setRoles(Collections.singleton(roles));

        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);

    }
}

	

