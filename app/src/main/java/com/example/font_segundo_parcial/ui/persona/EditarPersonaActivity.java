package com.example.font_segundo_parcial.ui.persona;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import com.example.font_segundo_parcial.R;
import com.example.font_segundo_parcial.api.RetrofitUtil;
import com.example.font_segundo_parcial.api.Persona;
import com.example.font_segundo_parcial.databinding.FragmentEditarPersonaBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditarPersonaActivity extends AppCompatActivity {

    com.example.font_segundo_parcial.api.Persona persona;

    Persona p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_persona);

        // establecer y configurar el toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbarEditarPersona);
        setSupportActionBar(myToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Editar Paciente");

        // deshabilitar los campos que no se pueden editar en este caso
        View fragmento = findViewById(R.id.activityeditarPersonaFragment);
//        fragmento.findViewById(R.id.inputNombreNuevaPersona).setFocusable(false);
//        fragmento.findViewById(R.id.inputApellidoNuevaPersona).setFocusable(false);
//        fragmento.findViewById(R.id.inputTelefonoNuevaPersona).setFocusable(false);
//        fragmento.findViewById(R.id.inputEmailNuevaPersona).setFocusable(false);
//        fragmento.findViewById(R.id.inputRucNuevaPersona).setFocusable(false);
//        fragmento.findViewById(R.id.inputCedulaNuevaPersona).setFocusable(false);
//
//        fragmento.findViewById(R.id.text_TipoPersonaNuevaPersona).setVisibility(View.GONE);
//        fragmento.findViewById(R.id.spinnerTipoPersonaNuevaPersona).setFocusable(false);
//
//        fragmento.findViewById(R.id.inputfechaNacimientoNuevaPersona).setFocusable(false);

        // cambiar nombre
        ((TextView)fragmento.findViewById(R.id.textoTitulo)).setText("Editar Paciente");

        // para el data binding
        FragmentEditarPersonaBinding binding = DataBindingUtil
                .bind(fragmento);
        EditarPersonaActivity estaActividad = this;

        // traer del back la PERSONA
        Toast.makeText(this, "Cargando datos...", Toast.LENGTH_SHORT).show();
        Call<Persona> callApi = RetrofitUtil.getPersonaService()
                .getPersona(getIntent().getIntExtra("idPersona",-1));
        callApi.enqueue(new Callback<Persona>() {
            @Override
            public void onResponse(Call<Persona> call, Response<Persona> response) {
                persona = response.body();

                p = persona;
                // cargar los datos de la PERSONA en los campos correspondientes
                binding.setPersona(persona);

                // para rellenar los spinners

                String vector [] ={"FISICA","JURIDICA"};
                ArrayAdapter<String> aaTipoPersona = new ArrayAdapter<>(estaActividad, android.R.layout.simple_spinner_item, vector);

                aaTipoPersona.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                ((Spinner)fragmento.findViewById(R.id.spinnerTipoPersonaNuevaPersona))
                        .setAdapter(aaTipoPersona);
            }
            @Override
            public void onFailure(Call<Persona> call, Throwable t) {
                Log.e("PERSONA: ", t.toString());
            }
        });

        // para hacer el post en caso de querer guardar
        fragmento.findViewById(R.id.btnGuardarNuevaPersona).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Persona personaActualizada = new Persona();
                        personaActualizada.setIdPersona(persona.getIdPersona());
                        personaActualizada.setNombre(persona.getNombre());
                        personaActualizada.setApellido(persona.getApellido());
                        personaActualizada.setTelefono(persona.getTelefono());
                        personaActualizada.setRuc(persona.getRuc());
                        personaActualizada.setCedula(persona.getCedula());
                        personaActualizada.setTipoPersona(persona.getTipoPersona());
                        personaActualizada.setEmail(persona.getEmail());
                        try {

                            String fecha_ = persona.getFechaNacimiento();
                            if (fecha_!= null){
                                personaActualizada.setFechaNacimiento(fecha_.concat(" 00:00:00"));
                            }else{
                                Toast.makeText(estaActividad, "Fecha no valida", Toast.LENGTH_LONG).show();
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        Call<Void> callApi= RetrofitUtil.getPersonaService()
                                .putPersona(personaActualizada);
                        callApi.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if(response.isSuccessful()){
                                    Toast.makeText(estaActividad, "Éxito al guardar cambios", Toast.LENGTH_LONG).show();
                                    estaActividad.finish();
                                }
                                else {
                                    Log.e("s", "Error al hacer put");
                                    Toast.makeText(estaActividad, "Error al guardar", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Log.e("PERSONA: ", t.toString());
                            }
                        });
                    }
                }
        );


        //eliminar persona
        fragmento.findViewById(R.id.btnChau).setVisibility(fragmento.VISIBLE);
        fragmento.findViewById(R.id.btnChau).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(p!=null){
                    int idPersona = p.getIdPersona();
                    Log.i("ELIMINAR", "Se procede a eliminar la persona");
                    //eliminar(idPersona);

                    Call<Void> callApi= RetrofitUtil.getPersonaService()
                            .deletePersona(idPersona);
                    callApi.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if(response.isSuccessful()){
                                Toast.makeText(estaActividad, "Éxito al eliminar", Toast.LENGTH_LONG).show();
                                estaActividad.finish();
                            }
                            else {
                                Log.e("s", "Error al hacer delete");
                                Toast.makeText(estaActividad, "Error al eliminar, registro en uso", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.e("PERSONA: ", t.toString());
                        }
                    });


                }
            }
        });

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

}