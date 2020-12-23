package com.example.pointtosellpractice;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pointtosellpractice.customer.AddCustomerResponse;
import com.example.pointtosellpractice.customer.CustomerCustomAdapter;
import com.example.pointtosellpractice.customer.CustomerData;
import com.example.pointtosellpractice.customer.CustomerInformationData;
import com.example.pointtosellpractice.customer.CustomerInformationDataResponse;
import com.example.pointtosellpractice.model_class.product.GetProductData;
import com.example.pointtosellpractice.model_class.product.GetProductDataResponse;
import com.example.pointtosellpractice.product.ProductCustomAdapter;
import com.example.pointtosellpractice.retrofit.ApiInterface;
import com.example.pointtosellpractice.retrofit.RetrofitClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Product extends AppCompatActivity {
    List<GetProductData> getProductDataList;
    String token;
    ApiInterface apiInterface;
    ProductCustomAdapter productCustomAdapter;
    RecyclerView productRecyclerView;
    ProgressBar productProgressBar;
    FloatingActionButton addProductButton;
    EditText productNameEditText,productRegularPriceEditText,productSellingPriceEditText,
            productStockEditText,productDescriptionEditText;
    TextView pieceTextView;
    ImageView productSelectImageView;
    Button uploadProductButton;


    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 99 ;
    private static final int CAPTURE_REQUEST_CODE = 0;
    private static final int SELECT_REQUEST_CODE = 1;
    private Button captureImage,selectImage;
    private ImageView imageView;
   // private OurRetrofitClient ourRetrofitClient;
    private ProgressDialog progressDialog;
    Button imageUploadButton;

    File file;
    Uri imageUri;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        token= getIntent().getStringExtra("token");
        apiInterface = RetrofitClient.getRetrofit("http://mern-pos.herokuapp.com/").create(ApiInterface.class);
        productRecyclerView=findViewById(R.id.productRecyclerViewId);
        productProgressBar=findViewById(R.id.productProgressBarId);
        addProductButton=findViewById(R.id.addProductButtonId);

        //alert dialog view show


        getAllProduct();

        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createProduct();
            }
        });

    }

    public void getAllProduct() {
        apiInterface.getAllProduct("Bearer "+token).enqueue(new Callback<GetProductDataResponse>() {
            @Override
            public void onResponse(Call<GetProductDataResponse> call, Response<GetProductDataResponse> response) {
                GetProductDataResponse getProductDataResponse=response.body();
        if (response.isSuccessful()){

                if (getProductDataResponse.getSuccess()==true){
                    getProductDataList=new ArrayList<>();
                    getProductDataList.addAll(response.body().getProducts());
                    Toast.makeText(Product.this, String.valueOf(getProductDataList.size()), Toast.LENGTH_SHORT).show();
                    if (getProductDataList.size ()>0){
                        productCustomAdapter = new ProductCustomAdapter(Product.this,token,getProductDataList);
                        productRecyclerView.setLayoutManager(new LinearLayoutManager(Product.this));
                        productRecyclerView.setAdapter(productCustomAdapter);
                    }
            }
                productProgressBar.setVisibility(View.GONE);
            }
                else {
                    Toast.makeText(Product.this, "some problem", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<GetProductDataResponse> call, Throwable t) {

            }
        });
    }

    private void createProduct(){
        AlertDialog.Builder builder     =new AlertDialog.Builder(Product.this);
        LayoutInflater layoutInflater   =LayoutInflater.from(Product.this);
        View view                       =layoutInflater.inflate(R.layout.create_product,null);
        builder.setView(view);
        final AlertDialog alertDialog   = builder.create();

        productNameEditText=view.findViewById(R.id.productNameEditTextId);
        productRegularPriceEditText=view.findViewById(R.id.productRegularPriceEditTextId);
        productSellingPriceEditText=view.findViewById(R.id.productSellingPriceEditTextId);
        productStockEditText=view.findViewById(R.id.productStockEditTextId);
        productDescriptionEditText=view.findViewById(R.id.productDescriptionEditTextId);
        pieceTextView=view.findViewById(R.id.pieceTextViewId);
        productSelectImageView=view.findViewById(R.id.productSelectImageViewId);
        uploadProductButton=view.findViewById(R.id.uploadProductButtonId);



        alertDialog.show();

    }



}