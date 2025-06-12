package com.example.proyectouja;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class SeleccionarActividadFragment extends Fragment {

    public SeleccionarActividadFragment() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_seleccionar_actividad, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation.findNavController(view);
        ActividadViewModel viewModel = new ViewModelProvider(requireActivity()).get(ActividadViewModel.class);

        ImageButton buttonAtras = view.findViewById(R.id.image_arrow);
        buttonAtras.setOnClickListener(v ->
                navController.navigate(R.id.action_seleccionarActividadFragment_to_pantallaPrincipalProfesorFragment)
        );

        setupActivityButton(view, R.id.imageButton, "zona_entrenamiento", navController, viewModel);
        setupActivityButton(view, R.id.imageButton6, "gimnasio", navController, viewModel);
        setupActivityButton(view, R.id.imageButton7, "cueva", navController, viewModel);
        setupActivityButton(view, R.id.imageButton8, "liga", navController, viewModel);
    }

    private void setupActivityButton(View view, int buttonId, String actividad,
                                     NavController navController, ActividadViewModel viewModel) {
        ImageButton button = view.findViewById(buttonId);
        button.setOnClickListener(v -> {
            viewModel.setActividad(actividad); // Guardar en ViewModel
            Bundle bundle = new Bundle();
            bundle.putString("actividad", actividad); // Enviar por bundle también si lo necesitas
            navController.navigate(R.id.action_seleccionarActividadFragment_to_seleccionarInsigniaFragment, bundle);
        });
    }
}


