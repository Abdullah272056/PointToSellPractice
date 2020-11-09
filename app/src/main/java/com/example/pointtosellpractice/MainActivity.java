package com.example.pointtosellpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pointtosellpractice.model_class.OurDataSet;
import com.example.pointtosellpractice.retrofit.ApiInterface;
import com.example.pointtosellpractice.retrofit.RetrofitClient;

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



        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                EditText companyNameEditText,companyOwnerEditText,emailEditText,phoneEditText,
//                        addressEditText,aboutCompanyEditText,passwordEditText,confirmPasswordEditText;
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





                OurDataSet ourDataSet       =new OurDataSet(companyName,companyOwner,email,companyType,
                        aboutCompany,password,address,phone);
                apiInterface.postData(ourDataSet).enqueue(new Callback<OurDataSet>(){
                    @Override
                    public void onResponse(Call<OurDataSet> call, Response<OurDataSet> response) {

                        Toast.makeText(MainActivity.this, "create success", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailure(Call<OurDataSet> call, Throwable t){
                        Log.e("wwe",t.getMessage().toString());
                        Toast.makeText(MainActivity.this, "fail", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });




    }
}