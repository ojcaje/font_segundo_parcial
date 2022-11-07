package com.example.font_segundo_parcial.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {

    private static Retrofit retrofit = null;
    private static String URL_BASE = "https://equipoyosh.com/stock-nutrinatalia/";

    public static Retrofit getClient(String baseUrl) {
        if (retrofit==null) {
            retrofit=new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static FichaClinicaService getFichaClinicaService(){
        return RetrofitUtil.getClient(URL_BASE).create(FichaClinicaService.class);
    }

    public static ReservaService getReservaService(){
        return RetrofitUtil.getClient(URL_BASE).create(ReservaService.class);
    }
}
