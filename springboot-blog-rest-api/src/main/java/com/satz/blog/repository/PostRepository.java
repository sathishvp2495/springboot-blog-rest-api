package com.satz.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.satz.blog.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long>{

}
