package com.example.pointtosellpractice.customer;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pointtosellpractice.CustomerActivity;
import com.example.pointtosellpractice.CustomerAllInfoActivity;
import com.example.pointtosellpractice.CustomerDetailsActivity;
import com.example.pointtosellpractice.R;
import com.example.pointtosellpractice.retrofit.ApiInterface;
import com.example.pointtosellpractice.retrofit.RetrofitClient;

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

    ApiInterface apiInterface;

    public CustomerCustomAdapter(Context context, String token, List<CustomerInformationData> customerInformationList) {
        this.context = context;
        this.token = token;
        this.customerInformationList = customerInformationList;
        apiInterface = RetrofitClient.getRetrofit("http://mern-pos.herokuapp.com/").create(ApiInterface.class);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.customer_recyclerview_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.customerNameTextView.setText(String.valueOf(customerInformationList.get(position).getName()));
        holder.customerPhoneTextView.setText(String.valueOf(customerInformationList.get(position).getPhone()));
        holder.customerDueTextView.setText(String.valueOf(customerInformationList.get(position).getDue()));
        holder.customerSerialTextView.setText(String.valueOf(position+1));

        holder.customerItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context, CustomerAllInfoActivity.class);
                intent.putExtra("cName",customerInformationList.get(position).getName());
                intent.putExtra("cPhone",customerInformationList.get(position).getPhone());
                intent.putExtra("cEmail",customerInformationList.get(position).getEmail());
                intent.putExtra("cAddress",customerInformationList.get(position).getAddress());

                intent.putExtra("cDue",customerInformationList.get(position).getDue().toString());
                intent.putExtra("cCreatedAt",customerInformationList.get(position).getCreatedAt());
                intent.putExtra("cId",customerInformationList.get(position).getId());
                intent.putExtra("token",token);

                context.startActivity(intent);

            }
        });

        holder.editImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCustomerInformation(position);

            }
        });
        holder.deleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteCustomer(position);
                Log.e("idid",customerInformationList.get(position).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return customerInformationList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView customerNameTextView,customerPhoneTextView,customerDueTextView,customerSerialTextView;
        ImageView editImageView,deleteImageView;
        LinearLayout customerItem;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            customerNameTextView=itemView.findViewById(R.id.customerNameTextViewId);
            customerPhoneTextView=itemView.findViewById(R.id.customerPhoneTextViewId);
            customerDueTextView=itemView.findViewById(R.id.customerDueTextViewId);
            customerSerialTextView=itemView.findViewById(R.id.customerSerialTextViewId);
            editImageView=itemView.findViewById(R.id.editImageViewId);
            deleteImageView=itemView.findViewById(R.id.deleteImageViewId);

            customerItem=itemView.findViewById(R.id.customerItemId);
        }
    }

    private  void  deleteCustomer(final int position){

    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder.setCancelable(false);
    builder.setMessage("Do you want to Delete?");

    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            apiInterface.deleteCustomer("Bearer "+token,customerInformationList.get(position).getId().toString())
                    .enqueue(new Callback<CustomerDeleteResponse>() {
                        @Override
                        public void onResponse(Call<CustomerDeleteResponse> call, Response<CustomerDeleteResponse> response) {
                            CustomerDeleteResponse customerDeleteResponse=response.body();
                            if (response.body().getSuccess()==true){
                                Toast.makeText(context, "success delete", Toast.LENGTH_SHORT).show();
                            }

                            ((CustomerActivity)context).getAllCustomer();
                        }

                        @Override
                        public void onFailure(Call<CustomerDeleteResponse> call, Throwable t) {
                            Toast.makeText(context, "fail delete", Toast.LENGTH_SHORT).show();

                        }
                    });

        }
    });
    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
        }
    });
    AlertDialog alert = builder.create();
    alert.show();
}

    private void addCustomerInformation(final int position){

        AlertDialog.Builder builder     =new AlertDialog.Builder(context);
        LayoutInflater layoutInflater   =LayoutInflater.from(context);
        View view                       =layoutInflater.inflate(R.layout.add_customer_data,null);
        builder.setView(view);
        final AlertDialog alertDialog   = builder.create();


        customerNameEditText=view.findViewById(R.id.customerNameEditTextId);
        customerPhoneEditText=view.findViewById(R.id.customerPhoneEditTextId);
        customerEmailEditText=view.findViewById(R.id.customerEmailEditTextId);
        customerAddressEditText=view.findViewById(R.id.customerAddressEditTextId);
        progressBar=view.findViewById(R.id.newCustomerProgressBarId);

        addCustomerDataButton=view.findViewById(R.id.saveCustomerDataButtonId);
        cancelCustomerButton=view.findViewById(R.id.cancelCustomerDataButtonId);


        customerNameEditText.setText(customerInformationList.get(position).getName());
        customerAddressEditText.setText(customerInformationList.get(position).getAddress());
        customerPhoneEditText.setText(customerInformationList.get(position).getPhone());
        if (customerInformationList.get(position).getEmail()!=null || TextUtils.isEmpty(customerInformationList.get(position).getEmail())){
            customerEmailEditText.setText(customerInformationList.get(position).getEmail());
        }


        addCustomerDataButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String customerName=customerNameEditText.getText().toString();
                String customerPhone=customerPhoneEditText.getText().toString();
                String customerEmail=customerEmailEditText.getText().toString();
                String customerAddress=customerAddressEditText.getText().toString();

                if (TextUtils.isEmpty(customerName) || customerAddress==null){
                    customerNameEditText.setError("Enter customer name");
                    customerNameEditText.requestFocus();
                    return;
                }
                if (customerName.length()<4){
                    customerNameEditText.setError("don't smaller than 4 character");
                    customerNameEditText.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(customerPhone)|| customerPhone==null){
                    customerPhoneEditText.setError("Enter customer phone");
                    customerPhoneEditText.requestFocus();
                    return;
                }
                if (customerPhone.length()<8){
                    customerPhoneEditText.setError("don't smaller than 8 character");
                    customerPhoneEditText.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(customerAddress) ||customerAddress==null){
                    customerAddressEditText.setError("Enter customer name");
                    customerAddressEditText.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(customerEmail)|| customerEmail==null){
                    customerData=new CustomerData(customerName,customerPhone,customerAddress);
                }
                if (!TextUtils.isEmpty(customerEmail ) && customerEmail!=null){
                    if (!Patterns.EMAIL_ADDRESS.matcher(customerEmail).matches()){
                        customerEmailEditText.setError("Enter a valid  email address");
                        customerEmailEditText.requestFocus();
                        return;
                    }else {
                        customerData=new CustomerData(customerName,customerPhone,customerEmail,customerAddress);
                    }
                }
                progressBar.setVisibility(View.VISIBLE);

                apiInterface.updateCustomerData("Bearer "+token,customerInformationList.get(position).getId().toString(),customerData)
                        .enqueue(new Callback<AddCustomerResponse>(){
                            @Override
                            public void onResponse(Call<AddCustomerResponse> call, Response<AddCustomerResponse> response) {
                              Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
                                ((CustomerActivity)context).getAllCustomer();
                                progressBar.setVisibility(View.GONE);
                            }
                            @Override
                            public void onFailure(Call<AddCustomerResponse> call, Throwable t) {
                                Log.e("aq",t.getMessage());
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(context, "fail", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        cancelCustomerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                progressBar.setVisibility(View.GONE);
            }
        });
        alertDialog.show();

    }
}
