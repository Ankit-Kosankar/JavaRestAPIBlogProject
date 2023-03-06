package com.blog.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payload.PostDto;
import com.blog.payload.PostResponse;
import com.blog.service.PostService;


//Rule Never Return an Entity Object

@RestController
@RequestMapping("/api/post")
public class PostController {

	//post service same as Auto-wired
	private PostService postService;
	public PostController(PostService postService) {
		super();
		this.postService = postService;
	}
	
	//ResponseEntity is used to Send a status Back in Json
	//Object is the supermost Class in java so any class can be Upcasted to Object Class
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> createPost(@Valid @RequestBody PostDto postDto, BindingResult bindingResult){
		if(bindingResult.hasErrors()) {
			//int fieldErrorCount = bindingResult.getFieldErrorCount();
			FieldError fieldError = bindingResult.getFieldError();
			return new ResponseEntity<>(fieldError.getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}else {
			return new ResponseEntity<>(postService.createPost(postDto),HttpStatus.CREATED);
		}
	}
	
	@GetMapping("/all")
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
	
	//Pagination Concept we cannot Use CRUD repository
	//localhost:8080/api/posts?pageNo=1@pageSize=10
	@GetMapping("/pagination")
	public ResponseEntity<?> getAllPostsWithPagination(@RequestParam(value = "pageNo", defaultValue= "0", required = false)int pageNo,
			@RequestParam (value = "pageSize", defaultValue= "5", required = false ) int pageSize) {
		PostResponse findAllPostWithPagination = postService.findAllPostWithPagination(pageNo,pageSize);
		return new ResponseEntity<>(findAllPostWithPagination,HttpStatus.OK);
	}
	
	@GetMapping("/pagesort")
	public ResponseEntity<?> getAllPostsWithPaginationSort
			(
			 @RequestParam(value = "pageNo", defaultValue= "0", required = false)int pageNo,
			 @RequestParam (value = "pageSize", defaultValue= "5", required = false ) int pageSize,
			 @RequestParam(value = "sortBy", defaultValue ="id", required = false) String sortBy
			 ) {
		PostResponse findAllPostWithPaginationSort = postService.findAllPostWithPaginationSort(pageNo,pageSize,sortBy);
		return new ResponseEntity<>(findAllPostWithPaginationSort,HttpStatus.OK);
	}
	
	@GetMapping("/pagesortdir")
	public ResponseEntity<?> getAllPostsWithPaginationSortAndDirection
			(
			 @RequestParam(value = "pageNo", defaultValue= "0", required = false)int pageNo,
			 @RequestParam (value = "pageSize", defaultValue= "5", required = false ) int pageSize,
			 @RequestParam(value = "sortBy", defaultValue ="id", required = false) String sortBy
			 ) {
		PostResponse findAllPostWithPaginationSort = postService.findAllPostWithPaginationWithSortByAndDir(pageNo,pageSize,sortBy);
		return new ResponseEntity<>(findAllPostWithPaginationSort,HttpStatus.OK);
	}
}
