package com.example.font_segundo_parcial.ui.persona;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.font_segundo_parcial.R;
import com.example.font_segundo_parcial.api.AdapterPersona;
import com.example.font_segundo_parcial.api.Datos;
import com.example.font_segundo_parcial.api.RetrofitUtil;
import com.example.font_segundo_parcial.api.Persona;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PersonaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PersonaFragment extends Fragment {

    private RecyclerView rvPersonas;  // Para la lista de personas
    private AdapterPersona adapterPersona;

    Persona[] personas;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public PersonaFragment() {
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
    public static PersonaFragment newInstance(String param1, String param2) {
        PersonaFragment fragment = new PersonaFragment();
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

        // para que el botón fab pueda iniciar el activity de crear nueva persona
        getActivity().findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(), NuevaPersonaActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_personas, container, false);

        // RecyclerView para la lista de personas
        rvPersonas = vista.findViewById(R.id.listaPersonas);
        rvPersonas.setLayoutManager(new LinearLayoutManager(getContext()));

        // cargar los datos
        cargarPersonas();

        // establecer onClickListener para el botón de filtrar personas
        filtrarPersonasOnClickListener(vista);

        // Inflate the layout for this fragment
        return vista;
    }

    /**
     * Traer del back las personas
     */
    public void cargarPersonas() {
        Call<Datos<Persona>> callApi = RetrofitUtil.getPersonaService()
                .getPersonas(null);
        ejecutarGet(callApi);
    }


    /**
     * Ejecutar get en el back
     */
    public void ejecutarGet(Call<Datos<Persona>> callApi){
        callApi.enqueue(new Callback<Datos<Persona>>() {
            @Override
            public void onResponse(Call<Datos<Persona>> call, Response<Datos<Persona>> response) {

                personas = response.body().getLista();
                // poner en el RecyclerView
                adapterPersona = new AdapterPersona(personas);
                rvPersonas.setAdapter(adapterPersona);

                // log
                Log.i("s","imprimiendo personas");
                for (Persona persona: personas)
                {
                    Log.i("s", persona.getNombre()+" "+persona.getApellido());
                }

            }

            @Override
            public void onFailure(Call<Datos<Persona>> call, Throwable t) {
                Log.e("s", t.toString());
            }
        });
    }


    /**
     * Para establecer un OnClickListener para filtrar las personas
     * @param vista La vista actual
     */
    private void filtrarPersonasOnClickListener(View vista){
        Button botonBuscar = vista.findViewById(R.id.botonBuscarPersonas);
        botonBuscar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                filtrarPersonas();

                // ocultar teclado
                InputMethodManager miteclado=(InputMethodManager) getActivity()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                miteclado.hideSoftInputFromWindow(botonBuscar.getWindowToken(),0);
            }
        });
    }


    /**
     * Filtrar las personas por los campos de búsqueda
     */
    private void filtrarPersonas(){

        // obtener los valores de los campos de búsqueda
        String textoInputNombre= ((TextInputEditText)getActivity()
                .findViewById(R.id.inputNombreFiltroPersona)).getText().toString();
        String textoInputApellido = ((TextInputEditText)getActivity()
                .findViewById(R.id.inputApellidoFiltroPersona)).getText().toString();


        // generar el filtro para enviar al back
        JSONObject filtro = new JSONObject();
        try {

            if (!textoInputNombre.equals("")){
                filtro.accumulate("idCliente", textoInputNombre);
            }

            if (!textoInputApellido.equals("")){
                filtro.accumulate("idEmpleado", textoInputApellido);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Toast.makeText(getActivity(),"Filtrando...",Toast.LENGTH_SHORT).show();

        // ejecutar la búsqueda
        Call<Datos<Persona>> callApi = RetrofitUtil.getPersonaService()
                    .getPersonas(filtro);
        ejecutarGet(callApi);

    }
}