package com.example.pointtosellpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pointtosellpractice.model_class.invoice.GetSellInfoByDayResponse;
import com.example.pointtosellpractice.retrofit.ApiInterface;
import com.example.pointtosellpractice.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SellInfoByDayActivity extends AppCompatActivity {
String token;
    ApiInterface apiInterface;

    Button sellInfoByDayOkButton;
    EditText sellInfoByDayEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_info_by_day);
        //finding view
        sellInfoByDayOkButton=findViewById(R.id.sellInfoByDayOkButtonId);
        sellInfoByDayEditText=findViewById(R.id.sellInfoByDayEditTextId);

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
                    Toast.makeText(SellInfoByDayActivity.this, "sssss", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<GetSellInfoByDayResponse> call, Throwable t) {
                Toast.makeText(SellInfoByDayActivity.this, String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();
                Log.e("uyt",String.valueOf(t.getMessage()));
            }
        });
    }
}