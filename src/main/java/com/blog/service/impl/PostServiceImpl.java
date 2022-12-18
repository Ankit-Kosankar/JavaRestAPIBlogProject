package com.blog.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.blog.entity.Post;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payload.PostDto;
import com.blog.repository.PostRepository;
import com.blog.service.PostService;

@Service
public class PostServiceImpl implements PostService{

	
	private PostRepository postRepository;
	public PostServiceImpl(PostRepository postRepository) {
		this.postRepository = postRepository;
	}



	@Override
	public PostDto createPost(PostDto postDto) {
		//setting the entity to map the data
		Post post = mapToEntity(postDto);
		
		Post savedEntity = postRepository.save(post);
		
		//setting the dto response 
		PostDto responseDto = mapToDto(savedEntity);
		
		return responseDto;
	}
	
	//Mappin the Entity to Dto{resuable}
	public Post mapToEntity(PostDto postDto) {
		
		Post post = new Post();
		post.setContent(postDto.getContent());
		post.setDescription(postDto.getDescription());
		post.setTitle(postDto.getTitle());
		return post;
	}
	
	//Mappint the Dto to Entity{resuable}
	public PostDto mapToDto(Post post) {
		PostDto postDto = new PostDto();
		postDto.setContent(post.getContent());
		postDto.setDescription(post.getDescription());
		postDto.setId(post.getId());
		postDto.setTitle(post.getTitle());
		return postDto;
	}

	//Converting all the displayed Objects To the Dto Object
	@Override
	public List<PostDto> getAllPosts() {
		List<Post> posts = postRepository.findAll();
		//callint the number of times post has
		//maybe alternate of list
		return posts.stream().map(post -> mapToDto(post) ).collect(Collectors.toList());
		 
	}



	@Override
	public PostDto getPostById(Long id) {
		//If Found By Id or else Throw Runtime Error
		//THis method returns post object if not then it will
		// create an Object of Exception and It will
		// This doesnt Crash Our Project
		Post post = postRepository.findById(id).orElseThrow(
				()->new ResourceNotFoundException("Post", "id", id)
		);
		
		PostDto postDto = mapToDto(post);
		return postDto;
	}



	//Whatever Record we are updating all the details should be displayed
	@Override
	public PostDto updatePost(PostDto postDto, long id) {
		//first check wheather the id exist or not if exist fetch the object
		//with all the data in the database 
		Post post = postRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("Post","id",id)
				);
		
		//overwrite the post Data
		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setContent(postDto.getContent());
		
		postRepository.save(post);
		
		PostDto savedPostDto = mapToDto(post);
		
		return savedPostDto;
	}



	@Override
	public PostDto deletePost(Long id) {
		
		//if present find it or else throw exception so code down below wont run
		Post post = postRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("Post","id",id)
				);
		
		postRepository.deleteById(id);
		
		PostDto postDto = mapToDto(post);
		
		return postDto;
		
	}

}
