package org.example.dto;

import lombok.Data;

@Data
public class QuestionDto {
    private Long id;
    private String questionText;
    private String answerText;
    private int points;
    private boolean answered; // Переименовано с isAnswered на answered
}