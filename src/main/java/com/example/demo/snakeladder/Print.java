package com.example.demo.snakeladder;

public class Print {
    public void printMove(String playerName, Integer diceValue, int from, int to) {
        System.out.println(String.format("%s rolled a %s and moved from %s to %s", playerName, diceValue, from, to));
    }
}
