package com.example.font_segundo_parcial.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Persona {
    @SerializedName("idPersona")
    @Expose
    private Integer idPersona;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("apellido")
    @Expose
    private String apellido;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("telefono")
    @Expose
    private String telefono;
    @SerializedName("ruc")
    @Expose
    private String ruc;
    @SerializedName("cedula")
    @Expose
    private String cedula;
    @SerializedName("tipoPersona")
    @Expose
    private String tipoPersona;
    @SerializedName("usuarioLogin")
    @Expose
    private Object usuarioLogin;
    @SerializedName("nombreCompleto")
    @Expose
    private String nombreCompleto;
    @SerializedName("fechaNacimiento")
    @Expose
    private String fechaNacimiento;

    public Integer getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Integer idPersona) {
        this.idPersona = idPersona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(String tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public Object getUsuarioLogin() {
        return usuarioLogin;
    }

    public void setUsuarioLogin(Object usuarioLogin) {
        this.usuarioLogin = usuarioLogin;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }


}
