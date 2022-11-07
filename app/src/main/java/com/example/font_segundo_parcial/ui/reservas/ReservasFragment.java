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
import android.widget.TextView;

import com.example.font_segundo_parcial.R;
import com.example.font_segundo_parcial.api.AdapterReserva;
import com.example.font_segundo_parcial.api.Datos;
import com.example.font_segundo_parcial.api.Reserva;
import com.example.font_segundo_parcial.api.RetrofitUtil;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
public class ReservasFragment extends Fragment {

    private RecyclerView rvReservas;
    private AdapterReserva adapterReserva;
    List<Reserva> reservas;
    private TextView fechaDesdeTxt;
    private Date fechaDesde;
    private TextView fechaHastaTxt;
    private Date fechaHasta;

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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReservasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReservasFragment newInstance(String param1, String param2) {
        ReservasFragment fragment = new ReservasFragment();
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

        //recycler view con la lista de reservas
        rvReservas = view.findViewById(R.id.listaReservas);
        rvReservas.setLayoutManager(new LinearLayoutManager(getContext()));
        obtenerReservas();
        
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
}