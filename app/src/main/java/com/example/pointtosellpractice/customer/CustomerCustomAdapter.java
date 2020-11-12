package com.example.pointtosellpractice.customer;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pointtosellpractice.CustomerActivity;
import com.example.pointtosellpractice.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerCustomAdapter extends RecyclerView.Adapter<CustomerCustomAdapter.MyViewHolder> {
 // List<CustomerInformationData> customerInformationList;

    Context context;
    String token;
    List<CustomerInformationData> customerInformationList;



    EditText customerNameEditText,customerEmailEditText,customerPhoneEditText,customerAddressEditText;
    Button addCustomerDataButton,cancelCustomerButton;
    CustomerData customerData;
    ProgressBar progressBar;

    public CustomerCustomAdapter(Context context, String token, List<CustomerInformationData> customerInformationList) {
        this.context = context;
        this.token = token;
        this.customerInformationList = customerInformationList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.customer_recyclerview_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.customerNameTextView.setText(customerInformationList.get(position).getName());
        holder.customerPhoneTextView.setText(customerInformationList.get(position).getPhone());

        holder.editImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        holder.deleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("idid",customerInformationList.get(position).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return customerInformationList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView customerNameTextView,customerPhoneTextView;
        ImageView editImageView,deleteImageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            customerNameTextView=itemView.findViewById(R.id.customerNameTextViewId);
            customerPhoneTextView=itemView.findViewById(R.id.customerPhoneTextViewId);
            editImageView=itemView.findViewById(R.id.editImageViewId);
            deleteImageView=itemView.findViewById(R.id.deleteImageViewId);
        }
    }



   
}
