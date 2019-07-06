package com.vladid.controller;

import ch.qos.logback.core.util.DatePatternToRegexUtil;
import com.sun.org.apache.xml.internal.utils.StringToIntTable;
import com.sun.tracing.dtrace.ProviderAttributes;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.vladid.repository.*;
import com.vladid.entity.*;
import com.vladid.service.FootballService;

import javax.annotation.security.PermitAll;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class FootballController {
    private final FootballService footballService;

    FootballController(FootballService footballService){
        this.footballService = footballService;
    }
    @GetMapping("/football/team")
    public List<Team> allTeams(){
        return footballService.allTeams();
    }

    @GetMapping("/football/player")
    public List<Player> allPlayers(){
        return footballService.allPlayers();
    }

    @GetMapping("/football/player{teamId}")
    public List<Player> playersOfTeam(@PathVariable String teamId){
        return footballService.playersOfTeam(teamId);
    }

    @GetMapping("/football/captain{teamId}")
    public Player captainOfTeam(@PathVariable String teamId){
        return footballService.captainOfTeam(teamId);
    }

    @PostMapping("/football/setCaptain{playerId}ToTeam{teamId}")
    public String setCaptainToTeam(@PathVariable String playerId, @PathVariable String teamId){
        return footballService.setCaptainToTeam(playerId, teamId);
    }

    @PostMapping("/football/setPlayer{playerId}ToTeam{teamId}")
    public String setPlayerToTeam(@PathVariable String playerId, @PathVariable String teamId){
        return footballService.setPlayerToTeam(playerId, teamId);
    }

    @PostMapping("/football/newPlayerToTeamName{name}Surn{surn}Position{position}BirthdayDay{day}Month{month}Year{year}Team{idTeam}")
    public String newPlayerToTeam(@PathVariable String name, @PathVariable String surn, @PathVariable String position, @PathVariable String day,
                                  @PathVariable String month, @PathVariable String year, @PathVariable String idTeam){
        return footballService.newPlayerToTeam(name, surn, position, day, month, year, idTeam);
    }

    @PostMapping("/football/newPlayerName{name}Surn{surn}Position{position}BirthdayDay{day}Month{month}Year{year}")
    public String newPlayer(@PathVariable String name, @PathVariable String surn, @PathVariable String position, @PathVariable String day,
                            @PathVariable String month, @PathVariable String year){
        return footballService.newPlayer(name, surn, position, day, month, year);
    }

    @PostMapping("/football/newTeamName{name}")
    public String newTeam(@PathVariable String name){
       return footballService.newTeam(name);
    }

    @DeleteMapping("/football/deletePlayer{playerId}")
    public String deletePlayer(@PathVariable String playerId){
       return footballService.deletePlayer(playerId);
    }

    @DeleteMapping("/football/deleteTeam{teamId}")
    public String deleteTeam(@PathVariable String teamId){
       return footballService.deleteTeam(teamId);
    }

    @PutMapping("/football/updatePlayer{playerId}Name{name}Surn{surn}Position{position}BirthdayDay{day}Month{month}Year{year}")
    public String updatePlayer(@PathVariable String playerId, @PathVariable String name, @PathVariable String surn, @PathVariable String position, @PathVariable
            String day, @PathVariable String month, @PathVariable String year){
        return footballService.updatePlayer(playerId, name, surn, position, day, month, year);
    }

    @PutMapping("/football/updateTeam{teamId}Name{name}")
    public String updateTeam(@PathVariable String teamId, @PathVariable String name){
       return footballService.updateTeam(teamId, name);
    }

}
