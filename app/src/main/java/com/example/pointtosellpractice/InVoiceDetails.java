package com.example.pointtosellpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pointtosellpractice.model_class.owner_all_information.OwnerDataWithResponse;
import com.example.pointtosellpractice.retrofit.ApiInterface;
import com.example.pointtosellpractice.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InVoiceDetails extends AppCompatActivity {
    ApiInterface apiInterface;
    TextView companyNameTextView,ownerNameTextView,ownerAddressTextView,
    ownerEmailTextView,ownerPhoneTextView;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_voice_details);
        // textView finding
        companyNameTextView=findViewById(R.id.companyNameTextViewId);
        ownerNameTextView=findViewById(R.id.ownerNameTextViewId);
        ownerAddressTextView=findViewById(R.id.ownerAddressTextViewId);
        ownerEmailTextView=findViewById(R.id.ownerEmailTextViewId);
        ownerPhoneTextView=findViewById(R.id.ownerPhoneTextViewId);
        //receive token
        token= getIntent().getStringExtra("token");


        apiInterface = RetrofitClient.getRetrofit("http://mern-pos.herokuapp.com/").create(ApiInterface.class);

        apiInterface.getUserAllInformation("Bearer "+token).enqueue(new Callback<OwnerDataWithResponse>() {
            @Override
            public void onResponse(Call<OwnerDataWithResponse> call, Response<OwnerDataWithResponse> response) {

                OwnerDataWithResponse ownerDataWithResponse=response.body();
                Toast.makeText(InVoiceDetails.this, "success", Toast.LENGTH_SHORT).show();
                companyNameTextView.setText(String.valueOf(ownerDataWithResponse.getData().getCompanyName()));
                ownerNameTextView.setText(String.valueOf(ownerDataWithResponse.getData().getCompanyOwner()));
                ownerAddressTextView.setText(String.valueOf(ownerDataWithResponse.getData().getAddress()));
                ownerEmailTextView.setText(String.valueOf(ownerDataWithResponse.getData().getEmail()));
                ownerPhoneTextView.setText(String.valueOf(ownerDataWithResponse.getData().getPhone()));

                Log.e("gt", ownerDataWithResponse.getData().getEmail().toString());

            }

            @Override
            public void onFailure(Call<OwnerDataWithResponse> call, Throwable t) {
                Toast.makeText(InVoiceDetails.this, "fail", Toast.LENGTH_SHORT).show();
                Log.e("gt", "ff");
            }
        });
    }
}