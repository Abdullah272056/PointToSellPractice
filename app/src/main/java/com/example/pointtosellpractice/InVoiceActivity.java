package com.example.pointtosellpractice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.pointtosellpractice.invoice.InvoiceCustomAdapter;
import com.example.pointtosellpractice.model_class.invoice.get_all_invoice.InVoiceResponse;
import com.example.pointtosellpractice.model_class.invoice.get_all_invoice.Invoice;
import com.example.pointtosellpractice.retrofit.ApiInterface;
import com.example.pointtosellpractice.retrofit.RetrofitClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InVoiceActivity extends AppCompatActivity{
    ApiInterface apiInterface;
    String token;
    List<Invoice> invoiceList;
    InvoiceCustomAdapter invoiceCustomAdapter;
    RecyclerView invoiceRecyclerView;
    ProgressBar invoiceProgressBar;
    FloatingActionButton addInvoiceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("InVoice List");

        setContentView(R.layout.activity_in_voice);
        invoiceRecyclerView=findViewById(R.id.invoiceRecyclerViewId);
        invoiceProgressBar=findViewById(R.id.invoiceProgressBarId);
        addInvoiceButton=findViewById(R.id.addInvoiceButtonId);



        token= getIntent().getStringExtra("token");
        apiInterface = RetrofitClient.getRetrofit("http://mern-pos.herokuapp.com/").create(ApiInterface.class);
        getAllInVoice();
        addInvoiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(InVoiceActivity.this, CreateInVoice_Activity.class);
                intent.putExtra("token",token);
                startActivity(intent);
                finish();
                Toast.makeText(InVoiceActivity.this, "clicked", Toast.LENGTH_SHORT).show();
            }
        });

    }

    // getting  Get all invoice
    private void getAllInVoice(){

        apiInterface.getInvoice("Bearer "+token).enqueue(new Callback<InVoiceResponse>() {
            @Override
            public void onResponse(Call<InVoiceResponse> call, Response<InVoiceResponse> response) {
                Toast.makeText(InVoiceActivity.this, "success", Toast.LENGTH_SHORT).show();

                if (response.isSuccessful()){
                    if (response.body().getSuccess()==true){
                        invoiceList=new ArrayList<>();
                        invoiceList.addAll(response.body().getInvoices());

                        if (invoiceList.size ()>0){
                            Toast.makeText(InVoiceActivity.this, String.valueOf(invoiceList.size()), Toast.LENGTH_SHORT).show();
                            Log.e("se",String.valueOf(invoiceList.get(0).getCustomer().getName()));
                            invoiceCustomAdapter = new InvoiceCustomAdapter(InVoiceActivity.this,token,invoiceList);
                            invoiceRecyclerView.setLayoutManager(new LinearLayoutManager(InVoiceActivity.this));
                            invoiceRecyclerView.setAdapter(invoiceCustomAdapter);
                        }
                    }
                }

                invoiceProgressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<InVoiceResponse> call, Throwable t) {
                Toast.makeText(InVoiceActivity.this, "fail", Toast.LENGTH_SHORT).show();
                invoiceProgressBar.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent =new Intent(InVoiceActivity.this,HomePage.class);
        intent.putExtra("token",token);
        startActivity(intent);
        finish();
    }
}