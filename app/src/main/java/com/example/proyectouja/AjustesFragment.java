package com.example.proyectouja;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class AjustesFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private ImageButton btnSeguir;
    private ImageButton btnAtras;
    public AjustesFragment() {
        // Required empty public constructor
    }

    public static AjustesFragment newInstance(String param1, String param2) {
        AjustesFragment fragment = new AjustesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ajustes, container, false);

        btnSeguir = view.findViewById(R.id.btnSeguir);
        btnAtras = view.findViewById(R.id.imageButton16);

        // Acción botón seguir
        btnSeguir.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_ajustesFragment_to_cambiarContrasenaFragment);
        });

        // Acción botón atrás
        btnAtras.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.popBackStack(); // Más seguro que onBackPressed()
        });

        return view;
    }

}
