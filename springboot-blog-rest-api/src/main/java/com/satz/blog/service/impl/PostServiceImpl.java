package com.satz.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.satz.blog.entity.Post;
import com.satz.blog.exception.ResourceNotFoundException;
import com.satz.blog.payload.PostDto;
import com.satz.blog.payload.PostResponse;
import com.satz.blog.repository.PostRepository;
import com.satz.blog.service.PostService;

@Service
public class PostServiceImpl implements PostService {

	// constructor based dependancy Injection
	// If it is only one constructor then we can omit @autowired configuration

	private PostRepository postRepository;
	
	private ModelMapper mapper;

	@Autowired
	public PostServiceImpl(PostRepository postRepository,ModelMapper mapper) {
		this.postRepository = postRepository;
		this.mapper = mapper;
	}

	@Override
	public PostDto createPost(PostDto postDto) {

		// convert DTO to entity
		Post post = mapToEntiry(postDto);
		Post newPost = postRepository.save(post);

		// convert entity to DTO
		PostDto postResponse = mapToDto(newPost);
		return postResponse;
	}

	@Override
	public PostResponse getAllPosts(int pageNo,int pageSize,String sortBy,String sortDir) {
		//create pageable instance
//		Pageable pageable = PageRequest.of(pageNo, pageSize);
		
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		
		Page<Post> posts = postRepository.findAll(pageable);
		
		// get content from page object
		List<Post> listOfPosts = posts.getContent();
		
		
//		List<Post> posts = postRepository.findAll();
//		return posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
		
//		return listOfPosts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
		
		List<PostDto> content =  listOfPosts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(content);
		postResponse.setPageNo(posts.getNumber());
		postResponse.setPageSize(posts.getSize());
		postResponse.setTotalElements(posts.getTotalElements());
		postResponse.setTotalPages(posts.getTotalPages());
		postResponse.setLast(posts.isLast());
		
		return postResponse;
		
	}

	// convert Entity into DTO
	private PostDto mapToDto(Post post) {
		
		PostDto postDto = mapper.map(post, PostDto.class);		
		
//		PostDto postDto = new PostDto();
//		postDto.setId(post.getId());
//		postDto.setTitle(post.getTitle());
//		postDto.setDescription(post.getDescription());
//		postDto.setContent(post.getContent());
		return postDto;
	}

	// convert DTO into Entity
	private Post mapToEntiry(PostDto postDto) {
		
		Post post = mapper.map(postDto, Post.class);	
		
//		Post post = new Post();
//		post.setTitle(postDto.getTitle());
//		post.setDescription(postDto.getDescription());
//		post.setContent(postDto.getContent());
		return post;
	}

	@Override
	public PostDto getPostById(Long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		return mapToDto(post);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Long id) {
		// get post by id from the database
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setDescription(postDto.getDescription());
		
		Post updatedPost = postRepository.save(post);
		return mapToDto(updatedPost);
	}

	@Override
	public void deletePostById(Long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		postRepository.delete(post);
	}

}
