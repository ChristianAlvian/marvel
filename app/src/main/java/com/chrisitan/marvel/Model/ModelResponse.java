package com.chrisitan.marvel.Model;

import java.util.List;

public class ModelResponse {
    private String kode, pesan;
    private List<ModelMarvel> data;

    public String getKode(){return kode;}
    public String getPesan(){return pesan;}

    public List<ModelMarvel> getData(){
        return data;
    }
}
