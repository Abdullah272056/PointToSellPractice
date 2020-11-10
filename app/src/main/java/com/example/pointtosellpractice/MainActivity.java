package com.example.pointtosellpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pointtosellpractice.model_class.RegistrationData;
import com.example.pointtosellpractice.retrofit.ApiInterface;
import com.example.pointtosellpractice.retrofit.RetrofitClient;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    EditText companyNameEditText,companyOwnerEditText,emailEditText,phoneEditText,
    addressEditText,aboutCompanyEditText,passwordEditText,confirmPasswordEditText;
    Spinner businessTypeSpinner;
    Button signUpButton;
    TextView signInTextView;

    ApiInterface apiInterface,apiInterface1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // view finding
        companyNameEditText=findViewById(R.id.signUpCompanyNameEditTextId);
        companyOwnerEditText=findViewById(R.id.signUpCompanyOwnerEditTextId);
        emailEditText=findViewById(R.id.signUpEmailEditTextId);
        phoneEditText=findViewById(R.id.signUpPhoneEditTextId);
        addressEditText=findViewById(R.id.signUpAddressEditTextId);
        aboutCompanyEditText=findViewById(R.id.signUpAboutCompanyEditTextId);
        passwordEditText=findViewById(R.id.signUpPasswordEditTextId);
        confirmPasswordEditText=findViewById(R.id.signUpConfirmPasswordEditTextId);
        signUpButton=findViewById(R.id.signUpButtonId);
        businessTypeSpinner=findViewById(R.id.businessTypeSpinnerId);
        signInTextView=findViewById(R.id.signInTextViewId);

        //initialize apiInterface
        apiInterface = RetrofitClient.getRetrofit("http://mern-pos.herokuapp.com/").create(ApiInterface.class);

        signInTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              String  companyName=companyNameEditText.getText().toString().trim();
              String  companyOwner=companyOwnerEditText.getText().toString().trim();
              String  email=emailEditText.getText().toString().trim();
              String  phone=phoneEditText.getText().toString().trim();
              String  address=addressEditText.getText().toString().trim();
              String  aboutCompany=aboutCompanyEditText.getText().toString().trim();
              String  password=passwordEditText.getText().toString().trim();
              String  confirmPassword=confirmPasswordEditText.getText().toString().trim();
              String companyType=businessTypeSpinner.getSelectedItem().toString();
             // String  password=passwordEditText.getText().toString().trim();





                RegistrationData ourDataSet       =new RegistrationData(companyName,companyOwner,email,companyType,
                        aboutCompany,password,address,phone);
               
            }
        });




    }
}