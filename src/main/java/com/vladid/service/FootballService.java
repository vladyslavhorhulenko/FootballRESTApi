package com.vladid.service;
import com.vladid.repository.TeamRepository;
import com.vladid.repository.PlayerRepository;
import com.vladid.entity.Team;
import com.vladid.entity.Player;

import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class FootballService {
    private TeamRepository teamRepository;
    private PlayerRepository playerRepository;

    public FootballService(TeamRepository teamRepository, PlayerRepository playerRepository){
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
    }

    public List<Team> allTeams(){
        return teamRepository.findAll();
    }

    public List<Player> allPlayers(){
        return playerRepository.findAll();
    }

    public List<Player> playersOfTeam(String teamId){
        Long teamIdL = Long.valueOf(teamId);
        Team team = teamRepository.findTeamByIdTeam(teamIdL);
        return playerRepository.findAllByIdTeam(team);
    }

    public Player captainOfTeam(String teamId){
        Long teamIdL = Long.valueOf(teamId);
        Team team = teamRepository.findTeamByIdTeam(teamIdL);
        return team.getCaptain();
    }

    public String setCaptainToTeam(String playerId, String teamId){
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

    public String setPlayerToTeam(String playerId, String teamId){
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

    public String newPlayerToTeam(String name, String surn, String position, String day,
                                  String month, String year, String idTeam){
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

    public String newPlayer(String name, String surn, String position, String day,
                            String month, String year){
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

    public String newTeam(String name){
        if(name != null){
            Team team = new Team(name);
            teamRepository.save(team);
            return "Team "+team.getNameTeam()+" created";
        }
        else{
            return "Team name should not be empty";
        }
    }

    public String deletePlayer(String playerId){
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

    public String deleteTeam(String teamId){
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

    public String updatePlayer(String playerId, String name, String surn, String position,
            String day, String month, String year){
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

    public String updateTeam(String teamId, String name){
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
