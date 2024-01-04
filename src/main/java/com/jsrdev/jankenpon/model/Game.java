package com.jsrdev.jankenpon.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "game")
public class Game {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private String name;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "player_one_id")
    private Player player1;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "player_two_id")
    private Player player2;

    public Game() {}

    public Game(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Game game = (Game) o;

        return Objects.equals(id, game.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
