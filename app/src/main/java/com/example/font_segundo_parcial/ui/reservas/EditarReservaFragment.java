package com.example.font_segundo_parcial.ui.reservas;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.font_segundo_parcial.R;
import com.example.font_segundo_parcial.api.Datos;
import com.example.font_segundo_parcial.api.ObservacionReserva;
import com.example.font_segundo_parcial.api.Persona;
import com.example.font_segundo_parcial.api.Reserva;
import com.example.font_segundo_parcial.api.RetrofitUtil;
import com.example.font_segundo_parcial.api.SingleAdapterTurnos;
import com.example.font_segundo_parcial.ui.persona.PersonaPickerActivity;
import com.google.android.material.textfield.TextInputEditText;

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
 * Use the {@link EditarReservaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditarReservaFragment extends Fragment {


    private TextView tvFisio;
    private TextView tvPaciente;
    private TextView tvFecha;
    private TextView tvHorario;
    private TextView tvEstado;
    private RadioButton asistio;
    private RadioButton noAsistio;
    private Button guardar;
    private Button cancelarReserva;
    private Button salir;
    private int idReserva;
    private Reserva reserva;
    private TextInputEditText obs;
    //String s=Integer.toString(i);


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EditarReservaFragment() {
        // Required empty public constructor
    }

    public static EditarReservaFragment newInstance(String param1, String param2) {
    //public static EditarReservaFragment newInstance(String param1) {

        EditarReservaFragment fragment = new EditarReservaFragment();
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

            try {
                mParam1 = getArguments().getString(ARG_PARAM1);
                mParam2 = getArguments().getString(ARG_PARAM2);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_editar_reserva, container, false);

        //obtener parametros
        Bundle args = getArguments(); //this.getArguments();
        idReserva = getArguments().getInt("idReserva");

        //aceptar edicion
        guardar = view.findViewById(R.id.btnGuardarEditReserva);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardar();
            }
        });

        //cancelar reserva
        cancelarReserva = view.findViewById(R.id.btnCancelarReserva);
        cancelarReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelarReserva();
            }
        });

        //salir
        /*salir = view.findViewById(R.id.btnSalirEditReserva);
        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salir();
            }
        });*/

        tvFisio = view.findViewById(R.id.nombreFisioEditReserva);
        tvPaciente = view.findViewById(R.id.nombrePacienteEditReserva);
        tvHorario =view.findViewById(R.id.horaEditReserva);
        tvFecha = view.findViewById(R.id.fechaEditReserva);
        asistio = view.findViewById(R.id.btnAsistio);
        noAsistio = view.findViewById(R.id.btnNoAsistio);
        obs = view.findViewById(R.id.observacionEditReserva);
        tvEstado= view.findViewById(R.id.estadoEditReserva);

        getReserva(getContext(), idReserva);

        //para que ir al fragmento anterior (reservas) y no al activity
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    getFragmentManager().popBackStack();
                    return true;
                }
                return false;
            }
        });


        return view;
    }

    public void setDatos(){
        try {
            tvFisio.setText(reserva.getIdEmpleado().getNombreCompleto());
            tvPaciente.setText(reserva.getIdCliente().getNombreCompleto());
            tvFecha.setText(reserva.getFecha());
            tvHorario.setText(reserva.getHoraInicioCadena() + " - "+ reserva.getHoraFinCadena());
            tvEstado.setText(reserva.getFlagEstado());

            if(reserva.getFlagAsistio()!= null){

                if(reserva.getFlagAsistio().equals("S")){
                    asistio.setChecked(true);
                }else {
                    noAsistio.setChecked(true);
                }
            }

            if(reserva.getObservacion()!= null) {
                obs.setText(reserva.getObservacion());
            }

        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getContext(), "Error inicio", Toast.LENGTH_SHORT);
        }
    }


    public void cancelarReserva(){

        if(reserva != null) {
            deleteReserva(reserva.getIdReserva());
        }

    }

    public void salir(){
        //getFragmentManager().popBackStackImmediate();
        getFragmentManager().popBackStack();
    }

    public void guardar(){

        try {

            ObservacionReserva observacionReserva = new ObservacionReserva();
            observacionReserva.setIdReserva(reserva.getIdReserva());
            observacionReserva.setObservacion(obs.getText().toString());

            if( asistio.isChecked()) {
                observacionReserva.setFlagAsistio("S");
            }else if (noAsistio.isChecked()){
                observacionReserva.setFlagAsistio("N");
            }

            //put
            putReserva(observacionReserva);

        }catch (Exception e){
            Toast.makeText(getContext(), "Error al reservar turno", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void putReserva(ObservacionReserva r){
        Call<Void> callApi = RetrofitUtil.getReservaService().actualizarReserva(r);

        callApi.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if(response.code() == 200){
                    Toast.makeText(getContext(), "Reserva modificada con éxito", Toast.LENGTH_SHORT).show();
                    salir();

                }
                else {
                    Log.e("s", "Error al hacer post");
                    Toast.makeText(getContext(), "No se pudo modificar la reserva", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("s", t.toString());
                t.printStackTrace();
            }
        });
    }


    public void getReserva(Context context, int idReserva) {
        Call<Reserva> callApi = RetrofitUtil.getReservaService()
                .getReserva(idReserva);
        callApi.enqueue(new Callback<Reserva>() {
            @Override
            public void onResponse(Call<Reserva> call, Response<Reserva> response) {

                if(response.isSuccessful()){
                    reserva = response.body();
                    setDatos();

                }else{
                    Toast.makeText(context, "No se pudo obtener la reserva",Toast.LENGTH_SHORT);
                }

            }

            @Override
            public void onFailure(Call<Reserva> call, Throwable t) {
                Log.e("s", t.toString());
            }
        });
    }

    public void deleteReserva(int id){
        Call<Void> callApi = RetrofitUtil.getReservaService()
                .deleteReserva(id);
        callApi.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if(response.isSuccessful()){
                    Toast.makeText(getContext(), "Reserva cancelada", Toast.LENGTH_SHORT).show();
                    salir();

                }
                else {
                    Log.e("s", "Error al hacer post");
                    Toast.makeText(getContext(), "No se pudo cancelar la reserva", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("s", t.toString());
            }
        });
    }

    public int getIdReserva(){
        return idReserva;
    }

    public void setIdReserva(int id){
        this.idReserva = id;
    }



}