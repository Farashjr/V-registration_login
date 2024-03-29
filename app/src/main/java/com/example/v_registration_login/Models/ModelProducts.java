package com.example.v_registration_login.Models;

public class ModelProducts {
    private  String productId, productTitle, productDescription, productCategory, productQuantity,
            productIcon,productPrice, timestamp,uid;

   public ModelProducts(String productId, String productTitle, String productDescription, String productCategory, String productQuantity,
                        String productIcon, String productPrice, String timestamp, String uid){
       this.productId =productId;
       this.productTitle =productTitle;
       this.productDescription =productDescription;
       this.productCategory =productCategory;
       this.productQuantity =productQuantity;
       this.productIcon =productIcon;
       this.productPrice =productPrice;
       this.timestamp = timestamp;
       this.uid = uid;

   }
   public  String getProductId(){
       return productId;
   }
   public void setProductId(String productId){
       this.productId = productId;
   }
    public  String getProductTitle(){
        return productTitle;
    }
    public void setProductTitle(String productTitle){
        this.productTitle = productTitle;
    }
    public  String getProductDescription(){
        return productDescription;
    }
    public void setProductDescription(String productDescription){
        this.productDescription = productDescription;
    }
    public  String getProductCategory(){
        return productCategory;
    }
    public void setProductCategory(String productCategory){
        this.productCategory = productCategory;
    }
    public  String getProductQuantity(){
        return productQuantity;
    }
    public void setProductQuantity(String productQuantity){
        this.productQuantity = productQuantity;
    }
    public  String getProductIcon(){
        return productId;
    }
    public void setProductIcon(String productIcon){
        this.productIcon = productIcon;
    }
    public  String getProductPrice(){
        return productPrice;
    }
    public void setProductPrice(String productPrice){
        this.productPrice = productPrice;
    }
    public  String getTimestamp(){
        return timestamp;
    }
    public void setTimestamp(String timestamp){
        this.timestamp = timestamp;
    }
    public  String getUid(){
        return uid;
    }
    public void setUid(String uid){
        this.uid = uid;
    }
}
