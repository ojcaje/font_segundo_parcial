package com.example.font_segundo_parcial.ui.reservas;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.font_segundo_parcial.R;
import com.example.font_segundo_parcial.api.Datos;
import com.example.font_segundo_parcial.api.Persona;
import com.example.font_segundo_parcial.api.Reserva;
import com.example.font_segundo_parcial.api.RetrofitUtil;
import com.example.font_segundo_parcial.api.SingleAdapterTurnos;
import com.example.font_segundo_parcial.ui.persona.PersonaPickerActivity;
import com.google.android.material.textfield.TextInputEditText;

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
    private Persona fisio;
    private Persona paciente;
    private TextInputEditText fisioTxt;
    private TextInputEditText pacienteTxt;
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



        //datepicker
        tvFecha = view.findViewById(R.id.fechaTurno);
        tvFecha.setFocusable(false);
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

        // para que los botones de filtrar clientes y pacientes funcionen
        filtrarFisioterapeutasOnClickListener(view);
        filtrarClientesOnClickListener(view);


        return view;
    }

    public void reservar(){

        try {
            turnoSeleccionado = adapterTurnos.getSelected();
            String fecha = tvFecha.getText().toString().replace("/", "");

            if (turnoSeleccionado != null && !fecha.equals("") && fisio!=null && paciente!=null) {
                /*JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("fechaCadena", fecha);
                jsonObject.accumulate("horaInicioCadena", turnoSeleccionado.getHoraInicioCadena());
                jsonObject.accumulate("horaFinCadena", turnoSeleccionado.getHoraFinCadena());
                jsonObject.accumulate("idEmpleado", "{idPersona:"+ fisio.getIdPersona() + "}" );
                jsonObject.accumulate("idCliente", "{idPersona:"+ paciente.getIdPersona() + "}");*/

                Persona paciente_ = new Persona();
                Persona fisio_ = new Persona();
                paciente_.setIdPersona(paciente.getIdPersona());
                fisio_.setIdPersona(fisio.getIdPersona());


                Reserva r = new Reserva();
                r.setFechaCadena(fecha);
                r.setHoraInicioCadena(turnoSeleccionado.getHoraInicioCadena());
                r.setHoraFinCadena(turnoSeleccionado.getHoraFinCadena());
                r.setIdEmpleado(fisio_);
                r.setIdCliente(paciente_);

                postReserva(r);
                obtenerTurnos(getContext(), fisio.getIdPersona(), fecha);

            } else {
                Toast.makeText(getContext(), "No se puede reservar turno", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(getContext(), "Error al reservar turno", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void postReserva(Reserva r){
        Call<Datos<Reserva>> callApi = RetrofitUtil.getReservaService()
                .agregarReserva(r);
        callApi.enqueue(new Callback<Datos<Reserva>>() {
            @Override
            public void onResponse(Call<Datos<Reserva>> call, Response<Datos<Reserva>> response) {

                if(response.isSuccessful()){
                    Toast.makeText(getContext(), "Reserva agendada con éxito", Toast.LENGTH_LONG).show();
                }
                else {
                    Log.e("s", "Error al hacer post");
                    Toast.makeText(getContext(), "Error al reservar turno", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<Datos<Reserva>> call, Throwable t) {
                Log.e("s", t.toString());
            }
        });
    }


    private void filtrado(){

        String fecha = tvFecha.getText().toString();
        int idFisio=0;
        if(fisio !=null){
            idFisio = fisio.getIdPersona();
        }

        if(!fecha.equals("") && idFisio!=0) {
            obtenerTurnos(getContext(), idFisio, fecha.replace("/", ""));
        }else{
            Toast.makeText(getContext(), "Ingresar fecha y fisioterapeuta", Toast.LENGTH_SHORT).show();

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

    /**
     * Para los botones de elegir fisioterapeuta o paciente
     */
    public void elegirCliente(Boolean esFisioterapeuta){
        Intent i = new Intent(getActivity(), PersonaPickerActivity.class);
        i.putExtra("soloUsuariosDelSistema", esFisioterapeuta);
        startActivityForResult(i, 1);
    }

    /**
     * Para recibir de la otra actividad la persona elegida
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            // si se recibe un fisioterapeuta
            if(data.getBooleanExtra("soloUsuariosDelSistema", false)){
                fisio = (Persona)data.getSerializableExtra("persona");
                TextInputEditText textoFisio = getView()
                        .findViewById(R.id.fisioterapeutaNuevaReserva);
                textoFisio.setText(fisio.getNombreCompleto());
            } else {
                paciente = (Persona) data.getSerializableExtra("persona");
                TextInputEditText textoPaciente = getView()
                        .findViewById(R.id.pacienteNuevaReserva);
                textoPaciente.setText(paciente.getNombreCompleto());
            }
        }
    }

    /**
     * Para establecer un OnClickListener para filtrar los fisioterapeutas
     * @param vista La vista actual
     */
    private void filtrarFisioterapeutasOnClickListener(View vista){
        TextInputEditText textoFisio = vista.findViewById(R.id.fisioterapeutaNuevaReserva);
        textoFisio.setFocusable(false);
        textoFisio.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                elegirCliente(true);
            }
        });
    }

    /**
     * Para establecer un OnClickListener para filtrar los clientes
     * @param vista La vista actual
     */
    private void filtrarClientesOnClickListener(View vista){
        TextInputEditText inputCliente = vista.findViewById(R.id.pacienteNuevaReserva);
        inputCliente.setFocusable(false);
        inputCliente.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                elegirCliente(false);
            }
        });
    }
}