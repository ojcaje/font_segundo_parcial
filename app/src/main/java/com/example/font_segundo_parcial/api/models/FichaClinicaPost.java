package com.example.font_segundo_parcial.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Para hacer post de una FichaClínica
 */
public class FichaClinicaPost {

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
    private Integer idEmpleado;
    @SerializedName("idCliente")
    @Expose
    private Integer idCliente;

    @SerializedName("idTipoProducto")
    @Expose
    private Subcategoria idTipoProducto;

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Integer getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Integer idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Subcategoria getIdTipoProducto() {
        return idTipoProducto;
    }

    public void setIdTipoProducto(Subcategoria idTipoProducto) {
        this.idTipoProducto = idTipoProducto;
    }


    public String getMotivoConsulta() {
        return motivoConsulta;
    }

    public void setMotivoConsulta(String motivoConsulta) {
        this.motivoConsulta = motivoConsulta;
    }
}