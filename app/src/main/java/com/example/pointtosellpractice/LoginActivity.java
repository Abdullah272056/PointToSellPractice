package com.example.pointtosellpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pointtosellpractice.model_class.LogInData;
import com.example.pointtosellpractice.model_class.LogInResponse;
import com.example.pointtosellpractice.model_class.RegistrationData;
import com.example.pointtosellpractice.retrofit.ApiInterface;
import com.example.pointtosellpractice.retrofit.RetrofitClient;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText signInEmailEditText,signInPasswordEditText;
    Button signInButton;
    TextView signUpTextView,resetPasswordTextView;
    ProgressBar progressBar;

    ApiInterface apiInterface,apiInterface1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //view finding
        signInEmailEditText=findViewById(R.id.signInEmailEditTextId);
        signInPasswordEditText=findViewById(R.id.signInPasswordEditTextId);
        signInButton=findViewById(R.id.signInButtonId);
        signUpTextView=findViewById(R.id.signUpTextViewId);
        resetPasswordTextView=findViewById(R.id.resetPasswordTextViewId);
        progressBar=findViewById(R.id.signInProgressBarId);


        // listener set
        signInButton.setOnClickListener(this);
        signUpTextView.setOnClickListener(this);
        resetPasswordTextView.setOnClickListener(this);

        //initialize apiInterface
        apiInterface = RetrofitClient.getRetrofit("http://mern-pos.herokuapp.com/").create(ApiInterface.class);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signInButtonId:
              signIn();
              
                break;
            case R.id.signUpTextViewId:

                break;
    }
    }

    private void signIn() {
        String signInEmail= signInEmailEditText.getText().toString().trim();
        String signInPassword= signInPasswordEditText.getText().toString().trim();
        if (TextUtils.isEmpty(signInEmail)){
            signInEmailEditText.setError("Enter your email");
            signInEmailEditText.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(signInEmail).matches()){
            signInEmailEditText.setError("Enter a valid  email address");
            signInEmailEditText.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(signInPassword)){
            signInPasswordEditText.setError("Enter  password");
            signInPasswordEditText.requestFocus();
            return;
        }
        if (signInPassword.length()<6){
            signInPasswordEditText.setError("Minimum length of a password should be 6");
            signInPasswordEditText.requestFocus();
            return;
        }

        LogInData logInData       =new LogInData(signInEmail,signInPassword);

        apiInterface.logInData(logInData).enqueue(new Callback<LogInResponse>() {
            @Override
            public void onResponse(Call<LogInResponse> call, Response<LogInResponse> response) {
                if (response.isSuccessful()){
                    // receive response body
                    LogInResponse logInResponse=response.body();

                    Toast.makeText(LoginActivity.this, "success", Toast.LENGTH_SHORT).show();
                    Log.e("TAG", "success");

                    Log.e("res",logInResponse.getToken().toString());

                }
            }

            @Override
            public void onFailure(Call<LogInResponse> call, Throwable t) {

            }
        });


//

    }
}