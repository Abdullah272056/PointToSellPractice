package com.example.pointtosellpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pointtosellpractice.model_class.LogInData;
import com.example.pointtosellpractice.model_class.LogInResponse;
import com.example.pointtosellpractice.retrofit.ApiInterface;
import com.example.pointtosellpractice.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText signInEmailEditText,signInPasswordEditText;
    Button signInButton;
    TextView signUpTextView,resetPasswordTextView;
    ProgressBar logInProgressBar;

    ApiInterface apiInterface;
    CheckBox rememberCheckBox;
    String signInEmail,signInPassword;

    SharePref sharePref;
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
        logInProgressBar=findViewById(R.id.signInProgressBarId);
        rememberCheckBox=findViewById(R.id.rememberCheckBoxId);
        // listener set
        signInButton.setOnClickListener(this);
        signUpTextView.setOnClickListener(this);
        resetPasswordTextView.setOnClickListener(this);

        //initialize apiInterface
        apiInterface = RetrofitClient.getRetrofit("http://mern-pos.herokuapp.com/").create(ApiInterface.class);


        // remember data retrieve and checking
        sharePref=new SharePref();
        String emailValue= sharePref.loadRememberEmail(LoginActivity.this);
        String passwordValue= sharePref.loadRememberPassword(LoginActivity.this);
        if (!emailValue.isEmpty() && !passwordValue.isEmpty()){
            signInEmailEditText.setText(String.valueOf(emailValue));
            signInPasswordEditText.setText(String.valueOf(passwordValue));
            signIn();
        }
        else {
            // Toast.makeText(this, "empty", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signInButtonId:
              signIn();
              
                break;
            case R.id.signUpTextViewId:
                Intent intent=new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);


                break;
            case R.id.resetPasswordTextViewId:

                break;
    }
    }

    private void signIn() {

         signInEmail= signInEmailEditText.getText().toString().trim();
         signInPassword= signInPasswordEditText.getText().toString().trim();
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
        logInProgressBar.setVisibility(View.VISIBLE);

        apiInterface.logInData(logInData).enqueue(new Callback<LogInResponse>() {
            @Override
            public void onResponse(Call<LogInResponse> call, Response<LogInResponse> response) {

                if (response.isSuccessful()){
                    // receive response body
                    LogInResponse logInResponse=response.body();
                    if (logInResponse.getSuccess()==true){
                        sharePref=new SharePref();
                        if (rememberCheckBox.isChecked()){
                            sharePref.rememberData(LoginActivity.this,signInEmail,signInPassword);
                            Toast.makeText(LoginActivity.this, "success", Toast.LENGTH_SHORT).show();
                        }
                        Intent intent=new Intent(LoginActivity.this,HomePage.class);
                        intent.putExtra("token",response.body().getToken());
                        startActivity(intent);
                        finish();
                    }
                    Log.e("res",logInResponse.getToken());

                }

                else {
                    Toast.makeText(LoginActivity.this, "password can not match", Toast.LENGTH_SHORT).show();
                    signInPasswordEditText.setError("password can not match");
                    signInPasswordEditText.requestFocus();
                }
                  
                logInProgressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<LogInResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "login fail ! Try again", Toast.LENGTH_SHORT).show();
                Log.e("lee",t.getMessage());
                logInProgressBar.setVisibility(View.GONE);

            }
        });


    }


}