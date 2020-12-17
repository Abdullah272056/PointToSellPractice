package com.example.pointtosellpractice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pointtosellpractice.customer.CustomerCountResponse;
import com.example.pointtosellpractice.model_class.invoice.GetAllSellInfoResponse;
import com.example.pointtosellpractice.model_class.product.GetAllProductInfoDataResponse;
import com.example.pointtosellpractice.retrofit.ApiInterface;
import com.example.pointtosellpractice.retrofit.RetrofitClient;
import com.google.android.material.navigation.NavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePage extends AppCompatActivity {
    Toolbar toolbar;
    LinearLayout linearLayout;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ApiInterface apiInterface;

    String token;

    TextView customerCountTextView;
    String customerCount;
    TextView customerTextView,productTextView,invoiceTextView;
    TextView totalSaleAmountTextView,totalSoldProductQuantityTextView,totalSoldInvoiceTextView,
            totalDueAmountTextView,totalProfitTextView;
    TextView totalProductCostTextView,totalProductStockTextView,totalProductTypeTextView,extraInfoTextView;
    SharePref sharePref;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        linearLayout=findViewById(R.id.mainLayoutId);
        sharePref=new SharePref();
        //finding textView
        customerCountTextView=findViewById(R.id.customerCountId);
        totalSaleAmountTextView=findViewById(R.id.totalSaleAmountTextViewId);
        totalSoldProductQuantityTextView=findViewById(R.id.totalSoldProductQuantityTextViewId);
        totalSoldInvoiceTextView=findViewById(R.id.totalSoldInvoiceTextViewId);
        totalDueAmountTextView=findViewById(R.id.totalDueAmountTextViewId);
        totalProfitTextView=findViewById(R.id.totalProfitTextViewId);
        totalProductCostTextView=findViewById(R.id.totalProductCostTextViewId);
        totalProductStockTextView=findViewById(R.id.totalProductStockTextViewId);
        totalProductTypeTextView=findViewById(R.id.totalProductTypeTextViewId);

        customerTextView=findViewById(R.id.customerTextViewId);
        productTextView=findViewById(R.id.productTextViewId);
        invoiceTextView=findViewById(R.id.invoiceTextViewId);
        extraInfoTextView=findViewById(R.id.extraInfoTextViewId);

        toolbar=findViewById (R.id.toolbarId);
        drawerLayout=findViewById (R.id.drawerLayoutId);
        navigationView=findViewById (R.id.myNavigationViewId);
        //receive user token
        token= getIntent().getStringExtra("token");

        if (toolbar!=null){
            setSupportActionBar (toolbar);
        }

        apiInterface = RetrofitClient.getRetrofit("http://mern-pos.herokuapp.com/").create(ApiInterface.class);

        customerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent=new Intent(HomePage.this,CustomerActivity.class);
               intent.putExtra("token",token);
               startActivity(intent);
            }
        });
        productTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomePage.this,Product.class);
                intent.putExtra("token",token);
                startActivity(intent);
            }
        });
        invoiceTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomePage.this, InVoiceActivity.class);
                intent.putExtra("token",token);
                startActivity(intent);
            }
        });

        extraInfoTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomePage.this, OthersInformation.class);
                intent.putExtra("token",token);
                startActivity(intent);
            }
        });


        // call getCustomerCount for getting total customer count
      getCustomerCount();
        // call GetAllSellInfo for getting total customer count
        getAllSellInfo();

        // Get all product info
        getAllProductInfo();

       // call navigationDrawer for getting navigation drawer
      navigationDrawer();



    navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId ()){
            case R.id.reportItemIdId:
                Intent intent=new Intent(HomePage.this,UpComingFeature.class);
                startActivity(intent);
                break;
            case R.id.eCommerceItemIdId:
                Intent intent1=new Intent(HomePage.this,UpComingFeature.class);
                startActivity(intent1);
                break;
            case R.id.currentMonthItemIdId:
                Intent intent2=new Intent(HomePage.this,UpComingFeature.class);
                startActivity(intent2);
                break;
            case R.id.lastQuarterItemIdId:
                Intent intent3=new Intent(HomePage.this,UpComingFeature.class);
                startActivity(intent3);
                break;
            case R.id.yearEndSaleItemId:
                Intent intent4=new Intent(HomePage.this,UpComingFeature.class);
                startActivity(intent4);
                break;
                case R.id.calculatorItemIdId:
                Intent intent5=new Intent(HomePage.this,UpComingFeature.class);
                startActivity(intent5);
                break;
            case R.id.invoiceItemId:
                Intent intent6=new Intent(HomePage.this, InVoiceActivity.class);
                intent6.putExtra("token",token);
                startActivity(intent6);
                break;
            case R.id.productItemIdId:
                Intent intent7=new Intent(HomePage.this,Product.class);
                intent7.putExtra("token",token);
                startActivity(intent7);
                break;
                case R.id.logOutId:
                    sharePref.rememberData(HomePage.this,"","");
                    Intent intent8=new Intent(HomePage.this,LoginActivity.class);
                intent8.putExtra("token",token);
                startActivity(intent8);
                    finish();
                break;

        }
        return false;
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
            public void onDrawerClosed(View drawerView){
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
                totalSaleAmountTextView.setText(getAllSellInfoResponse.getGetAllSellInfoData().getTotalSaleAmount().toString());
                totalSoldProductQuantityTextView.setText("Total product sale "+getAllSellInfoResponse.getGetAllSellInfoData().getTotalSoldProductQuantity().toString());
                totalSoldInvoiceTextView.setText("Total invoice "+getAllSellInfoResponse.getGetAllSellInfoData().getTotalSoldInvoice().toString());
                totalDueAmountTextView.setText(getAllSellInfoResponse.getGetAllSellInfoData().getTotalDueAmount().toString());
                totalProfitTextView.setText(getAllSellInfoResponse.getGetAllSellInfoData().getTotalProfit().toString());
            }

            @Override
            public void onFailure(Call<GetAllSellInfoResponse> call, Throwable t) {
                Log.e("ts","success");

            }
        });

    }


    // Get all product info
    private void getAllProductInfo(){
        apiInterface.getAllProductInfo("Bearer "+token).enqueue(new Callback<GetAllProductInfoDataResponse>() {
            @Override
            public void onResponse(Call<GetAllProductInfoDataResponse> call, Response<GetAllProductInfoDataResponse> response) {

                GetAllProductInfoDataResponse getAllProductInfoDataResponse=response.body();
                totalProductCostTextView.setText(getAllProductInfoDataResponse.getGetAllProductInfoData().getTotalProductCost().toString());
                totalProductStockTextView.setText("Total product stock "+getAllProductInfoDataResponse.getGetAllProductInfoData().getTotalProduct().toString());
                totalProductTypeTextView.setText("Product type "+getAllProductInfoDataResponse.getGetAllProductInfoData().getTotalProductType().toString());
            }
            @Override
            public void onFailure(Call<GetAllProductInfoDataResponse> call, Throwable t) {

            }
        });
    }




    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)){
            drawerLayout.closeDrawers();
        }else {
            HomePage.super.onBackPressed();
        }
    }

}