package com.example.font_segundo_parcial.api.services;

import com.example.font_segundo_parcial.api.Datos;
import com.example.font_segundo_parcial.api.models.FichaClinica;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface FichaClinicaService {
    @Headers({
            "Accept: application/json"
    })
    @GET("fichaClinica")
    Call<Datos<FichaClinica>> obtenerFichasClinicas(
            @Query("ejemplo") JSONObject jsonEjemplo
    );

    @Headers({
            "Accept: application/json",
            "usuario: usuario5"
    })
    @POST("fichaClinica")
    Call<Datos<FichaClinica>> agregarFichaClinica(@Body FichaClinica ficha);
}
