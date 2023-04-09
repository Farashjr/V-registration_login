package com.example.v_registration_login.Models;

import android.content.SharedPreferences;

import com.google.gson.Gson;

public class User {
    private String userId;
    private String username;
    private String email;
    private String address;
    private String phonenumber;
    private String role;

    public  User(){

    }

    public User(String userId, String username, String email, String address, String phonenumber, String role) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.address = address;
        this.phonenumber = phonenumber;
        this.role = role;
    }

    public static void saveUserToSharedPreference(SharedPreferences sharedPreferences, User user){
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        Gson gson = new Gson();
        String userJson = gson.toJson(user);
        prefsEditor.putString("user", userJson);
        prefsEditor.commit();
    }

    public static User getUserFromSharedPreference(SharedPreferences sharedPreferences){
        Gson gson = new Gson();
        String userJson = sharedPreferences.getString("user","");

        User user = gson.fromJson(userJson, User.class);

        return user;
    }
}
