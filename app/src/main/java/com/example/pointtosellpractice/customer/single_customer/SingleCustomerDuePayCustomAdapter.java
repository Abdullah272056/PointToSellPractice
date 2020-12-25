package com.example.pointtosellpractice.customer.single_customer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pointtosellpractice.R;
import com.example.pointtosellpractice.customer.CustomerCustomAdapter;
import com.example.pointtosellpractice.customer.CustomerInformationData;
import com.example.pointtosellpractice.retrofit.ApiInterface;
import com.example.pointtosellpractice.retrofit.RetrofitClient;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SingleCustomerDuePayCustomAdapter extends RecyclerView.Adapter<SingleCustomerDuePayCustomAdapter.MyViewHolder> {

    Context context;
    String token;
    List<SingleCustomerDuePayHistory> singleCustomerDuePayHistoryList;
    ApiInterface apiInterface;
    public SingleCustomerDuePayCustomAdapter(Context context, String token, List<SingleCustomerDuePayHistory> singleCustomerDuePayHistoryList) {
        this.context = context;
        this.token = token;
        this.singleCustomerDuePayHistoryList = singleCustomerDuePayHistoryList;
        apiInterface = RetrofitClient.getRetrofit("http://mern-pos.herokuapp.com/").create(ApiInterface.class);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.due_pay_history_recyclerview_item,parent,false);
        return new SingleCustomerDuePayCustomAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy \nhh:mm a", Locale.forLanguageTag(String.valueOf(singleCustomerDuePayHistoryList.get(position).getDate())));
        String getDate = df.format(new Date());

        holder.duePayHistoryDateTextView.setText(getDate);
        holder.duePayHistoryPayAmountTextView.setText(String.valueOf(singleCustomerDuePayHistoryList.get(position).getPayAmount()));

    }

    @Override
    public int getItemCount() {
        return singleCustomerDuePayHistoryList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView duePayHistoryDateTextView,duePayHistoryPayAmountTextView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            duePayHistoryDateTextView= itemView.findViewById(R.id.duePayHistoryDateTextViewId);
            duePayHistoryPayAmountTextView=  itemView.findViewById(R.id.duePayHistoryPayAmountTextViewId);
        }
    }
}
