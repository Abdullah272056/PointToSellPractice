package com.example.pointtosellpractice;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pointtosellpractice.create_invoice.CustomerCustomAdapter;
import com.example.pointtosellpractice.create_invoice.ProductCustomAdapter2;
import com.example.pointtosellpractice.create_invoice.ProductCustomAdapter3;
import com.example.pointtosellpractice.create_invoice.SetInVoiceResponse;
import com.example.pointtosellpractice.create_invoice.SetProductData;
import com.example.pointtosellpractice.customer.CustomerInformationData;
import com.example.pointtosellpractice.customer.CustomerInformationDataResponse;
import com.example.pointtosellpractice.model_class.owner_all_information.OwnerDataWithResponse;
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
        CustomerCustomAdapter.OnContactClickListener1,ProductCustomAdapter3.OnContactClickListener,ProductCustomAdapter2.OnContactClickListener3 {
    int changeStatus=0;

    TextView nameTextView,phoneTextView,addressTextView,oldDueTextView,customerIdTextView;
    String  name,phone,address,due,customerId;

    TextView subTotalTextView,discountTextView,grandTotalTextView,dueTextView;
    Button subCountButton,addCountButton,calculateButton;
    EditText payAmountEditText;


    ApiInterface apiInterface;
    String token;
    List<SetProductData> setProductDataList;
    List<GetProductData> newList=new ArrayList<>();

    List<CustomerInformationData> customerInformationDataList;
    List<GetProductData> getProductDataList;
    List<GetProductData> filterProductDataList;

   SetInVoiceResponse setInVoiceResponse;
    Button inVoiceButton,product,selectCustomerButton;
    RecyclerView productRecyclerView;
    ListView listView;
    ProductCustomAdapter3.OnContactClickListener onContactClickListener;
   ProductCustomAdapter2.OnContactClickListener3 onContactClickListener3;
    CustomerCustomAdapter.OnContactClickListener1 onContactClickListener1;

   ProductCustomAdapter3 productCustomAdapter;
      ProductCustomAdapter2 productCustomAdapter2;
   CustomerCustomAdapter customerCustomAdapter;
    AlertDialog alertDialog;

    RecyclerView selectRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_create_voice_);
           onContactClickListener=this;
           onContactClickListener1=this;
           onContactClickListener3=this;


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

        inVoiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (changeStatus==1){
                    Toast.makeText(CreateInVoice_Activity.this, "Ready", Toast.LENGTH_SHORT).show();
                    setInVoice();
                }else {
                    Toast.makeText(CreateInVoice_Activity.this, "before click calculate button", Toast.LENGTH_SHORT).show();
                }
            }
        });


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

        subCountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int discount=Integer.parseInt(discountTextView.getText().toString());
                if (discount>0){
                    discount--;
                }
                discountTextView.setText(String.valueOf(discount));
                changeStatus=0;
            }
        });


        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int  payDue=0,due;
                int discountAmount=0;
                int subTotal=0;
                int sz=newList.size();
                if (sz>0){

                    for (int i=0;sz-1>=i;i++){

                        subTotal=subTotal+(newList.get(i).getSellingPrice()*newList.get(i).getQuantity());

                        // subTotal=subTotal+newList.get(i).getSellingPrice();
                        // setProductDataList.add(new SetProductData(newList.get(i).getId(),1));
                    }
                }
                int discount=Integer.parseInt(discountTextView.getText().toString());

                if (discount>0){
                    float dis=(subTotal*discount)/100;
                    discountAmount= (int) dis;
                }

                if (!TextUtils.isEmpty(payAmountEditText.getText().toString())){
                    payDue=Integer.parseInt(payAmountEditText.getText().toString());
                }

                subTotalTextView.setText(String.valueOf(subTotal));
                grandTotalTextView.setText(String.valueOf(subTotal-discountAmount));
                due=(subTotal-discountAmount)-payDue;
                dueTextView.setText(String.valueOf(due));

                changeStatus=1;

            }
        });







        addCountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int discount=Integer.parseInt(discountTextView.getText().toString());
                if (discount<100){
                    discount++;
                }
                discountTextView.setText(String.valueOf(discount));
                changeStatus=0;
            }
        });



    }


    public void  setInVoice(){
        customerId= customerIdTextView.getText().toString().trim();
        if (TextUtils.isEmpty(customerId)){
            nameTextView.setError("Enter your email");
            nameTextView.requestFocus();
            return;
        }
//        name= nameTextView.getText().toString().trim();
//        phone= phoneTextView.getText().toString().trim();


        int  totalProductAmount= Integer.parseInt(grandTotalTextView.getText().toString());
        int  discount= Integer.parseInt(discountTextView.getText().toString());
        int payAmount=0;

        if (!TextUtils.isEmpty(payAmountEditText.getText().toString())){
            payAmount= Integer.parseInt(payAmountEditText.getText().toString());
        }

        setProductDataList=new ArrayList<>();

        int sz=newList.size();
        for (int i=0;sz-1>=i;i++){
            setProductDataList.add(new SetProductData(newList.get(i).getId(),newList.get(i).getQuantity()));
        }
        setInVoiceResponse=new SetInVoiceResponse(customerId,payAmount,
                totalProductAmount,discount, setProductDataList);
        apiInterface.getInvoiceResponse("Bearer "+token,setInVoiceResponse).enqueue(new Callback<OwnerDataWithResponse>() {
            @Override
            public void onResponse(Call<OwnerDataWithResponse> call, Response<OwnerDataWithResponse> response) {
                Toast.makeText(CreateInVoice_Activity.this, "sssssssss", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<OwnerDataWithResponse> call, Throwable t) {
                Toast.makeText(CreateInVoice_Activity.this, String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();
                Log.e("getInvoice", String.valueOf(t.getMessage()));

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
                        filterProductDataList=new ArrayList<>();
                        getProductDataList.addAll(response.body().getProducts());
                        if (getProductDataList.size ()>0){

                            int sz=getProductDataList.size();
                            for (int i=0;sz-1>=i;i++){
                                int stck=getProductDataList.get(i).getStock();
                                if (stck>0){
                                    filterProductDataList.add(getProductDataList.get(i));
                                    Toast.makeText(CreateInVoice_Activity.this, String.valueOf(filterProductDataList.size()), Toast.LENGTH_SHORT).show();

                                }
                            }

                             addProductInformation(filterProductDataList);


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

    private void addProductInformation( List<GetProductData> getProductDataList){
        AlertDialog.Builder builder     =new AlertDialog.Builder(CreateInVoice_Activity.this);
        LayoutInflater layoutInflater   =LayoutInflater.from(CreateInVoice_Activity.this);
        View view                       =layoutInflater.inflate(R.layout.product,null);
        builder.setView(view);
        alertDialog   = builder.create();

        productRecyclerView=view.findViewById(R.id.productRecyclerViewId);
        productCustomAdapter = new ProductCustomAdapter3(CreateInVoice_Activity.this,token,getProductDataList,onContactClickListener);
        productRecyclerView.setLayoutManager(new LinearLayoutManager(CreateInVoice_Activity.this));
        productRecyclerView.setAdapter(productCustomAdapter);

        alertDialog.show();
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

    @Override
    public void onContactClick(int position) {
        //getAllProduct();
        Toast.makeText(this, String.valueOf(filterProductDataList.get(position).getName()), Toast.LENGTH_SHORT).show();
        //newList.add(getProductDataList.get(position));

        newList.add(new GetProductData(filterProductDataList.get(position).getPrice(),
                filterProductDataList.get(position).getSellingPrice(),
                filterProductDataList.get(position).getStock(),
                1,filterProductDataList.get(position).getId(),
                filterProductDataList.get(position).getName(),
                filterProductDataList.get(position).getUnit()));


        productCustomAdapter2 = new ProductCustomAdapter2(CreateInVoice_Activity.this,token,newList, onContactClickListener3);
        selectRecyclerView.setLayoutManager(new LinearLayoutManager(CreateInVoice_Activity.this));
        selectRecyclerView.setAdapter(productCustomAdapter2);
        Log.e("size",String.valueOf(newList.size()));
        alertDialog.dismiss();
    }

    @Override
    public void onContactClick3(int position) {

    }
}