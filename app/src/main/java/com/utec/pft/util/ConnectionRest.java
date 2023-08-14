package com.utec.pft.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConnectionRest {

    private static Retrofit retrofit= null;
    private static final String RUTA_API="http://192.168.193.54:8080/AEWeb3/rest/";
    //
    public static Retrofit getConnection(){
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy/MM/dd")
                .create();
        if(retrofit==null){
            retrofit=new Retrofit.Builder().baseUrl(RUTA_API).addConverterFactory(GsonConverterFactory.create()).addConverterFactory(GsonConverterFactory.create(gson)).build();
        }
        return retrofit;
    }
}
