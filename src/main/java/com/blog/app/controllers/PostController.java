package com.blog.app.controllers;

import com.blog.app.payload.PostDto;
import com.blog.app.payload.PostResponse;
import com.blog.app.services.PostService;
import com.blog.app.utils.AppConstants;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/posts")
@Tag(
        name = "CRUD REST API for Post resource",
        description = "CRUD REST API for Post resource"
)
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    /**
     * create post
     *
     * @param postDto postDto
     * @return {@link ResponseEntity}
     * @see ResponseEntity
     * @see PostDto
     */
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    /**
     * get all posts
     *
     * @param pageNo pageNo
     * @param pageSize pageSize
     * @param sortBy sortBy
     * @param sortDirection sortDirection
     * @return {@link ResponseEntity}
     * @see ResponseEntity
     * @see PostResponse
     */
    @GetMapping
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(
                    value = "pageNumber", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false
            ) int pageNo,
            @RequestParam(
                    value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false
            ) int pageSize,
            @RequestParam(
                    value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false
            ) String sortBy,
            @RequestParam(
                    value = "sortDirection", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false
            ) String sortDirection
    ) {
        return new ResponseEntity<>(postService.getAllPosts(pageNo, pageSize, sortBy, sortDirection), HttpStatus.OK);
    }

    /**
     * get post by id
     *
     * @param id id
     * @return {@link ResponseEntity}
     * @see ResponseEntity
     * @see PostDto
     */
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(postService.getPostById(id), HttpStatus.OK);
    }

    /**
     * update post
     *
     * @param id id
     * @param postDto postDto
     * @return {@link ResponseEntity}
     * @see ResponseEntity
     * @see PostDto
     */
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable(name = "id") Long id, @RequestBody PostDto postDto) {
        return new ResponseEntity<>(postService.updatePost(id, postDto), HttpStatus.OK);
    }

    /**
     * delete post
     *
     * @param id id
     * @return {@link ResponseEntity}
     * @see ResponseEntity
     * @see Map
     */
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deletePost(@PathVariable(name = "id") Long id) {
        postService.deletePost(id);

        return new ResponseEntity<>(Map.of("message", "Post deleted successfully!"), HttpStatus.OK);
    }

    /**
     * get posts by category
     *
     * @param id ida
     * @return {@link ResponseEntity}
     * @see ResponseEntity
     * @see List
     */
    @GetMapping("/category/{id}")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(postService.getPostsByCategoryId(id), HttpStatus.OK);
    }
}
