package org.example.service;

import org.example.dto.CreateGameDto;
import org.example.dto.GameDto;
import org.example.entity.Game;
import org.example.entity.GameStatus;
import org.example.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private TeamService teamService;

    @Autowired
    private CategoryService categoryService;

    @Transactional
    public GameDto createGame(CreateGameDto dto) {
        Game game = new Game();
        game.setTitle(dto.getTitle());
        game.setStatus(GameStatus.PREPARING);
        game = gameRepository.save(game);
        return mapToDto(game);
    }

    public GameDto getGame(Long id) {
        Game game = gameRepository.findById(id).orElseThrow(() -> new RuntimeException("Game not found"));
        return mapToDto(game);
    }

    public List<GameDto> getAllGames() {
        return gameRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Transactional
    public void deleteGame(Long id) {
        gameRepository.deleteById(id);
    }

    @Transactional
    public GameDto startGame(Long id) {
        Game game = gameRepository.findById(id).orElseThrow(() -> new RuntimeException("Game not found"));
        if (game.getStatus() != GameStatus.PREPARING) {
            throw new RuntimeException("Game already started or finished");
        }
        game.setStatus(GameStatus.STARTED);
        game = gameRepository.save(game);
        return mapToDto(game);
    }

    @Transactional
    public GameDto finishGame(Long id) {
        Game game = gameRepository.findById(id).orElseThrow(() -> new RuntimeException("Game not found"));
        if (game.getStatus() != GameStatus.STARTED) {
            throw new RuntimeException("Game not started");
        }
        game.setStatus(GameStatus.FINISHED);
        game = gameRepository.save(game);
        return mapToDto(game);
    }

    private GameDto mapToDto(Game game) {
        GameDto dto = new GameDto();
        dto.setId(game.getId());
        dto.setTitle(game.getTitle());
        dto.setStatus(game.getStatus());
        dto.setTeams(game.getTeams().stream().map(teamService::mapToDto).collect(Collectors.toList()));
        dto.setCategories(game.getCategories().stream().map(categoryService::mapToDto).collect(Collectors.toList()));
        return dto;
    }
}