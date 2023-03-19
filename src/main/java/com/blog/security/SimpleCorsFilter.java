package com.blog.security;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Order(0)
@Component
public class SimpleCorsFilter implements Filter{

	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		//Response Needs to Be HttpServeletRequest or Response For Browser
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		res.setHeader("Access-Control-Allow-Origin","*");
		res.setHeader("Access-Control-Allow-Credentials","*");
		res.setHeader("Access-Control-Allow-Methods","*");
		res.setHeader("Access-Control-Max-Age","3600");
		res.setHeader("Access-Control-Allow-Header","*");
		res.setHeader("Access-Control-Expose-Headers","*");
		
		if("OPTIONS".equalsIgnoreCase(req.getMethod())) {
			res.setStatus(HttpServletResponse.SC_OK);
		}else {
			chain.doFilter(request, response);
		}
		
	}
	
	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		 	CorsConfiguration configuration = new CorsConfiguration();
		 	//configuration.setAllowCredentials(true);
		 	configuration.addAllowedHeader("*");
		 	configuration.addAllowedMethod("*");
		 	configuration.addAllowedOrigin("*");
		 	configuration.setAllowedOriginPatterns(Arrays.asList("*"));
		 	configuration.addExposedHeader("*");
		 	/*
		 	configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
	        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
	        configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
	        */
	        source.registerCorsConfiguration("/**", configuration);
	        return new CorsFilter(source);
	        //return source;
	}
	
	

}
