package com.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.blog.entity.Comment;
import com.blog.entity.Post;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payload.CommentDto;
import com.blog.repository.CommentRepository;
import com.blog.repository.PostRepository;
import com.blog.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService{


	private CommentRepository commentRepository;
	private PostRepository postRepository;
	private ModelMapper modelMapper;
	public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository,ModelMapper modelMapper) {
		this.commentRepository = commentRepository;
		this.postRepository = postRepository;
		this.modelMapper = modelMapper;
		
	}
	@Override
	public CommentDto createComment(long postId, CommentDto commentDto) {
		
		Post post = postRepository.findById(postId).orElseThrow(
				()-> new ResourceNotFoundException("post", "id", postId)
				);
		Comment comment = mapToComment(commentDto);
		comment.setPost(post);
		Comment newComment = commentRepository.save(comment);
		CommentDto newCommentDto = mapToCommentDto(newComment);
		return newCommentDto;
	}
	
	
	@Override
	public List<CommentDto> getCommentByPostId(long postId) {
		List<Comment> comments = commentRepository.findByPostId(postId);
		List<CommentDto> listCommentsDto = comments.stream().map(comment->mapToCommentDto(comment)).collect(Collectors.toList());
		return listCommentsDto;
	}
	
	
	private CommentDto mapToCommentDto(Comment newComment) {
		CommentDto commentDto = new CommentDto();
		commentDto.setBody(newComment.getBody());
		commentDto.setEmail(newComment.getEmail());
		commentDto.setId(newComment.getId());
		commentDto.setName(newComment.getName());
		return commentDto;
	}

	public Comment mapToComment(CommentDto commentDto) {
		Comment comment = new Comment();
		comment.setName(commentDto.getName());
		comment.setEmail(commentDto.getEmail());
		comment.setBody(commentDto.getBody());
		return comment;
	}
	@Override
	public CommentDto updateComment(long postId, long id, CommentDto commentDto) {
		
		Post post = postRepository.findById(postId).orElseThrow(
				()-> new ResourceNotFoundException("post","id",postId)
				);
		Comment comment = commentRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("comment","id",id)
				);
		comment.setName(commentDto.getName());
		comment.setEmail(commentDto.getEmail());
		comment.setBody(commentDto.getBody());
		Comment updatedComment = commentRepository.save(comment);
		CommentDto responseCommentDto = mapToCommentDto(updatedComment);
		return responseCommentDto;
	}
	@Override
	public void deleteComment(long postId, long id) {
		postRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("comment","id",id));
		
		commentRepository.deleteById(id);
	}
	
	

}
