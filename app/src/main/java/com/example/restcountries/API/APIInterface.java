package com.example.restcountries.API;

import com.example.restcountries.Models.Region;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("Asia")
    Call<List<Region>> getUserDetails();
}
