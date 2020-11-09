package com.example.pointtosellpractice.model_class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserData {
        @SerializedName("companyName")
        @Expose
        private String companyName;
        @SerializedName("companyOwner")
        @Expose
        private String companyOwner;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("companyType")
        @Expose
        private String companyType;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("password")
        @Expose
        private String password;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("phone")
        @Expose
        private String phone;

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getCompanyOwner() {
            return companyOwner;
        }

        public void setCompanyOwner(String companyOwner) {
            this.companyOwner = companyOwner;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getCompanyType() {
            return companyType;
        }

        public void setCompanyType(String companyType) {
            this.companyType = companyType;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }


}
