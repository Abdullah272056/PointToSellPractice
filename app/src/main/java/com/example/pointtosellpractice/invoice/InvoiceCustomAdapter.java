package com.example.pointtosellpractice.invoice;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pointtosellpractice.InVoiceDetails;
import com.example.pointtosellpractice.R;
import com.example.pointtosellpractice.customer.CustomerCustomAdapter;
import com.example.pointtosellpractice.customer.CustomerData;
import com.example.pointtosellpractice.customer.CustomerInformationData;
import com.example.pointtosellpractice.model_class.invoice.get_all_invoice.Invoice;
import com.example.pointtosellpractice.retrofit.ApiInterface;
import com.example.pointtosellpractice.retrofit.RetrofitClient;

import java.util.List;

public class InvoiceCustomAdapter extends RecyclerView.Adapter<InvoiceCustomAdapter.MyViewHolderInvoice> {


    Context context;
    String token;
    List<Invoice> invoiceList;



    EditText customerNameEditText,customerEmailEditText,customerPhoneEditText,customerAddressEditText;
    Button addCustomerDataButton,cancelCustomerButton;
    CustomerData customerData;
    ProgressBar progressBar;

    ApiInterface apiInterface;
    public InvoiceCustomAdapter(Context context, String token, List<Invoice> invoiceList) {
        this.context = context;
        this.token = token;
        this.invoiceList = invoiceList;
        apiInterface = RetrofitClient.getRetrofit("http://mern-pos.herokuapp.com/").create(ApiInterface.class);
    }

    @NonNull
    @Override
    public MyViewHolderInvoice onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.invoice_item,parent,false);
        return new InvoiceCustomAdapter.MyViewHolderInvoice(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderInvoice holder, int position){
        holder.invoiceItemCustomerNameTextView.setText(invoiceList.get(position).getCustomer().getName());
        holder.invoiceItemDateTextView.setText(String.valueOf(invoiceList.get(position).getCreatedAt()));
        holder.invoiceItemPayAmountTextView.setText(String.valueOf(invoiceList.get(position).getPayAmount()));
        holder.inVoiceItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(context, InVoiceDetails.class);
                intent.putExtra("token",token);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount(){
        return invoiceList.size();
    }

    public class MyViewHolderInvoice extends RecyclerView.ViewHolder {
        TextView invoiceItemCustomerNameTextView,invoiceItemDateTextView,
                invoiceItemPayAmountTextView;
        LinearLayout inVoiceItem;
        public MyViewHolderInvoice(@NonNull View itemView) {
            super(itemView);
            invoiceItemCustomerNameTextView=itemView.findViewById(R.id.invoiceItemCustomerNameTextViewId);
            invoiceItemDateTextView=itemView.findViewById(R.id.invoiceItemDateTextViewId);
            invoiceItemPayAmountTextView=itemView.findViewById(R.id.invoiceItemPayAmountTextViewId);
            inVoiceItem=itemView.findViewById(R.id.inVoiceItemId);

        }
    }
}
