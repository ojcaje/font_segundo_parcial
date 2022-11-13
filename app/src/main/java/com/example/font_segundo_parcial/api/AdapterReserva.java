package com.example.font_segundo_parcial.api;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import com.example.font_segundo_parcial.R;
import com.example.font_segundo_parcial.ui.reservas.EditarReservaFragment;
import com.example.font_segundo_parcial.ui.reservas.NuevaReservaFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/* sirve para colocar el listado de reservas en el fragmento*/

public class AdapterReserva extends RecyclerView.Adapter<AdapterReserva.ViewHolder>{

    //public FragmentTransaction transaction; //
    public FragmentManager fragmentManager;
    private List<Reserva> listaReservas;

    public AdapterReserva(List<Reserva> listaReservas) {
        this.listaReservas = listaReservas;
    }

    public List<Reserva> getListaReservas() {
        return listaReservas;
    }

    public void setListaReservas(List<Reserva> listaReservas) {
        this.listaReservas = listaReservas;
    }

    public void setFragmentTransaction(FragmentManager f){
        this.fragmentManager = f;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_reserva_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tvPaciente.setText(listaReservas.get(position).getIdCliente().getNombreCompleto());
        holder.tvFisioterapeuta.setText(listaReservas.get(position).getIdEmpleado().getNombreCompleto());
        holder.tvObservacion.setText(listaReservas.get(position).getObservacion());
        holder.tvFecha.setText(listaReservas.get(position).getFecha());
        holder.tvAsistencia.setText(listaReservas.get(position).getFlagAsistio());
        holder.tvHorario.setText( listaReservas.get(position).getHoraInicioCadena() +" - "+ listaReservas.get(position).getHoraFinCadena());
        holder.tvEstado.setText(listaReservas.get(position).getFlagEstado());
        holder.btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(fragmentManager!= null) {
                    //String id = Integer.toString(listaReservas.get(holder.getAdapterPosition()).getIdReserva());
                    int id = listaReservas.get(holder.getAdapterPosition()).getIdReserva();
                    Fragment newFragment = new EditarReservaFragment();

                    Bundle args = new Bundle();
                    args.putInt("idReserva", id);
                    newFragment.setArguments(args);
                    //FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.frag_reservas, newFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaReservas.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvFecha;
        TextView tvHorario;
        TextView tvFisioterapeuta;
        TextView tvPaciente;
        TextView tvAsistencia;
        TextView tvObservacion;
        Button btnEditar;
        TextView tvEstado;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            tvFecha = itemView.findViewById(R.id.fechaReservaItem);
            tvHorario = itemView.findViewById(R.id.horarioReservaItem);
            tvFisioterapeuta = itemView.findViewById(R.id.fisioterapeutaReservaItem);
            tvPaciente = itemView.findViewById(R.id.pacienteReservaItem);
            tvAsistencia = itemView.findViewById(R.id.asistenciaReservaItem);
            tvObservacion = itemView.findViewById(R.id.observacionReservaItem);
            btnEditar = itemView.findViewById(R.id.btnEditarReserva);
            tvEstado= itemView.findViewById(R.id.EstadoReservaItem);



        }
    }

}
