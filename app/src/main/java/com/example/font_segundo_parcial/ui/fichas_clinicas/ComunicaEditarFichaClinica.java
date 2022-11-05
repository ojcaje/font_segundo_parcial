package com.example.font_segundo_parcial.ui.fichas_clinicas;

import com.example.font_segundo_parcial.api.models.Subcategoria;

/**
 * Para comunicar el fragment con una actividad
 */
public interface ComunicaEditarFichaClinica {
    public void datosFicha(Integer empleado, Integer paciente, Subcategoria subcategoria,
                           String motivo, String diagnostico, String observacion);
}
