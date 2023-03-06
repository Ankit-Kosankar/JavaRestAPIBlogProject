package com.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.blog.entity.Post;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payload.PostDto;
import com.blog.payload.PostResponse;
import com.blog.repository.PostRepository;
import com.blog.service.PostService;

@Service
public class PostServiceImpl implements PostService{

	//as modelMapper is not of spring so this will cause error
	
	private PostRepository postRepository;
	private ModelMapper modelMapper;
	public PostServiceImpl(PostRepository postRepository,ModelMapper modelMapper) {
		this.postRepository = postRepository;
		this.modelMapper = modelMapper;
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
		
		//this will conver postDto to PostClass Object
		Post map = modelMapper.map(postDto, Post.class);

		//old method to map the date 
		Post post = new Post();
		post.setContent(postDto.getContent());
		post.setDescription(postDto.getDescription());
		post.setTitle(postDto.getTitle());
		return map;
	}
	
	//Mappint the Dto to Entity{resuable}
	public PostDto mapToDto(Post post) {
		
		PostDto map = modelMapper.map(post, PostDto.class);
		
		PostDto postDto = new PostDto();
		postDto.setContent(post.getContent());
		postDto.setDescription(post.getDescription());
		postDto.setId(post.getId());
		postDto.setTitle(post.getTitle());
		return map;
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


	//pagination one 
	@Override
	public PostResponse findAllPostWithPagination(int pageNo,int pageSize) {
		//Pageable 
		Pageable pageable = PageRequest.of(pageNo, pageSize); //overloading of one of the methods
		//this will give you all the details					//same class same  methods name with same argumnets
		Page<Post> posts = postRepository.findAll(pageable);
		List<Post> listOfPosts = posts.getContent();
		List<PostDto> allPostsDto = listOfPosts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(allPostsDto);
		postResponse.setPageNo(posts.getNumber());
		postResponse.setPageSize(posts.getSize());
		postResponse.setTotalPages(posts.getTotalPages());
		postResponse.setTotalElements(posts.getTotalElements());
		postResponse.setLast(posts.isLast());
		return postResponse;
	}

	
	//pagination two 
	@Override
	public PostResponse findAllPostWithPaginationSort(int pageNo,int pageSize,String sortBy) {
		
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));  //convert String to Sort
		//this will give you all the details
		Page<Post> posts = postRepository.findAll(pageable);
		List<Post> listOfPosts = posts.getContent();
		List<PostDto> allPostsDto = listOfPosts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(allPostsDto);
		postResponse.setPageNo(posts.getNumber());
		postResponse.setPageSize(posts.getSize());
		postResponse.setTotalPages(posts.getTotalPages());
		postResponse.setTotalElements(posts.getTotalElements());
		postResponse.setLast(posts.isLast());
		return postResponse;
	}
	
	//pagination two 
		@Override
		public PostResponse findAllPostWithPaginationWithSortByAndDir(int pageNo,int pageSize,String sortBy) {
			
			//Pageable  = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
			
			
			PageRequest pageable = PageRequest.of(pageSize, pageSize, Direction.ASC, sortBy);
			//this will give you all the details
			Page<Post> posts = postRepository.findAll(pageable);
			List<Post> listOfPosts = posts.getContent();
			List<PostDto> allPostsDto = listOfPosts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
			PostResponse postResponse = new PostResponse();
			postResponse.setContent(allPostsDto);
			postResponse.setPageNo(posts.getNumber());
			postResponse.setPageSize(posts.getSize());
			postResponse.setTotalPages(posts.getTotalPages());
			postResponse.setTotalElements(posts.getTotalElements());
			postResponse.setLast(posts.isLast());
			return postResponse;
		}
}
