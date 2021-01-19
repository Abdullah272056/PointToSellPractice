package com.example.pointtosellpractice.invoice;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pointtosellpractice.HomePage;
import com.example.pointtosellpractice.R;
import com.example.pointtosellpractice.customer.CustomerActivity;
import com.example.pointtosellpractice.invoice.create_invoice.CustomerCustomAdapter;
import com.example.pointtosellpractice.invoice.create_invoice.ProductCustomAdapter2;
import com.example.pointtosellpractice.invoice.create_invoice.ProductCustomAdapter3;
import com.example.pointtosellpractice.invoice.create_invoice.SetInVoiceResponse;
import com.example.pointtosellpractice.invoice.create_invoice.SetProductData;
import com.example.pointtosellpractice.customer.get_customer.CustomerInformationData;
import com.example.pointtosellpractice.customer.get_customer.CustomerInformationDataResponse;
import com.example.pointtosellpractice.auth.owner_all_information.OwnerDataWithResponse;
import com.example.pointtosellpractice.product.ProductActivity;
import com.example.pointtosellpractice.product.delete_product.GetProductData;
import com.example.pointtosellpractice.product.get_product.GetProductDataResponse;
import com.example.pointtosellpractice.product.get_product.ProductCustomAdapter;
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

        // for add back Button in title bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
