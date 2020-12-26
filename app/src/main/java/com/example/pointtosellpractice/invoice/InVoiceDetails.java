package com.example.pointtosellpractice.invoice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pointtosellpractice.R;
import com.example.pointtosellpractice.invoice.single_invoice.SingleInvoiceCustomAdapter;
import com.example.pointtosellpractice.invoice.single_invoice.SingleInvoiceData;
import com.example.pointtosellpractice.invoice.single_invoice.SingleInvoiceGetResponse;
import com.example.pointtosellpractice.invoice.single_invoice.SingleInvoiceProductData;
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
    SingleInvoiceData singleInvoiceData;

    SingleInvoiceCustomAdapter singleInvoiceCustomAdapter;
    RecyclerView inVoiceDetailsRecyclerView;
    TextView productTotalPriceTextView,totalAmountAfterDiscountTextView,
            payAmountTextView, dueTextView,discountTextView;
    int sub=0;
    ProgressBar invoiceDetailsProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Invoice Details");
        setContentView(R.layout.activity_in_voice_details);

        // for add back Button in title bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        //progressbar finding
        invoiceDetailsProgressBar=findViewById(R.id.invoiceDetailsProgressBarId);

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
                invoiceDetailsProgressBar.setVisibility(View.INVISIBLE);
                if (singleInvoiceGetResponse.getSuccess()==true){
                    singleInvoiceProductDataList=new ArrayList<>();
                     singleInvoiceData=response.body().getSingleInvoiceData();
                    singleInvoiceProductDataList.addAll(response.body().getSingleInvoiceData().getSingleInvoiceProductDataList());
                    if (singleInvoiceProductDataList.size()>0){
                        singleInvoiceCustomAdapter=new SingleInvoiceCustomAdapter(InVoiceDetails.this,token,singleInvoiceProductDataList);
                        inVoiceDetailsRecyclerView.setLayoutManager(new LinearLayoutManager(InVoiceDetails.this));
                        inVoiceDetailsRecyclerView.setAdapter(singleInvoiceCustomAdapter);
//

                        totalAmountAfterDiscountTextView.setText(String.valueOf( singleInvoiceData.getTotalAmountAfterDiscount()));
                        payAmountTextView.setText(String.valueOf(singleInvoiceData.getPayAmount()));
                        dueTextView.setText(String.valueOf(singleInvoiceData.getDue()));
                        discountTextView.setText("after "+String.valueOf(singleInvoiceData.getDiscount())+"% discount : ");


                        int size=singleInvoiceProductDataList.size();
                        for ( int i=size-1; i>=0;i--){
                            Log.e(String.valueOf(i),String.valueOf(singleInvoiceProductDataList.get(i).getSellingPrice()));
                            sub=sub+(singleInvoiceProductDataList.get(i).getSellingPrice()*singleInvoiceProductDataList.get(i).getQuantity());
                            Log.e("svs",String.valueOf(sub));
                        }
                        productTotalPriceTextView.setText(String.valueOf(sub));


                    }

                }
            }

            @Override
            public void onFailure(Call<SingleInvoiceGetResponse> call, Throwable t) {
                Toast.makeText(InVoiceDetails.this, String.valueOf(t.getMessage()), Toast.LENGTH_LONG).show();
                invoiceDetailsProgressBar.setVisibility(View.INVISIBLE);
                Log.e("tsd",String.valueOf(t.getMessage()));
            }
        });

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