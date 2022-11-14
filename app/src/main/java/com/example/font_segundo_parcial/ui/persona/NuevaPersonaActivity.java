package com.example.font_segundo_parcial.ui.persona;


import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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
        setContentView(R.layout.activity_nueva_persona);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Nuevo Paciente");


        // para rellenar los spinners
        View fragmento = findViewById(R.id.editarPersonaFragment);
        NuevaPersonaActivity estaActividad = this;

        String vector [] ={"FISICA","JURIDICA"};
        ArrayAdapter<String> aaTipoPersona = new ArrayAdapter<>(estaActividad, android.R.layout.simple_spinner_item, vector);

        aaTipoPersona.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ((Spinner)fragmento.findViewById(R.id.spinnerTipoPersonaNuevaPersona))
                .setAdapter(aaTipoPersona);

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
    public void datosPersona(String nombre, String apellido,
                                         String telefono, String email, String ruc, String cedula,
                                         String tipoPersona, String fechaNacimiento){

        // crear la nueva persona
        Persona persona = new Persona();
        persona.setNombre(nombre);
        persona.setApellido(apellido);
        persona.setTelefono(telefono);
        persona.setRuc(ruc);
        persona.setCedula(cedula);
        persona.setTipoPersona(tipoPersona);
        persona.setEmail(email);

        fechaNacimiento = fechaNacimiento.replace("/","-").concat(" 00:00:00");
        persona.setFechaNacimiento(fechaNacimiento);


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