package com.example.proyectouja;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class RankingFragment extends Fragment {

    public RankingFragment() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Infla el layout
        View view = inflater.inflate(R.layout.fragment_ranking, container, false);


        // Botón "Cerrar"
        ImageButton buttonCerrar = view.findViewById(R.id.buttoncerrar);
        buttonCerrar.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Simulación de alumnos (ejemplo)
        List<Alumno> alumnos = new ArrayList<>();


        // Validar que la lista tenga al menos 6 alumnos
        if (alumnos.size() >= 6) {
            ((TextView) view.findViewById(R.id.textView25)).setText(alumnos.get(0).getNombre());
            ((TextView) view.findViewById(R.id.textView27)).setText(alumnos.get(0).getPuntuacion() + " puntos");

            ((TextView) view.findViewById(R.id.textView24)).setText(alumnos.get(1).getNombre());
            ((TextView) view.findViewById(R.id.textView28)).setText(alumnos.get(1).getPuntuacion() + " puntos");

            ((TextView) view.findViewById(R.id.textView26)).setText(alumnos.get(2).getNombre());
            ((TextView) view.findViewById(R.id.textView29)).setText(alumnos.get(2).getPuntuacion() + " puntos");

            ((TextView) view.findViewById(R.id.nombre2)).setText(alumnos.get(3).getNombre());
            ((TextView) view.findViewById(R.id.puntos2)).setText(alumnos.get(3).getPuntuacion() + " puntos");

            ((TextView) view.findViewById(R.id.nombre4)).setText(alumnos.get(4).getNombre());
            ((TextView) view.findViewById(R.id.puntos4)).setText(alumnos.get(4).getPuntuacion() + " puntos");

            ((TextView) view.findViewById(R.id.nombre5)).setText(alumnos.get(5).getNombre());
            ((TextView) view.findViewById(R.id.puntos5)).setText(alumnos.get(5).getPuntuacion() + " puntos");
        }
    }
}