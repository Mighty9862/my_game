package org.example.service;

import org.example.dto.CreateQuestionDto;
import org.example.dto.QuestionDto;
import org.example.dto.UpdateQuestionDto;
import org.example.entity.Category;
import org.example.entity.GameStatus;
import org.example.entity.Question;
import org.example.repository.CategoryRepository;
import org.example.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    public QuestionDto createQuestion(Long categoryId, CreateQuestionDto dto) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));
        if (category.getGame().getStatus() != GameStatus.PREPARING) {
            throw new RuntimeException("Cannot modify questions after game started");
        }
        Question question = new Question();
        question.setCategory(category);
        question.setQuestionText(dto.getQuestionText());
        question.setAnswerText(dto.getAnswerText());
        question.setPoints(dto.getPoints());
        question.setAnswered(false); // Исправлено
        question = questionRepository.save(question);
        return mapToDto(question);
    }

    public QuestionDto getQuestion(Long id) {
        Question question = questionRepository.findById(id).orElseThrow(() -> new RuntimeException("Question not found"));
        return mapToDto(question);
    }

    public List<QuestionDto> getQuestionsByCategory(Long categoryId) {
        return questionRepository.findByCategoryId(categoryId).stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Transactional
    public QuestionDto updateQuestion(Long id, UpdateQuestionDto dto) {
        Question question = questionRepository.findById(id).orElseThrow(() -> new RuntimeException("Question not found"));
        if (question.getCategory().getGame().getStatus() != GameStatus.PREPARING) {
            throw new RuntimeException("Cannot modify questions after game started");
        }
        if (dto.getQuestionText() != null) {
            question.setQuestionText(dto.getQuestionText());
        }
        if (dto.getAnswerText() != null) {
            question.setAnswerText(dto.getAnswerText());
        }
        if (dto.getPoints() != 0) {
            question.setPoints(dto.getPoints());
        }
        question = questionRepository.save(question);
        return mapToDto(question);
    }

    @Transactional
    public void deleteQuestion(Long id) {
        Question question = questionRepository.findById(id).orElseThrow(() -> new RuntimeException("Question not found"));
        if (question.getCategory().getGame().getStatus() != GameStatus.PREPARING) {
            throw new RuntimeException("Cannot delete questions after game started");
        }
        questionRepository.deleteById(id);
    }

    @Transactional
    public QuestionDto markAsAnswered(Long id) {
        Question question = questionRepository.findById(id).orElseThrow(() -> new RuntimeException("Question not found"));
        if (question.getCategory().getGame().getStatus() != GameStatus.STARTED) {
            throw new RuntimeException("Game not started");
        }
        if (question.isAnswered()) { // Исправлено
            throw new RuntimeException("Question already answered");
        }
        question.setAnswered(true); // Исправлено
        question = questionRepository.save(question);
        return mapToDto(question);
    }

    public QuestionDto selectQuestion(Long id) {
        Question question = questionRepository.findById(id).orElseThrow(() -> new RuntimeException("Question not found"));
        if (question.isAnswered()) { // Исправлено
            throw new RuntimeException("Question already answered");
        }
        return mapToDto(question);
    }

    public String getAnswer(Long id) {
        Question question = questionRepository.findById(id).orElseThrow(() -> new RuntimeException("Question not found"));
        return question.getAnswerText();
    }

    QuestionDto mapToDto(Question question) {
        QuestionDto dto = new QuestionDto();
        dto.setId(question.getId());
        dto.setQuestionText(question.getQuestionText());
        dto.setAnswerText(question.getAnswerText());
        dto.setPoints(question.getPoints());
        dto.setAnswered(question.isAnswered()); // Исправлено
        return dto;
    }
}