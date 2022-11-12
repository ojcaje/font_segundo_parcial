package com.example.font_segundo_parcial.api;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface PersonaService {
    @Headers({
            "Accept: application/json"
    })
    @GET("persona")
    Call<Datos<Persona>> getPersonas(
            @Query("ejemplo") JSONObject json
    );

    @Headers({
            "Accept: application/json"
    })
    @GET("persona/{idPersona}")
    Call<Persona> getPersona(@Path("idPersona") Integer idPersona);

    @Headers({
            "Accept: application/json"
    })
    @POST("persona")
    Call<Datos<Persona>> postPersona(@Body Persona persona);

    @Headers({
            "Accept: application/json"
    })
    @PUT("persona")
    Call<Persona> putPersona(@Body Persona persona);

    @Headers({
            "Accept: application/json"
    })
    @DELETE("persona/{idPersona}")
    Call<Persona> deletePersona(@Path("idPersona") Integer idPersona);

}
