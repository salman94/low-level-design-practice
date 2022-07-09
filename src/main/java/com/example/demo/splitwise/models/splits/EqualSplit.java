package com.example.demo.splitwise.models.splits;

import com.example.demo.splitwise.models.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EqualSplit extends Split{
    public EqualSplit(User user) {
        super(user);
    }

}
