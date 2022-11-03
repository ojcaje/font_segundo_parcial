package com.example.font_segundo_parcial.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FichaClinica {
    @SerializedName("motivoConsulta")
    @Expose
    private String motivoConsulta;
    @SerializedName("diagnostico")
    @Expose
    private String diagnostico;
    @SerializedName("observacion")
    @Expose
    private String observacion;

    // TODO: cambiar la clase Object por Empleado o Persona
    @SerializedName("idEmpleado")
    @Expose
    private Object idEmpleado;
    @SerializedName("idCliente")
    @Expose
    private Object idCliente;

    // TODO: cambiar la clase Object por Subcategoria
    @SerializedName("idTipoProducto")
    @Expose
    private Object idTipoProducto;

    @SerializedName("fechaHoraCadenaFormateada")
    @Expose
    private String fechaHoraCadenaFormateada;
    @SerializedName("idFichaClinica")
    @Expose
    private Integer idFichaClinica;
    @SerializedName("fechaHora")
    @Expose
    private String fechaHora;

    public String getMotivoConsulta() {
        return motivoConsulta;
    }

    public void setMotivoConsulta(String motivoConsulta) {
        this.motivoConsulta = motivoConsulta;
    }

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

    public Object getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Object idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Object getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Object idCliente) {
        this.idCliente = idCliente;
    }

    public Object getIdTipoProducto() {
        return idTipoProducto;
    }

    public void setIdTipoProducto(Object idTipoProducto) {
        this.idTipoProducto = idTipoProducto;
    }

    public String getFechaHoraCadenaFormateada() {
        return fechaHoraCadenaFormateada;
    }

    public void setFechaHoraCadenaFormateada(String fechaHoraCadenaFormateada) {
        this.fechaHoraCadenaFormateada = fechaHoraCadenaFormateada;
    }

    public Integer getIdFichaClinica() {
        return idFichaClinica;
    }

    public void setIdFichaClinica(Integer idFichaClinica) {
        this.idFichaClinica = idFichaClinica;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }
}
