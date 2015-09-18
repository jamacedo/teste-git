package com.example.sandorferreira.listasapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListaActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        List<String> listaAlunos = new ArrayList<String>();
        listaAlunos.add("Joao Paulo");
        listaAlunos.add("marieta");
        listaAlunos.add("josefa");
        listaAlunos.add("maria");

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaAlunos));
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView textView = (TextView) view;
        Toast.makeText(this, "Voce selecionou o aluno: "+ textView.getText(), Toast.LENGTH_LONG).show();
    }
}
