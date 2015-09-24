package com.example.sandorferreira.facebookposts;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by sandorferreira on 23/09/15.
 */

public interface PrimeSpot {
    @GET("/ws/{cidade}")
    Call<Cidade> getCidade(@Path("cidade") String cidade);
}
