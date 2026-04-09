package com.telopresto.app.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.telopresto.app.R;
import com.telopresto.app.model.PrestamoConDetalles;

import java.util.ArrayList;
import java.util.List;

public class PrestamoAdapter extends RecyclerView.Adapter<PrestamoAdapter.PrestamoViewHolder> {

    private List<PrestamoConDetalles> prestamos = new ArrayList<>();
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(PrestamoConDetalles prestamo);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setPrestamos(List<PrestamoConDetalles> prestamos) {
        this.prestamos = prestamos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PrestamoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_prestamo, parent, false);
        return new PrestamoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PrestamoViewHolder holder, int position) {
        PrestamoConDetalles item = prestamos.get(position);
        holder.textNombrePersona.setText(item.persona != null ? item.persona.nombre : "—");
        holder.textObjeto.setText(item.prestamo.objeto);
        holder.textFecha.setText("Prestado: " + item.prestamo.fechaPrestamo);
        holder.textEstado.setText(item.prestamo.estado);
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(item);
        });
    }

    @Override
    public int getItemCount() {
        return prestamos.size();
    }

    static class PrestamoViewHolder extends RecyclerView.ViewHolder {
        TextView textNombrePersona, textObjeto, textFecha, textEstado;

        PrestamoViewHolder(@NonNull View itemView) {
            super(itemView);
            textNombrePersona = itemView.findViewById(R.id.textNombrePersona);
            textObjeto = itemView.findViewById(R.id.textObjeto);
            textFecha = itemView.findViewById(R.id.textFecha);
            textEstado = itemView.findViewById(R.id.textEstado);
        }
    }
}