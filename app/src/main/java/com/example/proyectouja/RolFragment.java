package com.example.proyectouja;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

public class RolFragment extends Fragment {

    private static final String PREFS_NAME = "PreferenciasUsuario";
    private static final String ROL_KEY = "rol";

    public RolFragment() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rol, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button buttonProfesor = view.findViewById(R.id.buttonProfesor);
        Button buttonAlumno = view.findViewById(R.id.buttonAlumno);

        buttonProfesor.setOnClickListener(v -> {
            guardarRolEnPreferencias("profesor");
            navegarAInicioSesion();
        });

        buttonAlumno.setOnClickListener(v -> {
            guardarRolEnPreferencias("alumno");
            navegarAInicioSesion();
        });
    }

    private void guardarRolEnPreferencias(String rol) {
        Context context = getActivity();
        if (context != null) {
            SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            prefs.edit().putString(ROL_KEY, rol).apply();
        }
    }

    private void navegarAInicioSesion() {
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(R.id.action_rolFragment_to_inicioSesionFragment);
    }
}



