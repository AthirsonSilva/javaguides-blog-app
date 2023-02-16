package com.blog.app.services.implementation;

import com.blog.app.entities.Comment;
import com.blog.app.entities.Post;
import com.blog.app.exceptions.BlogAPIException;
import com.blog.app.exceptions.ResourceNotFoundException;
import com.blog.app.payload.CommentDto;
import com.blog.app.repositories.CommentRepository;
import com.blog.app.repositories.PostRepository;
import com.blog.app.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 *  comment service implementation
 *
 */
@Service
public class CommentServiceImplementation implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CommentServiceImplementation(CommentRepository commentRepository, PostRepository postRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * create comment
     *
     * @param postId postId
     * @param commentDto commentDto
     * @return {@link CommentDto}
     * @see CommentDto
     */
    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        // Map commentDto to comment entity
        Comment comment = mapToEntity(commentDto);

        // Get post by the given postId
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        // Set post to comment
        comment.setPost(post);

        return mapToDto(commentRepository.save(comment));
    }

    /**
     * get comments by post id
     *
     * @param postId postId
     * @return {@link List}
     * @see List
     * @see CommentDto
     */
    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        // Get post by the given postId
        List<Comment> comments = commentRepository.findByPostId(postId);

        // Map comments to commentDto list and return
        return comments.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    /**
     * get comment by id
     *
     * @param postId postId
     * @param commentId commentId
     * @return {@link CommentDto}
     * @see CommentDto
     */
    @Override
    public CommentDto getCommentById(long postId, long commentId) {
        // Get comment by the given commentId
        Comment comment = verifyPostCommentRelation(postId, commentId);

        // Map comment to commentDto and return
        return mapToDto(comment);
    }

    /**
     * verify post comment relation
     *
     * @param postId postId
     * @param commentId commentId
     * @return {@link Comment}
     * @see Comment
     */
    private Comment verifyPostCommentRelation(long postId, long commentId) {
        // Get post by the given postId and throw exception if post not found
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        // Get comment by the given commentId and throw exception if comment not found
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        // Check if comment belongs to post
        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        return comment;
    }

    /**
     * update comment
     *
     * @param postId postId
     * @param commentId commentId
     * @param commentDto commentDto
     * @return {@link CommentDto}
     * @see CommentDto
     */
    @Override
    public CommentDto updateComment(long postId, long commentId, CommentDto commentDto) {
        // Get comment by the given commentId
        Comment comment = verifyPostCommentRelation(postId, commentId);

        /*
        *  Update comment fields if they are not null
        * */
        if (commentDto.getBody() != null)
            comment.setBody(commentDto.getBody());

        if (commentDto.getEmail() != null)
            comment.setEmail(commentDto.getEmail());

        if (commentDto.getName() != null)
            comment.setName(commentDto.getName());

        // Save updated comment and return
        Comment updatedComment = commentRepository.save(comment);

        return mapToDto(updatedComment);
    }

    /**
     * delete comment
     *
     * @param postId postId
     * @param commentId commentId
     * @return {@link String}
     * @see String
     */
    @Override
    public String deleteComment(long postId, long commentId) {
        // Get comment by the given commentId
        Comment comment = verifyPostCommentRelation(postId, commentId);

        // Delete comment
        commentRepository.delete(comment);

        return "Comment deleted successfully";
    }

    /**
     * map to dto
     *
     * @param comment comment
     * @return {@link CommentDto}
     * @see CommentDto
     */
    private CommentDto mapToDto(Comment comment) {
        return modelMapper.map(comment, CommentDto.class);
    }

    /**
     * map to entity
     *
     * @param commentDto commentDto
     * @return {@link Comment}
     * @see Comment
     */
    private Comment mapToEntity(CommentDto commentDto) {
        return modelMapper.map(commentDto, Comment.class);
    }
}
