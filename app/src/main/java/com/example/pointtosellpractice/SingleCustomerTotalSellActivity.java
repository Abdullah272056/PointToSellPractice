package com.example.pointtosellpractice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.pointtosellpractice.customer.single_customer.SingleCustomerDuePayCustomAdapter;
import com.example.pointtosellpractice.customer.single_customer.SingleCustomerDuePayHistory;
import com.example.pointtosellpractice.customer.single_customer.SingleCustomerGetResponse;
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

public class SingleCustomerTotalSellActivity extends AppCompatActivity {
    String customer_id,token;

    SingleCustomerTotalSellCustomAdapter singleCustomerTotalSellCustomAdapter;
    List<SingleCustomerTotalSell> singleCustomerTotalSellList;

    RecyclerView singleCustomerTotalSellRecyclerView;
    ApiInterface apiInterface;
    ProgressBar singleCustomerTotalSellProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Single customer total sell");

        setContentView(R.layout.activity_single_customer_total_sell);

        //receive data
        customer_id= getIntent().getStringExtra("customerId");
        token=getIntent().getStringExtra("token");
        //recycler view finding
        singleCustomerTotalSellRecyclerView=findViewById(R.id.singleCustomerTotalSellRecyclerViewId);
        //progressbar finding
        singleCustomerTotalSellProgressBar=findViewById(R.id.singleCustomerTotalSellProgressBarId);

        apiInterface = RetrofitClient.getRetrofit("http://mern-pos.herokuapp.com/").create(ApiInterface.class);
        singleCustomerTotalSell();
    }


    public void singleCustomerTotalSell(){
        apiInterface.getSingleCustomerInformation("Bearer "+token,customer_id)
                .enqueue(new Callback<SingleCustomerGetResponse>() {
                    @Override
                    public void onResponse(Call<SingleCustomerGetResponse> call, Response<SingleCustomerGetResponse> response) {
                        SingleCustomerGetResponse singleCustomerGetResponse=response.body();
                        singleCustomerTotalSellProgressBar.setVisibility(View.INVISIBLE);
                        if (singleCustomerGetResponse.getSuccess()==true){
                            singleCustomerTotalSellList=new ArrayList<>();
                            singleCustomerTotalSellList.addAll(response.body().getSingleCustomerInformation().getTotalSell());
                            // reverse list inserting
                            //Collections.reverse(singleCustomerTotalSellList);
                            if (singleCustomerTotalSellList.size()>0){
                                singleCustomerTotalSellCustomAdapter = new SingleCustomerTotalSellCustomAdapter(SingleCustomerTotalSellActivity.this,token,singleCustomerTotalSellList);
                                singleCustomerTotalSellRecyclerView.setLayoutManager(new LinearLayoutManager(SingleCustomerTotalSellActivity.this));
                                singleCustomerTotalSellRecyclerView.setAdapter(singleCustomerTotalSellCustomAdapter);
                            }

                            Log.e("oooo",String.valueOf(singleCustomerTotalSellList.size()));
                        }
                    }

                    @Override
                    public void onFailure(Call<SingleCustomerGetResponse> call, Throwable t) {
                        singleCustomerTotalSellProgressBar.setVisibility(View.INVISIBLE);
                    }
                });
    }
}