package com.example.demo.ratelimiter;

import java.util.concurrent.TimeUnit;

public class RateLimiterHelper extends Thread{
    private RateLimiter rateLimiter;
    private String user;

    public RateLimiterHelper(RateLimiter rateLimiter, String user) {
        this.rateLimiter = rateLimiter;
        this.user = user;
    }

    @Override
    public void run() {
        for(int i=1; i<=65; i++) {
            System.out.printf("user %s hit the API, Time - %s, rateLimit %s %n", user, i, rateLimiter.isAllow(user));
            try {
                Thread.sleep(TimeUnit.MILLISECONDS.convert(1, TimeUnit.SECONDS));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.printf("Done! %s %n", user);
    }
}
