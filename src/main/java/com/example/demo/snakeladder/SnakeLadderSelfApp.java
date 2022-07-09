package com.example.demo.snakeladder;

import com.example.demo.snakeladder.model.Board;
import com.example.demo.snakeladder.model.Player;
import com.example.demo.snakeladder.model.Position;
import com.example.demo.snakeladder.service.DiceRollStrategyImpl;
import com.example.demo.snakeladder.service.GameService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class SnakeLadderSelfApp {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int numberOfSnake = Integer.parseInt(reader.readLine());
        List<Position> snakePositions = new ArrayList<>();

        while ((numberOfSnake > 0)) {
            String[] position = reader.readLine().split(" ");
            snakePositions.add(new Position(Integer.parseInt(position[0]), Integer.parseInt(position[1])));
            numberOfSnake--;
        }

        int numberOfLadders = Integer.parseInt(reader.readLine());
        List<Position> ladderPositions = new ArrayList<>();

        while ((numberOfLadders > 0)) {
            String[] position = reader.readLine().split(" ");
            ladderPositions.add(new Position(Integer.parseInt(position[0]), Integer.parseInt(position[1])));
            numberOfLadders--;
        }

        int numberOfPlayers = Integer.parseInt(reader.readLine());
        Map<String, Integer> playerPositions  = new HashMap<>();
        Queue<Player> players = new LinkedList<>();
        while (numberOfPlayers > 0) {
            String name = reader.readLine();
            players.add(new Player(name));
            playerPositions.put(name, 1);
            numberOfPlayers--;
        }

        Board board = Board.getInstance();
        board.initializeBoard(snakePositions, ladderPositions);

        GameService gameService = new GameService(players, board, new DiceRollStrategyImpl(6, 1), playerPositions, new Print());
        gameService.startGame();
    }
}
