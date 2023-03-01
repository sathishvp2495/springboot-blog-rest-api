package com.satz.blog.controller;

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

import com.satz.blog.payload.CommentDto;
import com.satz.blog.service.CommentService;

@RestController
@RequestMapping("/api")
public class CommentController {

	
	private CommentService commentService;

	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}
	
	@PostMapping("/posts/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@PathVariable(value="postId") long postId,
													@RequestBody CommentDto commentDto){
		
		return new ResponseEntity<CommentDto>(commentService.createComment(postId, commentDto),HttpStatus.CREATED);
		
	}
	
	@GetMapping("/posts/{postId}/comments")
	public List<CommentDto> getCommentsbyPostId(@PathVariable(value = "postId") long postId){
		return commentService.getCommentsByPostId(postId);
	}
	
	@GetMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<CommentDto> getCommentById(@PathVariable(name = "postId") long postId,
													@PathVariable(name="commentId") long commentId){
		CommentDto commentDto = commentService.getCommentById(postId, commentId);
		return new ResponseEntity<CommentDto>(commentDto,HttpStatus.OK);		
	}
	
	
	@PutMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<CommentDto> updateComment(@PathVariable(value = "postId") long postId,
													@PathVariable(value = "commentId") long commentId,
													@RequestBody CommentDto commentDto){
		CommentDto updatedComment = commentService.updateComment(postId, commentId, commentDto);
		return new ResponseEntity<CommentDto>(updatedComment,HttpStatus.OK);
	}
	
	@DeleteMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<String> deleteComment(@PathVariable(value = "postId") long postId,
												@PathVariable(value = "commentId") long commentId){
		
		commentService.deleteComment(postId, commentId);
		return new ResponseEntity<String>("Comment Deleted Successfully",HttpStatus.OK);
	}
	
}