//////////////

        // create invoice button click
        inVoiceButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                   if (changeStatus==1){
                       Toast.makeText(CreateInVoice_Activity.this, "Ready", Toast.LENGTH_SHORT).show();
                       setInVoice();
                   }else {
                       Toast.makeText(CreateInVoice_Activity.this, "click calculate button", Toast.LENGTH_SHORT).show();
                   }
            }
        });

        // selected customer button click
        selectCustomerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAllCustomerInformation();
            }
        });

        // text change listener add
        payAmountEditText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                changeStatus=0;
            }
        });


        //selected product button click
        product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeStatus=0;
                getAllProduct();
            }
        });


        // percent decrement button
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

        // percent increment button
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

    }


    public void  setInVoice(){
        customerId= customerIdTextView.getText().toString().trim();
        if (TextUtils.isEmpty(customerId)){
            nameTextView.setError("Enter your email");
            nameTextView.requestFocus();
            return;
        }if (newList.size()<=0){
            Toast.makeText(this, "please select add product", Toast.LENGTH_SHORT).show();
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




       // int discount=Integer.parseInt(discountTextView.getText().toString());


        int TotalAmount=0;
        int sze=newList.size();
        int discountAmount=0;
        if (sze>0){

            for (int i=0;sze-1>=i;i++){
                TotalAmount=TotalAmount+(newList.get(i).getSellingPrice()*newList.get(i).getQuantity());
            }
        }
        if (discount>0){
            float dis=(TotalAmount*discount)/100;
            discountAmount= (int) dis;
        }


        setInVoiceResponse=new SetInVoiceResponse(customerId,payAmount,
                (TotalAmount-discountAmount),discount, setProductDataList);
        apiInterface.getInvoiceResponse("Bearer "+token,setInVoiceResponse).enqueue(new Callback<OwnerDataWithResponse>() {
            @Override
            public void onResponse(Call<OwnerDataWithResponse> call, Response<OwnerDataWithResponse> response) {
              if (response.code()==201){
                  Intent intent=new Intent(CreateInVoice_Activity.this, InVoiceActivity.class);
                  intent.putExtra("token",token);
                  startActivity(intent);
                  finish();
                  Toast.makeText(CreateInVoice_Activity.this, "create success", Toast.LENGTH_SHORT).show();
              }
              else if (response.code()==500){
                  Toast.makeText(CreateInVoice_Activity.this, "internal server error", Toast.LENGTH_SHORT).show();
              }
              else{
                  Toast.makeText(CreateInVoice_Activity.this, "create failed", Toast.LENGTH_SHORT).show();
              }
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


                if (response.code()==500){
                    Toast.makeText(CreateInVoice_Activity.this, "Cannot read property 'id' of null", Toast.LENGTH_SHORT).show();
                }
                else if (response.code()==200){
                    customerInformationDataList=new ArrayList<>();
                    customerInformationDataList.addAll(response.body().getCustomerInformation());
                    if (customerInformationDataList.size ()>0){
                        addCustomerInformation(customerInformationDataList);
                        Toast.makeText(CreateInVoice_Activity.this, String.valueOf(customerInformationDataList.size()), Toast.LENGTH_SHORT).show();
                    }
                }
                else if (response.code()==404){
                    Toast.makeText(CreateInVoice_Activity.this, "No customer found", Toast.LENGTH_SHORT).show();
                }else {

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

                if (response.code()==200){
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

                   // Toast.makeText(CreateInVoice_Activity.this, "All product fetched", Toast.LENGTH_SHORT).show();
                }else if (response.code()==404){
                    Toast.makeText(CreateInVoice_Activity.this, "Product not found", Toast.LENGTH_SHORT).show();
                }
                else if (response.code()==401){
                    Toast.makeText(CreateInVoice_Activity.this, "Invalid token", Toast.LENGTH_SHORT).show();
                }else {
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


    //customer
    @Override
    public void onContactClick1(int position) {
        nameTextView.setError(null);
        nameTextView.requestFocus();
        nameTextView.setText(String.valueOf(customerInformationDataList.get(position).getName()));
        phoneTextView.setText(String.valueOf(customerInformationDataList.get(position).getPhone()));
        addressTextView.setText(String.valueOf(customerInformationDataList.get(position).getAddress()));
        oldDueTextView.setText(String.valueOf(customerInformationDataList.get(position).getDue()));
        customerIdTextView.setText(String.valueOf(customerInformationDataList.get(position).getId()));
        alertDialog.dismiss();

    }


    //product
    @Override
    public void onContactClick(int position) {
        //getAllProduct();
       // Toast.makeText(this, String.valueOf(filterProductDataList.get(position).getName()), Toast.LENGTH_SHORT).show();
        //newList.add(getProductDataList.get(position));

        int sSize= newList.size();
        if (sSize>0){
        for (int i=0;sSize-1>=i;i++){
            String id=newList.get(i).getId();
            if (id.equals(filterProductDataList.get(position).getId())){
                Toast.makeText(CreateInVoice_Activity.this, "already selected", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
                return;
            }
            newList.add(new GetProductData(filterProductDataList.get(position).getPrice(),
                    filterProductDataList.get(position).getSellingPrice(),
                    filterProductDataList.get(position).getStock(),
                    1,filterProductDataList.get(position).getId(),
                    filterProductDataList.get(position).getName(),
                    filterProductDataList.get(position).getUnit()));
        }
        }else {

            newList.add(new GetProductData(filterProductDataList.get(position).getPrice(),
                    filterProductDataList.get(position).getSellingPrice(),
                    filterProductDataList.get(position).getStock(),
                    1,filterProductDataList.get(position).getId(),
                    filterProductDataList.get(position).getName(),
                    filterProductDataList.get(position).getUnit()));
        }


        productCustomAdapter2 = new ProductCustomAdapter2(CreateInVoice_Activity.this,token,newList, onContactClickListener3);
        selectRecyclerView.setLayoutManager(new LinearLayoutManager(CreateInVoice_Activity.this));
        selectRecyclerView.setAdapter(productCustomAdapter2);
        Log.e("size",String.valueOf(newList.size()));
        alertDialog.dismiss();
    }

    @Override
    public void onContactClick3(int position) {

    }

    @Override
    public void onBackPressed() {
        Intent intent =new Intent(CreateInVoice_Activity.this, InVoiceActivity.class);
        intent.putExtra("token",token);
        startActivity(intent);
        finish();
    }

    // title bar  button clicked
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
