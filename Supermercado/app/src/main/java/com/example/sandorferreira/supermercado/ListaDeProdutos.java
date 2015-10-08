package com.example.sandorferreira.supermercado;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListaDeProdutos extends AppCompatActivity implements SearchView.OnQueryTextListener,
        AdapterView.OnItemClickListener{

    ListView listOfItens;
    SearchView mSearchView;
    ArrayList<String> comprados;
    ArrayList<Item> itens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_de_produtos);
        mSearchView = (SearchView) findViewById(R.id.searchView);
        final Button concluido = (Button) findViewById(R.id.buttonConcluido);

        comprados = new ArrayList<String>();
        itens = new ArrayList<>();
        itens.add(new Item("Café",0));
        itens.add(new Item("Leite",0));
        itens.add(new Item("Biscoito",0));
        itens.add(new Item("Sabonete",0));
        itens.add(new Item("Sabão em pó",0));
        itens.add(new Item("Bolacha",0));
        itens.add(new Item("Miojo",0));

        listOfItens = (ListView) findViewById(R.id.listView);
        listOfItens.setAdapter(new ItemAdapter(this, itens));
        listOfItens.setOnItemClickListener(this);
        listOfItens.setTextFilterEnabled(true);
        mSearchView.setQueryHint("Nome do produto");
        setmSearchView();

        concluido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListaDeProdutos.this, MainActivity.class);
                intent.putStringArrayListExtra("comprados", comprados);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Item item = (Item) listOfItens.getItemAtPosition(position);
        String textName = item.getNomeProduto();
        if(!isInsideComprados(textName)){
            comprados.add(textName);
            Toast.makeText(ListaDeProdutos.this, "Voce adicionou: " + textName, Toast.LENGTH_SHORT).show();
        }else
            Toast.makeText(ListaDeProdutos.this,textName+ " já está adicionado à lista", Toast.LENGTH_SHORT).show();
    }


    public void setmSearchView(){
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setQueryHint("Nome do produto");
    }

    public boolean isInsideComprados(String s){
        for(int index=0;index<comprados.size();index++){
            if(comprados.get(index).equals(s)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if(TextUtils.isEmpty(s)){
            listOfItens.clearTextFilter();
        }else{
            listOfItens.setFilterText(s);
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }
}
