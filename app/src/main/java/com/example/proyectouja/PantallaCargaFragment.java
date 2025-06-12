package com.example.proyectouja;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

public class PantallaCargaFragment extends Fragment {

    public PantallaCargaFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pantalla_carga, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        new Handler().postDelayed(() -> {
            if (isAdded()) {
                NavController navController = NavHostFragment.findNavController(PantallaCargaFragment.this);
                navController.navigate(R.id.action_pantallaCargaFragment_to_inicioFragment2);
            }
        }, 3000);
    }
}







