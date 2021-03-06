package com.example.pointtosellpractice.customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.pointtosellpractice.R;
import com.example.pointtosellpractice.customer.single_customer.SingleCustomerGetResponse;
import com.example.pointtosellpractice.customer.single_customer.SingleCustomerTotalSell;
import com.example.pointtosellpractice.customer.single_customer.SingleCustomerTotalSellCustomAdapter;
import com.example.pointtosellpractice.retrofit.ApiInterface;
import com.example.pointtosellpractice.retrofit.RetrofitClient;

import java.util.ArrayList;
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

        // for add back Button in title bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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

                            if (response.code()==404){
                                Toast.makeText(SingleCustomerTotalSellActivity.this, "No customer found", Toast.LENGTH_SHORT).show();
                            }else if (response.code()==500){
                                Toast.makeText(SingleCustomerTotalSellActivity.this, "internal server error", Toast.LENGTH_SHORT).show();
                            }else if (response.code()==200){
                                singleCustomerTotalSellList=new ArrayList<>();
                                singleCustomerTotalSellList.addAll(response.body().getSingleCustomerInformation().getTotalSell());
                                // reverse list inserting
                                //Collections.reverse(singleCustomerTotalSellList);
                                if (singleCustomerTotalSellList.size()>0){
                                            singleCustomerTotalSellCustomAdapter = new SingleCustomerTotalSellCustomAdapter(SingleCustomerTotalSellActivity.this, token, singleCustomerTotalSellList);
                                            singleCustomerTotalSellRecyclerView.setLayoutManager(new LinearLayoutManager(SingleCustomerTotalSellActivity.this));
                                            singleCustomerTotalSellRecyclerView.setAdapter(singleCustomerTotalSellCustomAdapter);
                                       }
                            }
                            else {

                            }
                            singleCustomerTotalSellProgressBar.setVisibility(View.INVISIBLE);


                        }


                    @Override
                    public void onFailure(Call<SingleCustomerGetResponse> call, Throwable t) {
                        singleCustomerTotalSellProgressBar.setVisibility(View.INVISIBLE);
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