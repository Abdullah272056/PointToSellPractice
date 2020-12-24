package com.example.pointtosellpractice;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pointtosellpractice.authorization.ChangePasswordGetResponse;
import com.example.pointtosellpractice.authorization.ChangePasswordSetResponse;
import com.example.pointtosellpractice.model_class.owner_all_information.OwnerDataWithResponse;
import com.example.pointtosellpractice.retrofit.ApiInterface;
import com.example.pointtosellpractice.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutMeActivity extends AppCompatActivity {
String token;
ApiInterface apiInterface;
TextView companyNameTextView,companyEmailTextView,companyPhoneTextView,
        companyAddressTextView, memberSinceTextView;
Button uploadPictureButton,changePasswordButton,deleteAccountButton;


//dialog box view
    TextView oldPasswordEditText,newPasswordEditText,confirmPasswordEditText;
    Button saveChangePasswordButton;
    ProgressBar changePasswordProgressBar;

    ChangePasswordSetResponse changePasswordSetResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        //receive user token
        token= getIntent().getStringExtra("token");
        apiInterface = RetrofitClient.getRetrofit("http://mern-pos.herokuapp.com/").create(ApiInterface.class);
        getUserAllInfo();
        // textView finding
        companyNameTextView=findViewById(R.id.companyNameTextViewId);
        companyEmailTextView=findViewById(R.id.companyEmailTextViewId);
        companyPhoneTextView=findViewById(R.id.companyPhoneTextViewId);
        companyAddressTextView=findViewById(R.id.companyAddressTextViewId);
        memberSinceTextView=findViewById(R.id.memberSinceTextViewId);
        // button finding
        uploadPictureButton=findViewById(R.id.uploadPictureButtonId);
        changePasswordButton=findViewById(R.id.changePasswordButtonId);
        deleteAccountButton=findViewById(R.id.deleteAccountButtonId);
        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePasswordInputBoxShow();
            }
        });


    }




    public void  changePasswordInputBoxShow(){
        AlertDialog.Builder builder     =new AlertDialog.Builder(AboutMeActivity.this);
        LayoutInflater layoutInflater   =LayoutInflater.from(AboutMeActivity.this);
        View view                       =layoutInflater.inflate(R.layout.change_password_page,null);
        builder.setView(view);
        final AlertDialog alertDialog   = builder.create();

        oldPasswordEditText=view.findViewById(R.id.oldPasswordEditTextId);
        newPasswordEditText=view.findViewById(R.id.newPasswordEditTextId);
        confirmPasswordEditText=view.findViewById(R.id.confirmPasswordEditTextId);
        saveChangePasswordButton=view.findViewById(R.id.saveChangePasswordButtonId);
        changePasswordProgressBar=view.findViewById(R.id.changePasswordProgressBarId);

        saveChangePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();

            }
        });

        alertDialog.show();
    }

    public void getUserAllInfo (){
        apiInterface.getUserAllInformation("Bearer "+token).enqueue(new Callback<OwnerDataWithResponse>() {
            @Override
            public void onResponse(Call<OwnerDataWithResponse> call, Response<OwnerDataWithResponse> response) {
                if (response.isSuccessful()){
                    if (response.body().getSuccess()==true){
                        companyNameTextView.setText(String.valueOf(response.body().getData().getCompanyName()));
                        companyEmailTextView.setText(String.valueOf(response.body().getData().getEmail()));
                        companyPhoneTextView.setText(String.valueOf(response.body().getData().getPhone()));
                        companyAddressTextView.setText(String.valueOf(response.body().getData().getAddress()));
                        memberSinceTextView.setText(String.valueOf(response.body().getData().getCreatedAt()));
                        Toast.makeText(AboutMeActivity.this, "success", Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(AboutMeActivity.this, "server error", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(AboutMeActivity.this, "server error", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<OwnerDataWithResponse> call, Throwable t) {
                Toast.makeText(AboutMeActivity.this, String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public  void changePassword(){
        String newPassword=newPasswordEditText.getText().toString();
        String confirmPassword=confirmPasswordEditText.getText().toString();
        String oldPassword=oldPasswordEditText.getText().toString();

        if (TextUtils.isEmpty(oldPassword)){
            oldPasswordEditText.setError("Enter your old password");
            oldPasswordEditText.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(newPassword)){
            newPasswordEditText.setError("Enter new password");
            newPasswordEditText.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(confirmPassword)){
            confirmPasswordEditText.setError("Enter confirm password");
            confirmPasswordEditText.requestFocus();
            return;
        }
        if (!newPassword.equals(confirmPassword)){
            confirmPasswordEditText.setError("can not matching confirm password");
            confirmPasswordEditText.requestFocus();
            return;
        }
        changePasswordProgressBar.setVisibility(View.VISIBLE);

        changePasswordSetResponse=new ChangePasswordSetResponse(oldPassword,newPassword);
        apiInterface.changePassword("Bearer "+token,changePasswordSetResponse).
                enqueue(new Callback<ChangePasswordGetResponse>() {
                    @Override
                    public void onResponse(Call<ChangePasswordGetResponse> call, Response<ChangePasswordGetResponse> response) {
                        if (response.isSuccessful()){
                            if (response.body().getSuccess()==true){
                                changePasswordProgressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(AboutMeActivity.this, "Password changed successfully", Toast.LENGTH_SHORT).show();
                                Intent intent =new Intent(AboutMeActivity.this,LoginActivity.class);
                                startActivity(intent);
                                finish();
                            } if (response.body().getSuccess()==false){
                                Toast.makeText(AboutMeActivity.this, "Old password does not match", Toast.LENGTH_SHORT).show();
                                changePasswordProgressBar.setVisibility(View.INVISIBLE);
                            }
                        }else {
                            changePasswordProgressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(AboutMeActivity.this, "Old password does not match", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ChangePasswordGetResponse> call, Throwable t) {
                        Toast.makeText(AboutMeActivity.this, "failed ! try again ", Toast.LENGTH_SHORT).show();
                        changePasswordProgressBar.setVisibility(View.INVISIBLE);
                    }
                });
    }
    @Override
    public void onBackPressed() {
        Intent intent =new Intent(AboutMeActivity.this,HomePage.class);
        intent.putExtra("token",token);
        startActivity(intent);
        finish();
    }
}