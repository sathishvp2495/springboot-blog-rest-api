package com.satz.blog.service;

import java.util.List;

import com.satz.blog.payload.CommentDto;

public interface CommentService {
	CommentDto createComment(long postId, CommentDto commentDto);
	
	List<CommentDto> getCommentsByPostId(long postId);
	
	CommentDto getCommentById(Long postId,Long CommentId);
	
	CommentDto updateComment(Long postId,long commentId,CommentDto commentRequest);
	
	void deleteComment(long postId,long CommentId);

}
