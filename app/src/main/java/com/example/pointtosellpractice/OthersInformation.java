package com.example.pointtosellpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class OthersInformation extends AppCompatActivity {
    TextView customerTextView,productTextView,invoiceTextView;
    TextView totalSaleAmountTextView,totalSoldProductQuantityTextView,totalSoldInvoiceTextView,
            totalDueAmountTextView,totalProfitTextView;
    TextView totalProductCostTextView,totalProductStockTextView,totalProductTypeTextView,extraInfoTextView;
    TextView customerCountTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others_information);
        //finding textView
        customerCountTextView=findViewById(R.id.customerCountId);
        totalSaleAmountTextView=findViewById(R.id.totalSaleAmountTextViewId);
        totalSoldProductQuantityTextView=findViewById(R.id.totalSoldProductQuantityTextViewId);
        totalSoldInvoiceTextView=findViewById(R.id.totalSoldInvoiceTextViewId);
        totalDueAmountTextView=findViewById(R.id.totalDueAmountTextViewId);
        totalProfitTextView=findViewById(R.id.totalProfitTextViewId);
        totalProductCostTextView=findViewById(R.id.totalProductCostTextViewId);
        totalProductStockTextView=findViewById(R.id.totalProductStockTextViewId);
        totalProductTypeTextView=findViewById(R.id.totalProductTypeTextViewId);

        customerTextView=findViewById(R.id.customerTextViewId);
        productTextView=findViewById(R.id.productTextViewId);
        invoiceTextView=findViewById(R.id.invoiceTextViewId);
        extraInfoTextView=findViewById(R.id.extraInfoTextViewId);

    }
}