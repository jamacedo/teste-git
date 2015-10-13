package com.example.sandorferreira.supermercado;

import android.widget.Button;
import android.widget.CheckBox;

import java.io.Serializable;

/**
 * Created by sandorferreira on 30/09/15.
 */
public class Item{
    private String nomeProduto;
    private boolean selected = false;
    private int isSeparator;

    public int getIsSeparator() {
        return isSeparator;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {

        return selected;
    }

    public Item(String nomeProduto, int separator){
        isSeparator = separator;
        this.nomeProduto = nomeProduto;
    }

    public String getNomeProduto(){
        return nomeProduto;
    }
}
