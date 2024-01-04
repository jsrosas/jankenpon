package com.jsrdev.jankenpon;

import com.jsrdev.jankenpon.model.Player;
import com.jsrdev.jankenpon.model.PlayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Initializer implements CommandLineRunner {
    @Autowired
    PlayerRepository playerRepository;

    private final Logger log = LoggerFactory.getLogger(Initializer.class);

    @Override
    public void run(String... args) throws Exception {
        Player computerDefaultPlayer = new Player();
        computerDefaultPlayer.setName("Default Computer");
        playerRepository.save(computerDefaultPlayer);
        playerRepository.findAll().forEach(player -> log.info(player.toString()));
    }
}
