package com.example.sandorferreira.comparador;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    TextView resultFinalMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        resultFinalMessage = (TextView) findViewById(R.id.resultFinalMessage);
        resultFinalMessage.setText(getIntent().getStringExtra("resultadoMessage"));
    }
    
}
