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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class VisualizarPerfilAlumnoFragment extends Fragment {

    private static final String ARG_NOMBRE = "nombre";
    private static final String ARG_CORREO = "correo";

    private String nombre;
    private String correo;

    public VisualizarPerfilAlumnoFragment() {
        // Constructor vacío requerido
    }

    public static VisualizarPerfilAlumnoFragment newInstance(String nombre, String correo) {
        VisualizarPerfilAlumnoFragment fragment = new VisualizarPerfilAlumnoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NOMBRE, nombre);
        args.putString(ARG_CORREO, correo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            nombre = getArguments().getString(ARG_NOMBRE);
            correo = getArguments().getString(ARG_CORREO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_visualizar_perfil_alumno, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView nombreTextView = view.findViewById(R.id.nombre);
        TextView correoTextView = view.findViewById(R.id.correo);
        ImageButton buttonCerrar = view.findViewById(R.id.cerrarButton2);

        if (nombre != null) nombreTextView.setText(nombre);
        if (correo != null) correoTextView.setText(correo);

        NavController navController = Navigation.findNavController(view);
        buttonCerrar.setOnClickListener(v ->
                navController.navigate(R.id.action_visualizarPerfilAlumnoFragment_to_pantallaPrincipalAlumnoFragment)
        );
    }
}

