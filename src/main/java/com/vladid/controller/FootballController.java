package com.vladid.controller;

import ch.qos.logback.core.util.DatePatternToRegexUtil;
import com.sun.org.apache.xml.internal.utils.StringToIntTable;
import com.sun.tracing.dtrace.ProviderAttributes;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.vladid.repository.*;
import com.vladid.entity.*;

import javax.annotation.security.PermitAll;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class FootballController {
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    FootballController(PlayerRepository playerRepository,
                       TeamRepository teamRepository){
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
    }
    @GetMapping("/football/team")
    public List<Team> allTeams(){
        return teamRepository.findAll();
    }

    @GetMapping("/football/player")
    public List<Player> allPlayers(){
        return playerRepository.findAll();
    }

    @GetMapping("/football/player{teamId}")
    public List<Player> playersOfTeam(@PathVariable String teamId){
        Long teamIdL = Long.valueOf(teamId);
        Team team = teamRepository.findTeamByIdTeam(teamIdL);
        return playerRepository.findAllByIdTeam(team);
    }

    @GetMapping("/football/captain{teamId}")
    public Player captainOfTeam(@PathVariable String teamId){
        Long teamIdL = Long.valueOf(teamId);
        Team team = teamRepository.findTeamByIdTeam(teamIdL);
        return team.getCaptain();
    }

    @PostMapping("/football/setCaptain{playerId}ToTeam{teamId}")
    public String setCaptainToTeam(@PathVariable String playerId, @PathVariable String teamId){
        Player player = playerRepository.findPlayerByIdPlayer(Long.valueOf(playerId));
        Team team = teamRepository.findTeamByIdTeam(Long.valueOf(teamId));
        if(player.getIdTeam() != null) {
            if (player.getIdTeam().getIdTeam() == Long.valueOf(teamId)) {
                team.setCaptain(player);
                teamRepository.save(team);
                return player.getNamePlayer() + " " + player.getSurnPlayer() + " began to be a captain of " + team.getNameTeam();
            } else {
                return player.getNamePlayer() + " " + player.getSurnPlayer() + " is not a member of " + team.getNameTeam();
            }
        }
        else{
            return "Player npt assigned to any team";
        }
    }

    @PostMapping("/football/setPlayer{playerId}ToTeam{teamId}")
    public String setPlayerToTeam(@PathVariable String playerId, @PathVariable String teamId){
        Player player = playerRepository.findPlayerByIdPlayer(Long.valueOf(playerId));
        if(player != null){
            if(player.getIdTeam() != null){
                Team team = teamRepository.findTeamByIdTeam(player.getIdTeam().getIdTeam());
                if(team.getCaptain().getIdPlayer() == player.getIdPlayer()){
                    team.setCaptain(null);
                    teamRepository.save(team);
                }
            }
            player.setIdTeam(teamRepository.findTeamByIdTeam(Long.valueOf(teamId)));
            playerRepository.save(player);
            return player.getNamePlayer()+" "+player.getSurnPlayer()+" assigned to team "+teamRepository.findTeamByIdTeam(Long.valueOf(teamId)).getNameTeam();
        }
        else return "No such player";
    }

    @PostMapping("/football/newPlayerToTeamName{name}Surn{surn}Position{position}BirthdayDay{day}Month{month}Year{year}Team{idTeam}")
    public String newPlayerToTeam(@PathVariable String name, @PathVariable String surn, @PathVariable String position, @PathVariable String day,
                                  @PathVariable String month, @PathVariable String year, @PathVariable String idTeam){
        if(name != null && surn != null && position != null && day != null && month != null && year!= null && idTeam != null) {
            String dateString = day+"-"+month+"-"+year;
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            Date date = new Date();
            try {
                date = format.parse(dateString);
            } catch (ParseException e){
            }
            Team idTeamT = teamRepository.findTeamByIdTeam(Long.valueOf(idTeam));
            Player player = new Player(name, surn, position, date, idTeamT);
            playerRepository.save(player);
            return player.getNamePlayer()+" "+player.getSurnPlayer()+" assigned to team "+ teamRepository.findTeamByIdTeam(Long.valueOf(idTeam)).getNameTeam();
        }
        else{
            return "One or more paramaters are null";
        }
    }

    @PostMapping("/football/newPlayerName{name}Surn{surn}Position{position}BirthdayDay{day}Month{month}Year{year}")
    public String newPlayer(@PathVariable String name, @PathVariable String surn, @PathVariable String position, @PathVariable String day,
                            @PathVariable String month, @PathVariable String year){
        if(name != null && surn != null && position != null && day != null && month != null && year!= null) {
            String dateString = day+"-"+month+"-"+year;
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            Date date = new Date();
            try {
                date = format.parse(dateString);
            } catch (ParseException e){
            }
            Player player = new Player(name, surn, position, date);
            playerRepository.save(player);
            return player.getNamePlayer()+" "+player.getSurnPlayer()+" created";
        }
        else{
            return "One or more paramaters are null";
        }
    }

    @PostMapping("/football/newTeamName{name}")
    public String newTeam(@PathVariable String name){
        if(name != null){
            Team team = new Team(name);
            teamRepository.save(team);
            return "Team "+team.getNameTeam()+" created";
        }
        else{
            return "Team name should not be empty";
        }
    }

    @PostMapping("/football/deletePlayer{playerId}")
    public String deletePlayer(@PathVariable String playerId){
        Player player = playerRepository.findPlayerByIdPlayer(Long.valueOf(playerId));
        if(player != null){
            if(player.getIdTeam() != null){
                Team team = teamRepository.findTeamByIdTeam(player.getIdTeam().getIdTeam());
                if(team.getCaptain().getIdPlayer() == player.getIdPlayer()){
                    team.setCaptain(null);
                    teamRepository.save(team);
                }
            }
            playerRepository.delete(player);
            return player.getNamePlayer()+" "+player.getSurnPlayer()+" deleted";
        }
        else{
            return "No such player";
        }
    }

    @PostMapping("/football/deleteTeam{teamId}")
    public String deleteTeam(@PathVariable String teamId){
        Team team = teamRepository.findTeamByIdTeam(Long.valueOf(teamId));
        if(team != null){
            List<Player> players = playerRepository.findAllByIdTeam(team);
            for(int i = 0; i < players.size(); i++){
                players.get(i).setIdTeam(null);
            }
            teamRepository.delete(team);
            return team.getNameTeam()+ " deleted";
        }
        else{
            return "No such team";
        }
    }

    @PostMapping("/football/updatePlayer{playerId}Name{name}Surn{surn}Position{position}BirthdayDay{day}Month{month}Year{year}")
    public String updatePlayer(@PathVariable String playerId, @PathVariable String name, @PathVariable String surn, @PathVariable String position, @PathVariable
            String day, @PathVariable String month, @PathVariable String year){
        Player player = playerRepository.findPlayerByIdPlayer(Long.valueOf(playerId));
        if(player != null){
            player.setNamePlayer(name);
            player.setSurnPlayer(surn);
            player.setPosition(position);
            String dateString = day+"-"+month+"-"+year;
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            Date date = new Date();
            try {
                date = format.parse(dateString);
            } catch (ParseException e){
            }
            player.setBirthday(date);
            playerRepository.save(player);
            return "Player updated";
        }
        else {
            return "No such player";
        }
    }

    @PostMapping("/football/updateTeam{teamId}Name{name}")
    public String updateTeam(@PathVariable String teamId, @PathVariable String name){
        Team team = teamRepository.findTeamByIdTeam(Long.valueOf(teamId));
        if(team != null){
            team.setNameTeam(name);
            teamRepository.save(team);
            return "Team updated";
        }
        else {
            return "No such team";
        }
    }

}
