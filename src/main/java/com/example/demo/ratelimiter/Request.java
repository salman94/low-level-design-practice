package com.example.demo.ratelimiter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@AllArgsConstructor
@Getter
@Setter
public class Request {
    private Instant timeStamp;
    private Integer count;
}
