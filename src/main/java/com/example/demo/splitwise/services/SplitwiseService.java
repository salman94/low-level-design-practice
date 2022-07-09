package com.example.demo.splitwise.services;

import com.example.demo.splitwise.enums.ExpenseType;
import com.example.demo.splitwise.models.User;
import com.example.demo.splitwise.models.expenses.Expense;
import com.example.demo.splitwise.models.splits.Split;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SplitwiseService {

    private Map<String, Map<String, Double>> balances;

    private UserService userService;

    public SplitwiseService(UserService userService) {
        this.balances = new HashMap<>();
        this.userService = userService;
    }

    public void addExpense(User paidBy, double amount, List<Split> splits, ExpenseType expenseType) {
        Expense expense = ExpenseService.createExpense(paidBy, amount, splits, expenseType);
        assert expense != null;
        if(!balances.containsKey(paidBy.getName())) {
            balances.put(paidBy.getName(), new HashMap<>());
        }
        for (Split split : expense.getSplits()) {
            User paidTo = split.getUser();
            Map<String, Double> balanceSheet = balances.get(paidBy.getName());

            balanceSheet.put(paidTo.getName(), balanceSheet.getOrDefault(paidTo.getName(), 0.0) + split.getAmount());
            if(!balances.containsKey(paidTo.getName())) {
                balances.put(paidTo.getName(), new HashMap<>());
            }
            balanceSheet = balances.get(paidTo.getName());

            balanceSheet.put(paidBy.getName(), balanceSheet.getOrDefault(paidBy.getName(), 0.0) - split.getAmount());
        }

    }

    public void showBalances(String userId) {
        User user = userService.getUser(userId);
        boolean isEmpty = true;
        Map<String, Double> userBalanceSheet = balances.get(user.getName());
        if(userBalanceSheet != null) {
            for(Map.Entry<String, Double> balanceSheet: userBalanceSheet.entrySet()) {
                printBalances(user.getName(), balanceSheet.getKey(), balanceSheet.getValue());
                isEmpty = false;
            }
        }
        if(isEmpty) {
            System.out.println("No balances");
        }
    }

    public void showBalances() {
        boolean isEmpty = true;
        for(Map.Entry<String, Map<String, Double>> balanceSheets: balances.entrySet()) {
            String userName = balanceSheets.getKey();
            Map<String, Double> userBalanceSheet = balances.get(userName);
            for(Map.Entry<String, Double> balanceSheet: userBalanceSheet.entrySet()) {
                printBalances(userName, balanceSheet.getKey(), balanceSheet.getValue());
                isEmpty = false;
            }
        }
        if(isEmpty) {
            System.out.println("No balances");
        }
    }

    private void printBalances(String userA, String userB, Double amount) {
        if(amount < 0) {
            System.out.println(userA+" owes "+userB+" "+ (-amount));
        } else if(amount > 0){
            System.out.println(userB+" owes "+userA+" "+amount);
        }
    }
}
