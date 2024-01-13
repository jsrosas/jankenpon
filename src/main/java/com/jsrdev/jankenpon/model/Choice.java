package com.jsrdev.jankenpon.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public enum Choice {
    ROCK(0), PAPER(1), SCISSORS(2);

    private int playerChoice;
    private static Map<Integer, Choice> choiceToEnumValuesMapping = new HashMap<>();

    static {
        for (Choice choice : Choice.values()) {
            choiceToEnumValuesMapping.put(
                    choice.playerChoice,
                    choice
            );
        }
    }


    Choice(int playerChoice){
        this.playerChoice = playerChoice;
    }

    public int getPlayerChoice(){
        return playerChoice;
    }

    private static final Random random = new Random();
    public static Choice getRandom() {
        Choice[] choices = values();
        return choices[random.nextInt(choices.length)];
    }

    public static Choice castIntToEnum(int playerChoice) {
        return choiceToEnumValuesMapping.get(playerChoice);
    }
}
