package com.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.entity.User;

public interface UserRepository extends JpaRepository<User,Long>{

	Optional<User> findByEmail(String email);
	Optional<User> findByUsername(String email);
	Optional<User> findByUsernameOrEmail(String username, String email);
	Boolean existsByUsername(String useranme);
	Boolean existsByEmail(String email);
}
