package com.vladid.repository;

import com.vladid.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    @Override
    List<Player> findAll();

    List<Player> findAllByIdTeam(Team team);

    Player findPlayerByIdPlayer(Long idPlayer);


}