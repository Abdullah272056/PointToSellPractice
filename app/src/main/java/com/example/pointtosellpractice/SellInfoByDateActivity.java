package com.example.pointtosellpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class SellInfoByDateActivity extends AppCompatActivity {
String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_info_by_date);
        //receive data
        token= getIntent().getStringExtra("token");

    }
}