package com.blog.service;

import java.util.List;

import com.blog.payload.PostDto;

public interface PostService {

	PostDto createPost(PostDto postDto);

	List<PostDto> getAllPosts();
	
	PostDto getPostById(Long id);

	PostDto updatePost(PostDto postDto, long id);

	PostDto deletePost(Long id);
	
}
