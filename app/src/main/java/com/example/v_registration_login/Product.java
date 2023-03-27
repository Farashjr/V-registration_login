package com.example.v_registration_login;


public class Product {
    private String productName;
    private String productImage;
    private double productPrice;
    private String description;
    public Product() {
        // Required empty constructor for Firestore
    }

    public Product(String productName, String productImage, String productPrice,string description) {
        this.productName = productName;
        this.productImage = productImage;
        this.productPrice = Double.parseDouble(productPrice);
        this.description = String.valueOf(description);

    }

    public Product(String productName, String description, String productPrice, String imageUrl) {

    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }
}

