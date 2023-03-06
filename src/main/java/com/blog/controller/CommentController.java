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
import com.blog.payload.CommentDto;
import com.blog.service.CommentService;

@RestController
@RequestMapping("/api")
public class CommentController {
	private CommentService commentService;
	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}
	
	@PostMapping("posts/{postId}/comment")
	public ResponseEntity<CommentDto> createComment(
										@RequestBody CommentDto commentDto,
										@PathVariable("postId") long postId
										) {
    CommentDto createdComment = commentService.createComment(postId, commentDto);
    //return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
    return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
	}
	
	@GetMapping("posts/{postId}/comment")
	public ResponseEntity<List<CommentDto>> getCommentOfPostById(
											@PathVariable("postId") long postId
												){
		List<CommentDto> listCommentDto = commentService.getCommentByPostId(postId);
		return new ResponseEntity<>(listCommentDto, HttpStatus.OK);
	}
	
	@PutMapping("posts/{postId}/comments/{id}")
	public ResponseEntity<CommentDto> updateComment(
			@PathVariable("postId") long postId,
			@PathVariable("id") long id,
			@RequestBody CommentDto commentDto){
		CommentDto updateComment = commentService.updateComment(postId,id,commentDto);
		return new ResponseEntity<>(updateComment,HttpStatus.OK);
	}
	
	@DeleteMapping("posts/{postId}/comments/{id}")
	public ResponseEntity<?> deleteComment(
			@PathVariable("postId") long postId,
			@PathVariable("id") long id
			){
		commentService.deleteComment(postId,id);
		return new ResponseEntity<>("Comment is Deleted",HttpStatus.OK);
	}
}