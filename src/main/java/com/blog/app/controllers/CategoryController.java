package com.blog.app.controllers;

import com.blog.app.payload.CategoryDto;
import com.blog.app.services.implementation.CategoryServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
	private final CategoryServiceImplementation categoryService;

	@Autowired
	public CategoryController(CategoryServiceImplementation categoryService) {
		this.categoryService = categoryService;
	}

	/**
	 * create category
	 *
	 * @param categoryDto categoryDto
	 * @return {@link ResponseEntity}
	 * @see ResponseEntity
	 * @see CategoryDto
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
		// returns a response entity with a status code of 201 (created) and the categoryDto
		return new ResponseEntity<>(categoryService.createCategory(categoryDto), HttpStatus.CREATED);
	}

	/**
	 * @param id id
	 */
	@GetMapping("/{id}")
	public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id) {
		return new ResponseEntity<>(categoryService.getCategoryById(id), HttpStatus.OK);
	}

	/**
	 * @param id id
	 * @return {@link ResponseEntity}
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, String>> deleteCategory(@PathVariable Long id) {
		categoryService.deleteCategory(id);
		return new ResponseEntity<>(Map.of("Message", "Category deleted!"), HttpStatus.OK);
	}

	/**
	 * @return {@link ResponseEntity}
	 */
	@GetMapping
	public ResponseEntity<List<CategoryDto>> getAllCategories() {
		return new ResponseEntity<>(categoryService.getAllCategories(), HttpStatus.OK);
	}

	/**
	 * update category
	 *
	 * @param id          id
	 * @param categoryDto categoryDto
	 * @return {@link ResponseEntity}
	 * @see ResponseEntity
	 * @see CategoryDto
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@PatchMapping("/{id}")
	public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long id, @RequestBody CategoryDto categoryDto) {
		return new ResponseEntity<>(categoryService.updateCategory(id, categoryDto), HttpStatus.OK);
	}
}
