package com.example.demo.ratelimiter;

public class Writer {
    public void writeToConsole(String userId, boolean result) {
        System.out.printf("Users request with userId %s processed %s %n", userId, result);
    }
}
