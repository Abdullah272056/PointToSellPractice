package com.example.pointtosellpractice.model_class.product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetProductInfoDataResponse{
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("productInfo")
    @Expose
    private GetProductData getProductData;
    @SerializedName("msg")
    @Expose
    private String msg;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public GetProductData getGetProductData() {
        return getProductData;
    }

    public void setGetProductData(GetProductData getProductData) {
        this.getProductData = getProductData;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
