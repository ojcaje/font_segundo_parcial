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
import com.example.font_segundo_parcial.ui.persona.EditarPersonaActivity;


public class AdapterPersona extends RecyclerView.Adapter<AdapterPersona.ViewHolder> {

    private Persona[] dspersona;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_persona, parent, false);
        ViewHolder pvh=new ViewHolder(v);

        return pvh;
    }


    /**
     * Para rellenar los items del RecyclerView que muestra la lista de personas
     */
    @Override
    public void onBindViewHolder(@NonNull AdapterPersona.ViewHolder viewHolder, int position) {

        // setear los datos de la persona actual
        // TODO: dejar de usar JSONObject cuando ya estén las clases correspondientes a cada elemento

        viewHolder.tvNombreyApellido.setText(
            new StringBuilder().append(
                    dspersona[position].getIdPersona()+" - "+dspersona[position].getNombre()+" "+dspersona[position].getApellido())
            );
        viewHolder.tvTelefono.setText(dspersona[position].getTelefono());
        viewHolder.tvEmail.setText(dspersona[position].getEmail());
        viewHolder.tvRucCedula.setText(
                new StringBuilder().append(
                        dspersona[position].getCedula()+"/"+dspersona[position].getRuc())
        );
        viewHolder.tvTipoPersona.setText(dspersona[position].getTipoPersona());
        viewHolder.tvFechaNacimiento.setText(dspersona[position].getFechaNacimiento());

        Integer idPersona = dspersona[position].getIdPersona();


        // para lanzar la actividad de editar la persona actual
        viewHolder.btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(view.getContext(), EditarPersonaActivity.class);
                i.putExtra("idPersona", idPersona);
                view.getContext().startActivity(i);
            }
        });

        // para lanzar la actividad de eliminar la persona actual
        viewHolder.btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(view.getContext(), EditarPersonaActivity.class);
                i.putExtra("idPersona", idPersona);
                view.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dspersona.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNombreyApellido;
        public TextView tvTelefono;
        public TextView tvEmail;
        public TextView tvRucCedula;
        public TextView tvTipoPersona;
        public TextView tvFechaNacimiento;
        public Button btnEditar;
        public Button btnEliminar;

        public ViewHolder(View v) {
            super(v);
            tvNombreyApellido=v.findViewById(R.id.txtNombreyApellido);
            tvTelefono=v.findViewById(R.id.txtTelefono);
            tvEmail=v.findViewById(R.id.txtEmail);
            tvRucCedula=v.findViewById(R.id.txtRucCedula);
            tvTipoPersona=v.findViewById(R.id.txtTipoPersona);
            tvFechaNacimiento=v.findViewById(R.id.txtFechaNacimiento);
            btnEditar=v.findViewById(R.id.btnEditarPersona);
            btnEliminar=v.findViewById(R.id.btnEliminarPersona);
        }
    }

    public AdapterPersona(Persona[] listaDeFichasClinicas){
        this.dspersona=listaDeFichasClinicas;
    }
}
