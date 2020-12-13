package com.example.pointtosellpractice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.pointtosellpractice.customer.single_customer.SingleCustomerGetResponse;
import com.example.pointtosellpractice.customer.single_customer.SingleCustomerProduct;
import com.example.pointtosellpractice.customer.single_customer.SingleCustomerSellsDetailsCustomAdapter;
import com.example.pointtosellpractice.customer.single_customer.SingleCustomerTotalSell;
import com.example.pointtosellpractice.customer.single_customer.SingleCustomerTotalSellCustomAdapter;
import com.example.pointtosellpractice.retrofit.ApiInterface;
import com.example.pointtosellpractice.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SellDetailsActivity extends AppCompatActivity {
    int position;
    String customer_id,token;


    SingleCustomerSellsDetailsCustomAdapter singleCustomerSellsDetailsCustomAdapter;
    List<SingleCustomerTotalSell> singleCustomerTotalSellList;
    List<SingleCustomerProduct> singleCustomerProducts;

    RecyclerView sellDetailsRecyclerView;
    ApiInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_details);
        position=getIntent().getIntExtra("position",10);

        //recycler view finding
        sellDetailsRecyclerView=findViewById(R.id.sellDetailsRecyclerViewId);
        customer_id= getIntent().getStringExtra("customerId");
        token=getIntent().getStringExtra("token");
        apiInterface = RetrofitClient.getRetrofit("http://mern-pos.herokuapp.com/").create(ApiInterface.class);

        singleCustomerTotalSell();
    }

    public void singleCustomerTotalSell(){
        apiInterface.getSingleCustomerInformation("Bearer "+token,customer_id)
                .enqueue(new Callback<SingleCustomerGetResponse>() {
                    @Override
                    public void onResponse(Call<SingleCustomerGetResponse> call, Response<SingleCustomerGetResponse> response) {
                        SingleCustomerGetResponse singleCustomerGetResponse=response.body();

                        if (singleCustomerGetResponse.getSuccess()==true){
                            singleCustomerTotalSellList=new ArrayList<>();
                            singleCustomerProducts=new ArrayList<>();

                            singleCustomerTotalSellList.addAll(response.body().getSingleCustomerInformation().getTotalSell());
                            singleCustomerProducts.addAll(singleCustomerTotalSellList.get(position).getProducts());
                            if (singleCustomerProducts.size()>0){
                                singleCustomerSellsDetailsCustomAdapter = new SingleCustomerSellsDetailsCustomAdapter(SellDetailsActivity.this,token,singleCustomerProducts,position);
                                sellDetailsRecyclerView.setLayoutManager(new LinearLayoutManager(SellDetailsActivity.this));
                                sellDetailsRecyclerView.setAdapter(singleCustomerSellsDetailsCustomAdapter);
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<SingleCustomerGetResponse> call, Throwable t) {
                    }
                });
    }
}