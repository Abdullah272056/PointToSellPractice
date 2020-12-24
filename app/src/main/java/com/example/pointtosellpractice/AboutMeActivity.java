package com.example.pointtosellpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AboutMeActivity extends AppCompatActivity {
String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        //receive user token
        token= getIntent().getStringExtra("token");
    }
}