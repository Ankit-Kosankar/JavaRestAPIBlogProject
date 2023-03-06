package com.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment,Long> {

	//custom method
	List<Comment> findByPostId(long postId);
	
}
