package com.example.pointtosellpractice.auth;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pointtosellpractice.HomePage;
import com.example.pointtosellpractice.R;
import com.example.pointtosellpractice.auth.change_password.ChangePasswordGetResponse;
import com.example.pointtosellpractice.auth.change_password.ChangePasswordSetResponse;
import com.example.pointtosellpractice.auth.delete_user.DeleteUserGetDataResponse;
import com.example.pointtosellpractice.auth.delete_user.DeleteUserSetDataResponse;
import com.example.pointtosellpractice.auth.owner_all_information.OwnerDataWithResponse;
import com.example.pointtosellpractice.retrofit.ApiInterface;
import com.example.pointtosellpractice.retrofit.RetrofitClient;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutMeActivity extends AppCompatActivity {
String token;
ApiInterface apiInterface;
TextView companyNameTextView,companyEmailTextView,companyPhoneTextView,
        companyAddressTextView, memberSinceTextView;
Button uploadPictureButton,changePasswordButton,deleteAccountButton;


//change password dialog box view
    TextView oldPasswordEditText,newPasswordEditText,confirmPasswordEditText;
    Button saveChangePasswordButton;
    ProgressBar changePasswordProgressBar;
    ChangePasswordSetResponse changePasswordSetResponse;

    ///delete password dialog box view
    Button deleteCancelButton,deleteConfirmButton;
    EditText passwordEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        // for add back Button in title bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        uploadPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AboutMeActivity.this, "Sorry! It is not available now !", Toast.LENGTH_SHORT).show();
            }
        });
        deleteAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUser();
            }
        });


    }


public  void  deleteUser(){
    AlertDialog.Builder builder     =new AlertDialog.Builder(AboutMeActivity.this);
    LayoutInflater layoutInflater   =LayoutInflater.from(AboutMeActivity.this);
    View view                       =layoutInflater.inflate(R.layout.delete_user_dialog_box,null);
    builder.setView(view);
    final AlertDialog alertDialog   = builder.create();
    deleteCancelButton=view.findViewById(R.id.deleteCancelButtonId);
    deleteConfirmButton=view.findViewById(R.id.deleteConfirmButtonId);
    passwordEditText=view.findViewById(R.id.passwordEditTextId);
    deleteConfirmButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String password=passwordEditText.getText().toString();
            if (TextUtils.isEmpty(password)){
                passwordEditText.setError("Enter your password");
                passwordEditText.requestFocus();
                return;
            }
            apiInterface.deleteUser("Bearer "+token,new DeleteUserSetDataResponse(password))
                    .enqueue(new Callback<DeleteUserGetDataResponse>() {
                        @Override
                        public void onResponse(Call<DeleteUserGetDataResponse> call, Response<DeleteUserGetDataResponse> response) {
                           if (response.code()==200){
                               Toast.makeText(AboutMeActivity.this, "Delete success", Toast.LENGTH_SHORT).show();
                               alertDialog.dismiss();
                               Intent intent=new Intent(AboutMeActivity.this,RegistrationActivity.class);
                               startActivity(intent);
                               finish();
                           }else if (response.code()==400){
                               passwordEditText.setError("incorrect password");
                               passwordEditText.requestFocus();
                              // Toast.makeText(AboutMeActivity.this, "incorrect password", Toast.LENGTH_SHORT).show();

                           }else if (response.code()==500){
                               Toast.makeText(AboutMeActivity.this, "Cannot read property 'email' of null", Toast.LENGTH_SHORT).show();
                           }
                           else {
                                Toast.makeText(AboutMeActivity.this, "Try again", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<DeleteUserGetDataResponse> call, Throwable t) {
                            Toast.makeText(AboutMeActivity.this, "Failed", Toast.LENGTH_SHORT).show();

                        }
                    });

        }
    });
    deleteCancelButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            alertDialog.dismiss();
        }
    });

    alertDialog.show();


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

                        SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy  hh:mm a", Locale.forLanguageTag(String.valueOf(response.body().getData().getCreatedAt())));
                        String getCreatedAt = df.format(new Date());

                        memberSinceTextView.setText(String.valueOf(getCreatedAt));
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
                                Intent intent =new Intent(AboutMeActivity.this, LoginActivity.class);
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
        Intent intent =new Intent(AboutMeActivity.this, HomePage.class);
        intent.putExtra("token",token);
        startActivity(intent);
        finish();
    }


    // title bar  button clicked
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}