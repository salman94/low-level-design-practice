package com.example.demo.snakeladder.service;

import com.example.demo.snakeladder.Print;
import com.example.demo.snakeladder.model.Board;
import com.example.demo.snakeladder.model.Player;

import java.util.Map;
import java.util.Queue;

public class GameService {
    private final Queue<Player> players;
    private final Board board;
    private final DiceRollStrategy diceRollStrategy;
    private final Map<String, Integer> playerPositions;
    private final Print printer;

    public GameService(Queue<Player> players, Board board, DiceRollStrategy diceRollStrategy, Map<String, Integer> playerPositions, Print printer) {
        this.players = players;
        this.board = board;
        this.diceRollStrategy = diceRollStrategy;
        this.playerPositions = playerPositions;
        this.printer = printer;
    }

    public void startGame() {
        Player currPlayer;
        if(players.size() < 2)
            throw new RuntimeException("Minimum 2 players required to play the game");
        do{
            currPlayer = players.poll();
            int currPosition = playerPositions.get(currPlayer.getName());
            int diceNumber = diceRollStrategy.getDiceValue();

            int updatedPosition = currPosition + diceNumber;
            if(cheIfPlayerWithinBoard(updatedPosition)) {
                if(board.getLadderMoves().containsKey(updatedPosition)) {
                    updatedPosition = board.getLadderMoves().get(updatedPosition);
                } else if(board.getSnakeMoves().containsKey(updatedPosition)){
                    updatedPosition = board.getSnakeMoves().get(updatedPosition);
                }
            }
            printer.printMove(currPlayer.getName(), diceNumber, currPosition, updatedPosition);
            playerPositions.put(currPlayer.getName(), updatedPosition);
            players.offer(currPlayer);
        } while (!checkIfPlayerWonTheGame(currPlayer));
        System.out.println(String.format("Player %s won the game", currPlayer.getName()));
    }

    private boolean checkIfPlayerWonTheGame(Player currPlayer) {
        return this.playerPositions.get(currPlayer.getName()) == 100;
    }

    private boolean cheIfPlayerWithinBoard(int updatedPosition) {
        return updatedPosition <= 100;
    }


}
