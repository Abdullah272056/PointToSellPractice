package com.example.pointtosellpractice;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
TextView companyNameTextView,companyEmailTextView,companyPhoneTextView,
        companyAddressTextView, memberSinceTextView;
Button uploadPictureButton,changePasswordButton,deleteAccountButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        //receive user token
        token= getIntent().getStringExtra("token");
        apiInterface = RetrofitClient.getRetrofit("http://mern-pos.herokuapp.com/").create(ApiInterface.class);
        getUserAllInfo();
        // textView finding
        companyNameTextView=findViewById(R.id.companyNameTextViewId);
        companyEmailTextView=findViewById(R.id.companyEmailTextViewId);
        companyPhoneTextView=findViewById(R.id.companyPhoneTextViewId);
        companyAddressTextView=findViewById(R.id.companyAddressTextViewId);
        memberSinceTextView=findViewById(R.id.memberSinceTextViewId);
        // button finding
        uploadPictureButton=findViewById(R.id.uploadPictureButtonId);
        changePasswordButton=findViewById(R.id.changePasswordButtonId);
        deleteAccountButton=findViewById(R.id.deleteAccountButtonId);
        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder     =new AlertDialog.Builder(AboutMeActivity.this);
                LayoutInflater layoutInflater   =LayoutInflater.from(AboutMeActivity.this);
                View view                       =layoutInflater.inflate(R.layout.change_password_page,null);
                builder.setView(view);
                final AlertDialog alertDialog   = builder.create();

                alertDialog.show();
            }
        });


    }
    public void getUserAllInfo (){
        apiInterface.getUserAllInformation("Bearer "+token).enqueue(new Callback<OwnerDataWithResponse>() {
            @Override
            public void onResponse(Call<OwnerDataWithResponse> call, Response<OwnerDataWithResponse> response) {
                if (response.isSuccessful()){
                    if (response.body().getSuccess()==true){
//                        TextView companyNameTextView,companyEmailTextView,companyPhoneTextView,
//                                companyAddressTextView, memberSinceTextView;
                        companyNameTextView.setText(String.valueOf(response.body().getData().getCompanyName()));
                        companyEmailTextView.setText(String.valueOf(response.body().getData().getEmail()));
                        companyPhoneTextView.setText(String.valueOf(response.body().getData().getPhone()));
                        companyAddressTextView.setText(String.valueOf(response.body().getData().getAddress()));
                        memberSinceTextView.setText(String.valueOf(response.body().getData().getCreatedAt()));
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