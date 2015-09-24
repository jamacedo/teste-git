package com.example.sandorferreira.facebookposts;

/**
 * Created by sandorferreira on 23/09/15.
 */

import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Cidade {

    @SerializedName("itens")
    List<Itens> listaDeItens;

    public List<Itens> getListaDeItens() {
        return listaDeItens;
    }

    public void setListaDeItens(List<Itens> listaDeItens) {

        this.listaDeItens = listaDeItens;
    }

}
