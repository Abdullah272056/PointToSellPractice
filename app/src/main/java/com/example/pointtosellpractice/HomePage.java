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

import com.example.pointtosellpractice.auth.AboutMeActivity;
import com.example.pointtosellpractice.auth.LoginActivity;
import com.example.pointtosellpractice.customer.CustomerActivity;
import com.example.pointtosellpractice.customer.get_customer.CustomerCountResponse;
import com.example.pointtosellpractice.invoice.InVoiceActivity;
import com.example.pointtosellpractice.invoice.get_all_sell_info.GetAllSellInfoResponse;
import com.example.pointtosellpractice.product.get_all_product_info.GetAllProductInfoDataResponse;
import com.example.pointtosellpractice.product.ProductActivity;
import com.example.pointtosellpractice.retrofit.ApiInterface;
import com.example.pointtosellpractice.retrofit.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePage extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    LinearLayout linearLayout;


    ApiInterface apiInterface;
    Intent intent;
    String token;

    TextView customerCountTextView;
    String customerCount;

    TextView totalSaleAmountTextView,totalSoldProductQuantityTextView,totalSoldInvoiceTextView,
            totalDueAmountTextView,totalProfitTextView;
    TextView totalProductCostTextView,totalProductStockTextView,totalProductTypeTextView;
    SharePref sharePref;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        bottomNavigationView=findViewById(R.id.bottomBarId);

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

        toolbar=findViewById (R.id.toolbarId);
        drawerLayout=findViewById (R.id.drawerLayoutId);
        navigationView=findViewById (R.id.myNavigationViewId);
        //receive user token
        token= getIntent().getStringExtra("token");

        if (toolbar!=null){
            setSupportActionBar (toolbar);
        }

        apiInterface = RetrofitClient.getRetrofit("http://mern-pos.herokuapp.com/").create(ApiInterface.class);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId()==R.id.homeItemId){

                    Toast.makeText(HomePage.this, "", Toast.LENGTH_SHORT).show();
                }
                if (item.getItemId()==R.id.customerItemId){
                    Intent intent=new Intent(HomePage.this, CustomerActivity.class);
                    intent.putExtra("token",token);
                    startActivity(intent);
                    finish();
                }
                if (item.getItemId()==R.id.productItemId){
                    Intent intent=new Intent(HomePage.this, ProductActivity.class);
                    intent.putExtra("token",token);
                    startActivity(intent);
                    finish();
                } if (item.getItemId()==R.id.invoiceItemId){
                    Intent intent=new Intent(HomePage.this, InVoiceActivity.class);
                    intent.putExtra("token",token);
                    startActivity(intent);
                    finish();
                }
                if (item.getItemId()==R.id.othersItemId){
                    Intent intent=new Intent(HomePage.this, OthersInformation.class);
                    intent.putExtra("token",token);
                    startActivity(intent);
                }

                return true;
            }
        });


        // call getCustomerCount for getting total customer count
      getCustomerCount();
        // call GetAllSellInfo for getting total customer count
        getAllSellInfo();

        // Get all select_customer info
        getAllProductInfo();

       // call navigationDrawer for getting navigation drawer
         navigationDrawer();



    navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId ()){
            case R.id.reportItemIdId:
                intent=new Intent(HomePage.this,UpComingFeature.class);
                startActivity(intent);
                break;
            case R.id.eCommerceItemIdId:
                intent=new Intent(HomePage.this,UpComingFeature.class);
                startActivity(intent);
                break;
            case R.id.currentMonthItemIdId:
                intent=new Intent(HomePage.this,UpComingFeature.class);
                startActivity(intent);
                break;
            case R.id.lastQuarterItemIdId:
                intent=new Intent(HomePage.this,UpComingFeature.class);
                startActivity(intent);
                break;
            case R.id.yearEndSaleItemId:
                intent=new Intent(HomePage.this,UpComingFeature.class);
                startActivity(intent);
                break;
                case R.id.calculatorItemIdId:
                    intent=new Intent(HomePage.this,UpComingFeature.class);
                    startActivity(intent);
                    drawerLayout.closeDrawer(Gravity.LEFT);
                break;
            case R.id.invoiceItemId:
                intent=new Intent(HomePage.this, InVoiceActivity.class);
                intent.putExtra("token",token);
                startActivity(intent);
                finish();
                break;
            case R.id.productItemIdId:
                intent=new Intent(HomePage.this, ProductActivity.class);
                intent.putExtra("token",token);
                startActivity(intent);
                finish();
                break;
                case R.id.logOutId:
                    sharePref.rememberData(HomePage.this,"","");
                    intent=new Intent(HomePage.this, LoginActivity.class);
                    intent.putExtra("token",token);
                    startActivity(intent);
                    finish();
                break;
            case R.id.customerItemId:
                intent=new Intent(HomePage.this,CustomerActivity.class);
                intent.putExtra("token",token);
                 startActivity(intent);
                finish();
                break;
            case R.id.dashBoardAllDataItemIdId:
                intent=new Intent(HomePage.this, AboutMeActivity.class);
                intent.putExtra("token",token);
                startActivity(intent);
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
               if (response.isSuccessful()){
                   Log.e("count","success");
                   CustomerCountResponse customerCountResponse=response.body();
                   customerCount=customerCountResponse.getCustomerCount().toString();
                   customerCountTextView.setText(customerCount);
               }else {
                   Toast.makeText(HomePage.this, "fail", Toast.LENGTH_SHORT).show();
               }

            }

            @Override
            public void onFailure(Call<CustomerCountResponse> call, Throwable t) {
                Toast.makeText(HomePage.this, "fail", Toast.LENGTH_SHORT).show();

            }
        });
    }

    // getting  Get all sale info
    private void getAllSellInfo() {
        apiInterface.getAllSellInfo("Bearer "+token).enqueue(new Callback<GetAllSellInfoResponse>() {
            @Override
            public void onResponse(Call<GetAllSellInfoResponse> call, Response<GetAllSellInfoResponse> response) {
                if (response.isSuccessful()){
                GetAllSellInfoResponse getAllSellInfoResponse=response.body();
                assert getAllSellInfoResponse != null;
                Log.e("totalSaleAmount",getAllSellInfoResponse.getGetAllSellInfoData().getTotalSaleAmount().toString());
                totalSaleAmountTextView.setText(getAllSellInfoResponse.getGetAllSellInfoData().getTotalSaleAmount().toString());
                totalSoldProductQuantityTextView.setText(getAllSellInfoResponse.getGetAllSellInfoData().getTotalSoldProductQuantity().toString());
                totalSoldInvoiceTextView.setText(getAllSellInfoResponse.getGetAllSellInfoData().getTotalSoldInvoice().toString());
                totalDueAmountTextView.setText(getAllSellInfoResponse.getGetAllSellInfoData().getTotalDueAmount().toString());
                totalProfitTextView.setText(getAllSellInfoResponse.getGetAllSellInfoData().getTotalProfit().toString());
                }
                else {
                    Toast.makeText(HomePage.this, "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetAllSellInfoResponse> call, Throwable t) {
                Toast.makeText(HomePage.this, "failed try again", Toast.LENGTH_SHORT).show();

            }
        });

    }

    // Get all select_customer info
    private void getAllProductInfo(){
        apiInterface.getAllProductInfo("Bearer "+token).enqueue(new Callback<GetAllProductInfoDataResponse>() {
            @Override
            public void onResponse(Call<GetAllProductInfoDataResponse> call, Response<GetAllProductInfoDataResponse> response) {

                if (response.code()==200){
                        totalProductCostTextView.setText(response.body().getGetAllProductInfoData().getTotalProductCost().toString());
                        totalProductStockTextView.setText(response.body().getGetAllProductInfoData().getTotalProduct().toString());
                        totalProductTypeTextView.setText(response.body().getGetAllProductInfoData().getTotalProductType().toString());

                }
                else if (response.code()==401){
                    //Toast.makeText(HomePage.this, "Invalid token", Toast.LENGTH_SHORT).show();
                }else if (response.code()==404){
                    //Toast.makeText(HomePage.this, "No information found", Toast.LENGTH_SHORT).show();
                }
                else {
                    //Toast.makeText(HomePage.this, "Invalid token", Toast.LENGTH_SHORT).show();
                }

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