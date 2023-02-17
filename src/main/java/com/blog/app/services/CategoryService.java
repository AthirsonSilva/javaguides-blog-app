package com.blog.app.services;


import com.blog.app.payload.CategoryDto;

import java.util.List;


public interface CategoryService {
	CategoryDto createCategory(CategoryDto categoryDto);

	CategoryDto getCategoryById(Long id);

	List<CategoryDto> getAllCategories();

	CategoryDto updateCategory(Long id, CategoryDto categoryDto);

	void deleteCategory(Long id);
}
