package org.example.service;

import org.example.dto.CategoryDto;
import org.example.dto.CreateCategoryDto;
import org.example.entity.Category;
import org.example.entity.Game;
import org.example.entity.GameStatus;
import org.example.repository.CategoryRepository;
import org.example.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private QuestionService questionService;

    @Transactional
    public CategoryDto createCategory(Long gameId, CreateCategoryDto dto) {
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new RuntimeException("Game not found"));
        if (game.getStatus() != GameStatus.PREPARING) {
            throw new RuntimeException("Cannot modify categories after game started");
        }
        Category category = new Category();
        category.setGame(game);
        category.setName(dto.getName());
        category = categoryRepository.save(category);
        return mapToDto(category);
    }

    public CategoryDto getCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
        return mapToDto(category);
    }

    public List<CategoryDto> getCategoriesByGame(Long gameId) {
        return categoryRepository.findByGameId(gameId).stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Transactional
    public CategoryDto updateCategory(Long id, CreateCategoryDto dto) { // Reuse CreateDto for update
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
        if (category.getGame().getStatus() != GameStatus.PREPARING) {
            throw new RuntimeException("Cannot modify categories after game started");
        }
        if (dto.getName() != null) {
            category.setName(dto.getName());
        }
        category = categoryRepository.save(category);
        return mapToDto(category);
    }

    @Transactional
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
        if (category.getGame().getStatus() != GameStatus.PREPARING) {
            throw new RuntimeException("Cannot delete categories after game started");
        }
        categoryRepository.deleteById(id);
    }

    CategoryDto mapToDto(Category category) {
        CategoryDto dto = new CategoryDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setQuestions(category.getQuestions().stream().map(questionService::mapToDto).collect(Collectors.toList()));
        return dto;
    }
}