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


public class SingleAdapterTurnos extends RecyclerView.Adapter<SingleAdapterTurnos.SingleViewHolder> {

    private Context context;
    private ArrayList<Reserva> listaTurnos;
    private ArrayList<Reserva> listaTurnosOriginal;
    private int checkedPosition = 0;


    public SingleAdapterTurnos(Context context, ArrayList<Reserva> listaTurnos) {

        this.context = context;
        this.listaTurnos = listaTurnos;
        this.listaTurnosOriginal = new ArrayList<>();
        this.listaTurnosOriginal.addAll(listaTurnos);

    }


    @NonNull
    @Override
    public SingleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_turno_item, parent, false);
        return new SingleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SingleViewHolder holder, int position) {
        holder.bind(listaTurnos.get(position));
    }

    @Override
    public int getItemCount() {
        return listaTurnos.size();
    }

    public void filtrado(final String nombre){

        int longitud = nombre.length();
        if(longitud ==0){
            listaTurnos.clear();
            listaTurnos.addAll(listaTurnosOriginal);
        } else {
            listaTurnos.clear();
            for(Reserva p: listaTurnosOriginal){
                if(p.getHoraInicio().toLowerCase().contains(nombre.toLowerCase())){
                    listaTurnos.add(p);
                }
            }
        }

        notifyDataSetChanged();

    }

    public ArrayList<Reserva> getListaTurnos() {
        return listaTurnos;
    }

    public void setListaTurnos(ArrayList<Reserva> listaTurnos) {
        this.listaTurnos = listaTurnos;
        notifyDataSetChanged();
    }

    public Reserva getSelected(){
        if(checkedPosition != -1){
            return listaTurnos.get(checkedPosition);
        }
        return null;
    }

    //clase interna para manejar cada item
    class SingleViewHolder extends RecyclerView.ViewHolder{
        TextView tvHorario;
        ImageView ivCheck;

        public SingleViewHolder(@NonNull View itemView){
            super(itemView);
            tvHorario = itemView.findViewById(R.id.horarioTurno);
            ivCheck = itemView.findViewById(R.id.checkTurno);
        }

        void bind(final Reserva reserva){

            if(checkedPosition == -1){
                ivCheck.setBackgroundColor(Color.parseColor("#f2f4f7"));
            }else{

                if(checkedPosition == getAdapterPosition()){
                    ivCheck.setBackgroundColor(Color.parseColor("#6A47D2"));
                }else{
                    ivCheck.setBackgroundColor(Color.parseColor("#f2f4f7"));
                }
            }

            String horario = reserva.getHoraInicioCadena() +" - "+ reserva.getHoraFinCadena();
            tvHorario.setText(horario);
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
