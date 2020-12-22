package com.example.pointtosellpractice;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pointtosellpractice.create_invoice.CustomerCustomAdapter;
import com.example.pointtosellpractice.customer.CustomerInformationData;
import com.example.pointtosellpractice.customer.CustomerInformationDataResponse;
import com.example.pointtosellpractice.model_class.product.GetProductData;
import com.example.pointtosellpractice.model_class.product.GetProductDataResponse;
import com.example.pointtosellpractice.product.ProductCustomAdapter;
import com.example.pointtosellpractice.retrofit.ApiInterface;
import com.example.pointtosellpractice.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateInVoice_Activity extends AppCompatActivity implements
        CustomerCustomAdapter.OnContactClickListener1 {
    int changeStatus=0;

    TextView nameTextView,phoneTextView,addressTextView,oldDueTextView,customerIdTextView;
    String  name,phone,address,due,customerId;

    TextView subTotalTextView,discountTextView,grandTotalTextView,dueTextView;
    Button subCountButton,addCountButton,calculateButton;
    EditText payAmountEditText;


    ApiInterface apiInterface;
    String token;
//    List<SetProductData> setProductDataList;
//    List<GetProductData> newList=new ArrayList<>();
//
    List<CustomerInformationData> customerInformationDataList;
    List<GetProductData> getProductDataList;

//    SetInVoiceResponse setInVoiceResponse;
    Button inVoiceButton,product,selectCustomerButton;
    RecyclerView productRecyclerView;
    ListView listView;
//    ProductCustomAdapter.OnContactClickListener onContactClickListener;
//    ProductCustomAdapter2.OnContactClickListener3 onContactClickListener3;
    CustomerCustomAdapter.OnContactClickListener1 onContactClickListener1;

//    ProductCustomAdapter productCustomAdapter;
//    ProductCustomAdapter2 productCustomAdapter2;
   CustomerCustomAdapter customerCustomAdapter;
    AlertDialog alertDialog;

    RecyclerView selectRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_create_voice_);
//        onContactClickListener=this;
        onContactClickListener1=this;
//        onContactClickListener3=this;


        inVoiceButton=findViewById(R.id.inVoiceButtonId);
        selectCustomerButton=findViewById(R.id.selectCustomerButtonId);
        product=findViewById(R.id.productId);
        selectRecyclerView=findViewById(R.id.selectRecyclerView);
        customerIdTextView=findViewById(R.id.customerIdTextViewId);

        nameTextView=findViewById(R.id.nameTextViewId);
        phoneTextView=findViewById(R.id.phoneTextViewId);
        addressTextView=findViewById(R.id.addressTextViewId);
        oldDueTextView=findViewById(R.id.oldDueTextViewId);

        subTotalTextView=findViewById(R.id.subTotalTextViewId);
        discountTextView=findViewById(R.id.discountTextViewId);
        grandTotalTextView=findViewById(R.id.grandTotalTextViewId);
        dueTextView=findViewById(R.id.dueTextViewId);
        subCountButton=findViewById(R.id.subCountButtonId);
        addCountButton=findViewById(R.id.addCountButtonId);
        calculateButton=findViewById(R.id.calculateButtonId);
        payAmountEditText=findViewById(R.id.payAmountEditTextId);
        apiInterface = RetrofitClient.getRetrofit("http://mern-pos.herokuapp.com/").create(ApiInterface.class);

        //receive user token
        token= getIntent().getStringExtra("token");

        selectCustomerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAllCustomerInformation();
            }
        });


        product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeStatus=0;
                getAllProduct();
            }
        });


    }
// get All customer
    public  void getAllCustomerInformation(){
        apiInterface.getAllCustomerInformation("Bearer "+token).enqueue(new Callback<CustomerInformationDataResponse>() {
            @Override
            public void onResponse(Call<CustomerInformationDataResponse> call, Response<CustomerInformationDataResponse> response) {
                if (response.isSuccessful()){
                    if (response.body().getSuccess()==true){
                        customerInformationDataList=new ArrayList<>();
                        customerInformationDataList.addAll(response.body().getCustomerInformation());
                        if (customerInformationDataList.size ()>0){
                            addCustomerInformation(customerInformationDataList);
                            Toast.makeText(CreateInVoice_Activity.this, String.valueOf(customerInformationDataList.size()), Toast.LENGTH_SHORT).show();
                        }
                    }
                }else {
                    Toast.makeText(CreateInVoice_Activity.this, "Server Error", Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onFailure(Call<CustomerInformationDataResponse> call, Throwable t) {
                Toast.makeText(CreateInVoice_Activity.this, "fail Customer", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addCustomerInformation(List<CustomerInformationData> customerInformationDataList1){
        AlertDialog.Builder builder     =new AlertDialog.Builder(CreateInVoice_Activity.this);
        LayoutInflater layoutInflater   =LayoutInflater.from(CreateInVoice_Activity.this);
        View view                       =layoutInflater.inflate(R.layout.select_customer,null);
        builder.setView(view);
        alertDialog   = builder.create();
        productRecyclerView=view.findViewById(R.id.productRecyclerViewId);

        customerCustomAdapter = new CustomerCustomAdapter(CreateInVoice_Activity.this,token,customerInformationDataList1,onContactClickListener1);
        productRecyclerView.setLayoutManager(new LinearLayoutManager(CreateInVoice_Activity.this));
        productRecyclerView.setAdapter(customerCustomAdapter);
        alertDialog.show();
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
                        Toast.makeText(CreateInVoice_Activity.this, String.valueOf(getProductDataList.size()), Toast.LENGTH_SHORT).show();
                        if (getProductDataList.size ()>0){
//                             addProductInformation(getProductDataList);
                        }
                    }
                }
                else {
                    Toast.makeText(CreateInVoice_Activity.this, "some problem", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<GetProductDataResponse> call, Throwable t) {
            }
        });
    }

    



    @Override
    public void onContactClick1(int position) {
        nameTextView.setText(String.valueOf(customerInformationDataList.get(position).getName()));
        phoneTextView.setText(String.valueOf(customerInformationDataList.get(position).getPhone()));
        addressTextView.setText(String.valueOf(customerInformationDataList.get(position).getAddress()));
        oldDueTextView.setText(String.valueOf(customerInformationDataList.get(position).getDue()));
        customerIdTextView.setText(String.valueOf(customerInformationDataList.get(position).getId()));
        alertDialog.dismiss();
    }
}