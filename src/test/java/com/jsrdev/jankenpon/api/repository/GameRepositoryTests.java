package com.jsrdev.jankenpon.api.repository;

import com.jsrdev.jankenpon.model.Game;
import com.jsrdev.jankenpon.model.GameRepository;
import com.jsrdev.jankenpon.model.User;
import com.jsrdev.jankenpon.model.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Stream;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class GameRepositoryTests {
    @Autowired
    GameRepository gameRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    public void GameRepository_Save_ReturnSavedGame(){
        Game game = new Game("Test");
        Game gameResult = gameRepository.save(game);
        Assertions.assertThat(gameResult).isNotNull();
        Assertions.assertThat(gameResult.getId()).isGreaterThan(0);
    }

    @Test
    public void GameRepository_FindAllByUserID_ReturnsAllGamesForUser(){
        ArrayList<Game> gameList = new ArrayList<>();
        User user =  userRepository.save(new User("1", "Test", "test@test.com"));
        Game game1 = new Game("Game1");
        Game game2 = new Game("Game2");
        game1.setUser(user);
        game2.setUser(user);
        gameList.add(game1);
        gameList.add(game2);
        gameRepository.saveAll(gameList);
        Collection<Game> gamesResult = gameRepository.findAllByUserId(user.getId());
        Assertions.assertThat(gamesResult).isNotNull();
        Assertions.assertThat(gamesResult.size()).isEqualTo(2);
        Stream<Game> result = gamesResult.stream().filter(game -> !Objects.equals(game.getUser().getId(), user.getId()));
        Assertions.assertThat(result.count()).isEqualTo(0);
    }
}
