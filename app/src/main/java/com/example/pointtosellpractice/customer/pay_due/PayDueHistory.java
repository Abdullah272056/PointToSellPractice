package com.example.pointtosellpractice.customer.pay_due;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PayDueHistory {
    @SerializedName("payAmount")
    @Expose
    private Integer payAmount;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("date")
    @Expose
    private String date;

    public Integer getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(Integer payAmount) {
        this.payAmount = payAmount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
