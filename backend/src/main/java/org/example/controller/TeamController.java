package org.example.controller;

import org.example.dto.CreateTeamDto;
import org.example.dto.TeamDto;
import org.example.dto.UpdateTeamDto;
import org.example.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @PostMapping("/games/{gameId}/teams")
    public ResponseEntity<TeamDto> createTeam(@PathVariable Long gameId, @RequestBody CreateTeamDto dto) {
        return ResponseEntity.ok(teamService.createTeam(gameId, dto));
    }

    @GetMapping("/teams/{id}")
    public ResponseEntity<TeamDto> getTeam(@PathVariable Long id) {
        return ResponseEntity.ok(teamService.getTeam(id));
    }

    @GetMapping("/games/{gameId}/teams")
    public ResponseEntity<List<TeamDto>> getTeamsByGame(@PathVariable Long gameId) {
        return ResponseEntity.ok(teamService.getTeamsByGame(gameId));
    }

    @PutMapping("/teams/{id}")
    public ResponseEntity<TeamDto> updateTeam(@PathVariable Long id, @RequestBody UpdateTeamDto dto) {
        return ResponseEntity.ok(teamService.updateTeam(id, dto));
    }

    @DeleteMapping("/teams/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id) {
        teamService.deleteTeam(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/games/{gameId}/ranking")
    public ResponseEntity<List<TeamDto>> getRanking(@PathVariable Long gameId) {
        return ResponseEntity.ok(teamService.getRanking(gameId));
    }
}