package com.example.proyectouja;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlumnoInsigniaAdapter extends RecyclerView.Adapter<AlumnoInsigniaAdapter.ViewHolder> {

    private List<Alumno> listaAlumnos;
    private final Map<String, Boolean> seleccionados = new HashMap<>();
    private String insigniaActual;

    public AlumnoInsigniaAdapter(List<Alumno> listaAlumnos) {
        this.listaAlumnos = listaAlumnos;
    }

    public void setInsigniaActual(String insignia) {
        this.insigniaActual = insignia;
    }

    public void updateList(List<Alumno> nuevaLista) {
        this.listaAlumnos = nuevaLista;
        notifyDataSetChanged();
    }

    public Map<String, Boolean> getSeleccionados() {
        return seleccionados;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alumno, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Alumno alumno = listaAlumnos.get(position);

        String nombre = alumno.getNombre();
        String correo = alumno.getCorreo1();

        // Mostramos datos
        holder.textNombre.setText(nombre != null ? nombre : "Sin nombre");
        holder.textCorreo.setText(correo != null ? correo : "Sin correo");
        holder.avatarInicial.setText(nombre != null && !nombre.isEmpty() ? String.valueOf(nombre.charAt(0)).toUpperCase() : "?");

        // Evita reciclaje incorrecto del CheckBox
        holder.checkBox.setOnCheckedChangeListener(null);
        boolean isChecked = seleccionados.getOrDefault(correo, false);
        holder.checkBox.setChecked(isChecked);
        holder.checkBox.setOnCheckedChangeListener((buttonView, checked) -> {
            seleccionados.put(correo, checked);
        });

        // Icono de insignia asignada
        List<String> insignias = alumno.getInsignias();
        boolean tieneInsignia = insignias != null && insigniaActual != null &&
                insignias.stream().anyMatch(i -> i != null && i.trim().equalsIgnoreCase(insigniaActual.trim()));

        holder.iconoCheck.setVisibility(tieneInsignia ? View.VISIBLE : View.GONE);

        // Log para debug
        Log.d("ADAPTER", "Alumno: " + nombre + " | Insignia: " + insigniaActual + " | Check: " + isChecked);
    }

    @Override
    public int getItemCount() {
        return listaAlumnos != null ? listaAlumnos.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textNombre, textCorreo, avatarInicial;
        CheckBox checkBox;
        ImageView iconoCheck;

        ViewHolder(View itemView) {
            super(itemView);
            textNombre = itemView.findViewById(R.id.nombre);
            textCorreo = itemView.findViewById(R.id.correo);
            avatarInicial = itemView.findViewById(R.id.avatarInicial3);
            checkBox = itemView.findViewById(R.id.checkboxSeleccionado);
            iconoCheck = itemView.findViewById(R.id.iconoInsigniaAsignada);
        }
    }
}








