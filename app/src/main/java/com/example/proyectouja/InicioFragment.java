package com.example.proyectouja;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

public class InicioFragment extends Fragment {

    public InicioFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inicio, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //buttonContinuar
        Button btnContinuar = view.findViewById(R.id.buttonContinuar);

        if (btnContinuar != null) {
            btnContinuar.setOnClickListener(v -> {
                Log.d("InicioFragment", "Botón CONTINUAR pulsado");
                NavController navController = NavHostFragment.findNavController(InicioFragment.this);
                navController.navigate(R.id.action_inicioFragment2_to_rolFragment);
            });
        } else {
            Log.e("InicioFragment", "ERROR: No se encontró el botón con ID buttonContinuar");
        }
    }
}


