package com.example.demo.coupancategory;

import java.text.SimpleDateFormat;
import java.util.*;

public class CouponCategoryPart3 {

    static class Coupon {
        String couponName;
        Date dateModified;

        Coupon(String couponName, Date dateModified) {
            this.couponName = couponName;
            this.dateModified = dateModified;
        }


    }

    private static Map<String, List<Coupon>> categoryToCoupons;
    private static Map<String, String> categoryToParent;
    private static final String datePattern = "yyyy-MM-dd";

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

    private static List<Coupon> getCouponDetails(String category) {
        if(categoryToCoupons.containsKey(category)) {
            return categoryToCoupons.get(category);
        }
        String parentCategory = findParent(category);
        if(parentCategory.equals("None")) {
            return List.of();
        }
        return categoryToCoupons.get(parentCategory);
    }

    private static List<Coupon> getLatestCouponByLinearSearch(List<Coupon> coupons) {

        Date currentDate = new Date();
        List<Coupon> result = new ArrayList<>();
        int i=coupons.size()-1;
        for(; i>=0; i--) {
            if(coupons.get(i).dateModified.compareTo(currentDate) > 0) {
                continue;
            } else {
                break;
            }
        }
        Date latestDate = coupons.get(i).dateModified;
        while(i >= 0 && coupons.get(i).dateModified.compareTo(latestDate) == 0) {
            result.add(coupons.get(i));
            i--;
        }
        return result;
    }

    private static List<Coupon> getCoupon(String category) {
        List<Coupon> coupons = getCouponDetails(category);
        if(coupons == null || coupons.size() == 0) return List.of();

        return getLatestCouponByLinearSearch(coupons);
    }

    private static String parse(String str) {
        return str.split(":")[1];
    }

    public static void main(String[] args) throws java.text.ParseException {
        List<String[]> coupons = List.of(
                new String[]{"CategoryName:Comforter Sets", "CouponName:Comforters Sale", "DateModified:2020-01-01"},
                new String[]{"CategoryName:Comforter Sets", "CouponName:Cozy Comforter Coupon", "DateModified:2020-01-01"},

                new String[]{"CategoryName:Bedding", "CouponName:Best Bedding Bargains", "DateModified:2020-01-01"},
                new String[]{"CategoryName:Bedding", "CouponName:Savings on Bedding", "DateModified:2019-01-01"},
                new String[]{"CategoryName:Bed & Bath", "CouponName:Low price for Bed & Bath", "DateModified:2018-01-01"},
                new String[]{"CategoryName:Bed & Bath", "CouponName:Bed & Bath extravaganza", "DateModified:2018-01-01"},
                new String[]{"CategoryName:Bed & Bath", "CouponName:Big Savings for Bed & Bath", "DateModified:2034-01-01"}
        );

        List<String[]> categories = List.of(
                new String[]{"CategoryName:Comforter Sets", "CategoryParentName:Bedding"},
                new String[]{"CategoryName:Bedding", "CategoryParentName:Bed & Bath"},
                new String[]{"CategoryName:Bed & Bath", "CategoryParentName:None"},
                new String[]{"CategoryName:Soap Dispensers", "CategoryParentName:Bathroom Accessories"},
                new String[]{"CategoryName:Bathroom Accessories", "CategoryParentName:Bed & Bath"},
                new String[]{"CategoryName:Toy Organizers", "CategoryParentName:Baby And Kids"},
                new String[]{"CategoryName:Baby And Kids", "CategoryParentName:None"}
        );
        categoryToParent = new HashMap<>();
        categoryToCoupons = new HashMap<>();
        SimpleDateFormat format = new SimpleDateFormat(datePattern);

        for(var str: coupons) {
            String category = parse(str[0]);
            String couponName = parse(str[1]);
            String dateModified = parse(str[2]);
            if(!categoryToCoupons.containsKey(category)) {
                categoryToCoupons.put(category, new ArrayList<Coupon>());
            }
            categoryToCoupons.get(category).add(new Coupon(couponName, format.parse(dateModified)));
        }
        for(Map.Entry<String, List<Coupon>> entry: categoryToCoupons.entrySet()) {
            Collections.sort(categoryToCoupons.get(entry.getKey()), (a, b) -> a.dateModified.compareTo(b.dateModified));
        }
        for(var str: categories) {
            categoryToParent.put(parse(str[0]), parse(str[1]));
        }

        System.out.print("Comforter Sets => ");
        getCoupon("Comforter Sets").forEach(item -> System.out.print(item.couponName +" , "));
        System.out.println();
        System.out.print("Bedding => ");
        getCoupon("Bedding").forEach(item -> System.out.print(item.couponName +" , "));
        System.out.println();
        System.out.print("Bathroom Accessories => ");
        getCoupon("Bathroom Accessories").forEach(item -> System.out.print(item.couponName +" , "));
        System.out.println();
        System.out.print("Soap Dispensers => ");
        getCoupon("Soap Dispensers").forEach(item -> System.out.print(item.couponName +" , "));
        System.out.println();
        System.out.print("Toy Organizers => ");
        getCoupon("Toy Organizers").forEach(item -> System.out.print(item.couponName +" , "));
    }

}

