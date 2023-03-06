package com.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.blog.security.CustomUserDetailsService;

//@configuration will help us to define the configuration files 
@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)  //admin to access certain methods 
//you can use @PreAuthorize("hasRoles('ADMIN')")
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	// this annotation specifies that the object creation of password encoder is managed by spring 
	//
	@Autowired
	private CustomUserDetailsService userDetailsService; //loadByUsername
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//hcd4ah //basic authentication 
		//after you logged in it will generate session cookies so until logout it will work
		http
			.csrf().disable()
			.authorizeRequests()
			.antMatchers(HttpMethod.GET, "api/**").permitAll()
			.antMatchers("/api/auth/**").permitAll()
			.anyRequest()
			.authenticated()
			.and()
			.httpBasic();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userDetailsService)
		.passwordEncoder(passwordEncoder());
	}
	
	//bean is not automatically created
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception{
		return super.authenticationManager();
	}
	
	
	
	//session factory is how hibernate establishes the connection with the database
	/*
	@Override
	@Bean
	protected UserDetailsService userDetailsService() {
		UserDetails user1 = User.builder().username("ankit").password(passwordEncoder().encode("ankit")).roles("USER").build();
		UserDetails user2 = User.builder().username("admin").password(passwordEncoder().encode("admin")).roles("ADMIN").build();
		return new InMemoryUserDetailsManager(user1,user2);
		//User Name and Password Can be stored like this too 
				//with the help of UserDetails Service and InMemoryDB --one way
	}*/
}
