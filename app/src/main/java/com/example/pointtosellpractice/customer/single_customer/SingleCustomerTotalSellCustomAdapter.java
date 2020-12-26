package com.example.pointtosellpractice.customer.single_customer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pointtosellpractice.R;
import com.example.pointtosellpractice.customer.SellDetailsActivity;
import com.example.pointtosellpractice.retrofit.ApiInterface;
import com.example.pointtosellpractice.retrofit.RetrofitClient;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy \nhh:mm a", Locale.forLanguageTag(String.valueOf(singleCustomerTotalSellList.get(position).getCreatedAt())));
        String getCreatedAt = df.format(new Date());
        holder.dateTextView.setText(String.valueOf(getCreatedAt));


        holder.totalSellAmountTextView.setText(String.valueOf(singleCustomerTotalSellList.get(position).getTotalAmountAfterDiscount()));
        holder.totalPayAmountTextView.setText(String.valueOf(singleCustomerTotalSellList.get(position).getPayAmount()));
        holder.totalDueAmountTextView.setText(String.valueOf(singleCustomerTotalSellList.get(position).getDue()));
        holder.totalDueAmountTextView.setText(String.valueOf(singleCustomerTotalSellList.get(position).getDue()));

        holder.singleCustomerItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(context, SellDetailsActivity.class);
                intent.putExtra("position",position);
                intent.putExtra("token",token);
                intent.putExtra("customerId",String.valueOf(singleCustomerTotalSellList.get(position).getCustomer()));
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return singleCustomerTotalSellList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView dateTextView,totalSellAmountTextView,totalPayAmountTextView,totalDueAmountTextView;
        ImageView editImageView,deleteImageView;
        LinearLayout singleCustomerItem;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTextView= itemView.findViewById(R.id.dateTextViewId);
            totalSellAmountTextView= itemView.findViewById(R.id.totalSellAmountTextViewId);
            totalPayAmountTextView= itemView.findViewById(R.id.totalPayAmountTextViewId);
            totalDueAmountTextView= itemView.findViewById(R.id.totalDueAmountTextViewId);

            editImageView= itemView.findViewById(R.id.editImageViewId);
            deleteImageView= itemView.findViewById(R.id.deleteImageViewId);
            singleCustomerItem= itemView.findViewById(R.id.singleCustomerItemId);


        }
    }
}
