package com.example.font_segundo_parcial.ui.persona;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.font_segundo_parcial.R;
import com.example.font_segundo_parcial.api.Persona;
import com.example.font_segundo_parcial.api.RetrofitUtil;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PersonaItemFragmentLISTA#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PersonaItemFragmentLISTA extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PersonaItemFragmentLISTA() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PersonaItemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PersonaItemFragmentLISTA newInstance(String param1, String param2) {
        PersonaItemFragmentLISTA fragment = new PersonaItemFragmentLISTA();
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
        View vista = inflater.inflate(R.layout.item_persona, container, false);

        // para el botón de eliminar persona
        vista.findViewById(R.id.btnEliminar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String eliminar =((TextView) getActivity()
                        .findViewById(R.id.txtNombreyApellido)).getText().toString().split(" ")[0];

                // Eliminar del back la PERSONA
                Toast.makeText(null, "Eliminando paciente...", Toast.LENGTH_SHORT).show();
                Call<Persona> callApi = RetrofitUtil.getPersonaService()
                        .deletePersona(Integer.getInteger(eliminar));
                callApi.enqueue(new Callback<Persona>() {
                    @Override
                    public void onResponse(Call<Persona> call, Response<Persona> response) {
                        Toast.makeText(null, "Paciente eliminado exitosamente...", Toast.LENGTH_SHORT).show();

                    }
                    @Override
                    public void onFailure(Call<Persona> call, Throwable t) {
                        Log.e("PERSONA: ", t.toString());
                    }
                });

            }
        });

        return vista;
    }
}