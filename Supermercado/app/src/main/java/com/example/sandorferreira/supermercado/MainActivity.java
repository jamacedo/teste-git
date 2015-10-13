package com.example.sandorferreira.supermercado;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private Toolbar mToolbar;
    private ItemListadoAdapter mAdapter;
    private ListView listComprados;
    public ArrayList<Item> compras;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        compras = new ArrayList<>();
        listComprados = (ListView) findViewById(R.id.listViewComprados);
        mToolbar = (Toolbar) findViewById(R.id.mainToolbar);
        mToolbar.setTitle("Supermercado");
        mToolbar.setSubtitle("Lista de Compras");
        setSupportActionBar(mToolbar);
        button = (Button) findViewById(R.id.buttonStart);
        mAdapter = new ItemListadoAdapter(MainActivity.this, compras);

        //listComprados.setOnItemClickListener(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ListaDeProdutos.class);
                startActivityForResult(i, 1);
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Item item = (Item) listComprados.getItemAtPosition(position);
        if(!(item.isSelected())) {
            item.setSelected(true);
            compras.remove(position);
            compras.add(item);
        }else{
            item.setSelected(false);
            compras.remove(position);
            compras.add(getPositionSeparador(),item);
        }
        listComprados.setAdapter(new ItemListadoAdapter(MainActivity.this, compras));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                ArrayList<String> nomes = data.getStringArrayListExtra("comprados");
                int indexComprado;
                for(int index = 0; index < nomes.size(); index++){
                    if(!isInsideCompras(nomes.get(index))){
                        if(isInsideCompras("")) {
                            indexComprado = getPositionSeparador();
                            compras.add(indexComprado,new Item(nomes.get(index),0));
                        }else{
                            compras.add(new Item(nomes.get(index),0));
                        }
                    }
                }
                if(!isInsideCompras("")) {
                    compras.add(new Item("", 1));
                }
                listComprados.setAdapter(new ItemListadoAdapter(MainActivity.this, compras));
            }
        }
    }

    public boolean isInsideCompras(String s){
        for(int index=0;index<compras.size();index++){
            if(compras.get(index).getNomeProduto().equals(s)){
                return true;
            }
        }
        return false;
    }

    public int getPositionSeparador(){
        int i;
        for(i=0;i<compras.size();i++){
            if(compras.get(i).getIsSeparator()==1){
                return i;
            }
        }
        return i;
    }


}
