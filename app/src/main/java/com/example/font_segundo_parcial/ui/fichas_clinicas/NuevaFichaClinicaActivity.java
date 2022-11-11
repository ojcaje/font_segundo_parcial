package com.example.font_segundo_parcial.ui.fichas_clinicas;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.font_segundo_parcial.R;
import com.example.font_segundo_parcial.api.Datos;
import com.example.font_segundo_parcial.api.Persona;
import com.example.font_segundo_parcial.api.RetrofitUtil;
import com.example.font_segundo_parcial.api.models.FichaClinica;
import com.example.font_segundo_parcial.api.models.FichaClinicaPost;
import com.example.font_segundo_parcial.api.models.Subcategoria;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NuevaFichaClinicaActivity
        extends AppCompatActivity
        implements ComunicaEditarFichaClinica {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_ficha_clinica);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Nueva ficha");

    }

    // this event will enable the back
    // function to the button on press
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // al recibir los datos de la ficha, hacemos post
    @Override
    public void datosFicha(Persona empleado, Persona paciente, Subcategoria subcategoria,
                           String motivo, String diagnostico, String observacion) {

        // crear la nueva ficha
        FichaClinicaPost nuevaFicha = new FichaClinicaPost();
        nuevaFicha.setDiagnostico(diagnostico);
        nuevaFicha.setIdCliente(paciente.getIdPersona());
        nuevaFicha.setIdEmpleado(empleado.getIdPersona());
        nuevaFicha.setIdTipoProducto(subcategoria);
        nuevaFicha.setMotivoConsulta(motivo);
        nuevaFicha.setObservacion(observacion);

        NuevaFichaClinicaActivity estaActividad = this;

        // hacer el post
        Call<Datos<FichaClinicaPost>> callApi= RetrofitUtil.getFichaClinicaService()
                .agregarFichaClinica(nuevaFicha);
        callApi.enqueue(new Callback<Datos<FichaClinicaPost>>() {
            @Override
            public void onResponse(Call<Datos<FichaClinicaPost>> call, Response<Datos<FichaClinicaPost>> response) {
                if(response.isSuccessful()){
                    Toast.makeText(estaActividad, "Éxito al crear ficha", Toast.LENGTH_LONG).show();
                    estaActividad.finish();
                }
                else {
                    Log.e("s", "Error al hacer post");
                    Toast.makeText(estaActividad, "Error al guardar", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Datos<FichaClinicaPost>> call, Throwable t) {
                Log.e("s", "Error al hacer post");
                Toast.makeText(estaActividad, "Error al guardar", Toast.LENGTH_LONG).show();
            }
        });
    }
}