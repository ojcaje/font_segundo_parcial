package com.example.font_segundo_parcial.ui.persona;

/**
 * Para comunicar el fragment con una actividad
 */
public interface ComunicaEditarPersona {
    public void datosPersona(String nombre, String apellido,
                           String telefono, String email, String ruc, String cedula,
                             String tipoPersona, String fechaNacimiento);
}
