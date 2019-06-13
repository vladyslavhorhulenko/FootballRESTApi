package com.vladid.entity;
import com.fasterxml.jackson.annotation.*;
import com.vladid.entity.Player;
import org.aspectj.apache.bcel.util.Play;

import javax.persistence.*;
import java.util.List;

@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property = "idTeam")
@Table(name = "team")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTeam;

    @Column(name = "name_team")
    private String nameTeam;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_captain")
    private Player idCaptain;

    public Team(String nameTeam, Player captain){
        this.setNameTeam(nameTeam);
        this.setCaptain(captain);
    }

    public Team(String nameTeam){
        this.setNameTeam(nameTeam);
    }

    public Team(){}

    public Long getIdTeam() {
        return idTeam;
    }

    public void setIdTeam(Long idTeam) {
        this.idTeam = idTeam;
    }

    public void setNameTeam(String nameTeam){
        this.nameTeam = nameTeam;
    }

    public String getNameTeam(){
        return this.nameTeam;
    }

    public Player getCaptain(){
        return this.idCaptain;
    }

    public void setCaptain(Player captain){
        this.idCaptain = captain;
    }
}
