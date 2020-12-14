package com.example.pointtosellpractice.model_class.invoice.single_invoice;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SingleInvoiceData {
    @SerializedName("due")
    @Expose
    private Integer due;
    @SerializedName("discount")
    @Expose
    private Integer discount;
    @SerializedName("totalAmountAfterDiscount")
    @Expose
    private Integer totalAmountAfterDiscount;
    @SerializedName("payAmount")
    @Expose
    private Integer payAmount;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("customer")
    @Expose
    private SingleInvoiceCustomerData singleInvoiceCustomerData;
    @SerializedName("products")
    @Expose
    private List<SingleInvoiceProductData> singleInvoiceProductDataList = null;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public Integer getDue() {
        return due;
    }

    public void setDue(Integer due) {
        this.due = due;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Integer getTotalAmountAfterDiscount() {
        return totalAmountAfterDiscount;
    }

    public void setTotalAmountAfterDiscount(Integer totalAmountAfterDiscount) {
        this.totalAmountAfterDiscount = totalAmountAfterDiscount;
    }

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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public SingleInvoiceCustomerData getSingleInvoiceCustomerData() {
        return singleInvoiceCustomerData;
    }

    public void setSingleInvoiceCustomerData(SingleInvoiceCustomerData singleInvoiceCustomerData) {
        this.singleInvoiceCustomerData = singleInvoiceCustomerData;
    }

    public List<SingleInvoiceProductData> getSingleInvoiceProductDataList() {
        return singleInvoiceProductDataList;
    }

    public void setSingleInvoiceProductDataList(List<SingleInvoiceProductData> singleInvoiceProductDataList) {
        this.singleInvoiceProductDataList = singleInvoiceProductDataList;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

}

