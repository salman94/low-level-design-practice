package com.example.demo.splitwise.services;

import com.example.demo.splitwise.model.split.PercentSplit;
import com.example.demo.splitwise.enums.ExpenseType;
import com.example.demo.splitwise.models.User;
import com.example.demo.splitwise.models.expenses.EqualExpense;
import com.example.demo.splitwise.models.expenses.ExactExpense;
import com.example.demo.splitwise.models.expenses.Expense;
import com.example.demo.splitwise.models.expenses.PercentageExpense;
import com.example.demo.splitwise.models.splits.PercentageSplit;
import com.example.demo.splitwise.models.splits.Split;

import java.util.List;
import java.util.UUID;

public class ExpenseService {

    public static Expense createExpense(User paidBy, Double amount, List<Split> splits, ExpenseType expenseType) {
        switch (expenseType) {
            case EXACT:
                return new ExactExpense(UUID.randomUUID().toString(), paidBy, amount, splits);
            case EQUAL:
                double splitAmount = amount/ splits.size();
                for(Split split: splits) {
                    split.setAmount(splitAmount);
                }
                splits.get(0).setAmount(amount - splitAmount * splits.size());
                return new EqualExpense(UUID.randomUUID().toString(), paidBy, amount, splits);
            case PERCENT:
                for(Split split: splits) {
                    PercentageSplit percentSplit = (PercentageSplit) split;
                    double percentageSplitAmount = (amount*percentSplit.getPercentage())/ 100.00;
                    split.setAmount(percentageSplitAmount);
                }
                return new PercentageExpense(UUID.randomUUID().toString(), paidBy, amount, splits);
            default:
                return null;
        }
    }
}
