package com.example.proyectouja;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AlumnoAdapter extends RecyclerView.Adapter<AlumnoAdapter.AlumnoViewHolder> {

    private List<Alumno> listaAlumnos;

    // Constructor
    public AlumnoAdapter(List<Alumno> listaAlumnos) {
        this.listaAlumnos = listaAlumnos;
    }

    @NonNull
    @Override
    public AlumnoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_alumno, parent, false); // usa tu layout personalizado
        return new AlumnoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlumnoViewHolder holder, int position) {
        Alumno alumno = listaAlumnos.get(position);

        // Inicial del nombre en avatar
        String inicial = alumno.getNombre() != null && !alumno.getNombre().isEmpty()
                ? alumno.getNombre().substring(0, 1).toUpperCase()
                : "?";
        holder.avatarInicial.setText(inicial);

        // Nombre completo
        String nombreCompleto = alumno.getNombre() + " " + alumno.getPrimerApellido();
        if (alumno.getSegundoApellido() != null && !alumno.getSegundoApellido().isEmpty()) {
            nombreCompleto += " " + alumno.getSegundoApellido();
        }
        holder.nombre.setText(nombreCompleto);

        // Correo
        holder.correo.setText(alumno.getCorreo1());

        // Estado del checkbox
        holder.checkBox.setChecked(alumno.isSeleccionado());

        // Cambio de selección desde el checkbox
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            alumno.setSeleccionado(isChecked);
        });

        // Tocar la tarjeta completa también cambia la selección
        holder.itemView.setOnClickListener(v -> {
            boolean nuevoEstado = !alumno.isSeleccionado();
            alumno.setSeleccionado(nuevoEstado);
            holder.checkBox.setChecked(nuevoEstado);
        });
    }

    @Override
    public int getItemCount() {
        return listaAlumnos != null ? listaAlumnos.size() : 0;
    }

    // Obtener lista de alumnos seleccionados
    public List<Alumno> getSeleccionados() {
        List<Alumno> seleccionados = new ArrayList<>();
        for (Alumno a : listaAlumnos) {
            if (a.isSeleccionado()) {
                seleccionados.add(a);
            }
        }
        return seleccionados;
    }

    // Permitir actualizar la lista
    public void updateList(List<Alumno> nuevaLista) {
        this.listaAlumnos = nuevaLista;
        notifyDataSetChanged();
    }

    // ViewHolder
    public static class AlumnoViewHolder extends RecyclerView.ViewHolder {
        TextView avatarInicial, nombre, correo;
        CheckBox checkBox;

        public AlumnoViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarInicial = itemView.findViewById(R.id.avatarInicial3);
            nombre = itemView.findViewById(R.id.nombre);
            correo = itemView.findViewById(R.id.correo);
            checkBox = itemView.findViewById(R.id.checkboxSeleccionado);
        }
    }
}




