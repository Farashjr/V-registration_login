package com.example.v_registration_login;

public class Constants {
    public static final String[] productCategories;

    static {
        productCategories = new String[]{
                "Baking and cooking supplies",
                "Beverages",
                "Canned and jarred foods",
                "Dairy products",
                "Fruits",
                "Grains and cereals",
                "Meat and poultry",
                "Seafood",
                "Snacks and confectionery",
                "Vegetables",
                "others"
        };


    }

    public static String productCategories1(int which) {
        String[] productCategories1 = new String[]{
                "All",
                "Baking and cooking supplies",
                "Beverages",
                "Canned and jarred foods",
                "Dairy products",
                "Fruits",
                "Grains and cereals",
                "Meat and poultry",
                "Seafood",
                "Snacks and confectionery",
                "Vegetables",
                "others"
        };
        return productCategories1[which];
    }
}


