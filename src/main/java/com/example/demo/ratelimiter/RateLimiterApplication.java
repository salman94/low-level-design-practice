package com.example.demo.ratelimiter;

public class RateLimiterApplication {

    public static void main(String[] args) {

        System.out.println("Testing started....");
        RateLimiter rateLimiter = new RateLimiter();
        new RateLimiterHelper(rateLimiter, "user1").run();
        new RateLimiterHelper(rateLimiter, "user2").run();
    }
}
