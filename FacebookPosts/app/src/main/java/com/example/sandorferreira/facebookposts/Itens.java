package com.example.sandorferreira.facebookposts;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

/**
 * Created by sandorferreira on 24/09/15.
 */

@Generated("org.jsonschema2pojo")
public class Itens {

    @SerializedName("id")
    private int id;

    @SerializedName("nome")
    private String nome;

    @SerializedName("detalhes")
    private String detalhes;

    @SerializedName("imagem")
    private String imagem;

    @SerializedName("audio")
    private String audio;

    @SerializedName("thumb")
    private String thumb;

    @SerializedName("galeria_fotos")
    private String galeria_fotos;

    @SerializedName("latitude")
    private float latitude;

    @SerializedName("longitude")
    private float longitude;

    public int getId(){
        return id;
    }

    public String getDetalhes() {
        return detalhes;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public String getImagem() {
        return imagem;
    }

    public String getAudio() {
        return audio;
    }

    public String getThumb() {
        return thumb;
    }

    public String getGaleria_fotos() {
        return galeria_fotos;
    }

    public String getNome() {
        return nome;
    }
}

