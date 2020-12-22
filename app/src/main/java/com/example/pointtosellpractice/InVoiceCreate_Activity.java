package com.example.pointtosellpractice;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.pointtosellpractice.retrofit.ApiInterface;
import com.example.pointtosellpractice.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

public class InVoiceCreate_Activity extends AppCompatActivity {
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
//    List<CustomerInformationData> customerInformationDataList;
//    List<GetProductData> getProductDataList;
//
//    SetInVoiceResponse setInVoiceResponse;
    Button inVoiceButton,product,selectCustomerButton;
    RecyclerView productRecyclerView;
    ListView listView;
//    ProductCustomAdapter.OnContactClickListener onContactClickListener;
//    ProductCustomAdapter2.OnContactClickListener3 onContactClickListener3;
//    CustomerCustomAdapter.OnContactClickListener1 onContactClickListener1;

//    ProductCustomAdapter productCustomAdapter;
//    ProductCustomAdapter2 productCustomAdapter2;
//    CustomerCustomAdapter customerCustomAdapter;
    AlertDialog alertDialog;

    RecyclerView selectRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_voice_create_);

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


    }
}