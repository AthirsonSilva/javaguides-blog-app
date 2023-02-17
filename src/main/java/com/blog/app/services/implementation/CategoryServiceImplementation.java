package com.blog.app.services.implementation;

import com.blog.app.entities.Category;
import com.blog.app.exceptions.ResourceNotFoundException;
import com.blog.app.payload.CategoryDto;
import com.blog.app.repositories.CategoryRepository;
import com.blog.app.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImplementation implements CategoryService {
	private final CategoryRepository categoryRepository;
	private final ModelMapper modelMapper;

	@Autowired
	public CategoryServiceImplementation(CategoryRepository categoryRepository, ModelMapper modelMapper) {
		this.categoryRepository = categoryRepository;
		this.modelMapper = modelMapper;
	}


	/**
	 * create category
	 *
	 * @param categoryDto categoryDto
	 * @return {@link CategoryDto}
	 * @see CategoryDto
	 */
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		// map categoryDto to category
		Category category = modelMapper.map(categoryDto, Category.class);

		// save category
		Category savedCategory = categoryRepository.save(category);

		// map savedCategory to categoryDto and return
		return modelMapper.map(savedCategory, CategoryDto.class);
	}


	/**
	 * get category by id
	 *
	 * @param id id
	 * @return {@link CategoryDto}
	 * @see CategoryDto
	 */
	@Override
	public CategoryDto getCategoryById(Long id) {
		// find category by id
		Category category = categoryRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));

		return modelMapper.map(category, CategoryDto.class);
	}


	/**
	 * get all categories
	 *
	 * @return {@link List}
	 * @see List
	 * @see CategoryDto
	 */
	@Override
	public List<CategoryDto> getAllCategories() {
		// get all categories
		List<Category> categories = categoryRepository.findAll();

		return categories
			.stream()
			// map categories to categoryDto
			.map(category -> modelMapper.map(category, CategoryDto.class))
			// collect to list
			.collect(Collectors.toList());
	}

	/**
	 * update category
	 *
	 * @param id          id
	 * @param categoryDto categoryDto
	 * @return {@link CategoryDto}
	 * @see CategoryDto
	 */
	@Override
	public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
		// find category by id or throw exception
		Category categoryToUpdate = categoryRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));

		// update category for not empty fields
		if (categoryDto.getName() != null)
			categoryToUpdate.setName(categoryDto.getName());

		if (categoryDto.getDescription() != null)
			categoryToUpdate.setDescription(categoryDto.getDescription());

		// save updated category and return
		Category updatedCategory = categoryRepository.save(categoryToUpdate);

		return modelMapper.map(updatedCategory, CategoryDto.class);
	}

	/**
	 * delete category
	 *
	 * @param id id
	 */
	@Override
	public void deleteCategory(Long id) {
		// find category by id and delete
		Category categoryToDelete = categoryRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));

		categoryRepository.delete(categoryToDelete);
	}
}
