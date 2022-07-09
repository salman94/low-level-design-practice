package com.example.demo.ratelimiter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RateLimiter {
    private Map<String, HitCounter> rateLimiterHitMap;

    public RateLimiter() {
        this.rateLimiterHitMap = new ConcurrentHashMap<>();
    }

    public boolean isAllow(String userId) {
        Long currentTimeStamp = System.currentTimeMillis();
        HitCounter hitCounter = rateLimiterHitMap.get(userId);

        if(hitCounter == null) {
            hitCounter = new HitCounter();
            rateLimiterHitMap.put(userId, hitCounter);
        }

        return hitCounter.setHit(currentTimeStamp);
    }
}
