package com.example.font_segundo_parcial.ui.persona;


import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.font_segundo_parcial.R;
import com.example.font_segundo_parcial.api.Datos;
import com.example.font_segundo_parcial.api.RetrofitUtil;
import com.example.font_segundo_parcial.api.Persona;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NuevaPersonaActivity
        extends AppCompatActivity
        implements ComunicaEditarPersona {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_persona);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Nuevo Paciente");

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

    // al recibir los datos de la persona, hacemos post
    @Override
    public void datosPersona(String nombre, String apellido, String telefono, String email, String ruc, String cedula, String tipoPersona, String fechaNacimiento) {


        // crear la nueva ficha
        Persona persona = new Persona();
        persona.setNombre(nombre);
        persona.setApellido(apellido);
        persona.setTelefono(telefono);
        persona.setRuc(ruc);
        persona.setCedula(cedula);
        persona.setTipoPersona(tipoPersona);
        persona.setEmail(email);

        try {
            // convertir string a fecha
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Date datofecha = sdf.parse(fechaNacimiento);

            SimpleDateFormat sdfTIME = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
            // convertir fecha a string
            String fecha = sdf.format(datofecha);
            persona.setFechaNacimiento(fecha);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        NuevaPersonaActivity estaActividad = this;

        // hacer el post
        Call<Datos<Persona>> callApi= RetrofitUtil.getPersonaService()
                .postPersona(persona);
        callApi.enqueue(new Callback<Datos<Persona>>() {
            @Override
            public void onResponse(Call<Datos<Persona>> call, Response<Datos<Persona>> response) {
                if(response.isSuccessful()){
                    Toast.makeText(estaActividad, "Éxito al crear el Paciente", Toast.LENGTH_LONG).show();
                    estaActividad.finish();
                }
                else {
                    Log.e("s", "Error al hacer post");
                    Toast.makeText(estaActividad, "Error al guardar", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Datos<Persona>> call, Throwable t) {
                Log.e("s", "Error al hacer post");
                Toast.makeText(estaActividad, "Error al guardar", Toast.LENGTH_LONG).show();
            }
        });
    }

}