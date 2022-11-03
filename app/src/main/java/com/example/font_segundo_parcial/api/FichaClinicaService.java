package com.example.font_segundo_parcial.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface FichaClinicaService {
    @Headers({
            "Accept: application/json"
    })
    @GET("fichaClinica")
    Call<Datos<FichaClinica>> obtenerFichasClinicas();
}
