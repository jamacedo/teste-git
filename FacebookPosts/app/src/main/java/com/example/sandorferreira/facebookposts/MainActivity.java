package com.example.sandorferreira.facebookposts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.*;
import retrofit.mime.TypedByteArray;

public class MainActivity extends AppCompatActivity {

    public static final String BASE_URL = "http://serra.primespot.city";
    TextView textInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textInfo = (TextView) findViewById(R.id.textViewInformation);
        textInfo.setMovementMethod(new ScrollingMovementMethod());

        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL).build();

        PrimeSpot apiService = restAdapter.create(PrimeSpot.class);

        String cidade = "cidade";
        apiService.getCidade(cidade, new Callback<Cidade>() {
            @Override
            public void success(Cidade cidade, retrofit.client.Response response) {
                String text = new String(((TypedByteArray) response.getBody()).getBytes());
                textInfo.setText(text);
            }
            @Override
            public void failure(RetrofitError error) {
                //indicar o motivo do erro
                textInfo.setText(error.toString());
            }
        });
    }

}
