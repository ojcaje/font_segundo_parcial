package com.example.font_segundo_parcial.ui.reservas;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.font_segundo_parcial.R;
import com.example.font_segundo_parcial.api.AdapterPersona;
import com.example.font_segundo_parcial.api.AdapterReserva;
import com.example.font_segundo_parcial.api.Datos;
import com.example.font_segundo_parcial.api.Persona;
import com.example.font_segundo_parcial.api.Reserva;
import com.example.font_segundo_parcial.api.RetrofitUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReservasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
//public class ReservasFragment extends Fragment implements SearchView.OnQueryTextListener {
public class ReservasFragment extends Fragment {

    private RecyclerView rvReservas;
    private AdapterReserva adapterReserva;
    List<Reserva> reservas;
    private TextView fechaDesdeTxt;
    private Date fechaDesde;
    private TextView fechaHastaTxt;
    private Date fechaHasta;
    private TextView pacienteTxt;
    private Persona paciente;
    private TextView fisioterapeutaTxt;
    private Persona fisioterapeuta;
    ArrayList<Persona> fisioterapeutas;
    ArrayList<Persona> pacientes;
    private AdapterPersona adapterFisio;
    private AdapterPersona adapterPaciente;
    private RecyclerView rvFisio;
    private RecyclerView rvPaciente;
    private SearchView buscarFisio;
    private SearchView buscarPaciente;
    public static ReservasFragment fragment;



    private static final String TAG = "Reservas fragment";


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ReservasFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ReservasFragment newInstance(String param1, String param2) {

        if(fragment == null){
            fragment = new ReservasFragment();

        }
        //ReservasFragment fragment = new ReservasFragment();
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
        View view = inflater.inflate(R.layout.fragment_reservas, container, false);

        //listado de reservas
        rvReservas = view.findViewById(R.id.listaReservas);
        rvReservas.setLayoutManager(new LinearLayoutManager(getContext()));
        obtenerReservas();

        //listado de fisioterapeutas
        rvFisio = view.findViewById(R.id.listadoFisioterapeutasReservas);
        rvFisio.setLayoutManager(new LinearLayoutManager(getContext()));

        //listado de pacientes
        rvPaciente = view.findViewById(R.id.listadoPacientesReservas);
        rvPaciente.setLayoutManager(new LinearLayoutManager(getContext()));

        try {
            obtenerFisioterapeutas();
            obtenerPacientes();

        } catch (JSONException e) {
            e.printStackTrace();
        }


        //datepicker
        fechaDesdeTxt = view.findViewById(R.id.fechaDesdeFiltradoReservas);
        fechaDesdeTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDatePicker(fechaDesdeTxt);
            }
        });

        fechaHastaTxt = view.findViewById(R.id.fechaHastaFiltradoReservas);
        fechaHastaTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDatePicker(fechaHastaTxt);
            }
        });

        //busqueda de fisioterapeuta
        buscarFisio = view.findViewById(R.id.fisioterapeutaFiltradoReservas);
        buscarFisio.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                if(s != null) {

                    if(adapterFisio != null) {
                        adapterFisio.filtrado(s);
                    }

                }
                return false;
            }
        });


        //busqueda de paciente
        buscarPaciente = view.findViewById(R.id.pacienteFiltradoReservas);
        buscarPaciente.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(s != null) {

                    if(adapterPaciente != null) {
                        adapterPaciente.filtrado(s);
                    }

                }
                return false;
            }
        });


        return view;

    }


    private void openDatePicker(TextView fechaTxt) {
        Calendar calendar = Calendar.getInstance();

        int YEAR = calendar.get(Calendar.YEAR);
        int MONTH = calendar.get(Calendar.MONTH);
        int DATE = calendar.get(Calendar.DATE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                month = month + 1;
                String strFecha = dayOfMonth + "/" + month + "/" + year;
                fechaTxt.setText(strFecha);
            }
        } , YEAR , MONTH , DATE);
        datePickerDialog.show();
    }


    private Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy/MM/dd").parse(date);
        } catch (ParseException e) {
            return null;
        }
    }


    public void obtenerReservas() {
        Call<Datos<Reserva>> callApi = RetrofitUtil.getReservaService()
                .getReservas(new JSONObject());
        callApi.enqueue(new Callback<Datos<Reserva>>() {
            @Override
            public void onResponse(Call<Datos<Reserva>> call, Response<Datos<Reserva>> response) {

                reservas = Arrays.asList(response.body().getLista());
                adapterReserva = new AdapterReserva(reservas);
                rvReservas.setAdapter(adapterReserva);

                // log
                Log.i("s","reservas");
                for (Reserva r: reservas)
                {
                    Log.i("s", r.getFechaCadena());
                }

            }

            @Override
            public void onFailure(Call<Datos<Reserva>> call, Throwable t) {
                Log.e("s", t.toString());
            }
        });
    }

    public void obtenerFisioterapeutas() throws JSONException {

        JSONObject params = new JSONObject();
        params.put("soloUsuariosDelSistema", true);

        Call<Datos<Persona>> callApi = RetrofitUtil.getPersonaService().getPersonas(params);
        callApi.enqueue(new Callback<Datos<Persona>>() {
            @Override
            public void onResponse(Call<Datos<Persona>> call, Response<Datos<Persona>> response) {

                fisioterapeutas = new ArrayList<>(Arrays.asList(response.body().getLista()));
                adapterFisio = new AdapterPersona(fisioterapeutas);
                rvFisio.setAdapter(adapterFisio);

                // log
                Log.i("s","fisioterapeutas");
                for (Persona r: fisioterapeutas)
                {
                    Log.i("s", r.getNombreCompleto());
                }

            }

            @Override
            public void onFailure(Call<Datos<Persona>> call, Throwable t) {
                Log.e("s", t.toString());
            }
        });
    }

    public void obtenerPacientes() throws JSONException {

        JSONObject params = new JSONObject();
        params.put("soloUsuariosDelSistema", false);

        Call<Datos<Persona>> callApi = RetrofitUtil.getPersonaService().getPersonas(params);
        callApi.enqueue(new Callback<Datos<Persona>>() {
            @Override
            public void onResponse(Call<Datos<Persona>> call, Response<Datos<Persona>> response) {

                pacientes =new ArrayList<>( Arrays.asList( response.body().getLista() ));
                adapterPaciente = new AdapterPersona(pacientes);
                rvPaciente.setAdapter(adapterPaciente);

                // log
                Log.i("s","pacientes");
                for (Persona r: pacientes)
                {
                    Log.i("s", r.getNombreCompleto());
                }

            }

            @Override
            public void onFailure(Call<Datos<Persona>> call, Throwable t) {
                Log.e("s", t.toString());
            }
        });
    }

    /*@Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if(s != null) {

            /*
            Al ir a otro fragmento y regresar a este, por alguna razon ninguno de los atributos del fragmento
            actual estan inicializados y este segmento de codigo es ejecutado lanzando error, por lo que se coloca
            la siguiente condicion para evitar su ejecucion antes de que el fragmento este completamente incializado
            */

    /*        if(adapterFisio != null) {
                adapterFisio.filtrado(s);
            }

        }
        return false;
    }*/
}