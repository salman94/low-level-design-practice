package com.example.demo.snakeladder.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    private final Map<Integer, Integer> snakeMoves;
    private final Map<Integer, Integer> ladderMoves;
    private static Board ref;

    private Board() {
        snakeMoves = new HashMap<>();
        ladderMoves = new HashMap<>();
    }

    public static Board getInstance() {
        if(ref == null) {
            ref = new Board();
        }
        return ref;
    }

    public void initializeBoard(List<Position> snakes, List<Position> ladders) {
        snakes.forEach(snake -> {
            this.snakeMoves.put(snake.getStartPosition(), snake.getEndPosition());
        });
        ladders.forEach(snake -> {
            this.ladderMoves.put(snake.getStartPosition(), snake.getEndPosition());
        });
    }

    public Map<Integer, Integer> getSnakeMoves() {
        return new HashMap<>(snakeMoves);
    }

    public Map<Integer, Integer> getLadderMoves() {
        return new HashMap<>(ladderMoves);
    }
}
