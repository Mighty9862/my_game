package org.example.dto;

import lombok.Data;

@Data
public class UpdateQuestionDto {
    private String questionText;
    private String answerText;
    private int points;
}