package com.example.demo.parkinglot.enums;

import java.util.HashMap;
import java.util.Map;

public enum Command {
    CREATE_PARKING_LOT("create_parking_lot"),
    PARK_VEHICLE("park_vehicle"),
    UNPARK_VEHICLE("unpark_vehicle"),
    DISPLAY("display"),
    EXIT("exit");

    private static final Map<String, Command> commandMap = new HashMap<>();

    static {
        for (Command c : values()) {
            commandMap.put(c.command, c);
        }
    }

    private final String command;

    Command(String command) {
        this.command = command;
    }

    public static Command of(String command) {
        return commandMap.get(command);
    }

    public String toString() {
        return this.command;
    }
}
