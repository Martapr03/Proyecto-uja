package com.example.proyectouja;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ListaAlumnosQuitarFragment extends Fragment {

    private RecyclerView recyclerView;
    private AlumnoInsigniaAdapter alumnoAdapter;
    private SearchView buscador;
    private Button btnQuitar;

    private String insigniaSeleccionada;

    private final List<Alumno> listaAlumnos = new ArrayList<>();
    private final List<Alumno> listaFiltrada = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_alumnos_quitar, container, false);

        if (getArguments() != null) {
            insigniaSeleccionada = getArguments().getString("insignia", "");
        }

        recyclerView = view.findViewById(R.id.listaAlumnosQuitar);
        buscador = view.findViewById(R.id.buscador);
        btnQuitar = view.findViewById(R.id.btnQuitar);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        alumnoAdapter = new AlumnoInsigniaAdapter(listaFiltrada);
        alumnoAdapter.setInsigniaActual(insigniaSeleccionada);
        recyclerView.setAdapter(alumnoAdapter);

        cargarAlumnosConInsignia();

        buscador.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override public boolean onQueryTextSubmit(String query) { filtrar(query); return true; }
            @Override public boolean onQueryTextChange(String newText) { filtrar(newText); return true; }
        });

        btnQuitar.setOnClickListener(v -> {
            List<Alumno> seleccionados = obtenerAlumnosSeleccionados();
            if (seleccionados.isEmpty()) {
                Toast.makeText(getContext(), "Selecciona al menos un alumno", Toast.LENGTH_SHORT).show();
                return;
            }
            quitarInsignia(insigniaSeleccionada, seleccionados);
        });

        return view;
    }

    private void cargarAlumnosConInsignia() {
        listaAlumnos.clear();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("usuarios")
                .whereEqualTo("rol", "alumno")
                .get()
                .addOnSuccessListener(snapshot -> {
                    for (QueryDocumentSnapshot doc : snapshot) {
                        Alumno alumno = doc.toObject(Alumno.class);
                        List<String> insignias = alumno.getInsignias();

                        if (insigniaSeleccionada != null && insignias != null &&
                                insignias.stream().anyMatch(i ->
                                        i != null && i.trim().equalsIgnoreCase(insigniaSeleccionada.trim()))) {
                            listaAlumnos.add(alumno);
                        }
                    }
                    filtrar(buscador.getQuery().toString());
                })
                .addOnFailureListener(e -> {
                    Log.e("Firestore", "Error al cargar alumnos", e);
                    Toast.makeText(getContext(), "Error al cargar alumnos", Toast.LENGTH_SHORT).show();
                });
    }

    private void filtrar(String texto) {
        listaFiltrada.clear();
        if (texto == null || texto.trim().isEmpty()) {
            listaFiltrada.addAll(listaAlumnos);
        } else {
            String lower = texto.toLowerCase();
            for (Alumno a : listaAlumnos) {
                if ((a.getNombre() != null && a.getNombre().toLowerCase().contains(lower)) ||
                        (a.getCorreo1() != null && a.getCorreo1().toLowerCase().contains(lower))) {
                    listaFiltrada.add(a);
                }
            }
        }
        alumnoAdapter.updateList(listaFiltrada);
    }

    private List<Alumno> obtenerAlumnosSeleccionados() {
        List<Alumno> seleccionados = new ArrayList<>();
        for (Alumno a : listaFiltrada) {
            if (alumnoAdapter.getSeleccionados().getOrDefault(a.getCorreo1(), false)) {
                seleccionados.add(a);
            }
        }
        return seleccionados;
    }

    private void quitarInsignia(String insignia, List<Alumno> seleccionados) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        int total = seleccionados.size();
        final int[] contador = {0};

        for (Alumno alumno : seleccionados) {
            db.collection("usuarios")
                    .document(alumno.getCorreo1())
                    .update("insignias", FieldValue.arrayRemove(insignia))
                    .addOnSuccessListener(unused -> {
                        contador[0]++;
                        if (contador[0] == total) {
                            Toast.makeText(getContext(), "Insignia retirada", Toast.LENGTH_SHORT).show();
                            cargarAlumnosConInsignia();
                        }
                    })
                    .addOnFailureListener(e -> {
                        Log.e("Firestore", "Error al quitar insignia", e);
                        contador[0]++;
                        if (contador[0] == total) {
                            Toast.makeText(getContext(), "Error parcial al quitar", Toast.LENGTH_SHORT).show();
                            cargarAlumnosConInsignia();
                        }
                    });
        }
    }
}










