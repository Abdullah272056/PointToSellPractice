package com.example.pointtosellpractice.model_class.invoice;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetSellInfoByDayResponse {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("totalSaleInfoByDay")
    @Expose
    private GetSellInfoByDays getSellInfoByDays;
    @SerializedName("msg")
    @Expose
    private String msg;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public GetSellInfoByDays getGetSellInfoByDays() {
        return getSellInfoByDays;
    }

    public void setGetSellInfoByDays(GetSellInfoByDays getSellInfoByDays) {
        this.getSellInfoByDays = getSellInfoByDays;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
