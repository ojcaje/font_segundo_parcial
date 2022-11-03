package com.example.font_segundo_parcial.api;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface FichaClinicaService {
    @Headers({
            "Accept: application/json"
    })
    @GET("fichaClinica")
    Call<Datos<FichaClinica>> obtenerFichasClinicas(
            @Query("ejemplo") JSONObject jsonEjemplo
    );
}
