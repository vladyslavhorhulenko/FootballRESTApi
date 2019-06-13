package com.vladid.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Date;

@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property = "idPlayer")
@Table(name = "player")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPlayer;

    @Column(name = "name_player")
    private String namePlayer;

    @Column(name = "surn_player")
    private String surnPlayer;

    @Column(name = "position")
    private String position;

    @Column(name = "birthday")
    private Date birthday;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_team")
    private Team idTeam;

    public Player(String namePlayer, String surnPlayer, String position, Date birthday, Team idTeam){
        this.setNamePlayer(namePlayer);
        this.setSurnPlayer(surnPlayer);
        this.setBirthday(birthday);
        this.setPosition(position);
        this.setIdTeam(idTeam);
    }

    public Player(String namePlayer, String surnPlayer, String position, Date birthday){
        this.setNamePlayer(namePlayer);
        this.setSurnPlayer(surnPlayer);
        this.setBirthday(birthday);
        this.setPosition(position);
    }

    public Player(){}

    public Long getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(Long idPlayer) {
        this.idPlayer = idPlayer;
    }

    public String getNamePlayer() {
        return namePlayer;
    }

    public void setNamePlayer(String namePlayer) {
        this.namePlayer = namePlayer;
    }

    public String getSurnPlayer() {
        return surnPlayer;
    }

    public void setSurnPlayer(String surnPlayer) {
        this.surnPlayer = surnPlayer;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Team getIdTeam() {
        return idTeam;
    }

    public void setIdTeam(Team idTeam) {
        this.idTeam = idTeam;
    }
}

