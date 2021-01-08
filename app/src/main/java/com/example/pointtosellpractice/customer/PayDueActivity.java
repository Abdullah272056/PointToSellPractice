package com.example.pointtosellpractice.customer;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pointtosellpractice.R;
import com.example.pointtosellpractice.customer.pay_due.DuePayDataResponse;
import com.example.pointtosellpractice.customer.pay_due.PayData;
import com.example.pointtosellpractice.customer.single_customer.SingleCustomerGetResponse;
import com.example.pointtosellpractice.retrofit.ApiInterface;
import com.example.pointtosellpractice.retrofit.RetrofitClient;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PayDueActivity extends AppCompatActivity {
    ApiInterface apiInterface;
    ProgressBar pauDueProgressBar;

    TextView cNameTextView,cPhoneTextView,cEmailTextView,cAddressTextView;
    TextView dueTextView,allTimeSellTextView;

    EditText duePayAmountEditText;
    Button payDueButton;
    String customer_id,token;

    String duePayAmount;
    PayData payData;
    DuePayDataResponse duePayDataResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Pay Due Activity");
        setContentView(R.layout.activity_pay_due);

        // for add back Button in title bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // textView finding
        cNameTextView=findViewById(R.id.customerNameTextViewId);
        cPhoneTextView=findViewById(R.id.customerPhoneTextViewId);
        cEmailTextView=findViewById(R.id.customerEmailTextViewId);
        cAddressTextView=findViewById(R.id.customerAddressTextViewId);
        dueTextView=findViewById(R.id.dueTextViewId);
        allTimeSellTextView=findViewById(R.id.allTimeSellTextViewId);
        //button finding
        payDueButton=findViewById(R.id.payDueButtonId);

        //editText finding
        duePayAmountEditText=findViewById(R.id.duePayAmountEditTextId);

        pauDueProgressBar=findViewById(R.id.pauDueProgressBarId);
        //data receive
        customer_id=getIntent().getStringExtra("customerId");
        token= getIntent().getStringExtra("token");

        apiInterface = RetrofitClient.getRetrofit("http://mern-pos.herokuapp.com/").create(ApiInterface.class);

        customerInformation();

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
        }if(Integer.parseInt(duePayAmount)>Integer.parseInt(dueTextView.getText().toString())){
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

//
//                       if (response.code()==404){
//                           Toast.makeText(PayDueActivity.this, "You send more amount than due", Toast.LENGTH_SHORT).show();
//                       }else if (response)

                        assert response.body() != null;
                        if (response.body().getSuccess()){
                            Toast.makeText(PayDueActivity.this,response.body().getMsg() , Toast.LENGTH_SHORT).show();
                            duePayDataResponse=response.body();
                            //assert duePayDataResponse != null;
                            pauDueProgressBar.setVisibility(View.INVISIBLE);
                            payDueButton.setVisibility(View.VISIBLE);
                            customerInformation();
                        }else {
                            Toast.makeText(PayDueActivity.this,response.body().getMsg() , Toast.LENGTH_SHORT).show();

                        }


                    }
                    @Override
                    public void onFailure(Call<DuePayDataResponse> call, Throwable t) {
                      pauDueProgressBar.setVisibility(View.INVISIBLE);
                        payDueButton.setVisibility(View.VISIBLE);
                    }
                });

    }



    public void customerInformation(){
        apiInterface.getSingleCustomerInformation("Bearer "+token,customer_id)
                .enqueue(new Callback<SingleCustomerGetResponse>() {
                    @Override
                    public void onResponse(Call<SingleCustomerGetResponse> call, Response<SingleCustomerGetResponse> response) {
                        SingleCustomerGetResponse singleCustomerGetResponse=response.body();

                        if (singleCustomerGetResponse.getSuccess()==true){

                            dueTextView.setText(String.valueOf(response.body().getSingleCustomerInformation().getDue()));
                            allTimeSellTextView.setText(String.valueOf(response.body().getSingleCustomerInformation().getAllTimeSellAmount()));

                            cNameTextView.setText("Name :  "+String.valueOf(response.body().getSingleCustomerInformation().getName()));
                            cPhoneTextView.setText("Phone :  "+String.valueOf(response.body().getSingleCustomerInformation().getPhone()));
                             cEmailTextView.setText("Email :  "+String.valueOf(response.body().getSingleCustomerInformation().getEmail()));
                            cAddressTextView.setText("Address :  "+String.valueOf(response.body().getSingleCustomerInformation().getAddress()));
                        }
                    }

                    @Override
                    public void onFailure(Call<SingleCustomerGetResponse> call, Throwable t) {
                        //pauDueHistoryProgressBar.setVisibility(View.INVISIBLE);
                    }
                });
    }


    // title bar  button clicked
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}