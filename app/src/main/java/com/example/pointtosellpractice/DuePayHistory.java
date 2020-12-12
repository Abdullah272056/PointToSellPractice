package com.example.pointtosellpractice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.pointtosellpractice.customer.single_customer.SingleCustomerDuePayCustomAdapter;

public class DuePayHistory extends AppCompatActivity {
 String customer_id,token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_due_pay_history);
        customer_id= getIntent().getStringExtra("customerId");
        token=getIntent().getStringExtra("token");

    }
}