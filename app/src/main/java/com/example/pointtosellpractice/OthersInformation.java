package com.example.pointtosellpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pointtosellpractice.customer.CustomerCountResponse;
import com.example.pointtosellpractice.invoice.get_all_sell_info.GetAllSellInfoResponse;
import com.example.pointtosellpractice.product.get_all_product_info.GetAllProductInfoDataResponse;
import com.example.pointtosellpractice.retrofit.ApiInterface;
import com.example.pointtosellpractice.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OthersInformation extends AppCompatActivity {
    TextView customerTextView,productTextView,invoiceTextView;
    TextView totalSaleAmountTextView,totalSoldProductQuantityTextView,totalSoldInvoiceTextView,
            totalDueAmountTextView,totalProfitTextView;
    TextView totalProductCostTextView,totalProductStockTextView,totalProductTypeTextView,extraInfoTextView;
    TextView customerCountTextView;
    Button sellInfoByDateButton;

    ApiInterface apiInterface;
    String token;
    String customerCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("More Info");
        setContentView(R.layout.activity_others_information);
        // for add back Button in title bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        // button finding
        sellInfoByDateButton=findViewById(R.id.sellInfoByDateButtonId);

        customerTextView=findViewById(R.id.customerTextViewId);
        productTextView=findViewById(R.id.productTextViewId);
        invoiceTextView=findViewById(R.id.invoiceTextViewId);
        extraInfoTextView=findViewById(R.id.extraInfoTextViewId);
        //receive user token
        token= getIntent().getStringExtra("token");
        apiInterface = RetrofitClient.getRetrofit("http://mern-pos.herokuapp.com/").create(ApiInterface.class);
        sellInfoByDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add code

                Intent intent =new Intent(OthersInformation.this, SellInfoByDayActivity.class);
                intent.putExtra("token",token);
                startActivity(intent);
            }
        });

        getCustomerCount();
        getAllSellInfo();
        getAllProductInfo();
    }



    // getting total customer count
    private void getCustomerCount() {
        apiInterface.getCustomerCount("Bearer "+token).enqueue(new Callback<CustomerCountResponse>() {
            @Override
            public void onResponse(Call<CustomerCountResponse> call, Response<CustomerCountResponse> response) {
                Log.e("count","success");
                CustomerCountResponse customerCountResponse=response.body();
                customerCount=customerCountResponse.getCustomerCount().toString();
                customerCountTextView.setText(customerCount);
            }

            @Override
            public void onFailure(Call<CustomerCountResponse> call, Throwable t) {
                Log.e("count","success");
            }
        });
    }


    // getting  Get all sale info
    private void getAllSellInfo() {
        apiInterface.getAllSellInfo("Bearer "+token).enqueue(new Callback<GetAllSellInfoResponse>() {
            @Override
            public void onResponse(Call<GetAllSellInfoResponse> call, Response<GetAllSellInfoResponse> response) {

                if (response.isSuccessful()){
                if (response.body().getSuccess()==true){
                    GetAllSellInfoResponse getAllSellInfoResponse=response.body();
                    Log.e("totalSaleAmount",getAllSellInfoResponse.getGetAllSellInfoData().getTotalSaleAmount().toString());
                totalSaleAmountTextView.setText(getAllSellInfoResponse.getGetAllSellInfoData().getTotalSaleAmount().toString());
                totalSoldProductQuantityTextView.setText(getAllSellInfoResponse.getGetAllSellInfoData().getTotalSoldProductQuantity().toString());
                totalSoldInvoiceTextView.setText(getAllSellInfoResponse.getGetAllSellInfoData().getTotalSoldInvoice().toString());
                totalDueAmountTextView.setText(getAllSellInfoResponse.getGetAllSellInfoData().getTotalDueAmount().toString());
                totalProfitTextView.setText(getAllSellInfoResponse.getGetAllSellInfoData().getTotalProfit().toString());
            }
                }
            }
            @Override
            public void onFailure(Call<GetAllSellInfoResponse> call, Throwable t) {
                Log.e("ts","success");

            }
        });

    }


    // Get all select_customer info
    private void getAllProductInfo(){
        apiInterface.getAllProductInfo("Bearer "+token).enqueue(new Callback<GetAllProductInfoDataResponse>() {
            @Override
            public void onResponse(Call<GetAllProductInfoDataResponse> call, Response<GetAllProductInfoDataResponse> response) {
            if (response.isSuccessful()){
                if (response.body().getSuccess()==true){
                    GetAllProductInfoDataResponse getAllProductInfoDataResponse=response.body();
                    totalProductCostTextView.setText(getAllProductInfoDataResponse.getGetAllProductInfoData().getTotalProductCost().toString());
                    totalProductStockTextView.setText(getAllProductInfoDataResponse.getGetAllProductInfoData().getTotalProduct().toString());
                    totalProductTypeTextView.setText(getAllProductInfoDataResponse.getGetAllProductInfoData().getTotalProductType().toString());
                }
            }
            }
            @Override
            public void onFailure(Call<GetAllProductInfoDataResponse> call, Throwable t) {
                Toast.makeText(OthersInformation.this, "failed", Toast.LENGTH_SHORT).show();
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