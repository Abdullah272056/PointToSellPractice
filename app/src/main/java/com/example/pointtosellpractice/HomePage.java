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
import android.widget.Toast;

import com.example.pointtosellpractice.customer.CustomerData;
import com.example.pointtosellpractice.customer.CustomerInformationData;
import com.example.pointtosellpractice.customer.CustomerInformationDataResponse;
import com.example.pointtosellpractice.customer.AddCustomerResponse;
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
    Spinner spinner;
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


               Intent intent=new Intent(HomePage.this,CustomerActivity.class);
               intent.putExtra("token1",token);
               startActivity(intent);
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


                        CustomerInformationDataResponse customerInformationDataResponse=response.body();
                        customerInformationDataList.addAll(response.body().getCustomerInformation());
                        if (customerInformationDataResponse.getSuccess()==true){
                            customerInformationDataList=new ArrayList<>();
                            customerInformationDataList.addAll(response.body().getCustomerInformation());
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


}