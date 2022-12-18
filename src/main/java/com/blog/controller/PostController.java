package com.blog.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payload.PostDto;
import com.blog.service.PostService;


//Rule Never Return an Entity Object

@RestController
@RequestMapping("/api/post")
public class PostController {

	private PostService postService;
	public PostController(PostService postService) {
		super();
		this.postService = postService;
	}
	
	//ResponseEntity is used to Send a status Back in Json
	
	@PostMapping
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
		return new ResponseEntity<>(postService.createPost(postDto),HttpStatus.CREATED);
	}
	
	@GetMapping
	public List<PostDto> getAllPosts(){
		//keywords --> fetchAll , getAll, listAll
		return postService.getAllPosts();
	}
	
	//http://localhost:8080/api/post/1
	@GetMapping("/{id}")
	public ResponseEntity<PostDto> getPostById(@PathVariable("id") Long id) {
		//return ResponseEntity.ok(postService.getPostById(id));
		return ResponseEntity.status(HttpStatus.OK).body(postService.getPostById(id));
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable("id") long id){
		//We are taking JSON object and converting it into java Object and initializing it ot a vaible of that object
		//We are taking pathVariable id and initializing it to a long variable Id
		PostDto updatedPostDto = postService.updatePost(postDto,id);
		return new ResponseEntity<>(updatedPostDto,HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePost(@PathVariable("id") Long id){
		postService.deletePost(id);
		
		return new ResponseEntity<>("Post Deleted Succesfully",HttpStatus.OK);
	}
}
