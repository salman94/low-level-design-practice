package com.example.demo.ticktactoe.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Player {
    private MoveType moveType;
    private String name;
}
