package com.example.pointtosellpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.pointtosellpractice.model_class.invoice.GetSellInfoByDayResponse;
import com.example.pointtosellpractice.model_class.invoice.single_invoice.SingleInvoiceGetResponse;
import com.example.pointtosellpractice.retrofit.ApiInterface;
import com.example.pointtosellpractice.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SellInfoByDateActivity extends AppCompatActivity {
String token;
    ApiInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_info_by_date);
        //receive data
        token= getIntent().getStringExtra("token");
        apiInterface = RetrofitClient.getRetrofit("http://mern-pos.herokuapp.com/").create(ApiInterface.class);
        getSellInfoByDate();

    }

    public void getSellInfoByDate(){
        apiInterface.getSellInfoByDay("Bearer "+token,"4").enqueue(new Callback<GetSellInfoByDayResponse>() {
            @Override
            public void onResponse(Call<GetSellInfoByDayResponse> call, Response<GetSellInfoByDayResponse> response) {

                if (response.body().getSuccess()==true){
                    Toast.makeText(SellInfoByDateActivity.this, "sssss", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<GetSellInfoByDayResponse> call, Throwable t) {


                Toast.makeText(SellInfoByDateActivity.this, String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();
                Log.e("uyt",String.valueOf(t.getMessage()));
            }
        });
    }
}