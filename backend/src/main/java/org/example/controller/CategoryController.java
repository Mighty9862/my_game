package org.example.controller;

import org.example.dto.CategoryDto;
import org.example.dto.CreateCategoryDto;
import org.example.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/games/{gameId}/categories")
    public ResponseEntity<CategoryDto> createCategory(@PathVariable Long gameId, @RequestBody CreateCategoryDto dto) {
        return ResponseEntity.ok(categoryService.createCategory(gameId, dto));
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getCategory(id));
    }

    @GetMapping("/games/{gameId}/categories")
    public ResponseEntity<List<CategoryDto>> getCategoriesByGame(@PathVariable Long gameId) {
        return ResponseEntity.ok(categoryService.getCategoriesByGame(gameId));
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long id, @RequestBody CreateCategoryDto dto) {
        return ResponseEntity.ok(categoryService.updateCategory(id, dto));
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}