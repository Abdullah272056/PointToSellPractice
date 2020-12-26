package com.example.pointtosellpractice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.pointtosellpractice.customer.single_customer.SingleCustomerDuePayCustomAdapter;
import com.example.pointtosellpractice.customer.single_customer.SingleCustomerDuePayHistory;
import com.example.pointtosellpractice.customer.single_customer.SingleCustomerGetResponse;
import com.example.pointtosellpractice.retrofit.ApiInterface;
import com.example.pointtosellpractice.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DuePayHistory extends AppCompatActivity {
        String customer_id,token;
    SingleCustomerDuePayCustomAdapter singleCustomerDuePayCustomAdapter;
    List<SingleCustomerDuePayHistory> singleCustomerDuePayHistoryList;

    RecyclerView duePayHistoryRecyclerView;
    ApiInterface apiInterface;
    ProgressBar pauDueHistoryProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Due Pay History");
        setContentView(R.layout.activity_due_pay_history);

        // for add back Button in title bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //receive data
        customer_id= getIntent().getStringExtra("customerId");
        token=getIntent().getStringExtra("token");

        //recycler view finding
        duePayHistoryRecyclerView=findViewById(R.id.duePayHistoryRecyclerViewId);
        //ProgressBar finding
        pauDueHistoryProgressBar=findViewById(R.id.pauDueHistoryProgressBarId);

        apiInterface = RetrofitClient.getRetrofit("http://mern-pos.herokuapp.com/").create(ApiInterface.class);
        singleCustomerInformation();

    }

    public void singleCustomerInformation(){
        apiInterface.getSingleCustomerInformation("Bearer "+token,customer_id)
                .enqueue(new Callback<SingleCustomerGetResponse>() {
                    @Override
                    public void onResponse(Call<SingleCustomerGetResponse> call, Response<SingleCustomerGetResponse> response) {
                        SingleCustomerGetResponse singleCustomerGetResponse=response.body();

                        if (singleCustomerGetResponse.getSuccess()==true){
                            pauDueHistoryProgressBar.setVisibility(View.INVISIBLE);
                            singleCustomerDuePayHistoryList=new ArrayList<>();
                            singleCustomerDuePayHistoryList.addAll(response.body().getSingleCustomerInformation().getDuePayHistory());
                            // reverse list inserting
                            Collections.reverse(singleCustomerDuePayHistoryList);
                            if (singleCustomerDuePayHistoryList.size()>0){
                                singleCustomerDuePayCustomAdapter = new SingleCustomerDuePayCustomAdapter(DuePayHistory.this,token,singleCustomerDuePayHistoryList);
                                duePayHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(DuePayHistory.this));
                                duePayHistoryRecyclerView.setAdapter(singleCustomerDuePayCustomAdapter);
                            }

                            Log.e("asas",String.valueOf(singleCustomerDuePayHistoryList.size()));
                        }
                        Toast.makeText(DuePayHistory.this, "qqqq", Toast.LENGTH_SHORT).show();
                        Toast.makeText(DuePayHistory.this, singleCustomerGetResponse.getMsg().toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<SingleCustomerGetResponse> call, Throwable t) {
                        pauDueHistoryProgressBar.setVisibility(View.INVISIBLE);
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