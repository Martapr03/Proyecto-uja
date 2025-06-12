package com.example.proyectouja;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class BuscarAlumnoFragment extends Fragment {

    private SearchView searchView;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private BuscarAlumnoAdapter adapter;
    private final List<Alumno> listaOriginal = new ArrayList<>();
    private final List<Alumno> listaFiltrada = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_buscar_alumno, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchView = view.findViewById(R.id.buscarAlumnos);
        recyclerView = view.findViewById(R.id.listaAlumnos);
        progressBar = view.findViewById(R.id.progressBar); // Asegúrate de que esté en el XML

        ImageButton botonCerrar = view.findViewById(R.id.imageButton14);
        botonCerrar.setOnClickListener(v ->
                NavHostFragment.findNavController(this).popBackStack()
        );

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new BuscarAlumnoAdapter(listaFiltrada, requireContext(), alumno -> {
            Bundle args = new Bundle();
            args.putString("nombre", alumno.getNombre());
            args.putString("correo", alumno.getCorreo1());
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_buscarAlumnoFragment_to_visualizarPerfilAlumnoFragment, args);
        });

        recyclerView.setAdapter(adapter);
        cargarAlumnosDesdeFirestore();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override public boolean onQueryTextSubmit(String query) {
                filtrar(query);
                return true;
            }

            @Override public boolean onQueryTextChange(String newText) {
                filtrar(newText);
                return true;
            }
        });
    }

    private void cargarAlumnosDesdeFirestore() {
        progressBar.setVisibility(View.VISIBLE);

        FirebaseFirestore.getInstance().collection("usuarios")
                .whereEqualTo("rol", "alumno")
                .get()
                .addOnSuccessListener(snapshot -> {
                    listaOriginal.clear();
                    for (QueryDocumentSnapshot doc : snapshot) {
                        Alumno alumno = doc.toObject(Alumno.class);
                        Log.d("BuscarAlumno", "Alumno cargado: " + alumno.getNombre());
                        listaOriginal.add(alumno);
                    }
                    listaFiltrada.clear();
                    listaFiltrada.addAll(listaOriginal);
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(requireContext(), "Error al cargar alumnos", Toast.LENGTH_SHORT).show();
                    Log.e("BuscarAlumno", "Error: " + e.getMessage());
                    progressBar.setVisibility(View.GONE);
                });
    }

    private void filtrar(String texto) {
        List<Alumno> resultados = new ArrayList<>();
        for (Alumno alumno : listaOriginal) {
            if (alumno.getNombre().toLowerCase().contains(texto.toLowerCase()) ||
                    alumno.getCorreo1().toLowerCase().contains(texto.toLowerCase())) {
                resultados.add(alumno);
            }
        }

        listaFiltrada.clear();
        listaFiltrada.addAll(resultados);
        adapter.notifyDataSetChanged();
    }
}


