package com.example.foodorder.data;

import java.util.ArrayList;

public class UserData {

    private String name = null;
    private String email = null;
    private String phoneNumber = null;
    private String password = null;
    private String gender = null;
    private String userName = null;
    private String userImage = null;
    private int walletBalance = 0;
    private ArrayList<WishListData> favouriteFoods = new ArrayList<>();

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getGender() {
        return gender;
    }

    public String getUserName() {
        return userName;
    }

    public ArrayList<WishListData> getFavouriteFoods() {
        return favouriteFoods;
    }

    public void setFavouriteFoods(ArrayList<WishListData> favouriteFoods) {
        this.favouriteFoods = favouriteFoods;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setUserName(String userName) {

        this.userName = userName;
    }

    public int getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(int walletBalance) {
        this.walletBalance = walletBalance;
    }
}
