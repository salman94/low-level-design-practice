package com.example.demo.splitwise;

import com.example.demo.splitwise.enums.CommandType;
import com.example.demo.splitwise.enums.ExpenseType;
import com.example.demo.splitwise.models.User;
import com.example.demo.splitwise.models.splits.EqualSplit;
import com.example.demo.splitwise.models.splits.ExactSplit;
import com.example.demo.splitwise.models.splits.PercentageSplit;
import com.example.demo.splitwise.models.splits.Split;
import com.example.demo.splitwise.repositories.UserRepository;
import com.example.demo.splitwise.services.SplitwiseService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SplitwiseApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        UserRepository userRepository = new UserRepository();
        com.example.demo.splitwise.services.UserService userService = new com.example.demo.splitwise.services.UserService(userRepository);

        SplitwiseService splitwiseService = new SplitwiseService(userService);

        userService.addUser("u1", new User("u1", "user1", "u1@gmail.com", "919999999"));
        userService.addUser("u2", new User("u2", "user2", "u2@gmail.com", "929999999"));
        userService.addUser("u3", new User("u3", "user3", "u3@gmail.com", "939999999"));
        userService.addUser("u4", new User("u4", "user4", "u4@gmail.com", "949999999"));

        while(true) {
            String[] commands = sc.nextLine().split(" ");
            CommandType commandType = CommandType.valueOf(commands[0]);

            switch (commandType) {
                case SHOW:
                    if(commands.length == 2) {
                        splitwiseService.showBalances(commands[1]);
                    } else {
                        splitwiseService.showBalances();
                    }
                    break;
                case EXPENSE:
                    String paidBy = commands[1];
                    Double amount = Double.parseDouble(commands[2]);
                    int numberOfSplits = Integer.parseInt(commands[3]);
                    List<Split> splits = new ArrayList<>();
                    ExpenseType expenseType = ExpenseType.valueOf(commands[4+numberOfSplits]);

                    switch (expenseType) {
                        case EXACT:
                            for(int i=1; i<=numberOfSplits; i++) {
                                Split split = new ExactSplit(userService.getUser(commands[3+i]), Double.parseDouble(commands[4+numberOfSplits+i]));
                                splits.add(split);
                            }
                            splitwiseService.addExpense(userService.getUser(paidBy), amount, splits, expenseType);
                            break;
                        case EQUAL:
                            for(int i=1; i<=numberOfSplits; i++) {
                                Split split = new EqualSplit(userService.getUser(commands[3+i]));
                                splits.add(split);
                            }
                            splitwiseService.addExpense(userService.getUser(paidBy), amount, splits, expenseType);
                            break;
                        case PERCENT:
                            for(int i=1; i<=numberOfSplits; i++) {
                                Split split = new PercentageSplit(userService.getUser(commands[3+i]), Double.parseDouble(commands[4+numberOfSplits+i]));
                                splits.add(split);
                            }
                            splitwiseService.addExpense(userService.getUser(paidBy), amount, splits, expenseType);
                            break;
                        default:
                            break;
                    }
                default:
                    break;
            }
        }
    }
}
