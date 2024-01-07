package com.example.demo.coupancategory;

import java.io.IOException;
import java.util.*;

public class CouponCategoryPart1And2 {
    private static Map<String, String> categoryToCoupons;
    private static Map<String, String> categoryToParent;

    private static String findParent(String category) {
        String parentCategory = categoryToParent.get(category);
        if(parentCategory.equals("None")) {
            return "None";
        }
        if(categoryToCoupons.containsKey(parentCategory)) {
            return parentCategory;
        }
        categoryToParent.put(category, findParent(parentCategory));
        return categoryToParent.get(category);
    }

    private static String getCoupon(String category) {
        if(categoryToCoupons.containsKey(category)) {
            return categoryToCoupons.get(category);
        }
        String parentCategory = findParent(category);
        if(parentCategory.equals("None")) {
            return "None";
        }
        return categoryToCoupons.get(parentCategory);
    }

    private static String parse(String str) {
        return str.split(":")[1];
    }

    public static void main(String[] args) {
        List<String[]> coupons = List.of(
                new String[]{"CategoryName:Comforter Sets", "CouponName:Comforters Sale", "DateModified:2020-01-01","Discount:10%"},
                new String[]{"CategoryName:Comforter Sets", "CouponName:Cozy Comforter Coupon", "DateModified:2021-01-01","Discount:$15"},
                new String[]{"CategoryName:Bedding", "CouponName:Best Bedding Bargains", "DateModified:2020-01-01","Discount:30%"},
                new String[]{"CategoryName:Bedding", "CouponName:Savings on Bedding", "DateModified:2019-01-01","Discount:25%"},
                new String[]{"CategoryName:Bed & Bath", "CouponName:Low price for Bed & Bath", "DateModified:2018-01-01","Discount:50%"},
                new String[]{"CategoryName:Bed & Bath", "CouponName:Bed & Bath extravaganza", "DateModified:2019-01-01","Discount:20%"},
                new String[]{"CategoryName:Bed & Bath", "CouponName:Big Savings for Bed & Bath", "DateModified:2030-01-01","Discount:75%"}
        );

        List<String[]> categories = List.of(
                new String[]{"CategoryName:Comforter Sets", "CategoryParentName:Bedding"},
                new String[]{"CategoryName:Bedding", "CategoryParentName:Bed & Bath"},
                new String[]{"CategoryName:Bed & Bath", "CategoryParentName:null"},
                new String[]{"CategoryName:Soap Dispensers", "CategoryParentName:Bathroom Accessories"},
                new String[]{"CategoryName:Bathroom Accessories", "CategoryParentName:Bed & Bath"},
                new String[]{"CategoryName:Toy Organizers", "CategoryParentName:Baby And Kids"},
                new String[]{"CategoryName:Baby And Kids", "CategoryParentName:null"},
                new String[]{"CategoryName:Best Place", "CategoryParentName:Bedding"}
        );;
        categoryToParent = new HashMap<>();
        categoryToCoupons = new HashMap<>();

        for(var str: coupons) {
            categoryToCoupons.put(parse(str[0]), parse(str[1]));
        }
        for(var str: categories) {
            categoryToParent.put(parse(str[0]), parse(str[1]));
        }

        System.out.println("Comforter Sets => "+getCoupon("Comforter Sets"));
        System.out.println("Bedding => "+getCoupon("Bedding"));
        System.out.println("Bathroom Accessories => "+getCoupon("Bathroom Accessories"));
        System.out.println("Soap Dispensers => "+getCoupon("Soap Dispensers"));
        System.out.println("Toy Organizers => "+getCoupon("Toy Organizers"));
    }

}

