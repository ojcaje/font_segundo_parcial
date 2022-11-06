package com.example.font_segundo_parcial.ui.fichas_clinicas;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.font_segundo_parcial.api.AdapterFichaClinica;
import com.example.font_segundo_parcial.api.Datos;
import com.example.font_segundo_parcial.api.RetrofitUtil;
import com.example.font_segundo_parcial.api.models.Categoria;
import com.example.font_segundo_parcial.api.models.FichaClinica;
import com.example.font_segundo_parcial.api.models.Subcategoria;
import com.example.font_segundo_parcial.databinding.ActivityEditarFichaClinicaBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.ui.AppBarConfiguration;


import com.example.font_segundo_parcial.R;
import com.example.font_segundo_parcial.databinding.FragmentEditarFichaClinicaBinding;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditarFichaClinicaActivity extends AppCompatActivity {

    FichaClinica ficha; // la ficha que se está editando actualmente

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_ficha_clinica);

        // establecer y configurar el toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbarEditarFichaClinica);
        setSupportActionBar(myToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Editar ficha");

        // deshabilitar los campos que no se pueden editar en este caso
        View fragmento = findViewById(R.id.activityeditarFichaClinicaFragment);
        fragmento.findViewById(R.id.inputPacienteNuevaFicha).setFocusable(false);
        fragmento.findViewById(R.id.inputEmpleadoNuevaFicha).setFocusable(false);
        fragmento.findViewById(R.id.spinnerCategoriaNuevaFicha).setFocusable(false);
        fragmento.findViewById(R.id.textViewSpinnerCategoriaNuevaFicha).setVisibility(View.GONE);
        fragmento.findViewById(R.id.spinnerSubcategoriaNuevaFicha).setFocusable(false);
        fragmento.findViewById(R.id.inputMotivoNuevaFicha).setFocusable(false);
        fragmento.findViewById(R.id.inputDiagnosticoNuevaFicha).setFocusable(false);

        // cambiar nombre
        ((TextView)fragmento.findViewById(R.id.textoTitulo)).setText("Editar Ficha Clínica");

        // para el data binding
        FragmentEditarFichaClinicaBinding binding = DataBindingUtil
                .bind(fragmento);
        EditarFichaClinicaActivity estaActividad = this;

        // traer del back la ficha clínica
        Toast.makeText(this, "Cargando datos...", Toast.LENGTH_SHORT).show();
        Call<FichaClinica> callApi = RetrofitUtil.getFichaClinicaService()
                .obtenerFichaClinica(getIntent().getIntExtra("idFicha",-1));
        callApi.enqueue(new Callback<FichaClinica>() {
            @Override
            public void onResponse(Call<FichaClinica> call, Response<FichaClinica> response) {
                ficha = response.body();

                // cargar los datos de la ficha en los campos correspondientes
                binding.setFicha(ficha);


                // para rellenar los spinners

                ArrayAdapter<Categoria> aaCategorias = new ArrayAdapter<Categoria>(estaActividad, android.R.layout.simple_spinner_item,
                        new Categoria[]  {ficha.getIdTipoProducto().getIdCategoria()});
                ArrayAdapter<Subcategoria> aaSubcategorias = new ArrayAdapter<Subcategoria>(estaActividad, android.R.layout.simple_spinner_item,
                        new Subcategoria[] {ficha.getIdTipoProducto()});

                aaCategorias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                aaSubcategorias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                ((Spinner)fragmento.findViewById(R.id.spinnerCategoriaNuevaFicha))
                        .setAdapter(aaCategorias);
                ((Spinner)fragmento.findViewById(R.id.spinnerSubcategoriaNuevaFicha))
                        .setAdapter(aaSubcategorias);
            }

            @Override
            public void onFailure(Call<FichaClinica> call, Throwable t) {
                Log.e("s", t.toString());
            }
        });


        // para hacer el post en caso de querer guardar
        fragmento.findViewById(R.id.btnGuardarNuevaFicha).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FichaClinica fichaActualizada = new FichaClinica();
                        fichaActualizada.setIdFichaClinica(ficha.getIdFichaClinica());
                        fichaActualizada.setObservacion(ficha.getObservacion());

                        Call<FichaClinica> callApi= RetrofitUtil.getFichaClinicaService()
                                .actualizarFichaClinica(fichaActualizada);
                        callApi.enqueue(new Callback<FichaClinica>() {
                            @Override
                            public void onResponse(Call<FichaClinica> call, Response<FichaClinica> response) {
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
                            public void onFailure(Call<FichaClinica> call, Throwable t) {
                                Log.e("s", t.toString());
                            }
                        });
                    }
                }
        );
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