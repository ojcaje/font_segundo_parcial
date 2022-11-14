package com.example.font_segundo_parcial.ui.persona;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.font_segundo_parcial.R;
import com.example.font_segundo_parcial.api.Datos;
import com.example.font_segundo_parcial.api.RetrofitUtil;
import com.example.font_segundo_parcial.api.models.Categoria;
import com.example.font_segundo_parcial.ui.persona.ComunicaEditarPersona;
import com.google.android.material.textfield.TextInputEditText;
import com.example.font_segundo_parcial.api.Persona;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditarPersonaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditarPersonaFragment extends Fragment {

    String vector [] ={"FISICA","JURIDICA"};
    private TextView fechaNacimiento;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EditarPersonaFragment() {
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
    public static EditarPersonaFragment newInstance(String param1, String param2) {
        EditarPersonaFragment fragment = new EditarPersonaFragment();
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
        View vista = inflater.inflate(R.layout.fragment_editar_persona, container, false);

        //datepicker
        fechaNacimiento = vista.findViewById(R.id.inputfechaNacimientoNuevaPersona);
        fechaNacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDatePicker(fechaNacimiento);
            }
        });

        //setOnClickSpinnerTipoPersona(vista);

        // para el botón de cancelar
        vista.findViewById(R.id.btnCancelarNuevaPersona).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        // para el botón de guardar
        // le pasamos los datos del fragment a la actividad
        vista.findViewById(R.id.btnGuardarNuevaPersona).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // obtener los valores de los campos de la ficha
                String nombre = ((TextInputEditText)getActivity()
                        .findViewById(R.id.inputNombreNuevaPersona)).getText().toString();
                String apellido = ((TextInputEditText)getActivity()
                        .findViewById(R.id.inputApellidoNuevaPersona)).getText().toString();
                String telefono = ((TextInputEditText)getActivity()
                        .findViewById(R.id.inputTelefonoNuevaPersona)).getText().toString();
                String cedula = ((TextInputEditText)getActivity()
                        .findViewById(R.id.inputCedulaNuevaPersona)).getText().toString();
                String ruc = ((TextInputEditText)getActivity()
                        .findViewById(R.id.inputRucNuevaPersona)).getText().toString();
                String tipoPersona = ((String)((Spinner)getActivity()
                        .findViewById(R.id.spinnerTipoPersonaNuevaPersona)).getSelectedItem());
                String fechaNacimiento = ((EditText) getActivity()
                        .findViewById(R.id.inputfechaNacimientoNuevaPersona)).getText().toString();
                String email = ((TextInputEditText)getActivity()
                        .findViewById(R.id.inputEmailNuevaPersona)).getText().toString();

                // enviar los datos al activity
                ((ComunicaEditarPersona)getActivity()).datosPersona(
                        nombre,
                        apellido,
                        telefono,
                        email,
                        ruc,
                        cedula,
                        tipoPersona,
                        fechaNacimiento

                );
            }
        });

        return vista;
    }

    /**
     * Para establecer la función que rellene el spinner TipoPersona sólo cuando se cliquea en él.
     */
    private void setOnClickSpinnerTipoPersona(View vista) {

        // para rellenar el spinner sólo cuando se cliquea en él
        ((TextView) vista
                .findViewById(R.id.textViewSpinnerTipoPersonaNuevaPersona)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // rellenar y ejecutar un click en él
                        rellenarSpinnerTipoPersona();
                    }
                }
        );
    }

    /**
     * Para cargar los datos en el spinner de Tipo Persona
     */
    private void rellenarSpinnerTipoPersona(){
        ArrayAdapter<String> aaTipoPersona = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item,
                vector);
        aaTipoPersona.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            Spinner spinnerTipoPersona = (Spinner) getActivity()
                .findViewById(R.id.spinnerTipoPersonaNuevaPersona);
        spinnerTipoPersona.setAdapter(aaTipoPersona);
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