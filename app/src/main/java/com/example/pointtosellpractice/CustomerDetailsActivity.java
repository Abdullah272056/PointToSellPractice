package com.example.pointtosellpractice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.pointtosellpractice.customer.CustomerCustomAdapter;
import com.example.pointtosellpractice.customer.CustomerInformationData;
import com.example.pointtosellpractice.customer.CustomerInformationDataResponse;
import com.example.pointtosellpractice.retrofit.ApiInterface;
import com.example.pointtosellpractice.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerDetailsActivity extends AppCompatActivity {
    List<CustomerInformationData> customerInformationList;
    ApiInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);
        apiInterface = RetrofitClient.getRetrofit("http://mern-pos.herokuapp.com/").create(ApiInterface.class);



    }


}