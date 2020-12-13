package com.example.pointtosellpractice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ProgressBar;

import com.example.pointtosellpractice.customer.single_customer.SingleCustomerDuePayCustomAdapter;
import com.example.pointtosellpractice.customer.single_customer.SingleCustomerDuePayHistory;
import com.example.pointtosellpractice.retrofit.ApiInterface;

import java.util.List;

public class SingleCustomerTotalSellActivity extends AppCompatActivity {
    String customer_id,token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_customer_total_sell);

        //receive data
        customer_id= getIntent().getStringExtra("customerId");
        token=getIntent().getStringExtra("token");

    }
}