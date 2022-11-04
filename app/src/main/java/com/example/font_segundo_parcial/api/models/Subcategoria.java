package com.example.font_segundo_parcial.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Subcategoria {
    @SerializedName("idCategoria")
    @Expose
    private Categoria idCategoria;
    @SerializedName("descripcion")
    @Expose
    private String descripcion;
    @SerializedName("idTipoProducto")
    @Expose
    private Integer idTipoProducto;

    public Categoria getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Categoria idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getIdTipoProducto() {
        return idTipoProducto;
    }

    public void setIdTipoProducto(Integer idTipoProducto) {
        this.idTipoProducto = idTipoProducto;
    }

    public String toString(){
        return this.descripcion;
    }
}
