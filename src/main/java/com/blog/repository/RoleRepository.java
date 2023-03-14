package com.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.entity.Role;

public interface RoleRepository extends JpaRepository<Role,Long>{

	Role findByName(String name);

}
