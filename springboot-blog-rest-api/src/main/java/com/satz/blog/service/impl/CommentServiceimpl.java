package com.satz.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.satz.blog.entity.Comment;
import com.satz.blog.entity.Post;
import com.satz.blog.exception.BlogAPIException;
import com.satz.blog.exception.ResourceNotFoundException;
import com.satz.blog.payload.CommentDto;
import com.satz.blog.repository.CommentRepository;
import com.satz.blog.repository.PostRepository;
import com.satz.blog.service.CommentService;

@Service
public class CommentServiceimpl implements CommentService{
	
	private CommentRepository commentRepository;
	
	private PostRepository postRepository;
	
	private ModelMapper mapper;

	public CommentServiceimpl(CommentRepository commentRepository,PostRepository postRepository,ModelMapper mapper) {
		this.commentRepository = commentRepository;
		this.postRepository = postRepository;
		this.mapper = mapper;
	}

	@Override
	public CommentDto createComment(long postId, CommentDto commentDto) {
		Comment comment = mapToEntity(commentDto);
		
		// retrieve post entity by id
		Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		
		//set post to comment entity
		comment.setPost(post);
		
		//save comment entity to the database
		
		Comment newComment =  commentRepository.save(comment);
		return mapToDTO(newComment);
	}
	
	private CommentDto mapToDTO(Comment comment) {
		
		CommentDto commentDto = mapper.map(comment, CommentDto.class);
		
//		CommentDto commentDto = new CommentDto();
//		commentDto.setId(comment.getId());
//		commentDto.setName(comment.getName());
//		commentDto.setEmail(comment.getEmail());
//		commentDto.setBody(comment.getBody());
		return commentDto;
	}
	
	private Comment mapToEntity(CommentDto commentDto) {
		
		Comment comment = mapper.map(commentDto, Comment.class);
		
//		Comment comment = new Comment();
//		comment.setId(commentDto.getId());
//		comment.setName(commentDto.getName());
//		comment.setEmail(commentDto.getEmail());
//		comment.setBody(commentDto.getBody());
		return comment;
	}

	@Override
	public List<CommentDto> getCommentsByPostId(long postId) {
		//retrive  comments by psotId
		List<Comment> comments = commentRepository.findByPostId(postId);
		return comments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());
	}

	@Override
	public CommentDto getCommentById(Long postId, Long commentId) {
		
		// retrieve post entity by id
		Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		
		//retrieve comment by id
		Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
				
		if(!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belongs to post");
		}
		
		return mapToDTO(comment);
	}

	@Override
	public CommentDto updateComment(Long postId, long commentId, CommentDto commentRequest) {
		
		//retrieve post entity by id
		Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		
		//retrieve comment by id
		Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
		
		if(!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belongs to post");
		}
		
		comment.setName(commentRequest.getName());
		comment.setEmail(commentRequest.getEmail());
		comment.setBody(commentRequest.getBody());
		
		 Comment updatedComment =  commentRepository.save(comment);
		 return mapToDTO(updatedComment);
		
	}

	@Override
	public void deleteComment(long postId, long commentId) {
		
		//retrieve post entity by id
		Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
				
		//retrieve comment by id
		Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
				
		if(!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belongs to post");
		}
		
		commentRepository.delete(comment);
	}

}
