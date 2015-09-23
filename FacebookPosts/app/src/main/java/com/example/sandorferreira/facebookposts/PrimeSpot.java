package com.example.sandorferreira.facebookposts;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by sandorferreira on 23/09/15.
 */

public interface PrimeSpot {
    @GET("/ws/{cidade}")
    void getCidade(@Path("cidade") String cidade, Callback<Cidade> cb);
}
