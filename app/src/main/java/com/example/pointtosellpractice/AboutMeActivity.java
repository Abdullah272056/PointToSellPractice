package com.example.pointtosellpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.pointtosellpractice.model_class.owner_all_information.OwnerDataWithResponse;
import com.example.pointtosellpractice.retrofit.ApiInterface;
import com.example.pointtosellpractice.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutMeActivity extends AppCompatActivity {
String token;
ApiInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        //receive user token
        token= getIntent().getStringExtra("token");
        apiInterface = RetrofitClient.getRetrofit("http://mern-pos.herokuapp.com/").create(ApiInterface.class);
        getUserAllInfo();

    }
    public void getUserAllInfo (){
        apiInterface.getUserAllInformation("Bearer "+token).enqueue(new Callback<OwnerDataWithResponse>() {
            @Override
            public void onResponse(Call<OwnerDataWithResponse> call, Response<OwnerDataWithResponse> response) {
                if (response.isSuccessful()){
                    if (response.body().getSuccess()==true){
                        Toast.makeText(AboutMeActivity.this, "success", Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(AboutMeActivity.this, "server error", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(AboutMeActivity.this, "server error", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<OwnerDataWithResponse> call, Throwable t) {
                Toast.makeText(AboutMeActivity.this, String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();

            }
        });
    }
}