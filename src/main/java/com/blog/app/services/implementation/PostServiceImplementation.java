package com.blog.app.services.implementation;

import com.blog.app.entities.Category;
import com.blog.app.entities.Post;
import com.blog.app.exceptions.ResourceNotFoundException;
import com.blog.app.payload.PostDto;
import com.blog.app.payload.PostResponse;
import com.blog.app.repositories.CategoryRepository;
import com.blog.app.repositories.PostRepository;
import com.blog.app.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImplementation implements PostService {

	private final PostRepository postRepository;
	private final ModelMapper modelMapper;
	private final CategoryRepository categoryRepository;

	@Autowired
	public PostServiceImplementation(PostRepository postRepository, ModelMapper modelMapper, CategoryRepository categoryRepository) {
		this.postRepository = postRepository;
		this.modelMapper = modelMapper;
		this.categoryRepository = categoryRepository;
	}

	/**
	 * create post
	 *
	 * @param postDto postDto
	 * @return {@link PostDto}
	 * @see PostDto
	 */
	@Override
	public PostDto createPost(PostDto postDto) {
		Category category = categoryRepository.findById(postDto.getCategoryId())
			.orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));

		Post post = mapToEntity(postDto);
		post.setCategory(category);

		return mapToDto(postRepository.save(post));
	}

	/**
	 * get all posts
	 *
	 * @param pageNumber pageNumber
	 * @param pageSize pageSize
	 * @param sortBy sortBy
	 * @param sortDirection sortDirection
	 * @return {@link PostResponse}
	 * @see PostResponse
	 */
	@Override
	public PostResponse getAllPosts(int pageNumber, int pageSize, String sortBy, String sortDirection) {
		if (pageNumber < 0) {
			throw new IllegalArgumentException("Page number cannot be less than zero!");
		}

		Sort sort = sortDirection.equalsIgnoreCase(
			Sort.Direction.ASC.name())
			? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

		Page<Post> posts = postRepository.findAll(pageable);

		List<Post> listOfPosts = posts.getContent();

		return new PostResponse(
			listOfPosts.stream().map(this::mapToDto).collect(Collectors.toList()),
			posts.getNumber(),
			posts.getSize(),
			posts.getTotalElements(),
			posts.getTotalPages(),
			posts.isLast()
		);
	}

	/**
	 * get post by id
	 *
	 * @param id id
	 * @return {@link PostDto}
	 * @see PostDto
	 */
	@Override
	public PostDto getPostById(Long id) {
		Post post = postRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

		return mapToDto(post);
	}

	/**
	 * update post
	 *
	 * @param id id
	 * @param postDto postDto
	 * @return {@link PostDto}
	 * @see PostDto
	 */
	@Override
	public PostDto updatePost(Long id, PostDto postDto) {
		Post post = postRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

		Category category = categoryRepository.findById(postDto.getCategoryId())
			.orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));

		if (postDto.getTitle() != null)
			post.setTitle(postDto.getTitle());

		if (postDto.getDescription() != null)
			post.setDescription(postDto.getDescription());

		if (postDto.getContent() != null)
			post.setContent(postDto.getContent());

		if (postDto.getCategoryId() != null)
			post.setCategory(category);

		Post updatedPost = postRepository.save(post);

		return mapToDto(updatedPost);
	}

	/**
	 * delete post
	 *
	 * @param id id
	 */
	@Override
	public void deletePost(Long id) {
		Post post = postRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

		postRepository.delete(post);
	}

	private PostDto mapToDto(Post post) {
		return modelMapper.map(post, PostDto.class);
	}

	private Post mapToEntity(PostDto postDto) {
		return modelMapper.map(postDto, Post.class);
	}

	@Override
	public List<PostDto> getPostsByCategoryId(Long categoryId) {
		Category category = categoryRepository.findById(categoryId)
			.orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

		List<Post> posts = postRepository.findAllByCategoryId(category.getId());

		return posts.stream().map(this::mapToDto).collect(Collectors.toList());
	}
}
