package com.example.font_segundo_parcial.api;

import com.example.font_segundo_parcial.api.models.FichaClinica;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import org.json.JSONObject;


public interface ReservaService {
    @Headers({
            "Accept: application/json"
    })
    @GET("reserva")
    Call<Datos<Reserva>> getReservas(
            @Query("ejemplo") JSONObject json
    );


    @Headers({
            "Accept: application/json",
            "usuario: usuario5"
    })
    @POST("reserva")
    Call<Datos<Reserva>> agregarReserva(@Body Reserva reserva);


    @Headers({
            "Accept: application/json",
            "usuario: usuario5"
    })
    @PUT("reserva")
    Call<Reserva> actualizarReserva(@Body Reserva reserva);


    ///persona/4/agenda?fecha=20190903&disponible=S
    @Headers({
            "Accept: application/json"
    })
    @GET("persona/{id}/agenda")
    Call<Reserva[]> getTurnos(@Path("id") int id,  @Query("fecha") String fecha, @Query("disponible") String disponible);


    @Headers({
            "Accept: application/json"
    })
    @GET("reserva/{id}")
    Call<Reserva> getReserva(@Path("id") int id);


    @Headers({
            "Accept: application/json",
            "usuario: usuario5"
    })
    @PUT("reserva")
    Call<JSONObject> actualizarReserva(@Body JSONObject reserva);


    @Headers({
            "Accept: application/json"
    })
    @DELETE("reserva/{id}")
    Call<Reserva> deleteReserva(@Path("id") int id);





}
