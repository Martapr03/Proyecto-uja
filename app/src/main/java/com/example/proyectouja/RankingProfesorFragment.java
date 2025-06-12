package com.example.proyectouja;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class RankingProfesorFragment extends Fragment {

    public RankingProfesorFragment() {
        // Constructor vacío
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ranking_profesor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Botón de cerrar
        ImageButton buttonCerrar = view.findViewById(R.id.imageButton3);
        buttonCerrar.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        cargarRankingDesdeFirestore(view);
    }

    private void cargarRankingDesdeFirestore(View view) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("usuarios")
                .whereEqualTo("rol", "alumno")
                .orderBy("puntuacion", Query.Direction.DESCENDING)
                .limit(6)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    List<Alumno> topAlumnos = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : querySnapshot) {
                        Alumno alumno = doc.toObject(Alumno.class);
                        topAlumnos.add(alumno);
                    }
                    mostrarRanking(view, topAlumnos);
                })
                .addOnFailureListener(e ->
                        Toast.makeText(getContext(), "Error al cargar ranking", Toast.LENGTH_SHORT).show()
                );
    }

    private void mostrarRanking(View view, List<Alumno> alumnos) {
        if (alumnos.size() > 0) {
            ((TextView) view.findViewById(R.id.textView25)).setText(alumnos.get(0).getNombre());
            ((TextView) view.findViewById(R.id.textView27)).setText(alumnos.get(0).getPuntuacion() + " puntos");
        }
        if (alumnos.size() > 1) {
            ((TextView) view.findViewById(R.id.textView24)).setText(alumnos.get(1).getNombre());
            ((TextView) view.findViewById(R.id.textView28)).setText(alumnos.get(1).getPuntuacion() + " puntos");
        }
        if (alumnos.size() > 2) {
            ((TextView) view.findViewById(R.id.textView26)).setText(alumnos.get(2).getNombre());
            ((TextView) view.findViewById(R.id.textView29)).setText(alumnos.get(2).getPuntuacion() + " puntos");
        }
        if (alumnos.size() > 3) {
            ((TextView) view.findViewById(R.id.nombre2)).setText(alumnos.get(3).getNombre());
            ((TextView) view.findViewById(R.id.puntos2)).setText(alumnos.get(3).getPuntuacion() + " puntos");
        }
        if (alumnos.size() > 4) {
            ((TextView) view.findViewById(R.id.nombre4)).setText(alumnos.get(4).getNombre());
            ((TextView) view.findViewById(R.id.puntos4)).setText(alumnos.get(4).getPuntuacion() + " puntos");
        }
        if (alumnos.size() > 5) {
            ((TextView) view.findViewById(R.id.nombre5)).setText(alumnos.get(5).getNombre());
            ((TextView) view.findViewById(R.id.puntos5)).setText(alumnos.get(5).getPuntuacion() + " puntos");
        }
    }
}
