package com.example.font_segundo_parcial.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Se recibe esto del backend al hacer algún get
 */
public class Datos<T> {
    @SerializedName("lista")
    @Expose
    private T[] lista;
    @SerializedName("totalDatos")
    @Expose
    private Integer totalDatos;

    public T[] getLista() {
        return lista;
    }

    public void setLista(T[] lista) {
        this.lista = lista;
    }

    public Integer getTotalDatos() {
        return totalDatos;
    }

    public void setTotalDatos(Integer totalDatos) {
        this.totalDatos = totalDatos;
    }
}
