package com.blog.app.repositories;

import com.blog.app.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	List<Post> findAllByCategoryId(Long categoryId);
}
