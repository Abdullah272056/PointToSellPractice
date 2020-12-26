package com.example.pointtosellpractice.customer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.pointtosellpractice.R;

public class CustomerAllInfoActivity extends AppCompatActivity {
        TextView customerNameTextView,customerDataTextView,customerPayDueTextView,
                customerDuePayHistoryTextView,customerTotalSellTextView;
    String customer_id,token,cDue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Customer all info");
        setContentView(R.layout.activity_customer_all_info);
        // for add back Button in title bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //data receive
        customer_id=getIntent().getStringExtra("cId");
        token= getIntent().getStringExtra("token");
        cDue= getIntent().getStringExtra("cDue");

        //textView Finding
        customerNameTextView=findViewById(R.id.customerNameTextViewId);
        customerDataTextView=findViewById(R.id.customerDataTextViewId);
        customerPayDueTextView=findViewById(R.id.customerPayDueTextViewId);
        customerDuePayHistoryTextView=findViewById(R.id.customerDuePayHistoryTextViewId);
        customerTotalSellTextView=findViewById(R.id.customerTotalSellTextViewId);
        customerDataTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        customerPayDueTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CustomerAllInfoActivity.this, PayDueActivity.class);
                intent.putExtra("token",token);
                intent.putExtra("customerId",customer_id);
                intent.putExtra("cDue",cDue);
                startActivity(intent);
            }
        });
        customerDuePayHistoryTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CustomerAllInfoActivity.this, DuePayHistory.class);
                intent.putExtra("token",token);
                intent.putExtra("customerId",customer_id);
                startActivity(intent);
            }
        });
        customerTotalSellTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CustomerAllInfoActivity.this,SingleCustomerTotalSellActivity.class);
                intent.putExtra("token",token);
                intent.putExtra("customerId",customer_id);
                startActivity(intent);
            }
        });



    }

    // title bar  button clicked
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}