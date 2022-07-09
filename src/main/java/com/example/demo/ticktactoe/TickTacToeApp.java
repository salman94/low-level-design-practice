package com.example.demo.ticktactoe;

import com.example.demo.ticktactoe.models.Grid;
import com.example.demo.ticktactoe.models.MoveType;
import com.example.demo.ticktactoe.models.Player;
import com.example.demo.ticktactoe.servic.GameService;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class TickTacToeApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] inputPlayer = sc.nextLine().split(" ");
        Player player1 = new Player(MoveType.valueOf(inputPlayer[0]), inputPlayer[1]);
        inputPlayer = sc.nextLine().split(" ");
        Player player2 = new Player(MoveType.valueOf(inputPlayer[0]), inputPlayer[1]);
        Queue<Player> players = new LinkedList<>();
        players.add(player1);
        players.add(player2);

        Grid grid = new Grid(3);
        GameService gameService = new GameService(players, grid);
        while(true) {
            String commands = sc.nextLine();
            switch (commands) {
                case "exit":
                    System.exit(0);
                default:
                    String[] command = commands.split(" ");
                    int x = Integer.parseInt(command[0]);
                    int y = Integer.parseInt(command[1]);
                    gameService.playMove(x-1, y-1);
            }
        }
    }
}
