package com.example.pointtosellpractice;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pointtosellpractice.customer.CustomerData;
import com.example.pointtosellpractice.customer.CustomerInformationData;
import com.example.pointtosellpractice.customer.CustomerInformationDataResponse;
import com.example.pointtosellpractice.model_class.LogInResponse;
import com.example.pointtosellpractice.retrofit.ApiInterface;
import com.example.pointtosellpractice.retrofit.RetrofitClient;
import com.example.pointtosellpractice.user_all_information.UserDataWithResponse;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePage extends AppCompatActivity {
    Toolbar toolbar;
    LinearLayout linearLayout;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ApiInterface apiInterface;
    List<CustomerInformationData> customerInformationDataList;

    String token;
    Button userInFormationButton, addCustomer,allCustomerButton;


    EditText customerNameEditText,customerEmailEditText,customerPhoneEditText,customerAddressEditText;
    Button addCustomerDataButton,cancelCustomerButton;
    CustomerData customerData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        linearLayout=findViewById(R.id.mainLayoutId);

        toolbar=findViewById (R.id.toolbarId);
        if (toolbar!=null){
            setSupportActionBar (toolbar);
        }



        userInFormationButton=findViewById(R.id.userAllDataButtonId);
        addCustomer=findViewById(R.id.addCustomerId);
        allCustomerButton=findViewById(R.id.allCustomerButtonId);


        //receive user token
        token= getIntent().getStringExtra("token");
        apiInterface = RetrofitClient.getRetrofit("http://mern-pos.herokuapp.com/").create(ApiInterface.class);

        addCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCustomerInformation();
            }
        });

        userInFormationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiInterface.getUserAllInformation("Bearer "+token).enqueue(new Callback<UserDataWithResponse>() {
                    @Override
                    public void onResponse(Call<UserDataWithResponse> call, Response<UserDataWithResponse> response) {

                        UserDataWithResponse userDataWithResponse=response.body();
                        Toast.makeText(HomePage.this, "success", Toast.LENGTH_SHORT).show();
                        Log.e("gt", userDataWithResponse.getData().getEmail().toString());

                    }

                    @Override
                    public void onFailure(Call<UserDataWithResponse> call, Throwable t) {
                        Toast.makeText(HomePage.this, "fail", Toast.LENGTH_SHORT).show();
                        Log.e("gt", "ff");
                    }
                });
            }
        });
// all cu
        allCustomerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiInterface.getAllCustomerInformation("Bearer "+token).enqueue(new Callback<CustomerInformationDataResponse>() {
                    @Override
                    public void onResponse(Call<CustomerInformationDataResponse> call, Response<CustomerInformationDataResponse> response) {
                        Toast.makeText(HomePage.this, "Success", Toast.LENGTH_SHORT).show();

                        customerInformationDataList=new ArrayList<>();
                        CustomerInformationDataResponse customerInformationDataResponse=response.body();
                        customerInformationDataList.addAll(response.body().getCustomerInformation());
                        if (customerInformationDataResponse.getSuccess()==true){
                            if (TextUtils.isEmpty(customerInformationDataList.get(0).getEmail()) ||
                                    customerInformationDataList.get(0).getEmail()==null)  {
                                Log.e("namec","No Due");
                                Toast.makeText(HomePage.this, "Ssss", Toast.LENGTH_SHORT).show();

                            }else {
                                Log.e("namec",customerInformationDataList.get(2).getEmail().toString());
                                Toast.makeText(HomePage.this, "Ssss", Toast.LENGTH_SHORT).show();
                            }




                          //  customerInformationDataList.get(0).getEmail().toString();
                        }
                    }
                    @Override
                    public void onFailure(Call<CustomerInformationDataResponse> call, Throwable t) {
                        Toast.makeText(HomePage.this, "fail:  "+t.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        Log.e("namec","error: "+t.getMessage().toString());

                    }
                });
            }
        });



        drawerLayout=findViewById (R.id.drawerLayoutId);
        navigationView=findViewById (R.id.myNavigationViewId);
        ActionBarDrawerToggle actionBarDrawerToggle=new ActionBarDrawerToggle(
                HomePage.this,drawerLayout,toolbar,R.string.open,R.string.closed){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                Toast.makeText (HomePage.this, "Open", Toast.LENGTH_SHORT).show ();
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                Toast.makeText (HomePage.this, "Closed", Toast.LENGTH_SHORT).show ();

            }
        };

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

    }

    private void addCustomerInformation() {

        AlertDialog.Builder builder     =new AlertDialog.Builder(HomePage.this);
        LayoutInflater layoutInflater   =LayoutInflater.from(HomePage.this);
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

                if (TextUtils.isEmpty(customerName)){
                    customerNameEditText.setError("Enter customer name");
                    customerNameEditText.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(customerPhone)){
                    customerPhoneEditText.setError("Enter customer phone");
                    customerPhoneEditText.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(customerAddress)){
                    customerAddressEditText.setError("Enter customer name");
                    customerAddressEditText.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(customerEmail)){
                   customerData=new CustomerData(customerName,customerPhone,customerAddress);

                }if (!TextUtils.isEmpty(customerEmail)){
                    customerData=new CustomerData(customerName,customerPhone,customerEmail,customerAddress);
                }

                apiInterface.addCustomerInformation("Bearer "+token,customerData)
                        .enqueue(new Callback<CustomerInformationDataResponse>() {
                            @Override
                            public void onResponse(Call<CustomerInformationDataResponse> call, Response<CustomerInformationDataResponse> response) {
                                Toast.makeText(HomePage.this, "success", Toast.LENGTH_SHORT).show();
                                Log.e("ss", "ssss");

                                customerNameEditText.setText("");
                               customerPhoneEditText.setText("");
                                customerEmailEditText.setText("");
                                customerAddressEditText.setText("");

                            }

                            @Override
                            public void onFailure(Call<CustomerInformationDataResponse> call, Throwable t) {
                                Log.e("ff", "ffff");

                            }
                        });
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