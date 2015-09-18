package com.example.sandorferreira.services;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class AlertarActivity extends AppCompatActivity {

    ListView alertasLV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alertar);
        alertasLV = (ListView) findViewById(R.id.listViewAlertas);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Monitoramento");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if(e==null){
                    List<String> alertasRegistrados = new ArrayList<String>();
                    for(ParseObject object : list){
                        alertasRegistrados.add(object.get("assunto")+ " - " + object.get("descricao"));
                    }
                    alertasLV.setAdapter(new ArrayAdapter<String>(AlertarActivity.this,
                            android.R.layout.simple_list_item_1, alertasRegistrados));
                }else{
                    Toast.makeText(AlertarActivity.this, "Nao foi possivel buscar, tenta mais tarde",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
