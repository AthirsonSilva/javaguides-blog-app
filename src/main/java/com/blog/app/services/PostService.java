package com.blog.app.services;

import com.blog.app.payload.PostDto;
import com.blog.app.payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    PostResponse getAllPosts(int pageNumber, int pageSize, String sortBy, String sortDirection);

    PostDto getPostById(Long id);

    PostDto updatePost(Long id, PostDto postDto);

    void deletePost(Long id);

    List<PostDto> getPostsByCategoryId(Long categoryId);
}
