package com.example.demo.splitwise.models.splits;

import com.example.demo.splitwise.models.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Split {
    User user;
    double amount;

    public Split(User user) {
        this.user = user;
    }
}
