package com.example.pointtosellpractice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.pointtosellpractice.customer.single_customer.SingleCustomerDuePayCustomAdapter;
import com.example.pointtosellpractice.retrofit.ApiInterface;
import com.example.pointtosellpractice.retrofit.RetrofitClient;

public class DuePayHistory extends AppCompatActivity {
 String customer_id,token;
    SingleCustomerDuePayCustomAdapter singleCustomerDuePayCustomAdapter;
    RecyclerView duePayHistoryRecyclerView;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_due_pay_history);
        //receive data
        customer_id= getIntent().getStringExtra("customerId");
        token=getIntent().getStringExtra("token");

        //recycler view finding
        duePayHistoryRecyclerView=findViewById(R.id.duePayHistoryRecyclerViewId);

        apiInterface = RetrofitClient.getRetrofit("http://mern-pos.herokuapp.com/").create(ApiInterface.class);

        
    }
}