package com.jsrdev.jankenpon.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {
    Game findByName(String name);

    List<Game> findAllByUserId(String id);
}
