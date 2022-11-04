package com.example.font_segundo_parcial.api.services;

import com.example.font_segundo_parcial.api.Datos;
import com.example.font_segundo_parcial.api.models.Categoria;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface CategoriaService {
    @Headers({
            "Accept: application/json"
    })
    @GET("categoria")
    Call<Datos<Categoria>> obtenerCategorias(
    );
}
