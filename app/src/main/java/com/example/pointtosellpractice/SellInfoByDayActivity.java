package com.example.pointtosellpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pointtosellpractice.model_class.invoice.GetSellInfoByDayResponse;
import com.example.pointtosellpractice.retrofit.ApiInterface;
import com.example.pointtosellpractice.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SellInfoByDayActivity extends AppCompatActivity {
    String token;
    ApiInterface apiInterface;
    String day;

    Button sellInfoByDayOkButton;
    EditText sellInfoByDayEditText;
    TextView sellProductTextView,saleAmountTextView,profitTxtView,
            currentCashTextView,dueTextView,inVoiceTextView,productCostTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_info_by_day);
        //finding view
        sellInfoByDayOkButton=findViewById(R.id.sellInfoByDayOkButtonId);
        sellInfoByDayEditText=findViewById(R.id.sellInfoByDayEditTextId);
        //finding textView
        sellProductTextView=findViewById(R.id.sellProductTextViewId);
        saleAmountTextView=findViewById(R.id.saleAmountTextViewId);
        profitTxtView=findViewById(R.id.profitTxtViewId);
        currentCashTextView=findViewById(R.id.currentCashTextViewId);
        dueTextView=findViewById(R.id.dueTextViewId);
        inVoiceTextView=findViewById(R.id.inVoiceTextViewId);
        productCostTextView=findViewById(R.id.productCostTextViewId);

        //receive data
        token= getIntent().getStringExtra("token");
        apiInterface = RetrofitClient.getRetrofit("http://mern-pos.herokuapp.com/").create(ApiInterface.class);

        sellInfoByDayOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSellInfoByDate();
            }
        });



    }

    public void getSellInfoByDate(){
        day =sellInfoByDayEditText.getText().toString();
        if (TextUtils.isEmpty(day)){
            sellInfoByDayEditText.setError("Enter a value");
            sellInfoByDayEditText.requestFocus();
            return;
        }

        apiInterface.getSellInfoByDay("Bearer "+token,day).enqueue(new Callback<GetSellInfoByDayResponse>() {
            @Override
            public void onResponse(Call<GetSellInfoByDayResponse> call, Response<GetSellInfoByDayResponse> response) {
                if (response.body().getSuccess()==true){
//                    TextView sellProductTextView,saleAmountTextView,profitTxtView,
//                            currentCashTextView,dueTextView,inVoiceTextView,productCostTextView;
                    sellProductTextView.setText(String.valueOf(response.body().getGetSellInfoByDays().getTotalSoldProduct()));
                    saleAmountTextView.setText(String.valueOf(response.body().getGetSellInfoByDays().getTotalSaleAmount()));
                    profitTxtView.setText(String.valueOf(response.body().getGetSellInfoByDays().getTotalProfit()));
                    currentCashTextView.setText(String.valueOf(response.body().getGetSellInfoByDays().getCurrentCash()));
                    dueTextView.setText(String.valueOf(response.body().getGetSellInfoByDays().getTotalDue()));
                    inVoiceTextView.setText(String.valueOf(response.body().getGetSellInfoByDays().getTotalSoldInvoice()));
                    productCostTextView.setText(String.valueOf(response.body().getGetSellInfoByDays().getTotalProductCost()));

                    Toast.makeText(SellInfoByDayActivity.this, String.valueOf(response.body().getGetSellInfoByDays().getTotalSoldProduct()), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<GetSellInfoByDayResponse> call, Throwable t) {
                Toast.makeText(SellInfoByDayActivity.this, String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();
                Log.e("uyt",String.valueOf(t.getMessage()));
            }
        });
    }
}