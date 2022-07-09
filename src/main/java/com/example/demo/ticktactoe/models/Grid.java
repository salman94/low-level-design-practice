package com.example.demo.ticktactoe.models;


public class Grid {
    private final MoveType[][] grid;
    private final int size;

    public Grid(int size) {
        this.size = size;
        grid = new MoveType[size][size];
        initilazeGrid();
    }

    private void initilazeGrid() {
        for(int i=0; i<size; i++) {
            for(int j=0; j<size; j++) {
                grid[i][j] = MoveType.E;
            }
        }
        printGrid();
    }

    private void printGrid() {
        for(int i=0; i<size; i++) {
            for(int j=0; j<size; j++) {
                System.out.print(grid[i][j] +"  ");
            }
            System.out.println();
        }
    }

    public MoveType getCell(int x, int y) {
        return this.grid[x][y];
    }

    public void setCell(int x, int y, MoveType type) {
        this.grid[x][y] = type;
        printGrid();
    }

    public int getSize() {
        return this.size;
    }
}
