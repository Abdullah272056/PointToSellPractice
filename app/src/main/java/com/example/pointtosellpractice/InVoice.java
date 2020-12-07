package com.example.pointtosellpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.pointtosellpractice.model_class.invoice.get_all_invoice.InVoiceResponse;
import com.example.pointtosellpractice.model_class.invoice.get_all_invoice.Invoice;
import com.example.pointtosellpractice.retrofit.ApiInterface;
import com.example.pointtosellpractice.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InVoice extends AppCompatActivity {
    ApiInterface apiInterface;
    String token;
    List<Invoice> invoiceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_voice);
        token= getIntent().getStringExtra("token");
        apiInterface = RetrofitClient.getRetrofit("http://mern-pos.herokuapp.com/").create(ApiInterface.class);
        getAllSellInfo();
    }

    // getting  Get all invoice
    private void getAllSellInfo() {

        apiInterface.getInvoice("Bearer "+token).enqueue(new Callback<InVoiceResponse>() {
            @Override
            public void onResponse(Call<InVoiceResponse> call, Response<InVoiceResponse> response) {
                Toast.makeText(InVoice.this, "success", Toast.LENGTH_SHORT).show();
                InVoiceResponse inVoiceResponse=response.body();
                if (inVoiceResponse.getSuccess()==true){
                    invoiceList=new ArrayList<>();
                    invoiceList.addAll(response.body().getInvoices());

                    if (invoiceList.size ()>0){

                        Toast.makeText(InVoice.this, String.valueOf(invoiceList.size()), Toast.LENGTH_SHORT).show();
                        Log.e("se",String.valueOf(invoiceList.get(0).getCustomer().getName()));

//                        customerCustomAdapter = new CustomerCustomAdapter(CustomerActivity.this,token,customerInformationList);
//                        customerRecyclerView.setLayoutManager(new LinearLayoutManager(CustomerActivity.this));
//                        customerRecyclerView.setAdapter(customerCustomAdapter);
                    }

                }

            }

            @Override
            public void onFailure(Call<InVoiceResponse> call, Throwable t) {
                Toast.makeText(InVoice.this, "fail", Toast.LENGTH_SHORT).show();

            }
        });

    }
}