package com.example.demo.splitwise.models.expenses;

import com.example.demo.splitwise.models.User;
import com.example.demo.splitwise.models.splits.Split;

import java.util.List;

public class ExactExpense extends Expense{


    public ExactExpense(String id, User paidBy, Double amount, List<Split> splits) {
        super(id, paidBy, amount, splits);
    }

    @Override
    public boolean isValid() {
        return false;
    }
}
