package com.example.demo.parkinglot.enums;

import java.util.HashMap;
import java.util.Map;

public enum Display {
    FREE_COUNT("free_count"),
    FREE_SLOTS("free_slots"),
    OCCUPIED_SLOTS("occupied_slots");

    private static final Map<String, Display> displayMap = new HashMap<>();

    static {
        for (Display d : values()) {
            displayMap.put(d.display, d);
        }
    }

    private final String display;

    Display(String display) {
        this.display = display;
    }

    public static Display of(String display) {
        return displayMap.get(display);
    }

    public String toString() {
        return this.display;
    }

}
