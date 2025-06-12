package com.example.proyectouja;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageButton;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PantallaPrincipalProfesorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PantallaPrincipalProfesorFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PantallaPrincipalProfesorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PantallaPrincipalProfesorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PantallaPrincipalProfesorFragment newInstance(String param1, String param2) {
        PantallaPrincipalProfesorFragment fragment = new PantallaPrincipalProfesorFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pantalla_principal_profesor, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation.findNavController(view);

        // Botón: Ir al perfil del profesor
        ImageButton buttonPerfil = view.findViewById(R.id.botonPerfil);
        buttonPerfil.setOnClickListener(v ->
                navController.navigate(R.id.action_pantallaPrincipalProfesorFragment_to_perfilProfesorFragment2)
        );


        // Botón: Crear QR
        ImageButton buttonCrearQR = view.findViewById(R.id.qr);
        buttonCrearQR.setOnClickListener(v ->
                navController.navigate(R.id.action_pantallaPrincipalProfesorFragment_to_seleccionarActividadFragment)
        );

        // Botón: Ver ranking
        ImageButton buttonRanking = view.findViewById(R.id.ranking);
        buttonRanking.setOnClickListener(v ->
                navController.navigate(R.id.action_pantallaPrincipalProfesorFragment_to_rankingProfesorFragment)
        );

        // Botón: Visualizar perfil de alumno
        ImageButton buttonPerfilAlumno = view.findViewById(R.id.visualizarAlumno);
        buttonPerfilAlumno.setOnClickListener(v ->
                navController.navigate(R.id.action_pantallaPrincipalProfesorFragment_to_buscarAlumnoFragment)
        );

        //boton ajustes
        ImageButton buttonAjustes = view.findViewById(R.id.botonAjustes);
        buttonAjustes.setOnClickListener(v ->
                navController.navigate(R.id.action_pantallaPrincipalProfesorFragment_to_ajustesFragment)
        );

    }
}