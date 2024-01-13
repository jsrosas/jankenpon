package com.jsrdev.jankenpon.api.repository;

import com.jsrdev.jankenpon.model.User;
import com.jsrdev.jankenpon.model.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PlayerRepositoryTests {

    @Autowired
    UserRepository userRepository;

    @Test
    public void UserRepository_FindByID_ReturnUser(){
        User user = new User("id", "test", "test@test.com");
        userRepository.save(user);
        User userResult = userRepository.findById(user.getId()).get();
        Assertions.assertThat(userResult).isNotNull();
        Assertions.assertThat(userResult.getName()).isEqualTo(user.getName());
        Assertions.assertThat(userResult.getEmail()).isEqualTo(user.getEmail());
    }
}
