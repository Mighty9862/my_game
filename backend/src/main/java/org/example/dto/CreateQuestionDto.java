package org.example.dto;

import lombok.Data;

@Data
public class CreateQuestionDto {
    private String questionText;
    private String answerText;
    private int points;
}