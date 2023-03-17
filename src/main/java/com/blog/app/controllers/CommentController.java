package com.blog.app.controllers;

import com.blog.app.payload.CommentDto;
import com.blog.app.services.implementation.CommentServiceImplementation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/posts")
@Tag(
        name = "CRUD REST API for Comment resource",
        description = "CRUD REST API for Comment resource"
)
public class CommentController {
    private final CommentServiceImplementation commentService;

    @Autowired
    public CommentController(CommentServiceImplementation commentService) {
        this.commentService = commentService;
    }

    /**
     * create comment
     *
     * @param commentDto commentDto
     * @param postId postId
     * @return {@link ResponseEntity}
     * @see ResponseEntity
     * @see CommentDto
     */
    @PostMapping("/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(
            @Valid @RequestBody CommentDto commentDto,
            @PathVariable(value = "postId") long postId
    ) {
        return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
    }

    /**
     * get comments by post id
     *
     * @param postId postId
     * @return {@link ResponseEntity}
     * @see ResponseEntity
     * @see List
     */
    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(
            @PathVariable(value = "postId") long postId
    ) {
        return new ResponseEntity<>(commentService.getCommentsByPostId(postId), HttpStatus.OK);
    }

    /**
     * get comment by id
     *
     * @param postId postId
     * @param commentId commentId
     * @return {@link ResponseEntity}
     * @see ResponseEntity
     * @see CommentDto
     */
    @GetMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(
            @PathVariable(value = "postId") long postId, @PathVariable(value = "commentId") long commentId
    ) {
        return new ResponseEntity<>(commentService.getCommentById(postId, commentId), HttpStatus.OK);
    }

    /**
     * update comment
     *
     * @param postId postId
     * @param commentId commentId
     * @param commentDto commentDto
     * @return {@link ResponseEntity}
     * @see ResponseEntity
     * @see CommentDto
     */
    @PatchMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(
            @PathVariable(value = "postId") long postId,
            @PathVariable(value = "commentId") long commentId,
            @RequestBody CommentDto commentDto
    ) {
        return new ResponseEntity<>(commentService.updateComment(postId, commentId, commentDto), HttpStatus.OK);
    }

    /**
     * delete comment
     *
     * @param postId postId
     * @param commentId commentId
     * @return {@link ResponseEntity}
     * @see ResponseEntity
     * @see Map
     */
    @DeleteMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<Map<String, String>> deleteComment(
            @PathVariable(value = "postId") long postId,
            @PathVariable(value = "commentId") long commentId
    ) {
        return new ResponseEntity<>(Map.of(
                "message", commentService.deleteComment(postId, commentId)), HttpStatus.OK);
    }
}
