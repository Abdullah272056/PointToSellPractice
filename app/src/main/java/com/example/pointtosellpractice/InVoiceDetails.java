package com.example.pointtosellpractice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pointtosellpractice.model_class.invoice.get_all_invoice.Invoice;
import com.example.pointtosellpractice.model_class.invoice.single_invoice.SingleInvoiceCustomAdapter;
import com.example.pointtosellpractice.model_class.invoice.single_invoice.SingleInvoiceGetResponse;
import com.example.pointtosellpractice.model_class.invoice.single_invoice.SingleInvoiceProductData;
import com.example.pointtosellpractice.model_class.owner_all_information.OwnerDataWithResponse;
import com.example.pointtosellpractice.retrofit.ApiInterface;
import com.example.pointtosellpractice.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InVoiceDetails extends AppCompatActivity {
    ApiInterface apiInterface;
    String token,invoice_id;
    List<SingleInvoiceProductData> singleInvoiceProductDataList;
    SingleInvoiceCustomAdapter singleInvoiceCustomAdapter;
    RecyclerView inVoiceDetailsRecyclerView;
    TextView productTotalPriceTextView,totalAmountAfterDiscountTextView,
            payAmountTextView, dueTextView,discountTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_voice_details);

        //receive token
        token= getIntent().getStringExtra("token");
        invoice_id= getIntent().getStringExtra("invoice_id");
        //textView finding
        productTotalPriceTextView=findViewById(R.id.productTotalPriceTextViewId);
        totalAmountAfterDiscountTextView=findViewById(R.id.totalAmountAfterDiscountTextViewId);
        payAmountTextView=findViewById(R.id.payAmountTextViewId);
        dueTextView=findViewById(R.id.dueTextViewId);
        discountTextView=findViewById(R.id.discountTextViewId);
        //recyclerview finding
        inVoiceDetailsRecyclerView=findViewById(R.id.inVoiceDetailsRecyclerViewId);

        apiInterface = RetrofitClient.getRetrofit("http://mern-pos.herokuapp.com/").create(ApiInterface.class);
        singleInvoiceDetails();
//        apiInterface.getUserAllInformation("Bearer "+token).
//                enqueue(new Callback<OwnerDataWithResponse>() {
//            @Override
//            public void onResponse(Call<OwnerDataWithResponse> call, Response<OwnerDataWithResponse> response) {
//
//                OwnerDataWithResponse ownerDataWithResponse=response.body();
//                Toast.makeText(InVoiceDetails.this, "success", Toast.LENGTH_SHORT).show();
////                companyNameTextView.setText(String.valueOf(ownerDataWithResponse.getData().getCompanyName()));
////                ownerNameTextView.setText(String.valueOf(ownerDataWithResponse.getData().getCompanyOwner()));
////                ownerAddressTextView.setText(String.valueOf(ownerDataWithResponse.getData().getAddress()));
////                ownerEmailTextView.setText(String.valueOf(ownerDataWithResponse.getData().getEmail()));
////                ownerPhoneTextView.setText(String.valueOf(ownerDataWithResponse.getData().getPhone()));
//
//                Log.e("gt", ownerDataWithResponse.getData().getEmail().toString());
//
//            }
//
//            @Override
//            public void onFailure(Call<OwnerDataWithResponse> call, Throwable t) {
//                Toast.makeText(InVoiceDetails.this, "fail", Toast.LENGTH_SHORT).show();
//                Log.e("gt", "ff");
//            }
//        });
    }

    public void singleInvoiceDetails(){
        apiInterface.getSingleInvoiceInformation("Bearer "+token,invoice_id).enqueue(new Callback<SingleInvoiceGetResponse>() {
            @Override
            public void onResponse(Call<SingleInvoiceGetResponse> call, Response<SingleInvoiceGetResponse> response) {
                SingleInvoiceGetResponse singleInvoiceGetResponse=response.body();
                if (singleInvoiceGetResponse.getSuccess()==true){
                    singleInvoiceProductDataList=new ArrayList<>();
                    singleInvoiceProductDataList.addAll(response.body().getSingleInvoiceData().getSingleInvoiceProductDataList());
                    if (singleInvoiceProductDataList.size()>0){
                        singleInvoiceCustomAdapter=new SingleInvoiceCustomAdapter(InVoiceDetails.this,token,singleInvoiceProductDataList);
                        inVoiceDetailsRecyclerView.setLayoutManager(new LinearLayoutManager(InVoiceDetails.this));
                        inVoiceDetailsRecyclerView.setAdapter(singleInvoiceCustomAdapter);
//


                    }

                }
            }

            @Override
            public void onFailure(Call<SingleInvoiceGetResponse> call, Throwable t) {
                Toast.makeText(InVoiceDetails.this, "ibd ff", Toast.LENGTH_SHORT).show();

            }
        });

    }

}