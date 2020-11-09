package com.example.pointtosellpractice.retrofit;



import com.example.pointtosellpractice.model_class.RegistrationData;
import com.example.pointtosellpractice.model_class.LogInData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {

//    @GET("api/user/")
//    Call<AddresResponse> getAddress();

  //registration
    @POST("api/auth/register/")
    Call<RegistrationData>postData(@Body RegistrationData registrationData);

 


//    @PUT("api/user/{id}")
//    Call<OurDataSet> updateUser(@Path("id") String id, @Body OurDataSet ourDataSet);
//
//    @DELETE("api/user/{id}")
//    Call<OurDataSet> deleteUser(@Path("id") String id);


}
