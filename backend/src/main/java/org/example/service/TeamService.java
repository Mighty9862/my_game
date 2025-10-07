package org.example.service;

import org.example.dto.CreateTeamDto;
import org.example.dto.TeamDto;
import org.example.dto.UpdateTeamDto;
import org.example.entity.Game;
import org.example.entity.GameStatus;
import org.example.entity.Team;
import org.example.repository.GameRepository;
import org.example.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private GameRepository gameRepository;

    @Transactional
    public TeamDto createTeam(Long gameId, CreateTeamDto dto) {
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new RuntimeException("Game not found"));
        if (game.getStatus() != GameStatus.PREPARING) {
            throw new RuntimeException("Cannot modify teams after game started");
        }
        Team team = new Team();
        team.setGame(game);
        team.setName(dto.getName());
        team.setScore(0);
        team = teamRepository.save(team);
        return mapToDto(team);
    }

    public TeamDto getTeam(Long id) {
        Team team = teamRepository.findById(id).orElseThrow(() -> new RuntimeException("Team not found"));
        return mapToDto(team);
    }

    public List<TeamDto> getTeamsByGame(Long gameId) {
        return teamRepository.findByGameId(gameId).stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Transactional
    public TeamDto updateTeam(Long id, UpdateTeamDto dto) {
        Team team = teamRepository.findById(id).orElseThrow(() -> new RuntimeException("Team not found"));
        if (team.getGame().getStatus() != GameStatus.PREPARING) {
            throw new RuntimeException("Cannot modify teams after game started");
        }
        if (dto.getName() != null) {
            team.setName(dto.getName());
        }
        if (dto.getScore() != 0) { // Allow updating score during game? No, use separate method for scores
            throw new RuntimeException("Use awardPoints for score updates");
        }
        team = teamRepository.save(team);
        return mapToDto(team);
    }

    @Transactional
    public void deleteTeam(Long id) {
        Team team = teamRepository.findById(id).orElseThrow(() -> new RuntimeException("Team not found"));
        if (team.getGame().getStatus() != GameStatus.PREPARING) {
            throw new RuntimeException("Cannot delete teams after game started");
        }
        teamRepository.deleteById(id);
    }

    @Transactional
    public TeamDto awardPoints(Long teamId, int points) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new RuntimeException("Team not found"));
        if (team.getGame().getStatus() != GameStatus.STARTED) {
            throw new RuntimeException("Game not started");
        }
        team.setScore(team.getScore() + points);
        team = teamRepository.save(team);
        return mapToDto(team);
    }

    public List<TeamDto> getRanking(Long gameId) {
        return getTeamsByGame(gameId).stream()
                .sorted((t1, t2) -> Integer.compare(t2.getScore(), t1.getScore()))
                .collect(Collectors.toList());
    }

    TeamDto mapToDto(Team team) {
        TeamDto dto = new TeamDto();
        dto.setId(team.getId());
        dto.setName(team.getName());
        dto.setScore(team.getScore());
        return dto;
    }
}