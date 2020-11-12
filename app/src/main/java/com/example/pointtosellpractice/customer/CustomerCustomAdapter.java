package com.example.pointtosellpractice.customer;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pointtosellpractice.R;

import java.util.List;

public class CustomerCustomAdapter extends RecyclerView.Adapter<CustomerCustomAdapter.MyViewHolder> {
 // List<CustomerInformationData> customerInformationList;

    Context context;
    List<CustomerInformationData> customerInformationList;

    public CustomerCustomAdapter(Context context, List<CustomerInformationData> customerInformationList) {
        this.context = context;
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
                Log.e("idid",customerInformationList.get(position).getId());
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
