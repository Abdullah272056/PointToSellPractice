package com.example.pointtosellpractice.auth.delete_user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeleteUserSetDataResponse {

    @SerializedName("password")
    @Expose
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
