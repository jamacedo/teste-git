package com.example.sandorferreira.facebookposts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

    public static final String BASE_URL = "http://serra.primespot.city";
    TextView textView;
    Button button;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        textView.setMovementMethod(new ScrollingMovementMethod());

        button = (Button) findViewById(R.id.button);

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        final PrimeSpot apiService = retrofit.create(PrimeSpot.class);

        String cidade = "cidade";
        Call<Cidade> call = apiService.getCidade(cidade);
        call.enqueue(new Callback<Cidade>() {
            @Override
            public void onResponse(Response<Cidade> response) {
                final Cidade cidade1 = response.body();
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (i < cidade1.getListaDeItens().size()) {
                            button.setText("Proximo Item");
                            textView.setText(showInfo(i, cidade1));
                            i++;
                        } else {
                            textView.setText("Fim dos Itens");
                            button.setText("Voltar");
                            i = 0;
                        }
                    }
                });
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    public String showInfo(int indice, Cidade cidade){
        Itens item = cidade.getListaDeItens().get(indice);
        String info = "Item ID: "+ item.getId() + "\n\n"
                + "nome: " + item.getNome() +"\n\n"
                + "Detalhes: " + item.getDetalhes() + "\n\n"
                + "Imagem: " + item.getImagem() + "\n\n"
                + "Áudio: " + item.getAudio() + "\n\n"
                + "Localizacao: " + item.getLatitude() +" "+ item.getLongitude() + "\n\n"
                + "thumb: " + item.getThumb() + "\n\n"
                + "galeria_fotos: " + item.getGaleria_fotos() + "\n\n";
        return info;
    }

}
