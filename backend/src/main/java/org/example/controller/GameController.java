package org.example.controller;

import org.example.dto.CreateGameDto;
import org.example.dto.GameDto;
import org.example.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/games")
public class GameController {

    @Autowired
    private GameService gameService;

    @PostMapping
    public ResponseEntity<GameDto> createGame(@RequestBody CreateGameDto dto) {
        return ResponseEntity.ok(gameService.createGame(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameDto> getGame(@PathVariable Long id) {
        return ResponseEntity.ok(gameService.getGame(id));
    }

    @GetMapping
    public ResponseEntity<List<GameDto>> getAllGames() {
        return ResponseEntity.ok(gameService.getAllGames());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable Long id) {
        gameService.deleteGame(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/start")
    public ResponseEntity<GameDto> startGame(@PathVariable Long id) {
        return ResponseEntity.ok(gameService.startGame(id));
    }

    @PostMapping("/{id}/finish")
    public ResponseEntity<GameDto> finishGame(@PathVariable Long id) {
        return ResponseEntity.ok(gameService.finishGame(id));
    }
}