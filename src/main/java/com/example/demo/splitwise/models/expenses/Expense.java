package com.example.demo.splitwise.models.expenses;

import com.example.demo.splitwise.models.User;
import com.example.demo.splitwise.models.splits.Split;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public abstract class Expense {
    private String id;
    private User paidBy;
    private Double amount;
    private List<Split> splits;

    public abstract boolean isValid();
}
