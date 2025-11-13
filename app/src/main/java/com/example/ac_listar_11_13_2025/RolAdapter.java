package com.example.ac_listar_11_13_2025;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RolAdapter extends RecyclerView.Adapter<RolAdapter.RolVH> {
    public interface OnRolActionListener {
        void onEdit(Rol rol);
        void onDelete(Rol rol);
    }

    private final List<Rol> data = new ArrayList<>();
    private final OnRolActionListener listener;

    public RolAdapter(OnRolActionListener listener) {
        this.listener = listener;
    }

    public void submitList(List<Rol> list) {
        data.clear();
        if (list != null) data.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RolVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rol, parent, false);
        return new RolVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RolVH holder, int position) {
        Rol item = data.get(position);
        holder.tvNombre.setText(item.getNombre());
        holder.tvDescripcion.setText(item.getDescripcion());
        holder.btnEditar.setOnClickListener(v -> {
            if (listener != null) listener.onEdit(item);
        });
        holder.btnEliminar.setOnClickListener(v -> {
            if (listener != null) listener.onDelete(item);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class RolVH extends RecyclerView.ViewHolder {
        TextView tvNombre;
        TextView tvDescripcion;
        AppCompatButton btnEditar;
        AppCompatButton btnEliminar;

        public RolVH(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }
}
