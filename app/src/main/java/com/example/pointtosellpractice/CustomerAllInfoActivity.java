package com.example.pointtosellpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class CustomerAllInfoActivity extends AppCompatActivity {
        TextView customerNameTextView,customerDataTextView,customerPayDueTextView,
                customerDuePayHistoryTextView,customerTotalSellTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_all_info);

        //textView Finding
        customerNameTextView=findViewById(R.id.customerNameTextViewId);
        customerDataTextView=findViewById(R.id.customerDataTextViewId);
        customerPayDueTextView=findViewById(R.id.customerPayDueTextViewId);
        customerDuePayHistoryTextView=findViewById(R.id.customerDuePayHistoryTextViewId);
        customerTotalSellTextView=findViewById(R.id.customerTotalSellTextViewId);


    }
}