package com.example.pointtosellpractice;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
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
import com.example.pointtosellpractice.product.create_product.ProductDataResponse;
import com.example.pointtosellpractice.retrofit.ApiInterface;
import com.example.pointtosellpractice.retrofit.RetrofitClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Product extends AppCompatActivity {
    String productName,productRegularPrice,productSellingPrice,productStock,productDescription,piece;
 Spinner spinner;
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
   // private OurRetrofitClient ourRetrofitClient;
    private ProgressDialog progressDialog;
    Button imageUploadButton;

    File file;
    Uri imageUri=null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Product List");

        setContentView(R.layout.activity_product);
        token= getIntent().getStringExtra("token");
        apiInterface = RetrofitClient.getRetrofit("http://mern-pos.herokuapp.com/").create(ApiInterface.class);
        productRecyclerView=findViewById(R.id.productRecyclerViewId);
        productProgressBar=findViewById(R.id.productProgressBarId);
        addProductButton=findViewById(R.id.addProductButtonId);
        spinner=findViewById(R.id.spinnerId);

        progressDialog = new ProgressDialog(Product.this);
        progressDialog.setMessage("Image Upload....");

        //alert dialog view show


        getAllProduct();

        String[] country = { "India", "USA", "China", "Japan", "Other"};





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

            }
                else {
                    Toast.makeText(Product.this, "", Toast.LENGTH_SHORT).show();
                }
                productProgressBar.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(Call<GetProductDataResponse> call, Throwable t) {
                productProgressBar.setVisibility(View.GONE);
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


        productSelectImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(CheckPermission()) {
                    Intent select = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(select, SELECT_REQUEST_CODE);
                }
            }
        });



        uploadProductButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                productName=productNameEditText.getText().toString();
                productRegularPrice=productRegularPriceEditText.getText().toString();
                productSellingPrice=productSellingPriceEditText.getText().toString();
                productStock=productStockEditText.getText().toString();
                productDescription=productDescriptionEditText.getText().toString();
                piece=pieceTextView.getText().toString();

                if (TextUtils.isEmpty(productName)){
                    productNameEditText.setError("Enter product name");
                    productNameEditText.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(productRegularPrice)){
                    productRegularPriceEditText.setError("Enter product regular price");
                    productRegularPriceEditText.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(productSellingPrice)){
                    productSellingPriceEditText.setError("Enter product selling price");
                    productSellingPriceEditText.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(productStock)){
                    productStockEditText.setError("Enter product stock");
                    productStockEditText.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(productDescription)){
                    productDescriptionEditText.setError("Enter product description");
                    productDescriptionEditText.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(piece)){
                    pieceTextView.setError("Enter piece");
                    pieceTextView.requestFocus();
                    return;
                }
                if (productSelectImageView.getDrawable()==null){
                    Toast.makeText(Product.this, "select image", Toast.LENGTH_SHORT).show();
                    return;
                }

                 ImageUpload(imageUri,productName,productRegularPrice,productSellingPrice,piece,productStock,productDescription);



            }
        });

        alertDialog.show();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){

            case CAPTURE_REQUEST_CODE:

//                if(resultCode == RESULT_OK){
//
//                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//                    imageView.setImageBitmap(bitmap);
//                    progressDialog.show();
//                    //ImageUpload(bitmap);
//
//                }


                break;

            case SELECT_REQUEST_CODE:
                if(resultCode == RESULT_OK && data!=null
                        && data.getData()!=null){
                    try {
                        imageUri = data.getData();
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                        productSelectImageView.setImageBitmap(bitmap);
                        // progressDialog.show();
                        //ImageUpload(imageUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Toast.makeText(this, "url empty", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

    private void ImageUpload(Uri imgUri,String productName,String productRegularPrice,String productSellingPrice,
                             String productPiece,String productStock,String productDescription) {

        progressDialog.show();

        String path= getImagePath(imgUri);
        file = new File(path);
        MultipartBody.Part imageFile = null;

        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
        imageFile = MultipartBody.Part.createFormData("image", file.getName(), requestFile);

        RequestBody name = RequestBody.create(MediaType.parse("multipart/form-data"), productName);
        RequestBody price = RequestBody.create(MediaType.parse("multipart/form-data"), productRegularPrice);
        RequestBody sellingPrice = RequestBody.create(MediaType.parse("multipart/form-data"), productSellingPrice);
        RequestBody unit = RequestBody.create(MediaType.parse("multipart/form-data"), productPiece);
        RequestBody stock = RequestBody.create(MediaType.parse("multipart/form-data"), productStock);
        RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), productDescription);

        apiInterface.uploadImage("Bearer "+token,imageFile,name,price,sellingPrice,unit,stock,description).
                enqueue(new Callback<ProductDataResponse>() {
                    @Override
                    public void onResponse(Call<ProductDataResponse> call, Response<ProductDataResponse> response) {
                       // Log.e("eroor",new Gson().toJson(response.body()));
                        if (response.isSuccessful()){

                            if (response.body().getSuccess()==true){
                                Toast.makeText(Product.this, "success", Toast.LENGTH_SHORT).show();
                                getAllProduct();
                           }else {
                                Toast.makeText(Product.this, "server error", Toast.LENGTH_SHORT).show();

                            }
                        }else {
                            Log.e("ent",String.valueOf(response.message()));
                            Toast.makeText(Product.this, String.valueOf(response.message()), Toast.LENGTH_SHORT).show();

                        }
                        progressDialog.dismiss();

                    }
                    @Override
                    public void onFailure(Call<ProductDataResponse> call, Throwable t) {
                        Toast.makeText(Product.this, "Failed to Upload", Toast.LENGTH_SHORT).show();
                        Log.e("asd",t.getMessage());
                       progressDialog.dismiss();
                    }
                });

    }

    public boolean CheckPermission() {
        if (ContextCompat.checkSelfPermission(Product.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(Product.this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(Product.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(Product.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale(Product.this,
                    Manifest.permission.CAMERA) || ActivityCompat.shouldShowRequestPermissionRationale(Product.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                new AlertDialog.Builder(Product.this)
                        .setTitle("Permission")
                        .setMessage("Please accept the permissions")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(Product.this,
                                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        MY_PERMISSIONS_REQUEST_LOCATION);


                                startActivity(new Intent(Product
                                        .this, Product.class));
                                Product.this.overridePendingTransition(0, 0);
                            }
                        })
                        .create()
                        .show();


            } else {
                ActivityCompat.requestPermissions(Product.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }

            return false;
        } else {

            return true;

        }
    }
    public String getImagePath(Uri uri){
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":")+1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }

    @Override
    public void onBackPressed() {
        Intent intent =new Intent(Product.this,HomePage.class);
        intent.putExtra("token",token);
        startActivity(intent);
        finish();
    }
}