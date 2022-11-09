package com.example.font_segundo_parcial.api;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.font_segundo_parcial.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/* sirve para colocar el listado de Personas en el fragmento*/

public class AdapterPersona extends RecyclerView.Adapter<AdapterPersona.ViewHolder>{

    private ArrayList<Persona> listaPersonas;
    private ArrayList<Persona> listaPersonasOriginal;
    //private int checkedPosition = -1;
    //TextView tvNombre;
    //ImageView ivCheck;

    public AdapterPersona(ArrayList<Persona> listaPersonas) {

        this.listaPersonas = listaPersonas;
        this.listaPersonasOriginal = new ArrayList<>();
        this.listaPersonasOriginal.addAll(listaPersonas);

    }

    public void filtrado(final String nombre){

        int longitud = nombre.length();
        if(longitud ==0){
            listaPersonas.clear();
            listaPersonas.addAll(listaPersonasOriginal);
        } else {
            listaPersonas.clear();
            for(Persona p: listaPersonasOriginal){
                if(p.getNombreCompleto().toLowerCase().contains(nombre.toLowerCase())){

                    listaPersonas.add(p);
                }
            }
        }

        notifyDataSetChanged();

    }

    public ArrayList<Persona> getListaPersonas() {
        return listaPersonas;
    }

    public void setListaPersonas(ArrayList<Persona> listaPersonas) {
        this.listaPersonas = listaPersonas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_persona_item, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvNombre;
        //ImageView ivCheck;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            tvNombre = itemView.findViewById(R.id.nombreItemPersona);
            //ivCheck = itemView.findViewById(R.id.checkPersona);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tvNombre.setText(listaPersonas.get(position).getNombreCompleto());

        //// nuevo
        /*if(checkedPosition == position){

        }*/

    }

    @Override
    public int getItemCount() {
        return listaPersonas.size();
    }





}
