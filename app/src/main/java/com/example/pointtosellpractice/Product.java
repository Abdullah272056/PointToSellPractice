package com.example.pointtosellpractice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.pointtosellpractice.customer.CustomerCustomAdapter;
import com.example.pointtosellpractice.customer.CustomerInformationData;
import com.example.pointtosellpractice.customer.CustomerInformationDataResponse;
import com.example.pointtosellpractice.model_class.product.GetProductData;
import com.example.pointtosellpractice.model_class.product.GetProductDataResponse;
import com.example.pointtosellpractice.product.ProductCustomAdapter;
import com.example.pointtosellpractice.retrofit.ApiInterface;
import com.example.pointtosellpractice.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Product extends AppCompatActivity {
    List<GetProductData> getProductDataList;
    String token;
    ApiInterface apiInterface;
    ProductCustomAdapter productCustomAdapter;
    RecyclerView productRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        token= getIntent().getStringExtra("token");
        apiInterface = RetrofitClient.getRetrofit("http://mern-pos.herokuapp.com/").create(ApiInterface.class);
        productRecyclerView=findViewById(R.id.productRecyclerViewId);

        getAllProduct();


    }

    public void getAllProduct() {
        apiInterface.getAllProduct("Bearer "+token).enqueue(new Callback<GetProductDataResponse>() {
            @Override
            public void onResponse(Call<GetProductDataResponse> call, Response<GetProductDataResponse> response) {
                GetProductDataResponse getProductDataResponse=response.body();

                if (getProductDataResponse.getSuccess()==true){
                    getProductDataList=new ArrayList<>();
                    getProductDataList.addAll(response.body().getProducts());
                    Toast.makeText(Product.this, String.valueOf(getProductDataList.size()), Toast.LENGTH_SHORT).show();
                    if (getProductDataList.size ()>0){
                        productCustomAdapter = new ProductCustomAdapter(Product.this,token,getProductDataList);
                        productRecyclerView.setLayoutManager(new LinearLayoutManager(Product.this));
                        productRecyclerView.setAdapter(productCustomAdapter);
                    }
            }
            }

            @Override
            public void onFailure(Call<GetProductDataResponse> call, Throwable t) {

            }
        });


    }

}