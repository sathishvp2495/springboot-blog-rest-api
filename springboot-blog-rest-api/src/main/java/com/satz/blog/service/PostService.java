package com.satz.blog.service;

import java.util.List;

import com.satz.blog.payload.PostDto;
import com.satz.blog.payload.PostResponse;

public interface PostService {
	PostDto createPost(PostDto postDto);
	PostResponse getAllPosts(int pageNo,int pageSize,String sortBy,String sortDir);
	PostDto getPostById(Long id);
	PostDto updatePost(PostDto postDto,Long id);
	void deletePostById(Long id);
}
