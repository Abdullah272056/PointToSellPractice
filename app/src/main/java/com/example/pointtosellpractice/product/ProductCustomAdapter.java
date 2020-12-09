package com.example.pointtosellpractice.product;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pointtosellpractice.R;
import com.example.pointtosellpractice.invoice.InvoiceCustomAdapter;
import com.example.pointtosellpractice.model_class.invoice.get_all_invoice.Invoice;
import com.example.pointtosellpractice.model_class.product.GetProductData;
import com.example.pointtosellpractice.retrofit.ApiInterface;
import com.example.pointtosellpractice.retrofit.RetrofitClient;

import java.util.List;

public class ProductCustomAdapter extends RecyclerView.Adapter<ProductCustomAdapter.MyViewHolder> {
    Context context;
    String token;
    List<GetProductData> productDataList;

    ApiInterface apiInterface;
    public ProductCustomAdapter(Context context, String token, List<GetProductData> productDataList) {
        this.context = context;
        this.token = token;
        this.productDataList = productDataList;
        apiInterface = RetrofitClient.getRetrofit("http://mern-pos.herokuapp.com/").create(ApiInterface.class);
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_recyclerview_item,parent,false);
        return new ProductCustomAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.productNameTextView.setText(String.valueOf(productDataList.get(position).getName()));
        holder.productSellingPriceTextView.setText(String.valueOf(productDataList.get(position).getSellingPrice()));
        holder.productStockTextView.setText(String.valueOf(productDataList.get(position).getStock()));

    }

    @Override
    public int getItemCount() {
        return productDataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView productNameTextView,productSellingPriceTextView,productStockTextView;
        ImageView productImageView;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            productNameTextView=itemView.findViewById(R.id.productNameTextViewId);
            productSellingPriceTextView=itemView.findViewById(R.id.productSellingPriceTextViewId);
            productStockTextView=itemView.findViewById(R.id.productStockTextViewId);
            productImageView=itemView.findViewById(R.id.productImageViewId);
        }
    }
}
