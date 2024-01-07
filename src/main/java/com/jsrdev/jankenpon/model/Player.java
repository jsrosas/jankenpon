package com.jsrdev.jankenpon.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "players")
public class Player {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToMany(cascade = CascadeType.MERGE)
    private Set<Game> games;

    private boolean isDefaultComputer;

    public Player () {}

    public Player (String name){
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

    public Set<Game> getGames() {
        return games;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }

    public boolean isDefaultComputer() {
        return isDefaultComputer;
    }

    public void setDefaultComputer(boolean defaultComputer) {
        isDefaultComputer = defaultComputer;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
