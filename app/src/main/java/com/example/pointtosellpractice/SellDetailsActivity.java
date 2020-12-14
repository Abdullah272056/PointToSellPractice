package com.example.pointtosellpractice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
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
    List<SingleCustomerProduct> singleCustomerProductList;
    TextView productTotalPriceTextView,totalAmountAfterDiscountTextView,
            payAmountTextView, dueTextView,discountTextView;
    RecyclerView sellDetailsRecyclerView;
    ProgressBar sellDetailsProgressBar;
    ApiInterface apiInterface;
    int sub=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_details);
        position=getIntent().getIntExtra("position",10);

        //textView finding
        productTotalPriceTextView=findViewById(R.id.productTotalPriceTextViewId);
        totalAmountAfterDiscountTextView=findViewById(R.id.totalAmountAfterDiscountTextViewId);
        payAmountTextView=findViewById(R.id.payAmountTextViewId);
        dueTextView=findViewById(R.id.dueTextViewId);
        discountTextView=findViewById(R.id.discountTextViewId);

        //recycler view finding
        sellDetailsRecyclerView=findViewById(R.id.sellDetailsRecyclerViewId);
        //progressbar finding
        sellDetailsProgressBar=findViewById(R.id.sellDetailsProgressBarId);
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
                        sellDetailsProgressBar.setVisibility(View.INVISIBLE);
                        if (singleCustomerGetResponse.getSuccess()==true){
                            singleCustomerTotalSellList=new ArrayList<>();
                            singleCustomerProductList=new ArrayList<>();

                            singleCustomerTotalSellList.addAll(response.body().getSingleCustomerInformation().getTotalSell());
                            singleCustomerProductList.addAll(singleCustomerTotalSellList.get(position).getProducts());
                            if (singleCustomerProductList.size()>0){
                                singleCustomerSellsDetailsCustomAdapter = new SingleCustomerSellsDetailsCustomAdapter(SellDetailsActivity.this,token,singleCustomerProductList,position);
                                sellDetailsRecyclerView.setLayoutManager(new LinearLayoutManager(SellDetailsActivity.this));
                                sellDetailsRecyclerView.setAdapter(singleCustomerSellsDetailsCustomAdapter);
//

                                totalAmountAfterDiscountTextView.setText(String.valueOf(singleCustomerTotalSellList.get(position).getTotalAmountAfterDiscount()));
                                payAmountTextView.setText(String.valueOf(singleCustomerTotalSellList.get(position).getPayAmount()));
                                dueTextView.setText(String.valueOf(singleCustomerTotalSellList.get(position).getDue()));
                                discountTextView.setText("after "+String.valueOf(singleCustomerTotalSellList.get(position).getDiscount())+"% discount : ");

                                int size=singleCustomerProductList.size();
                                for ( int i=size-1; i>=0;i--){
                                    Log.e(String.valueOf(i),String.valueOf(singleCustomerProductList.get(i).getSellingPrice()));
                                    sub=sub+(singleCustomerProductList.get(i).getSellingPrice()*singleCustomerProductList.get(i).getQuantity());
                                    Log.e("svs",String.valueOf(sub));
                                }
                                 productTotalPriceTextView.setText(String.valueOf(sub));

                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<SingleCustomerGetResponse> call, Throwable t) {
                        sellDetailsProgressBar.setVisibility(View.INVISIBLE);

                    }
                });
    }
}