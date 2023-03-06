package com.blog.service;

import java.util.List;

import com.blog.payload.CommentDto;

public interface CommentService {

	CommentDto createComment(long id,CommentDto commentDto);
	List<CommentDto> getCommentByPostId(long postId);
	CommentDto updateComment(long postId,long id,CommentDto commentDto);
	void deleteComment(long postId, long id);
}
