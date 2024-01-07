package com.example.demo.coupancategory;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class CouponCategoryPart4 {

    enum DiscountType {
        PERCENT,
        ABSOLUTE
    }
    static class Coupon {
        String couponName;
        Date dateModified;
        double discount;
        DiscountType discountType;

        Coupon(String couponName, Date dateModified, double discount, DiscountType type) {
            this.couponName = couponName;
            this.dateModified = dateModified;
            this.discount = discount;
            this.discountType = type;
        }

        @Override
        public String toString() {
            return "Coupon{" +
                    "couponName='" + couponName + '\'' +
                    ", dateModified=" + dateModified +
                    ", discount=" + discount +
                    ", discountType=" + discountType +
                    '}';
        }
    }

    static class Product {
        String productName;
        String categoryName;
        double price;

        public Product(String productName, String categoryName, double price) {
            this.productName = productName;
            this.categoryName = categoryName;
            this.price = price;
        }
    }

    private static Map<String, List<Coupon>> categoryToCoupons;
    private static Map<String, String> categoryToParent;
    private static Map<String, Product> productToCategoryMap;
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

    private static List<Double> getDiscountedPrice(String productName) {
        if(!productToCategoryMap.containsKey(productName)) {
            return List.of();
        }
        Product product = productToCategoryMap.get(productName);
        List<Coupon> coupons = getCoupon(productToCategoryMap.get(productName).categoryName);
        if(coupons == null || coupons.size() == 0) {
            return List.of(product.price);
        }
        return coupons.stream().map(coupon -> coupon.discountType == DiscountType.ABSOLUTE
                    ? product.price - coupon.discount
                    : product.price - (product.price * coupon.discount)/100.00
        ).collect(Collectors.toList());
    }

    public static void main(String[] args) throws java.text.ParseException {
        List<String[]> coupons = List.of(
                new String[]{"CategoryName:Comforter Sets", "CouponName:Comforters Sale", "DateModified:2020-01-01","Discount:10%"},
                new String[]{"CategoryName:Comforter Sets", "CouponName:Cozy Comforter Coupon", "DateModified:2020-01-01","Discount:$15"},
                new String[]{"CategoryName:Bedding", "CouponName:Best Bedding Bargains", "DateModified:2019-01-01","Discount:35%"},
                new String[]{"CategoryName:Bedding", "CouponName:Savings on Bedding", "DateModified:2019-01-01","Discount:25%"},
                new String[]{"CategoryName:Bed & Bath", "CouponName:Low price for Bed & Bath", "DateModified:2018-01-01","Discount:50%"},
                new String[]{"CategoryName:Bed & Bath", "CouponName:Bed & Bath extravaganza", "DateModified:2019-01-01","Discount:75%"}
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

        List<String[]> products = List.of(
                new String[]{"ProductName:Cozy Comforter Sets","Price:100.00", "CategoryName:Comforter Sets"},
                new String[]{"ProductName:All-in-one Bedding Set", "Price:50.00", "CategoryName:Bedding"},
                new String[]{"ProductName:Infinite Soap Dispenser", "Price:500.00" ,"CategoryName:Bathroom Accessories"},
                new String[]{"ProductName:Rainbow Toy Box","Price:257.00", "CategoryName:Baby And Kids"}
        );

        categoryToParent = new HashMap<>();
        categoryToCoupons = new HashMap<>();
        productToCategoryMap = new HashMap<>();
        SimpleDateFormat format = new SimpleDateFormat(datePattern);

        for(var str: coupons) {
            String category = parse(str[0]);
            String couponName = parse(str[1]);
            String dateModified = parse(str[2]);
            String discount = parse(str[3]);
            DiscountType discountType = discount.startsWith("$") ? DiscountType.ABSOLUTE : DiscountType.PERCENT;
            double totalDiscount = Double.parseDouble(discount.replace("%", "").replace("$", ""));

            if(!categoryToCoupons.containsKey(category)) {
                categoryToCoupons.put(category, new ArrayList<Coupon>());
            }
            categoryToCoupons.get(category).add(new Coupon(couponName, format.parse(dateModified), totalDiscount, discountType));
        }
        for(Map.Entry<String, List<Coupon>> entry: categoryToCoupons.entrySet()) {
            Collections.sort(categoryToCoupons.get(entry.getKey()), (a, b) -> a.dateModified.compareTo(b.dateModified));
        }
        for(var str: categories) {
            categoryToParent.put(parse(str[0]), parse(str[1]));
        }

        for(var product: products) {
            String productName = parse(product[0]);
            double price = Double.parseDouble(parse(product[1]));
            String category = parse(product[2]);

            productToCategoryMap.put(productName, new Product(productName, category, price));
        }

        System.out.print("Cozy Comforter Sets => ");
        getDiscountedPrice("Cozy Comforter Sets").forEach(item -> System.out.print(item.toString() +" , "));
        System.out.println();
        System.out.print("All-in-one Bedding Set => ");
        getDiscountedPrice("All-in-one Bedding Set").forEach(item -> System.out.print(item.toString() +" , "));
        System.out.println();
        System.out.print("Infinite Soap Dispenser => ");
        getDiscountedPrice("Infinite Soap Dispenser").forEach(item -> System.out.print(item.toString() +" , "));
        System.out.println();
        System.out.print("Rainbow Toy Box => ");
        getDiscountedPrice("Rainbow Toy Box").forEach(item -> System.out.print(item.toString() +" , "));
    }

}

