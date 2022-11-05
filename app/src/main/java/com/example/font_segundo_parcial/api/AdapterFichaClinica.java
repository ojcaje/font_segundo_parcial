package com.example.font_segundo_parcial.api;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.font_segundo_parcial.R;
import com.example.font_segundo_parcial.api.models.FichaClinica;
import com.example.font_segundo_parcial.ui.fichas_clinicas.EditarFichaClinicaActivity;
import com.example.font_segundo_parcial.ui.fichas_clinicas.NuevaFichaClinicaActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class AdapterFichaClinica extends RecyclerView.Adapter<AdapterFichaClinica.ViewHolder> {

    private FichaClinica[] dsFichas;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ficha_clinica, parent, false);
        ViewHolder pvh=new ViewHolder(v);

        return pvh;
    }


    /**
     * Para rellenar los items del RecyclerView que muestra la lista de fichas clínicas
     */
    @Override
    public void onBindViewHolder(@NonNull AdapterFichaClinica.ViewHolder viewHolder, int position) {

        // setear los datos de la ficha clínica actual
        // TODO: dejar de usar JSONObject cuando ya estén las clases correspondientes a cada elemento
        try {
            viewHolder.tvCliente.setText(
                    new StringBuilder().append((new JSONObject((dsFichas[position].getIdCliente()
                            .toString().replaceAll(" +", ""))))
                            .get("nombre").toString()).append((new JSONObject((dsFichas[position].getIdCliente()
                            .toString().replaceAll(" +", ""))))
                            .get("idPersona").toString()).toString()
            );
            viewHolder.tvProfesional.setText(
                    (new JSONObject((dsFichas[position].getIdEmpleado()
                            .toString().replaceAll(" +",""))))
                            .get("nombre").toString()
            );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        viewHolder.tvSubcategoria.setText(dsFichas[position].getIdTipoProducto().getDescripcion());
        viewHolder.tvFecha.setText(dsFichas[position].getFechaHoraCadenaFormateada());

        // para lanzar la actividad de editar la ficha clínica actual
        viewHolder.btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(view.getContext(), EditarFichaClinicaActivity.class);
                view.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dsFichas.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvFecha;
        public TextView tvProfesional;
        public TextView tvCliente;
        public TextView tvSubcategoria;
        public Button btnEditar;

        public ViewHolder(View v) {
            super(v);
            tvFecha=v.findViewById(R.id.txtFecha);
            tvProfesional=v.findViewById(R.id.txtProfesional);
            tvCliente=v.findViewById(R.id.txtCliente);
            tvSubcategoria=v.findViewById(R.id.txtSubcategoria);
            btnEditar=v.findViewById(R.id.btnEditar);
        }
    }

    public AdapterFichaClinica(FichaClinica[] listaDeFichasClinicas){
        this.dsFichas=listaDeFichasClinicas;
    }
}
