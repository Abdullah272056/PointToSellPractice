package com.example.pointtosellpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText companyNameEditText,companyOwnerEditText,emailEditText,phoneEditText,
    addressEditText,aboutCompanyEditText,passwordEditText,confirmPasswordEditText;
    Spinner businessTypeSpinner;
    Button signUpButton;
    TextView signInTextView;


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



        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });




    }
}