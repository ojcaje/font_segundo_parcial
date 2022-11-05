package com.example.font_segundo_parcial.ui.fichas_clinicas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.font_segundo_parcial.databinding.ActivityEditarFichaClinicaBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.ui.AppBarConfiguration;


import com.example.font_segundo_parcial.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class EditarFichaClinicaActivity extends AppCompatActivity {

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