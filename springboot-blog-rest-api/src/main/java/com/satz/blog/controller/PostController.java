package com.satz.blog.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.satz.blog.payload.PostDto;
import com.satz.blog.payload.PostResponse;
import com.satz.blog.service.PostService;
import com.satz.blog.utils.AppConstants;

@RestController
@RequestMapping("/api/posts")
public class PostController {
	
	private PostService postService;
	
	public PostController(PostService postService) {
		this.postService = postService;
	}

	// create blog post
	@PostMapping
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
		return new ResponseEntity<PostDto>(postService.createPost(postDto),HttpStatus.CREATED);
		
	}
	
	//get all post rest api
	@GetMapping
	public PostResponse getAllPosts(
			@RequestParam(value="pageNo",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) int pageNo,
			@RequestParam(value = "pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortby,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,required = false) String sortDir
			){
		return postService.getAllPosts(pageNo,pageSize,sortby,sortDir);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PostDto> getPostById(@PathVariable(name="id") long id){
//		return ResponseEntity.ok(postService.getPostById(id));
		return new ResponseEntity<PostDto>(postService.getPostById(id),HttpStatus.OK);
	}
	
	//update post by id rest api
	@PutMapping("/{id}")
	public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto,@PathVariable(name="id") long id){
		PostDto postResponse = postService.updatePost(postDto, id);
		return new ResponseEntity<>(postResponse,HttpStatus.OK);
	}
	
	//delete post rest api
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePost(@PathVariable(name="id") long id){
		postService.deletePostById(id);
		return new ResponseEntity<String>("Post entity deleted successfully.",HttpStatus.OK);
		
	}
}
