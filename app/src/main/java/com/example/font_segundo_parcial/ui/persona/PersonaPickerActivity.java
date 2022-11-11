package com.example.font_segundo_parcial.ui.persona;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.font_segundo_parcial.api.Datos;
import com.example.font_segundo_parcial.api.Persona;
import com.example.font_segundo_parcial.api.RetrofitUtil;
import com.example.font_segundo_parcial.api.SingleAdapterPersona;
import com.example.font_segundo_parcial.databinding.ActivityPersonaPickerBinding;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.font_segundo_parcial.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonaPickerActivity extends AppCompatActivity {

    // la lista de personas actualmente
    ArrayList<Persona> listaPersonas;

    // para el recyclerview
    private SingleAdapterPersona adapterPersona;
    private ActivityPersonaPickerBinding binding;
    private RecyclerView rvPersonas;

    private SearchView buscarPersona;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persona_picker);

        binding = ActivityPersonaPickerBinding.inflate(getLayoutInflater());

        // configurar el recyclerview
        rvPersonas = findViewById(R.id.rvActivityPersonaPicker);
        rvPersonas.setLayoutManager(new LinearLayoutManager(this));

        // cargar las personas
        try {
            getPersonas(getIntent().
                    getBooleanExtra("soloUsuariosDelSistema", false));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // configurar el diseño
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Elegir persona");

        // configurar el filtrado
        buscarPersona = findViewById(R.id.filtradoPersonaPicker);
        buscarPersona.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                if(s != null) {

                    if(adapterPersona != null) {
                        adapterPersona.filtrado(s);
                    }

                }
                return false;
            }
        });

    }

    // this event will enable the back
    // function to the button on press
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                enviarPersonaElegida();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Trae del back la lista de personas filtradas dependiendo del tipo, y las carga en el
     * array listaPersonas
     * @throws JSONException
     */
    public void getPersonas(Boolean soloUsuariosDelSistema) throws JSONException {
        JSONObject params = new JSONObject();
        if(soloUsuariosDelSistema){
            params.put("soloUsuariosDelSistema", true);
        }

        Activity estaActividad = this;
        Call<Datos<Persona>> callApi = RetrofitUtil.getPersonaService().getPersonas(params);
        callApi.enqueue(new Callback<Datos<Persona>>() {
            @Override
            public void onResponse(Call<Datos<Persona>> call, Response<Datos<Persona>> response) {
                listaPersonas = new ArrayList<>(Arrays.asList(response.body().getLista()));
                adapterPersona = new SingleAdapterPersona(estaActividad, listaPersonas);
                rvPersonas.setAdapter(adapterPersona);
            }

            @Override
            public void onFailure(Call<Datos<Persona>> call, Throwable t) {
                Log.e("s", t.toString());
            }
        });
    }


    /**
     * Envía la persona elegida a la actividad anterior y finaliza esta actividad
     */
    public void enviarPersonaElegida(){
        Intent i = new Intent();
        i.putExtra("persona", adapterPersona.getSelected());
        i.putExtra("soloUsuariosDelSistema",
                getIntent().getBooleanExtra("soloUsuariosDelSistema", false));
        setResult(RESULT_OK, i);
        finish();
    }
}