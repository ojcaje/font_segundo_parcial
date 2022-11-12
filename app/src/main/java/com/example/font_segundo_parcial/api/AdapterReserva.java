package com.example.font_segundo_parcial.api;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.font_segundo_parcial.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/* sirve para colocar el listado de reservas en el fragmento*/

public class AdapterReserva extends RecyclerView.Adapter<AdapterReserva.ViewHolder>{

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

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            tvFecha = itemView.findViewById(R.id.fechaReservaItem);
            tvHorario = itemView.findViewById(R.id.horarioReservaItem);
            tvFisioterapeuta = itemView.findViewById(R.id.fisioterapeutaReservaItem);
            tvPaciente = itemView.findViewById(R.id.pacienteReservaItem);
            tvAsistencia = itemView.findViewById(R.id.asistenciaReservaItem);
            tvObservacion = itemView.findViewById(R.id.observacionReservaItem);



        }
    }

}
