package com.jsrdev.jankenpon;

import com.jsrdev.jankenpon.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class Initializer implements CommandLineRunner {
    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    MatchRepository matchRepository;

    private final Logger log = LoggerFactory.getLogger(Initializer.class);

    @Override
    public void run(String... args) throws Exception {
        Player computerDefaultPlayer = new Player();
        computerDefaultPlayer.setName("Default Computer");
        computerDefaultPlayer.setDefaultComputer(true);
        playerRepository.save(computerDefaultPlayer);
        playerRepository.findAll().forEach(player -> log.info(player.toString()));
    }
}
