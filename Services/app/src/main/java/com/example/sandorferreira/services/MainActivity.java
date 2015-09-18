package com.example.sandorferreira.services;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonAlerta = (Button) findViewById(R.id.buttonAlerta);
        Button buttonListar = (Button) findViewById(R.id.buttonListar);

        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "79vILPTtE8G6ISlozW5DyBjyy01D09OL6am6KV4Y", "gOnrX3lEHoTYX1r8EdvHv4wJUYUKKtwsKQkv0vQi");


        ParseQuery<ParseObject> query = ParseQuery.getQuery("Monitoramento");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if(e == null){
                    Log.d("CursoAndroid", "Recuperou" + list.size() + "monitoramento");
                }else{
                    Log.d("CursoAndroid", "Error: " + e.getMessage());
                }
            }
        });

        buttonAlerta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, CadastroActivity.class);
                startActivity(i);
            }
        });

        buttonListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AlertarActivity.class);
                startActivity(i);
            }
        });
    }


}
