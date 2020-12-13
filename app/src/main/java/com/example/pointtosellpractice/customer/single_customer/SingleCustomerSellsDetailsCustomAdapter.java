package com.example.pointtosellpractice.customer.single_customer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pointtosellpractice.R;
import com.example.pointtosellpractice.retrofit.ApiInterface;
import com.example.pointtosellpractice.retrofit.RetrofitClient;

import java.util.List;

public class SingleCustomerSellsDetailsCustomAdapter extends RecyclerView.Adapter<SingleCustomerSellsDetailsCustomAdapter.MyViewHolder> {
    Context context;
    String token;
    List<SingleCustomerTotalSell> singleCustomerTotalSellList;
    int position1;

    public SingleCustomerSellsDetailsCustomAdapter(Context context, String token, List<SingleCustomerTotalSell> singleCustomerTotalSellList, int position1) {
        this.context = context;
        this.token = token;
        this.singleCustomerTotalSellList = singleCustomerTotalSellList;
        this.position1 = position1;
        apiInterface = RetrofitClient.getRetrofit("http://mern-pos.herokuapp.com/").create(ApiInterface.class);

    }

    ApiInterface apiInterface;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sell_details_recclerview_item,parent,false);
        return new SingleCustomerSellsDetailsCustomAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
       // TextView productNameTextView,productPriceTextView,
        // productQuantityTexView,productTotalPriceTextView;

        holder.productNameTextView.setText(String.valueOf(singleCustomerTotalSellList.get(position1).getProducts().get(position).getName()));
        holder.productPriceTextView.setText(String.valueOf(singleCustomerTotalSellList.get(position1).getProducts().get(position).getSellingPrice()));
        holder.productQuantityTexView.setText(String.valueOf(singleCustomerTotalSellList.get(position1).getProducts().get(position).getQuantity()));
        holder.productTotalPriceTextView.setText(String.valueOf(singleCustomerTotalSellList.get(position1).getProducts().get(position).getSellingPrice()*singleCustomerTotalSellList.get(position1).getProducts().get(position).getQuantity()));

    }

    @Override
    public int getItemCount() {
        return singleCustomerTotalSellList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView productNameTextView,productPriceTextView,productQuantityTexView,productTotalPriceTextView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            productNameTextView=itemView.findViewById(R.id.productNameTextViewId);
            productPriceTextView=itemView.findViewById(R.id.productPriceTextViewId);
            productQuantityTexView=itemView.findViewById(R.id.productQuantityTexViewId);
            productTotalPriceTextView=itemView.findViewById(R.id.productTotalPriceTextViewId);
        }
    }
}
