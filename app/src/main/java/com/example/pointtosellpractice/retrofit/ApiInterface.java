package com.example.pointtosellpractice.retrofit;



import com.example.pointtosellpractice.customer.CustomerData;
import com.example.pointtosellpractice.customer.CustomerInformationDataResponse;
import com.example.pointtosellpractice.customer.AddCustomerResponse;
import com.example.pointtosellpractice.model_class.LogInResponse;
import com.example.pointtosellpractice.model_class.RegistrationData;
import com.example.pointtosellpractice.model_class.LogInData;
import com.example.pointtosellpractice.model_class.RegistrationResponse;
import com.example.pointtosellpractice.user_all_information.UserDataWithResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

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


    //user all information
    @GET("api/auth/me")
    Call<UserDataWithResponse> getUserAllInformation(@Header("Authorization") String authorization);



    //customer
   //add customer
  @POST("api/customer")
  Call<AddCustomerResponse> addCustomerInformation(@Header("Authorization") String authorization, @Body CustomerData customerData);

  // get allCustomer data
    @GET("api/customer")
    Call<CustomerInformationDataResponse> getAllCustomerInformation(@Header("Authorization") String authorization1);

    //customer Data Edit
    @PUT("api/customer/{id}")
    Call<CustomerInformationDataResponse> updateCustomerData(@Header("Authorization") String authorization2,@Path("id") String id, @Body CustomerData customerData);



//    @PUT("api/user/{id}")
//    Call<OurDataSet> updateUser(@Path("id") String id, @Body OurDataSet ourDataSet);


//    @DELETE("api/user/{id}")
//    Call<OurDataSet> deleteUser(@Path("id") String id);


}
