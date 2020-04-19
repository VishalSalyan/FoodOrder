package com.example.foodorder.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.foodorder.data.FoodData;
import com.example.foodorder.data.UserData;
import com.google.gson.Gson;

import java.util.ArrayList;

public class SessionData {
    private static final String MyPREFERENCES = "MyPrefs";
    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;

    private static final SessionData ourInstance = new SessionData();

    public static SessionData getInstance() {
        return ourInstance;
    }

    private SessionData() {
    }

    private Gson gson = new Gson();
    private String data = null;
    public ArrayList<FoodData> totalFoodList = new ArrayList<>();

    public void initSharedPref(Context context) {
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
    }

    public void saveLogin(boolean isLogin) {
        editor.putBoolean("key", isLogin);
        editor.apply();
    }

    public void saveLocalData(UserData userData) {
        data = gson.toJson(userData);
        editor.putString("data", data);
        editor.apply();
    }

    public UserData getLocalData() {
        data = sharedpreferences.getString("data", null);
        return gson.fromJson(data, UserData.class);
    }

    public boolean isLogin() {
        return sharedpreferences.getBoolean("key", false);
    }

    public void clearSessionData() {
        editor.clear();
        editor.apply();
    }
}
