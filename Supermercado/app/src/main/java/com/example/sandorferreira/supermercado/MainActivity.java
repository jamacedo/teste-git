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


public class MainActivity extends AppCompatActivity{

    private Toolbar mToolbar;
    private ListView listComprados;
    public ArrayList<Item> compras;
    private ItemListadoAdapter mAdapter;
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

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ListaDeProdutos.class);
                startActivityForResult(i, 1);
            }
        });
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
                listComprados.setAdapter(mAdapter);
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
    /*
    FUNCAO ONCLICK:
    refreshActivity apenas atualiza o adapter na listView.
    Essa parte não está otimizada. Toda a vez que eu clicar no checkbox ele chamará o adapter
    para a listView e isso pode ser custoso caso hajam vários itens.
     */

    public void refreshActivity(View v){
        listComprados.setAdapter(mAdapter);
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
