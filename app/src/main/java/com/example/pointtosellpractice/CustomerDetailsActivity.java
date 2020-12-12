package com.example.pointtosellpractice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pointtosellpractice.customer.CustomerCustomAdapter;
import com.example.pointtosellpractice.customer.CustomerInformationData;
import com.example.pointtosellpractice.customer.CustomerInformationDataResponse;
import com.example.pointtosellpractice.customer.pay_due.DuePayData;
import com.example.pointtosellpractice.customer.pay_due.DuePayDataResponse;
import com.example.pointtosellpractice.customer.pay_due.PayData;
import com.example.pointtosellpractice.customer.single_customer.SingleCustomerDuePayCustomAdapter;
import com.example.pointtosellpractice.customer.single_customer.SingleCustomerDuePayHistory;
import com.example.pointtosellpractice.customer.single_customer.SingleCustomerGetResponse;
import com.example.pointtosellpractice.model_class.LogInData;
import com.example.pointtosellpractice.retrofit.ApiInterface;
import com.example.pointtosellpractice.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerDetailsActivity extends AppCompatActivity {
    Button duePayHistoryButton;
    ApiInterface apiInterface;
    ProgressBar pauDueProgressBar;

    TextView cNameTextView,cPhoneTextView,cEmailTextView,cAddressTextView;
    TextView dueTextView;

    EditText duePayAmountEditText;
    Button payDueButton;
    String customer_id,token;

    String duePayAmount;
    PayData payData;
    DuePayDataResponse duePayDataResponse;
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
        duePayHistoryButton=findViewById(R.id.duePayHistoryButtonId);
        //editText finding
        duePayAmountEditText=findViewById(R.id.duePayAmountEditTextId);

        pauDueProgressBar=findViewById(R.id.pauDueProgressBarId);


        // textView set Text
        cNameTextView.setText("Name :  "+getIntent().getStringExtra("cName"));
        cPhoneTextView.setText("Phone :  "+getIntent().getStringExtra("cPhone"));
        cEmailTextView.setText("Email :  "+getIntent().getStringExtra("cEmail"));
        cAddressTextView.setText("Address :  "+getIntent().getStringExtra("cAddress"));
        dueTextView.setText("Due :  "+getIntent().getStringExtra("cDue"));
        customer_id=getIntent().getStringExtra("cId");

        token= getIntent().getStringExtra("token");

        apiInterface = RetrofitClient.getRetrofit("http://mern-pos.herokuapp.com/").create(ApiInterface.class);


        duePayHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CustomerDetailsActivity.this, DuePayHistory.class);
                intent.putExtra("token",token);
                intent.putExtra("customerId",customer_id);

                startActivity(intent);
            }
        });

        payDueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                payDue();

                }
        });


    }


    public void payDue(){
        duePayAmount = duePayAmountEditText.getText().toString();
        if (TextUtils.isEmpty(duePayAmount)){
            duePayAmountEditText.setError("Enter  password");
            duePayAmountEditText.requestFocus();
            return;
        }if(Integer.parseInt(duePayAmount)>Integer.parseInt(getIntent().getStringExtra("cDue"))){
            duePayAmountEditText.setError("You send more amount than due");
            duePayAmountEditText.requestFocus();
            return;
        }
        payDueButton.setVisibility(View.INVISIBLE);
            pauDueProgressBar.setVisibility(View.VISIBLE);
            payData=new PayData(customer_id,Integer.parseInt(duePayAmount));

            apiInterface.payDue("Bearer "+token,payData)
                .enqueue(new Callback<DuePayDataResponse>() {
                    @Override
                    public void onResponse(Call<DuePayDataResponse> call, Response<DuePayDataResponse> response) {
                        assert response.body() != null;
                        if (response.body().getSuccess()){

                            Toast.makeText(CustomerDetailsActivity.this,response.body().getMsg() , Toast.LENGTH_SHORT).show();
                            duePayDataResponse=response.body();
                            //assert duePayDataResponse != null;
                            pauDueProgressBar.setVisibility(View.INVISIBLE);
                            payDueButton.setVisibility(View.VISIBLE);
                        }else {
                            Toast.makeText(CustomerDetailsActivity.this,response.body().getMsg() , Toast.LENGTH_SHORT).show();

                        }


                    }
                    @Override
                    public void onFailure(Call<DuePayDataResponse> call, Throwable t) {
                      pauDueProgressBar.setVisibility(View.INVISIBLE);
                        payDueButton.setVisibility(View.VISIBLE);
                    }
                });

    }




}