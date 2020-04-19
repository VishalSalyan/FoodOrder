package com.example.foodorder.data;


import java.util.ArrayList;

public class FoodData {

    private String id = "";
    private String name = "";
    private String shopName = "";
    private String shopAddress = "";
    private String shopPhoneNumber = "";

    private String shopSpecification = "";

    private String shopimage = "";
    private String deliveryTime = "";

    private String promoCode = "";
    private String rating = "";
    private ArrayList<FoodTypeData> typesOfFood = new ArrayList<>();

    private String foodName = "";
    private String foodPrice = "";

    private String foodRecipe = "";
    private String ownerEmail = "";
    private String ownerAddress = "";
    private String mode = "";
    public void setId(String id) {
        this.id = id;
    }

    public String getShopSpecification() {
        return shopSpecification;
    }

    public void setShopSpecification(String shopSpecification) {
        this.shopSpecification = shopSpecification;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getShopPhoneNumber() {
        return shopPhoneNumber;
    }

    public void setShopPhoneNumber(String shopPhoneNumber) {
        this.shopPhoneNumber = shopPhoneNumber;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public ArrayList<FoodTypeData> getTypesOfFood() {
        return typesOfFood;
    }

    public void setTypesOfFood(ArrayList<FoodTypeData> typesOfFood) {
        this.typesOfFood = typesOfFood;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        this.foodPrice = foodPrice;
    }

    public String getFoodRecipe() {
        return foodRecipe;
    }

    public void setFoodRecipe(String foodRecipe) {
        this.foodRecipe = foodRecipe;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public String getOwnerAddress() {
        return ownerAddress;
    }

    public void setOwnerAddress(String ownerAddress) {
        this.ownerAddress = ownerAddress;
    }


    public String getId() {
        return id;
    }

    public String getShopImage() {
        return shopimage;
    }

    public void setShopImage(String shopImage) {
        this.shopimage = shopImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMode() {
        return mode;
    }
}