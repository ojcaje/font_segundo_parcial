package com.example.font_segundo_parcial.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ObservacionReserva {

    @SerializedName("idReserva")
    @Expose
    private int idReserva;

    @SerializedName("observacion")
    @Expose
    private String observacion;

    @SerializedName("flagAsistio")
    @Expose
    private String flagAsistio;

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getFlagAsistio() {
        return flagAsistio;
    }

    public void setFlagAsistio(String flagAsistio) {
        this.flagAsistio = flagAsistio;
    }
}
