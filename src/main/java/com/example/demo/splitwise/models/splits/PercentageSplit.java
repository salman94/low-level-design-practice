package com.example.demo.splitwise.models.splits;

import com.example.demo.splitwise.models.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PercentageSplit extends Split{

    private double percentage;

    public PercentageSplit(User user, double percentage) {
        super(user);
        this.percentage = percentage;
    }
}
