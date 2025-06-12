package com.example.proyectouja;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BuscarAlumnoAdapter extends RecyclerView.Adapter<BuscarAlumnoAdapter.ViewHolder> {

    private List<Alumno> listaAlumnos;
    private final List<Alumno> listaAlumnosCompleta;
    private final Context context;
    private final OnPerfilClickListener listener;

    public interface OnPerfilClickListener {
        void onPerfilClick(Alumno alumno);
    }

    public BuscarAlumnoAdapter(List<Alumno> listaAlumnos, Context context, OnPerfilClickListener listener) {
        this.listaAlumnos = new ArrayList<>(listaAlumnos);
        this.listaAlumnosCompleta = new ArrayList<>(listaAlumnos); // Copia para filtro
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_buscar_alumno, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Alumno alumno = listaAlumnos.get(position);

        String nombre = alumno.getNombre() != null ? alumno.getNombre() : "Sin nombre";
        String correo = alumno.getCorreo1() != null ? alumno.getCorreo1() : "Sin correo";

        holder.nombre.setText(nombre);
        holder.email.setText(correo);

        String inicial = !nombre.isEmpty() ? String.valueOf(nombre.charAt(0)).toUpperCase() : "?";
        holder.avatar.setText(inicial);

        holder.btnPerfil.setOnClickListener(v -> {
            if (listener != null) {
                listener.onPerfilClick(alumno);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaAlumnos.size();
    }

    public void filtrar(String texto) {
        listaAlumnos.clear();
        if (texto == null || texto.trim().isEmpty()) {
            listaAlumnos.addAll(listaAlumnosCompleta);
        } else {
            String textoBuscado = texto.toLowerCase();
            for (Alumno alumno : listaAlumnosCompleta) {
                if ((alumno.getNombre() != null && alumno.getNombre().toLowerCase().contains(textoBuscado)) ||
                        (alumno.getCorreo1() != null && alumno.getCorreo1().toLowerCase().contains(textoBuscado))) {
                    listaAlumnos.add(alumno);
                }
            }
        }
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, email, avatar;
        Button btnPerfil;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.avatarInicial);
            nombre = itemView.findViewById(R.id.nombre);
            email = itemView.findViewById(R.id.correo);
            btnPerfil = itemView.findViewById(R.id.button);
        }
    }
}




