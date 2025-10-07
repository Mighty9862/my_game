package org.example.dto;

import lombok.Data;
import org.example.entity.GameStatus;

import java.util.List;

@Data
public class GameDto {
    private Long id;
    private String title;
    private GameStatus status;
    private List<TeamDto> teams;
    private List<CategoryDto> categories;
}