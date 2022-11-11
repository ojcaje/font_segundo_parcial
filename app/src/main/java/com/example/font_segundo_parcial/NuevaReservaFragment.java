package com.example.font_segundo_parcial;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.font_segundo_parcial.api.Datos;
import com.example.font_segundo_parcial.api.Persona;
import com.example.font_segundo_parcial.api.Reserva;
import com.example.font_segundo_parcial.api.RetrofitUtil;
import com.example.font_segundo_parcial.api.SingleAdapterPersona;
import com.example.font_segundo_parcial.api.SingleAdapterTurnos;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NuevaReservaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NuevaReservaFragment extends Fragment {

    ArrayList<Reserva> turnos;
    private TextView tvHorario;
    ArrayList<Persona> fisioterapeutas;
    private SingleAdapterPersona adapterFisio;
    private SearchView buscarFisio;
    private RecyclerView rvFisio;
    private Button filtrar;
    private TextView tvFecha;
    private SingleAdapterTurnos adapterTurnos;
    private RecyclerView rvTurnos;
    private Button reservarTurno;
    private Reserva turnoSeleccionado;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NuevaReservaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NuevaReservaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NuevaReservaFragment newInstance(String param1, String param2) {
        NuevaReservaFragment fragment = new NuevaReservaFragment();
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

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nueva_reserva, container, false);

        //listado de fisioterapeutas
        rvFisio = view.findViewById(R.id.listadoFisioterapeutasNuevaReserva);
        rvFisio.setLayoutManager(new LinearLayoutManager(getContext()));

        try {
            obtenerFisioterapeutas(getContext());

        }catch (Exception e){
            e.printStackTrace();
        }
        //busqueda de fisioterapeuta
        buscarFisio = view.findViewById(R.id.fisioterapeutaNuevaReserva);
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

        //datepicker
        tvFecha = view.findViewById(R.id.fechaTurno);
        tvFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDatePicker(tvFecha);
            }
        });

        //btnFiltrarTurnos
        filtrar = view.findViewById(R.id.btnFiltrarTurnos);
        filtrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filtrado();
            }
        });


        //turnos
        rvTurnos = view.findViewById(R.id.listadoDeTurnos);
        rvTurnos.setLayoutManager(new LinearLayoutManager(getContext()));

        reservarTurno = view.findViewById(R.id.btnReservarTurno);
        reservarTurno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reservar();
            }
        });


        return view;
    }

    public void reservar(){

        turnoSeleccionado = adapterTurnos.getSelected();
        Persona fisio = adapterFisio.getSelected();
        Persona paciente = adapterFisio.getSelected(); /////CAMBIAR/////
        String fecha = tvFecha.getText().toString().replace("/", "");

        try {
            if (turnoSeleccionado != null && !fecha.equals("")) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("fechaCadena", fecha);
                jsonObject.accumulate("horaInicioCadena", turnoSeleccionado.getHoraInicioCadena());
                jsonObject.accumulate("horaFinCadena", turnoSeleccionado.getHoraFinCadena());
                jsonObject.accumulate("idEmpleado", "{idPersona:"+ fisio.getIdPersona() + "}" );
                jsonObject.accumulate("idCliente", "{idPersona:"+ paciente.getIdPersona() + "}");

                //post
                obtenerTurnos(getContext(), fisio.getIdPersona(), fecha);

            } else {
                Toast.makeText(getContext(), "No se puede reservar turno", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(getContext(), "Error al reservar turno", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    private void filtrado(){

        String fecha = tvFecha.getText().toString();
        int idPaciente, idFisio;
        idFisio = adapterFisio.getSelected().getIdPersona();

        if(!fecha.equals("") && idFisio!=0) {
            obtenerTurnos(getContext(), idFisio, fecha.replace("/", ""));
        }else{
            Toast.makeText(getContext(), "Ingresar fecha", Toast.LENGTH_SHORT).show();

        }

    }


    public void obtenerTurnos(Context context, int idFisio, String fecha) {
        Call<Reserva[]> callApi = RetrofitUtil.getReservaService()
                .getTurnos(idFisio, fecha, "S");
        callApi.enqueue(new Callback<Reserva[]>() {
            @Override
            public void onResponse(Call<Reserva[]> call, Response<Reserva[]> response) {

                try {
                    turnos = new ArrayList<>(Arrays.asList(response.body()));
                    adapterTurnos = new SingleAdapterTurnos(context, turnos);
                    rvTurnos.setAdapter(adapterTurnos);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<Reserva[]> call, Throwable t) {
                Log.e("s", t.toString());
            }
        });
    }

    public void obtenerFisioterapeutas(Context context) throws JSONException {

        JSONObject params = new JSONObject();
        params.put("soloUsuariosDelSistema", true);

        Call<Datos<Persona>> callApi = RetrofitUtil.getPersonaService().getPersonas(params);
        callApi.enqueue(new Callback<Datos<Persona>>() {
            @Override
            public void onResponse(Call<Datos<Persona>> call, Response<Datos<Persona>> response) {

                try{
                    fisioterapeutas = new ArrayList<>(Arrays.asList(response.body().getLista()));
                    adapterFisio = new SingleAdapterPersona(context, fisioterapeutas);
                    rvFisio.setAdapter(adapterFisio);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<Datos<Persona>> call, Throwable t) {
                Log.e("s", t.toString());
            }
        });
    }


    private void openDatePicker(TextView fechaTxt) {
        Calendar calendar = Calendar.getInstance();

        int YEAR = calendar.get(Calendar.YEAR);
        int MONTH = calendar.get(Calendar.MONTH);
        int DATE = calendar.get(Calendar.DATE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);

                SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
                String strFecha = format.format(newDate.getTime());

                fechaTxt.setText(strFecha);
            }
        } , YEAR , MONTH , DATE);
        datePickerDialog.show();
        datePickerDialog.getDatePicker().setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);

    }
}