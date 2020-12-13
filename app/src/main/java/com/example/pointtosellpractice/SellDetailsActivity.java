package com.example.pointtosellpractice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.pointtosellpractice.customer.single_customer.SingleCustomerGetResponse;
import com.example.pointtosellpractice.customer.single_customer.SingleCustomerTotalSell;
import com.example.pointtosellpractice.customer.single_customer.SingleCustomerTotalSellCustomAdapter;
import com.example.pointtosellpractice.retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SellDetailsActivity extends AppCompatActivity {
    int position;
    String customer_id,token;


    SingleCustomerTotalSellCustomAdapter singleCustomerTotalSellCustomAdapter;
    List<SingleCustomerTotalSell> singleCustomerTotalSellList;

    RecyclerView sellDetailsRecyclerView;
    ApiInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_details);
        position=getIntent().getIntExtra("position",10000);

        //recycler view finding
        sellDetailsRecyclerView=findViewById(R.id.sellDetailsRecyclerViewId);
        customer_id= getIntent().getStringExtra("customerId");
        token=getIntent().getStringExtra("token");
    }

   
}