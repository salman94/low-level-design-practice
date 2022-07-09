package com.example.demo.snakeladder.service;

import java.util.Random;

public class DiceRollStrategyImpl implements DiceRollStrategy {
    private Random random;
    private int max;
    private int min;

    public DiceRollStrategyImpl(int max, int min) {
        this.random = new Random();
        this.max = max;
        this.min = min;
    }

    @Override
    public int getDiceValue() {
        return random.nextInt((this.max - this.min) + 1) + 1;
    }
}
