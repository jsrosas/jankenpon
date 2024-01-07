package com.jsrdev.jankenpon.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "match")
public class Match {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne(cascade = CascadeType.MERGE)
    private User user;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JsonIgnore
    private Game game;

    private int player1Choice;

    private int player2Choice;

    public Match() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getPlayer1Choice() {
        return player1Choice;
    }

    public void setPlayer1Choice(int player1Choice) {
        this.player1Choice = player1Choice;
    }

    public int getPlayer2Choice() {
        return player2Choice;
    }

    public void setPlayer2Choice(int player2Choice) {
        this.player2Choice = player2Choice;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
