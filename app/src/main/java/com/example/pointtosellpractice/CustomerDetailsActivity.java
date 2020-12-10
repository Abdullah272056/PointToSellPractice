package com.example.pointtosellpractice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pointtosellpractice.customer.CustomerCustomAdapter;
import com.example.pointtosellpractice.customer.CustomerInformationData;
import com.example.pointtosellpractice.customer.CustomerInformationDataResponse;
import com.example.pointtosellpractice.retrofit.ApiInterface;
import com.example.pointtosellpractice.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerDetailsActivity extends AppCompatActivity {
    List<CustomerInformationData> customerInformationList;
    ApiInterface apiInterface;


    TextView cNameTextView,cPhoneTextView,cEmailTextView,cAddressTextView;
    TextView dueTextView;

    EditText duePayAmountEditText;
    Button payDueButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);

        // textView finding
        cNameTextView=findViewById(R.id.customerNameTextViewId);
        cPhoneTextView=findViewById(R.id.customerPhoneTextViewId);
        cEmailTextView=findViewById(R.id.customerEmailTextViewId);
        cAddressTextView=findViewById(R.id.customerAddressTextViewId);
        dueTextView=findViewById(R.id.dueTextViewId);
        //button finding
        payDueButton=findViewById(R.id.payDueButtonId);
        //editText finding
        duePayAmountEditText=findViewById(R.id.duePayAmountEditTextId);


        //token= getIntent().getStringExtra("token");

        // textView set Text
        cNameTextView.setText("Name :  "+getIntent().getStringExtra("cName"));
        cPhoneTextView.setText("Phone :  "+getIntent().getStringExtra("cPhone"));
        cEmailTextView.setText("Email :  "+getIntent().getStringExtra("cEmail"));
        cAddressTextView.setText("Address :  "+getIntent().getStringExtra("cAddress"));
        dueTextView.setText("Due :  "+getIntent().getStringExtra("cDue"));


        apiInterface = RetrofitClient.getRetrofit("http://mern-pos.herokuapp.com/").create(ApiInterface.class);



    }


}