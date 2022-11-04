package com.example.font_segundo_parcial.ui.fichas_clinicas;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.font_segundo_parcial.R;
import com.example.font_segundo_parcial.api.AdapterFichaClinica;
import com.example.font_segundo_parcial.api.Datos;
import com.example.font_segundo_parcial.api.FichaClinica;
import com.example.font_segundo_parcial.api.RetrofitUtil;
import com.example.font_segundo_parcial.api.models.Categoria;
import com.example.font_segundo_parcial.api.models.Subcategoria;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FichasClinicasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FichasClinicasFragment extends Fragment {

    private RecyclerView rvFichasClinicas;  // Para la lista de fichas clínicas
    private AdapterFichaClinica adapterFichaClinica;

    FichaClinica[] fichasClinicas;
    Categoria[] categorias;
    Subcategoria[] subcategorias;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public FichasClinicasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FichasClinicasFragment.
     */
    public static FichasClinicasFragment newInstance(String param1, String param2) {
        FichasClinicasFragment fragment = new FichasClinicasFragment();
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
        View vista = inflater.inflate(R.layout.fragment_fichas_clinicas, container, false);

        // RecyclerView para la lista de fichas clínicas
        rvFichasClinicas = vista.findViewById(R.id.listaFichasClinicas);
        rvFichasClinicas.setLayoutManager(new LinearLayoutManager(getContext()));

        // cargar los datos
        cargarFichasClinicas();
        getDatosFiltros(vista);

        // establecer onClickListener para el botón de filtrar fichas
        filtrarFichasOnClickListener(vista);

        // Inflate the layout for this fragment
        return vista;
    }

    /**
     * Traer del back las fichas clíncas
     */
    public void cargarFichasClinicas() {
        Call<Datos<FichaClinica>> callApi = RetrofitUtil.getFichaClinicaService()
                .obtenerFichasClinicas(null);
        ejecutarGet(callApi);
    }


    /**
     * Ejecutar get en el back
     */
    public void ejecutarGet(Call<Datos<FichaClinica>> callApi){
        callApi.enqueue(new Callback<Datos<FichaClinica>>() {
            @Override
            public void onResponse(Call<Datos<FichaClinica>> call, Response<Datos<FichaClinica>> response) {

                fichasClinicas = response.body().getLista();
                // poner en el RecyclerView
                adapterFichaClinica = new AdapterFichaClinica(fichasClinicas);
                rvFichasClinicas.setAdapter(adapterFichaClinica);

                // log
                Log.i("s","imprimiendo fichas clínicas");
                for (FichaClinica ficha: fichasClinicas)
                {
                    Log.i("s", ficha.getFechaHora());
                }

            }

            @Override
            public void onFailure(Call<Datos<FichaClinica>> call, Throwable t) {
                Log.e("s", t.toString());
            }
        });
    }


    /**
     * Para establecer un OnClickListener para filtrar las fichas clínicas
     * @param vista La vista actual
     */
    private void filtrarFichasOnClickListener(View vista){
        Button botonBuscar = vista.findViewById(R.id.botonBuscarFichas);
        botonBuscar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                filtrarFichas();

                // ocultar teclado
                InputMethodManager miteclado=(InputMethodManager) getActivity()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                miteclado.hideSoftInputFromWindow(botonBuscar.getWindowToken(),0);
            }
        });
    }


    /**
     * Filtrar las fichas por los campos de búsqueda
     */
    private void filtrarFichas(){

        // obtener los valores de los campos de búsqueda
        String textoInputPaciente = ((TextInputEditText)getActivity()
                .findViewById(R.id.inputPacienteFiltroFichaClinica)).getText().toString();
        String textoInputFisioterapeuta = ((TextInputEditText)getActivity()
                .findViewById(R.id.inputFisioterapeutaFiltroFicha)).getText().toString();
        String textoFechaDesde = ((EditText)getActivity()
                .findViewById(R.id.inputFechaDesdeFiltroFichas)).getText().toString();
        String textoFechaHasta = ((EditText)getActivity()
                .findViewById(R.id.inputFechaHastaFiltroFicha)).getText().toString();
        Subcategoria subcategoria = (Subcategoria) ((Spinner) getActivity()
                .findViewById(R.id.spinnerSubcategoriaFiltroFicha)).getSelectedItem();

        // generar el filtro para enviar al back
        JSONObject filtro = new JSONObject();
        try {

            if (!textoInputPaciente.equals("")){
                filtro.accumulate("idCliente",
                        new JSONObject("{idPersona:"+ textoInputPaciente + "}"));
            }

            if (!textoInputFisioterapeuta.equals("")){
                filtro.accumulate("idEmpleado",
                        new JSONObject("{idPersona:"+ textoInputFisioterapeuta + "}"));
            }

            if (!textoFechaDesde.equals("")){
                filtro.accumulate("fechaDesdeCadena",
                        textoFechaDesde.replace("/", ""));
            }

            if (!textoFechaHasta.equals("")){
                filtro.accumulate("fechaHastaCadena",
                        textoFechaHasta.replace("/", ""));
            }

            if (subcategoria!=null){
                filtro.accumulate("idTipoProducto",
                        new JSONObject("{idTipoProducto:"+
                                subcategoria.getIdCategoria().getIdCategoria().toString() + "}"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Toast.makeText(getActivity(),"Filtrando...",Toast.LENGTH_SHORT).show();

        // ejecutar la búsqueda
        Call<Datos<FichaClinica>> callApi = RetrofitUtil.getFichaClinicaService()
                    .obtenerFichasClinicas(filtro);
        ejecutarGet(callApi);
    }


    /**
     * Traer del back los datos necesarios para los campos de filtrado
     */
    private void getDatosFiltros(View vista){

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
    private void setOnClickSpinnerCategorias(View vista){

        // para rellenar el spinner sólo cuando se cliquea en él
        ((TextView) vista
                .findViewById(R.id.txtspinnerCategoriaFiltroFicha)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // rellenar y ejecutar un click en él
                        rellenarSpinnerCategorias().performClick();
                    }
                }
        );

        // para que el spinner pueda cambiar la lista de subcategorías del spinner de subcategorías
        ((Spinner) getActivity().findViewById(R.id.spinnerCategoriaFiltroFicha))
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
                .findViewById(R.id.spinnerCategoriaFiltroFicha);
        spinnerCategorias.setAdapter(aaCategorias);

        return spinnerCategorias;
    }


    /**
     * para traer las subcategorías que corresponden a la categoría seleccionada
     */
    private void cargarSubcategorias(){
        // ver qué categoría está seleccionada en el spinner de categoría
        Object idCategoria = ((Spinner) getActivity()
                .findViewById(R.id.spinnerCategoriaFiltroFicha)).getSelectedItem();

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
                .findViewById(R.id.spinnerSubcategoriaFiltroFicha);
        spinnerSubcategorias.setAdapter(aaSubcategorias);
    }
}