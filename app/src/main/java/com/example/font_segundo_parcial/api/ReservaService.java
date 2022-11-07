package com.example.font_segundo_parcial.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import org.json.JSONObject;


public interface ReservaService {
    @Headers({
            "Accept: application/json"
    })
    @GET("reserva")
    Call<Datos<Reserva>> getReservas(
            @Query("query") JSONObject json
    );
}
