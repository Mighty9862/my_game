package org.example.controller;

import org.example.dto.CreateQuestionDto;
import org.example.dto.QuestionDto;
import org.example.dto.UpdateQuestionDto;
import org.example.service.QuestionService;
import org.example.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private TeamService teamService;

    @PostMapping("/categories/{categoryId}/questions")
    public ResponseEntity<QuestionDto> createQuestion(@PathVariable Long categoryId, @RequestBody CreateQuestionDto dto) {
        return ResponseEntity.ok(questionService.createQuestion(categoryId, dto));
    }

    @GetMapping("/questions/{id}")
    public ResponseEntity<QuestionDto> getQuestion(@PathVariable Long id) {
        return ResponseEntity.ok(questionService.getQuestion(id));
    }

    @GetMapping("/categories/{categoryId}/questions")
    public ResponseEntity<List<QuestionDto>> getQuestionsByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(questionService.getQuestionsByCategory(categoryId));
    }

    @PutMapping("/questions/{id}")
    public ResponseEntity<QuestionDto> updateQuestion(@PathVariable Long id, @RequestBody UpdateQuestionDto dto) {
        return ResponseEntity.ok(questionService.updateQuestion(id, dto));
    }

    @DeleteMapping("/questions/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/questions/{id}/select")
    public ResponseEntity<QuestionDto> selectQuestion(@PathVariable Long id) {
        return ResponseEntity.ok(questionService.selectQuestion(id));
    }

    @GetMapping("/questions/{id}/answer")
    public ResponseEntity<String> getAnswer(@PathVariable Long id) {
        return ResponseEntity.ok(questionService.getAnswer(id));
    }

    // Для разрешения вопроса: mark answered, optionally award to team
    @PostMapping("/questions/{id}/resolve")
    public ResponseEntity<QuestionDto> resolveQuestion(@PathVariable Long id, @RequestBody Map<String, Long> body) {
        QuestionDto question = questionService.markAsAnswered(id);
        Long teamId = body.get("teamId");
        if (teamId != null) {
            teamService.awardPoints(teamId, question.getPoints());
        }
        return ResponseEntity.ok(question);
    }
}