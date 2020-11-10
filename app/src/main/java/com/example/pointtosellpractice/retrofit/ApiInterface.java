package com.example.pointtosellpractice.retrofit;



import com.example.pointtosellpractice.model_class.LogInResponse;
import com.example.pointtosellpractice.model_class.RegistrationData;
import com.example.pointtosellpractice.model_class.LogInData;
import com.example.pointtosellpractice.model_class.RegistrationResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {

//    @GET("api/user/")
//    Call<AddresResponse> getAddress();

  //registration
    @POST("api/auth/register/")
    Call<RegistrationResponse>registrationData(@Body RegistrationData registrationData);

    //signIn
    @POST("api/auth/login/")
    Call<LogInResponse>logInData(@Body LogInData logInData);



    //Call<jetaReponsePaboSetarModelClass>logInData(@Body jetaPathaboSetarModelClass variableName);




//    @PUT("api/user/{id}")
//    Call<OurDataSet> updateUser(@Path("id") String id, @Body OurDataSet ourDataSet);
//
//    @DELETE("api/user/{id}")
//    Call<OurDataSet> deleteUser(@Path("id") String id);


}
