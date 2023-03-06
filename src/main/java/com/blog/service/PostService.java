package com.blog.service;

import java.util.List;

import com.blog.payload.PostDto;
import com.blog.payload.PostResponse;

public interface PostService {

	PostDto createPost(PostDto postDto);

	List<PostDto> getAllPosts();
	
	PostDto getPostById(Long id);

	PostDto updatePost(PostDto postDto, long id);

	PostDto deletePost(Long id);

	PostResponse findAllPostWithPagination(int pageNo,int pageSize);

	PostResponse findAllPostWithPaginationSort(int pageNo, int pageSize, String sortBy);

	PostResponse findAllPostWithPaginationWithSortByAndDir(int pageNo, int pageSize, String sortBy);
	
}
