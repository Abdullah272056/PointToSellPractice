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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pointtosellpractice.customer.CustomerCountResponse;
import com.example.pointtosellpractice.customer.CustomerData;
import com.example.pointtosellpractice.customer.CustomerInformationData;
import com.example.pointtosellpractice.customer.CustomerInformationDataResponse;
import com.example.pointtosellpractice.customer.AddCustomerResponse;
import com.example.pointtosellpractice.model_class.LogInData;
import com.example.pointtosellpractice.model_class.invoice.GetAllSellInfoResponse;
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
    ApiInterface apiInterface,apiInterface1;

    String token;
    Button userInFormationButton, addCustomer;

    TextView customerCountTextView;
    String customerCount;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        linearLayout=findViewById(R.id.mainLayoutId);
        customerCountTextView=findViewById(R.id.customerCountId);
        userInFormationButton=findViewById(R.id.userAllDataButtonId);
        addCustomer=findViewById(R.id.addCustomerId);
        toolbar=findViewById (R.id.toolbarId);
        drawerLayout=findViewById (R.id.drawerLayoutId);
        navigationView=findViewById (R.id.myNavigationViewId);
        //receive user token
        token= getIntent().getStringExtra("token");

        if (toolbar!=null){
            setSupportActionBar (toolbar);
        }


        apiInterface = RetrofitClient.getRetrofit("http://mern-pos.herokuapp.com/").create(ApiInterface.class);
      //  apiInterface1 = RetrofitClient.getRetrofit("http://mern-pos.herokuapp.com/").create(ApiInterface.class);

        addCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent=new Intent(HomePage.this,CustomerActivity.class);
               intent.putExtra("token1",token);
               startActivity(intent);
            }
        });


        // call getCustomerCount for getting total customer count
      getCustomerCount();
        // call GetAllSellInfo for getting total customer count
        getAllSellInfo();

       // call navigationDrawer for getting navigation drawer
      navigationDrawer();



        userInFormationButton.setOnClickListener(new View.OnClickListener(){
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




    }






    // create for drawerLayout
    private void navigationDrawer() {

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


    // getting total customer count
    private void getCustomerCount() {
        apiInterface.getCustomerCount("Bearer "+token).enqueue(new Callback<CustomerCountResponse>() {
            @Override
            public void onResponse(Call<CustomerCountResponse> call, Response<CustomerCountResponse> response) {
                Log.e("count","success");

                CustomerCountResponse customerCountResponse=response.body();
                customerCount=customerCountResponse.getCustomerCount().toString();
                customerCountTextView.setText(customerCount);
            }

            @Override
            public void onFailure(Call<CustomerCountResponse> call, Throwable t) {
                Log.e("count","success");
            }
        });
    }


    // getting  Get all sale info

    private void getAllSellInfo() {
        apiInterface.getAllSellInfo("Bearer "+token).enqueue(new Callback<GetAllSellInfoResponse>() {
            @Override
            public void onResponse(Call<GetAllSellInfoResponse> call, Response<GetAllSellInfoResponse> response) {
                GetAllSellInfoResponse getAllSellInfoResponse=response.body();
                assert getAllSellInfoResponse != null;
                Log.e("totalSaleAmount",getAllSellInfoResponse.getGetAllSellInfoData().getTotalSaleAmount().toString());

            }

            @Override
            public void onFailure(Call<GetAllSellInfoResponse> call, Throwable t) {
                Log.e("ts","success");

            }
        });

    }




}