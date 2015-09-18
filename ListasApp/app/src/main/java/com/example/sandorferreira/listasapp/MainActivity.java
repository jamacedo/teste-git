package com.example.sandorferreira.listasapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonListar = (Button) findViewById(R.id.buttonListar);
        buttonListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listActivity = new Intent(MainActivity.this, ListaActivity.class);
                startActivity(listActivity);
            }
        });
    }


}
