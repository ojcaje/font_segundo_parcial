package com.example.font_segundo_parcial.ui.fichas_clinicas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.font_segundo_parcial.R;
import com.example.font_segundo_parcial.api.Datos;
import com.example.font_segundo_parcial.api.RetrofitUtil;
import com.example.font_segundo_parcial.api.models.Categoria;
import com.example.font_segundo_parcial.api.models.Subcategoria;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditarFichaClinicaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditarFichaClinicaFragment extends Fragment {

    Categoria[] categorias;
    Subcategoria[] subcategorias;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EditarFichaClinicaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditarFichaClinicaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditarFichaClinicaFragment newInstance(String param1, String param2) {
        EditarFichaClinicaFragment fragment = new EditarFichaClinicaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_editar_ficha_clinica, container, false);

        // obtener los datos de categoría y subcategoría
        getDatosFiltros(vista);

        // para el botón de cancelar
        vista.findViewById(R.id.btnCancelarNuevaficha).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        // para el botón de guardar
        // le pasamos los datos del fragment a la actividad
        vista.findViewById(R.id.btnGuardarNuevaFicha).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // obtener los valores de los campos de la ficha
                String paciente = ((TextInputEditText)getActivity()
                        .findViewById(R.id.inputPacienteNuevaFicha)).getText().toString();
                String fisioterapeuta = ((TextInputEditText)getActivity()
                        .findViewById(R.id.inputEmpleadoNuevaFicha)).getText().toString();
                Subcategoria subcategoria = (Subcategoria) ((Spinner) getActivity()
                        .findViewById(R.id.spinnerSubcategoriaNuevaFicha)).getSelectedItem();
                String motivo = ((TextInputEditText)getActivity()
                        .findViewById(R.id.inputMotivoNuevaFicha)).getText().toString();
                String diagnostico = ((TextInputEditText)getActivity()
                        .findViewById(R.id.inputDiagnosticoNuevaFicha)).getText().toString();
                String observacion = ((TextInputEditText)getActivity()
                        .findViewById(R.id.inputObservacionNuevaFicha)).getText().toString();

                // enviar los datos al activity
                ((ComunicaEditarFichaClinica)getActivity()).datosFicha(
                        (!fisioterapeuta.equals("")) ? Integer.parseInt(fisioterapeuta) : null,
                        (!paciente.equals("")) ? Integer.parseInt(paciente) : null,
                        subcategoria,
                        motivo,
                        diagnostico,
                        observacion
                );
            }
        });

        return vista;
    }


    /**
     * Traer del back los datos necesarios para los campos de filtrado
     */
    private void getDatosFiltros(View vista) {

        // traer las categorías
        Call<Datos<Categoria>> callCategoria = RetrofitUtil.getCategoriaService()
                .obtenerCategorias();
        callCategoria.enqueue(new Callback<Datos<Categoria>>() {
            @Override
            public void onResponse(Call<Datos<Categoria>> call, Response<Datos<Categoria>> response) {
                categorias = response.body().getLista();
                setOnClickSpinnerCategorias(vista);
            }

            @Override
            public void onFailure(Call<Datos<Categoria>> call, Throwable t) {
                Log.e("s", t.toString());
            }
        });
    }


    /**
     * Para establecer la función que rellene el spinner categorías sólo cuando se cliquea en él
     * y también para que pueda cambiar la lista de subcategorías del spinner de subcategorías
     */
    private void setOnClickSpinnerCategorias(View vista) {

        // para rellenar el spinner sólo cuando se cliquea en él
        ((TextView) vista
                .findViewById(R.id.textViewSpinnerCategoriaNuevaFicha)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // rellenar y ejecutar un click en él
                        rellenarSpinnerCategorias().performClick();
                    }
                }
        );

        // para que el spinner pueda cambiar la lista de subcategorías del spinner de subcategorías
        ((Spinner) getActivity().findViewById(R.id.spinnerCategoriaNuevaFicha))
                .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        cargarSubcategorias();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                    }

                });
    }


    /**
     * para rellenar el spinner de las categorías
     */
    private Spinner rellenarSpinnerCategorias(){

        ArrayAdapter<Categoria> aaCategorias = new ArrayAdapter<Categoria>(getActivity(), android.R.layout.simple_spinner_item,
                categorias);
        aaCategorias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinnerCategorias = (Spinner) getActivity()
                .findViewById(R.id.spinnerCategoriaNuevaFicha);
        spinnerCategorias.setAdapter(aaCategorias);

        return spinnerCategorias;
    }

    /**
     * para traer las subcategorías que corresponden a la categoría seleccionada
     */
    private void cargarSubcategorias(){
        // ver qué categoría está seleccionada en el spinner de categoría
        Object idCategoria = ((Spinner) getActivity()
                .findViewById(R.id.spinnerCategoriaNuevaFicha)).getSelectedItem();

        // preparar el filtro

        JSONObject filtro = new JSONObject();
        // en caso de que esté seleccionado un elemento
        if (idCategoria!=null){
            try {
                filtro.accumulate("idCategoria",
                        new JSONObject("{idCategoria:"+
                                ((Categoria)idCategoria).getIdCategoria().toString() + "}"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // traer filtrado del back
        Call<Datos<Subcategoria>> callApi = RetrofitUtil.getSubcategoriaService()
                .obtenerSubcategorias(filtro);
        callApi.enqueue(new Callback<Datos<Subcategoria>>() {
            @Override
            public void onResponse(Call<Datos<Subcategoria>> call, Response<Datos<Subcategoria>> response) {
                subcategorias = response.body().getLista();

                // rellenar el spinner de subcategorías
                rellenarSpinnerSubcategorias();
            }

            @Override
            public void onFailure(Call<Datos<Subcategoria>> call, Throwable t) {
                Log.e("s", t.toString());
            }
        });
    }


    /**
     * Para cargar los datos en el spinner de subcategorías
     */
    private void rellenarSpinnerSubcategorias(){
        ArrayAdapter aaSubcategorias = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item,
                subcategorias);
        aaSubcategorias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinnerSubcategorias = (Spinner) getActivity()
                .findViewById(R.id.spinnerSubcategoriaNuevaFicha);
        spinnerSubcategorias.setAdapter(aaSubcategorias);
    }
}