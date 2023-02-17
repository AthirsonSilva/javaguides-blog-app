package com.blog.app.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class PostDto {
	private long id;
	@NotEmpty(message = "Title is required")
	@Size(min = 3, max = 50, message = "Title must be between 3 and 50 characters long")
	private String title;

	@NotEmpty(message = "Description is required")
	@Size(min = 3, max = 50, message = "Description must be between 3 and 50 characters long")
	private String description;

	@NotEmpty(message = "Content is required")
	@Size(min = 3, max = 50, message = "Content must be between 3 and 50 characters long")
	private String content;

	private Set<CommentDto> comments;

	@NotNull(message = "Category id is required")
	private Long categoryId;
}
