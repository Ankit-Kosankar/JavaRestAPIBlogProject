package com.blog.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncodePassword {

	public static void main(String args[]) {
		BCryptPasswordEncoder encodePasswordEncoder = new BCryptPasswordEncoder();
		System.out.println(encodePasswordEncoder.encode("admin"));
	}
	
}
