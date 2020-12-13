package com.example.pointtosellpractice.customer.single_customer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pointtosellpractice.R;
import com.example.pointtosellpractice.retrofit.ApiInterface;
import com.example.pointtosellpractice.retrofit.RetrofitClient;

import java.util.List;

public class SingleCustomerTotalSellCustomAdapter extends RecyclerView.Adapter<SingleCustomerTotalSellCustomAdapter.MyViewHolder> {
    Context context;
    String token;
    List<SingleCustomerTotalSell> singleCustomerTotalSellList;
    ApiInterface apiInterface;

    public SingleCustomerTotalSellCustomAdapter(Context context, String token, List<SingleCustomerTotalSell> singleCustomerTotalSellList) {
        this.context = context;
        this.token = token;
        this.singleCustomerTotalSellList = singleCustomerTotalSellList;
        apiInterface = RetrofitClient.getRetrofit("http://mern-pos.herokuapp.com/").create(ApiInterface.class);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_customer_total_sell_recyclerview_item,parent,false);
        return new SingleCustomerTotalSellCustomAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        TextView dateTextView,totalSellAmountTextView,totalPayAmountTextView,totalDueAmountTextView;

        holder.dateTextView.setText(String.valueOf(singleCustomerTotalSellList.get(position).getCreatedAt()));
        holder.totalSellAmountTextView.setText(String.valueOf(singleCustomerTotalSellList.get(position).getTotalAmountAfterDiscount()));
        holder.totalPayAmountTextView.setText(String.valueOf(singleCustomerTotalSellList.get(position).getPayAmount()));
        holder.totalDueAmountTextView.setText(String.valueOf(singleCustomerTotalSellList.get(position).getDue()));
     //   holder.dateTextView.setText(singleCustomerTotalSellList.get(position).getCreatedAt());

    }

    @Override
    public int getItemCount() {
        return singleCustomerTotalSellList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView dateTextView,totalSellAmountTextView,totalPayAmountTextView,totalDueAmountTextView;
        ImageView editImageView,deleteImageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTextView= itemView.findViewById(R.id.dateTextViewId);
            totalSellAmountTextView= itemView.findViewById(R.id.totalSellAmountTextViewId);
            totalPayAmountTextView= itemView.findViewById(R.id.totalPayAmountTextViewId);
            totalDueAmountTextView= itemView.findViewById(R.id.totalDueAmountTextViewId);

            editImageView= itemView.findViewById(R.id.editImageViewId);
            deleteImageView= itemView.findViewById(R.id.deleteImageViewId);


        }
    }
}
