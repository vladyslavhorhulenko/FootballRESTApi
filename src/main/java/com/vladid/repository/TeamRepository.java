package com.vladid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vladid.entity.*;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findAll();

    Team findTeamByIdTeam(Long idTeam);


}
