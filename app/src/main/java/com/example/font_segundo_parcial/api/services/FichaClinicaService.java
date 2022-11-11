package com.example.font_segundo_parcial.api.services;

import com.example.font_segundo_parcial.api.Datos;
import com.example.font_segundo_parcial.api.models.FichaClinica;
import com.example.font_segundo_parcial.api.models.FichaClinicaPost;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
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
    Call<Datos<FichaClinicaPost>> agregarFichaClinica(@Body FichaClinicaPost ficha);

    @Headers({
            "Accept: application/json"
    })
    @GET("fichaClinica/{idFichaClinica}")
    Call<FichaClinica> obtenerFichaClinica(@Path("idFichaClinica") Integer idFichaClinica);

    @Headers({
            "Accept: application/json",
            "usuario: usuario5"
    })
    @PUT("fichaClinica")
    Call<FichaClinica> actualizarFichaClinica(@Body FichaClinica ficha);
}
