package com.example.font_segundo_parcial.ui.reservas;

import static android.app.Activity.RESULT_OK;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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

import com.example.font_segundo_parcial.R;
import com.example.font_segundo_parcial.api.AdapterReserva;
import com.example.font_segundo_parcial.api.Datos;
import com.example.font_segundo_parcial.api.Persona;
import com.example.font_segundo_parcial.api.Reserva;
import com.example.font_segundo_parcial.api.RetrofitUtil;
import com.example.font_segundo_parcial.api.SingleAdapterPersona;
import com.example.font_segundo_parcial.ui.persona.PersonaPickerActivity;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

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

    ArrayList<Reserva> reservas;
    private TextView fechaDesdeTxt;
    private TextView fechaHastaTxt;
    private Persona paciente;
    private Persona fisio;
    private TextInputEditText pacienteTxt;
    private TextInputEditText fisioTxt;

    private Button filtrar;
    public static ReservasFragment fragment;
    private Button nuevaReserva;




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
        obtenerReservas(new JSONObject());


        //datepicker
        fechaDesdeTxt = view.findViewById(R.id.fechaDesdeFiltradoReservas);
        fechaDesdeTxt.setFocusable(false);
        fechaDesdeTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDatePicker(fechaDesdeTxt);
            }
        });

        fechaHastaTxt = view.findViewById(R.id.fechaHastaFiltradoReservas);
        fechaHastaTxt.setFocusable(false);
        fechaHastaTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDatePicker(fechaHastaTxt);
            }
        });


        filtrarFisioterapeutasOnClickListener(view);
        filtrarClientesOnClickListener(view);

        //boton filtrado
        filtrar = view.findViewById(R.id.filtrarReservas);
        filtrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filtrado();
            }
        });


        //btn nueva reserva
        nuevaReserva = view.findViewById(R.id.btnNuevaReserva);
        nuevaReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuevaReserva(view);
            }
        });


        return view;

    }

    public void nuevaReserva(View view){
        Fragment newFragment = new NuevaReservaFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment_content_main, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    private void filtrado(){

        JSONObject filtro = new JSONObject();

        try {

            String fechaDesde = fechaDesdeTxt.getText().toString();
            String fechaHasta = fechaHastaTxt.getText().toString();
            int idPaciente, idFisio;


            if (!fechaDesde.equals("")) {
                filtro.accumulate("fechaDesdeCadena", fechaDesde.replace("/", ""));
            }

            if (!fechaHasta.equals("")) {
                filtro.accumulate("fechaHastaCadena", fechaHasta.replace("/", ""));
            }

            if(paciente != null){
                idPaciente = paciente.getIdPersona();
                filtro.accumulate("idCliente",
                        new JSONObject("{idPersona:" + idPaciente + "}")
                );
            }

            if(fisio != null){
                idFisio = fisio.getIdPersona();
                filtro.accumulate("idEmpleado",
                        new JSONObject("{idPersona:" + idFisio + "}")
                );
            }


        }catch (Exception e){
            showToast("Error");
            e.printStackTrace();
        }

        reservas.clear();
        obtenerReservas(filtro);


    }

    private void showToast(String msg){
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
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


    public void obtenerReservas(JSONObject filtro) {
        Call<Datos<Reserva>> callApi = RetrofitUtil.getReservaService()
                .getReservas(filtro);
        callApi.enqueue(new Callback<Datos<Reserva>>() {
            @Override
            public void onResponse(Call<Datos<Reserva>> call, Response<Datos<Reserva>> response) {

                reservas = new ArrayList<>(Arrays.asList(response.body().getLista()));
                adapterReserva = new AdapterReserva(reservas);
                rvReservas.setAdapter(adapterReserva);

            }

            @Override
            public void onFailure(Call<Datos<Reserva>> call, Throwable t) {
                Log.e("s", t.toString());
            }
        });
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
                        .findViewById(R.id.fisioterapeutaFiltradoReservas);
                textoFisio.setText(fisio.getNombreCompleto());
            } else {
                paciente = (Persona) data.getSerializableExtra("persona");
                TextInputEditText textoPaciente = getView()
                        .findViewById(R.id.pacienteFiltradoReservas);
                textoPaciente.setText(paciente.getNombreCompleto());
            }
        }
    }

    /**
     * Para establecer un OnClickListener para filtrar los fisioterapeutas
     * @param vista La vista actual
     */
    private void filtrarFisioterapeutasOnClickListener(View vista){
        TextInputEditText textoFisio = vista.findViewById(R.id.fisioterapeutaFiltradoReservas);
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
        TextInputEditText inputCliente = vista.findViewById(R.id.pacienteFiltradoReservas);
        inputCliente.setFocusable(false);
        inputCliente.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                elegirCliente(false);
            }
        });
    }

}