package com.example.pointtosellpractice.user_all_information;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDataWithResponse {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("data")
    @Expose
    private UserData data;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public UserData getData() {
        return data;
    }

    public void setData(UserData data) {
        this.data = data;
    }
}
