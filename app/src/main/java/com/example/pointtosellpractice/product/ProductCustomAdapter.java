package com.example.pointtosellpractice.product;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
   TextView productNameTextView,productRegularPriceTextView,productSellingPriceTextView,
           productStockTextView,productUnitTextView,productDescriptionTextView,
           productAddDateTextView,productUpdateDateTextView;
   ImageView productIDetailsImageView;
   Button okButton;

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
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.productNameTextView.setText(String.valueOf(productDataList.get(position).getName()));
        holder.productSellingPriceTextView.setText(String.valueOf(productDataList.get(position).getSellingPrice()));
        holder.productStockTextView.setText(String.valueOf(productDataList.get(position).getStock()));

        //image Load
        Picasso.with(context).load(Uri.parse(String.valueOf(productDataList.get(position).getImage()))).into(holder.productImageView);

        holder.productRecyclerViewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();

                addCustomerInformation(position);
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


    private void addCustomerInformation(int position){
        AlertDialog.Builder builder     =new AlertDialog.Builder(context);
        LayoutInflater layoutInflater   =LayoutInflater.from(context);
        View view                       =layoutInflater.inflate(R.layout.product_details,null);
        builder.setView(view);
        final AlertDialog alertDialog   = builder.create();

        productNameTextView=view.findViewById(R.id.productNameTextViewId);
        productRegularPriceTextView=view.findViewById(R.id.productRegularPriceTextViewId);
        productSellingPriceTextView=view.findViewById(R.id.productSellingPriceTextViewId);
        productStockTextView=view.findViewById(R.id.productStockTextViewId);
        productUnitTextView=view.findViewById(R.id.productUnitTextViewId);
        productDescriptionTextView=view.findViewById(R.id.productDescriptionTextViewId);
        productAddDateTextView=view.findViewById(R.id.productAddDateTextViewId);
        productUpdateDateTextView=view.findViewById(R.id.productUpdateDateTextViewId);
        productIDetailsImageView=view.findViewById(R.id.productImageViewId);

        okButton=view.findViewById(R.id.okButtonId);


        productNameTextView.setText(String.valueOf(productDataList.get(position).getName()));
        productRegularPriceTextView.setText(String.valueOf(productDataList.get(position).getPrice()));
        productSellingPriceTextView.setText(String.valueOf(productDataList.get(position).getSellingPrice()));
        productStockTextView.setText(String.valueOf(productDataList.get(position).getStock()));
        productDescriptionTextView.setText(String.valueOf(productDataList.get(position).getDescription()));
        productAddDateTextView.setText(String.valueOf(productDataList.get(position).getCreatedAt()));
        productUpdateDateTextView.setText(String.valueOf(productDataList.get(position).getUpdatedAt()));
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        Picasso.with(context).load(Uri.parse(String.valueOf(productDataList.get(position).getImage()))).into(productIDetailsImageView);

        alertDialog.show();
    }




}
