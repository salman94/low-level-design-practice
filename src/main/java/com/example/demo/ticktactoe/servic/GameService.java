package com.example.demo.ticktactoe.servic;

import com.example.demo.ticktactoe.models.Grid;
import com.example.demo.ticktactoe.models.MoveType;
import com.example.demo.ticktactoe.models.Player;

import java.util.Queue;

public class GameService {
    Queue<Player> players;
    Grid grid;
    int moveCount;

    public GameService(Queue<Player> players, Grid grid) {
        this.players = players;
        this.grid = grid;
        this.moveCount = 0;
    }

    public void playMove(int x, int y) {
        if(isValidMove(x, y)) {
            Player currentPlayer = players.poll();
            assert currentPlayer != null;
            makeMove(x, y, currentPlayer.getMoveType());
            moveCount++;
            if(isWin(currentPlayer.getMoveType(), y)) {
                System.out.println(currentPlayer.getName() +" won the game");
                System.exit(0);
            }
            if(isGameDraw(moveCount)) {
                System.out.println("Draw");
                System.exit(0);
            }
            players.add(currentPlayer);
        } else {
            System.out.println("Invalid Move");
        }
    }

    private boolean isGameDraw(int moveCount) {
        return moveCount == grid.getSize()* grid.getSize();
    }

    private boolean isWin(MoveType moveType, int col) {
        boolean rowWin = true, colWin = true, leftDiagWin = true, rightDiagWin = true;
        for(int i=0; i< grid.getSize(); i++) {
            if(!grid.getCell(i, col).equals(moveType)) {
                rowWin = false;
            }
            if(!grid.getCell(col, i).equals(moveType)) {
                colWin = false;
            }
            if(!grid.getCell(i, i).equals(moveType)) {
                leftDiagWin = false;
            }
            if(!grid.getCell(grid.getSize()-1-i, i).equals(moveType)) {
                rightDiagWin = false;
            }
        }

        return rowWin || colWin || leftDiagWin || rightDiagWin;
    }

    private boolean isValidMove(int x, int y) {
        return this.grid.getCell(x, y).equals(MoveType.E);
    }

    private void makeMove(int x, int y, MoveType moveType) {
        this.grid.setCell(x, y, moveType);
    }
}
