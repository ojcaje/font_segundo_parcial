package com.example.font_segundo_parcial.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {

    private static Retrofit retrofit = null;
    private static String URL_BASE = "https://equipoyosh.com/stock-nutrinatalia/";


    public static Retrofit getClient(String baseUrl) {

        if (retrofit==null) {

            // para hacer logs
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(logging);

            // construir retrofit
            retrofit=new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }
        return retrofit;
    }

    public static FichaClinicaService getFichaClinicaService(){
        return RetrofitUtil.getClient(URL_BASE).create(FichaClinicaService.class);
    }
}
