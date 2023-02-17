package com.blog.app.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
	private Long id;
	private String name;
	private String description;

	private Set<PostDto> posts;
}
