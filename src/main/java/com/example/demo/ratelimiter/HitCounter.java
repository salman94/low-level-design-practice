package com.example.demo.ratelimiter;

import java.util.LinkedList;
import java.util.Queue;

public class HitCounter {
    private Queue<Long> queue;

    public HitCounter() {
        this.queue = new LinkedList<Long>();
    }

    public boolean setHit(Long timeStamp) {
        while (!queue.isEmpty() && timeStamp - queue.peek() >= Constants.REQUEST_TIME_LIMIT) {
            queue.poll();
        }
        if(queue.size() > Constants.REQUEST_LIMIT) {
            return false;
        } else {
            queue.offer(timeStamp);
            return true;
        }
    }
}
