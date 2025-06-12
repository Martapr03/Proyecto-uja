package com.example.proyectouja;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class PantallaPrincipalAlumnoFragment extends Fragment {

    public PantallaPrincipalAlumnoFragment() {
        // Constructor vacío requerido
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pantalla_principal_alumno, container, false);

        NavController navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView);

        // Botón: Ir al perfil del alumno
        ImageButton buttonPerfilAlumno = view.findViewById(R.id.imageButton9);
        buttonPerfilAlumno.setOnClickListener(v ->
                navController.navigate(R.id.action_pantallaPrincipalAlumnoFragment_to_visualizarPerfilAlumnoFragment)
        );

        // Botón: Leer QR
        ImageButton buttonLeerQR = view.findViewById(R.id.buttonLeerqr);
        buttonLeerQR.setOnClickListener(v ->
                navController.navigate(R.id.action_pantallaPrincipalAlumnoFragment_to_leerQRFragment)
        );

        // Botón: Ver ranking
        ImageButton buttonRanking = view.findViewById(R.id.buttonRanking);
        buttonRanking.setOnClickListener(v ->
                navController.navigate(R.id.action_pantallaPrincipalAlumnoFragment_to_rankingFragment)
        );

        //boton ajustes
        ImageButton buttonAjustes = view.findViewById(R.id.imageButton10);
        buttonAjustes.setOnClickListener(v ->
                navController.navigate(R.id.action_pantallaPrincipalAlumnoFragment_to_ajustesFragment)
        );


        return view;
    }
}
