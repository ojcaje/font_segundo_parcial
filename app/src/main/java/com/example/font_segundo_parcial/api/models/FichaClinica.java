package com.example.font_segundo_parcial.api.models;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.font_segundo_parcial.api.Persona;
import com.example.font_segundo_parcial.api.models.Subcategoria;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FichaClinica extends BaseObservable {
    @SerializedName("motivoConsulta")
    @Expose
    private String motivoConsulta;
    @SerializedName("diagnostico")
    @Expose
    private String diagnostico;
    @SerializedName("observacion")
    @Expose
    private String observacion;

    @SerializedName("idEmpleado")
    @Expose
    private Persona idEmpleado;
    @SerializedName("idCliente")
    @Expose
    private Persona idCliente;

    @SerializedName("idTipoProducto")
    @Expose
    private Subcategoria idTipoProducto;

    @SerializedName("fechaHoraCadenaFormateada")
    @Expose
    private String fechaHoraCadenaFormateada;
    @SerializedName("idFichaClinica")
    @Expose
    private Integer idFichaClinica;
    @SerializedName("fechaHora")
    @Expose
    private String fechaHora;

    @Bindable
    public String getMotivoConsulta() {
        return motivoConsulta;
    }

    public void setMotivoConsulta(String motivoConsulta) {
        this.motivoConsulta = motivoConsulta;
    }

    @Bindable
    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    @Bindable
    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    @Bindable
    public Persona getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Persona idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    @Bindable
    public Persona getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Persona idCliente) {
        this.idCliente = idCliente;
    }

    @Bindable
    public Subcategoria getIdTipoProducto() {
        return idTipoProducto;
    }

    public void setIdTipoProducto(Subcategoria idTipoProducto) {
        this.idTipoProducto = idTipoProducto;
    }

    @Bindable
    public String getFechaHoraCadenaFormateada() {
        return fechaHoraCadenaFormateada;
    }

    public void setFechaHoraCadenaFormateada(String fechaHoraCadenaFormateada) {
        this.fechaHoraCadenaFormateada = fechaHoraCadenaFormateada;
    }

    @Bindable
    public Integer getIdFichaClinica() {
        return idFichaClinica;
    }

    public void setIdFichaClinica(Integer idFichaClinica) {
        this.idFichaClinica = idFichaClinica;
    }

    @Bindable
    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }
}
