package com.example.pointtosellpractice.product;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pointtosellpractice.CustomerActivity;
import com.example.pointtosellpractice.R;
import com.example.pointtosellpractice.model_class.product.GetProductData;
import com.example.pointtosellpractice.retrofit.ApiInterface;
import com.example.pointtosellpractice.retrofit.RetrofitClient;
import com.squareup.picasso.Picasso;

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
       //image Load
        Picasso.with(context).load(Uri.parse(String.valueOf(productDataList.get(position).getImage()))).into(holder.productImageView);

        holder.productRecyclerViewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();

                addCustomerInformation();
            }
        });
    }

    @Override
    public int getItemCount() {
        return productDataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView productNameTextView,productSellingPriceTextView,productStockTextView;
        ImageView productImageView;
        LinearLayout productRecyclerViewItem;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            productNameTextView=itemView.findViewById(R.id.productNameTextViewId);
            productSellingPriceTextView=itemView.findViewById(R.id.productSellingPriceTextViewId);
            productStockTextView=itemView.findViewById(R.id.productStockTextViewId);
            productImageView=itemView.findViewById(R.id.productImageViewId);
            productRecyclerViewItem=itemView.findViewById(R.id.productRecyclerViewItemId);
        }
    }


    private void addCustomerInformation(){

        AlertDialog.Builder builder     =new AlertDialog.Builder(context);
        LayoutInflater layoutInflater   =LayoutInflater.from(context);
        View view                       =layoutInflater.inflate(R.layout.product_details,null);
        builder.setView(view);
        final AlertDialog alertDialog   = builder.create();

        alertDialog.show();
    }




}
