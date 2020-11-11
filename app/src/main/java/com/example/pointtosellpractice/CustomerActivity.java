package com.example.pointtosellpractice;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.pointtosellpractice.customer.CustomerCustomAdapter;
import com.example.pointtosellpractice.customer.CustomerData;
import com.example.pointtosellpractice.customer.AddCustomerResponse;
import com.example.pointtosellpractice.customer.CustomerInformationData;
import com.example.pointtosellpractice.customer.CustomerInformationDataResponse;
import com.example.pointtosellpractice.retrofit.ApiInterface;
import com.example.pointtosellpractice.retrofit.RetrofitClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerActivity extends AppCompatActivity {
    RecyclerView customerRecyclerView;
    FloatingActionButton addCustomerButton;

   // List<CustomerInformationData> customerInformationDataList;
    List<CustomerInformationData> customerInformationList;
    String token;

    ApiInterface apiInterface;
    EditText customerNameEditText,customerEmailEditText,customerPhoneEditText,customerAddressEditText;
    Button addCustomerDataButton,cancelCustomerButton;
    CustomerData customerData;
    ProgressBar progressBar;
    CustomerCustomAdapter customerCustomAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        customerRecyclerView=findViewById(R.id.customerRecyclerViewId);
        addCustomerButton=findViewById(R.id.addCustomerButtonId);
        token= getIntent().getStringExtra("token1");
        apiInterface = RetrofitClient.getRetrofit("http://mern-pos.herokuapp.com/").create(ApiInterface.class);

        addCustomerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCustomerInformation();
            }
        });


        apiInterface.getAllCustomerInformation("Bearer "+token).enqueue(new Callback<CustomerInformationDataResponse>() {
            @Override
            public void onResponse(Call<CustomerInformationDataResponse> call, Response<CustomerInformationDataResponse> response) {
                Toast.makeText(CustomerActivity.this, "Success", Toast.LENGTH_SHORT).show();


                CustomerInformationDataResponse customerInformationDataResponse=response.body();
//
                if (customerInformationDataResponse.getSuccess()==true){
                    customerInformationList=new ArrayList<>();
                    customerInformationList.addAll(response.body().getCustomerInformation());
                    if (customerInformationList.size ()>0){
                        customerCustomAdapter = new CustomerCustomAdapter(CustomerActivity.this,customerInformationList);
                        customerRecyclerView.setLayoutManager(new LinearLayoutManager(CustomerActivity.this));
                        customerRecyclerView.setAdapter(customerCustomAdapter);
                    }


//                    if (TextUtils.isEmpty(customerInformationDataList.get(0).getEmail()) ||
//                            customerInformationDataList.get(0).getEmail()==null)  {
//                        Log.e("namec","No Email");
//                        Toast.makeText(CustomerActivity.this, "Ssss", Toast.LENGTH_SHORT).show();
//
//                    }else {
//                        Log.e("namec",customerInformationDataList.get(2).getEmail().toString());
//                        Toast.makeText(CustomerActivity.this, "Ssss", Toast.LENGTH_SHORT).show();
//                    }

                }
            }
            @Override
            public void onFailure(Call<CustomerInformationDataResponse> call, Throwable t) {
                Toast.makeText(CustomerActivity.this, "fail:  "+t.getMessage().toString(), Toast.LENGTH_SHORT).show();
                Log.e("namec","error: "+t.getMessage().toString());

            }
        });




    }








    private void addCustomerInformation(){

        AlertDialog.Builder builder     =new AlertDialog.Builder(CustomerActivity.this);
        LayoutInflater layoutInflater   =LayoutInflater.from(CustomerActivity.this);
        View view                       =layoutInflater.inflate(R.layout.add_customer_data,null);
        builder.setView(view);
        final AlertDialog alertDialog   = builder.create();

        customerNameEditText=view.findViewById(R.id.customerNameEditTextId);
        customerPhoneEditText=view.findViewById(R.id.customerPhoneEditTextId);
        customerEmailEditText=view.findViewById(R.id.customerEmailEditTextId);
        customerAddressEditText=view.findViewById(R.id.customerAddressEditTextId);

        addCustomerDataButton=view.findViewById(R.id.saveCustomerDataButtonId);
        cancelCustomerButton=view.findViewById(R.id.cancelCustomerDataButtonId);
        addCustomerDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String customerName=customerNameEditText.getText().toString();
                String customerPhone=customerPhoneEditText.getText().toString();
                String customerEmail=customerEmailEditText.getText().toString();
                String customerAddress=customerAddressEditText.getText().toString();

                if (TextUtils.isEmpty(customerName) || customerAddress==null){
                    customerNameEditText.setError("Enter customer name");
                    customerNameEditText.requestFocus();
                    return;
                }
                if (customerName.length()<4){
                    customerNameEditText.setError("don't smaller than 4 character");
                    customerNameEditText.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(customerPhone)|| customerPhone==null){
                    customerPhoneEditText.setError("Enter customer phone");
                    customerPhoneEditText.requestFocus();
                    return;
                }
                if (customerPhone.length()<8){
                    customerPhoneEditText.setError("don't smaller than 8 character");
                    customerPhoneEditText.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(customerAddress) ||customerAddress==null){
                    customerAddressEditText.setError("Enter customer name");
                    customerAddressEditText.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(customerEmail)|| customerEmail==null){
                    customerData=new CustomerData(customerName,customerPhone,customerAddress);

                }
                if (!TextUtils.isEmpty(customerEmail ) && customerEmail!=null){
                    if (!Patterns.EMAIL_ADDRESS.matcher(customerEmail).matches()){
                        customerEmailEditText.setError("Enter a valid  email address");
                        customerEmailEditText.requestFocus();
                        return;
                    }else {
                        customerData=new CustomerData(customerName,customerPhone,customerEmail,customerAddress);
                    }
                }

                apiInterface.addCustomerInformation("Bearer "+token,customerData).enqueue(
                        new Callback<AddCustomerResponse>() {
                            @Override
                            public void onResponse(Call<AddCustomerResponse> call, Response<AddCustomerResponse> response) {
                                Toast.makeText(CustomerActivity.this, "success", Toast.LENGTH_SHORT).show();
                                Log.e("ss", "ssss");
                            }
                            @Override
                            public void onFailure(Call<AddCustomerResponse> call, Throwable t) {
                                Log.e("ffuo", t.getMessage().toString());

                            }
                        }
                );
            }
        });
        cancelCustomerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();

    }

}
