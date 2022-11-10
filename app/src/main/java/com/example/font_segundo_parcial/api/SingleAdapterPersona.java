package com.example.font_segundo_parcial.api;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.font_segundo_parcial.R;

import java.util.ArrayList;


public class SingleAdapterPersona extends RecyclerView.Adapter<SingleAdapterPersona.SingleViewHolder> {

    private Context context;
    private ArrayList<Persona> listaPersonas;
    private ArrayList<Persona> listaPersonasOriginal;
    private int checkedPosition = 0;


    public SingleAdapterPersona(Context context, ArrayList<Persona> listaPersonas) {

        this.context = context;
        this.listaPersonas = listaPersonas;
        this.listaPersonasOriginal = new ArrayList<>();
        this.listaPersonasOriginal.addAll(listaPersonas);

    }

    /*public void setListaPersonasOriginal(ArrayList<Persona> listaPersonasOriginal) {
        this.listaPersonasOriginal = new ArrayList<>();
        this.listaPersonasOriginal = listaPersonasOriginal;
        notifyDataSetChanged();
    }*/

    @NonNull
    @Override
    public SingleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_persona_item, parent, false);
        return new SingleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SingleViewHolder holder, int position) {
        holder.bind(listaPersonas.get(position));
    }

    @Override
    public int getItemCount() {
        return listaPersonas.size();
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
        notifyDataSetChanged();
    }

    public Persona getSelected(){
        if(checkedPosition != -1){
            return listaPersonas.get(checkedPosition);
        }
        return null;
    }

    //clase interna para manejar cada item
    class SingleViewHolder extends RecyclerView.ViewHolder{
        TextView tvNombre;
        ImageView ivCheck;

        public SingleViewHolder(@NonNull View itemView){
            super(itemView);
            tvNombre = itemView.findViewById(R.id.nombreItemPersona);
            ivCheck = itemView.findViewById(R.id.checkPersona);
        }

        void bind(final Persona persona){

            if(checkedPosition == -1){
                ivCheck.setBackgroundColor(Color.parseColor("#f2f4f7"));
            }else{

                if(checkedPosition == getAdapterPosition()){
                    ivCheck.setBackgroundColor(Color.parseColor("#6A47D2"));
                }else{
                    ivCheck.setBackgroundColor(Color.parseColor("#f2f4f7"));
                }
            }

            tvNombre.setText(persona.getNombreCompleto());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ivCheck.setBackgroundColor(Color.parseColor("#6A47D2"));

                    if(checkedPosition != getAdapterPosition()){
                        notifyItemChanged(checkedPosition);
                        checkedPosition = getAdapterPosition();
                    }
                }
            });
        }
    }

}
