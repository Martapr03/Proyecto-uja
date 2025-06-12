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
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class SeleccionarAlumnoFragment extends Fragment {

    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private SeleccionarAlumnoPagerAdapter pagerAdapter;

    public SeleccionarAlumnoFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_seleccionar_alumno, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation.findNavController(view);

        ActividadViewModel viewModel = new ViewModelProvider(requireActivity()).get(ActividadViewModel.class);
        String actividad = viewModel.getActividad().getValue();
        String insignia = viewModel.getInsignia().getValue();

        // Inicializar TabLayout y ViewPager2
        tabLayout = view.findViewById(R.id.tabLayout2);
        viewPager = view.findViewById(R.id.viewPager);

        // Crear e instalar el adaptador
        pagerAdapter = new SeleccionarAlumnoPagerAdapter(requireActivity(), actividad, insignia);
        viewPager.setAdapter(pagerAdapter);

        // Conectar TabLayout con ViewPager2
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Asignar insignia");
                    break;
                case 1:
                    tab.setText("Quitar insignia");
                    break;
            }
        }).attach();

        // Botón atrás
        ImageButton buttonAtras = view.findViewById(R.id.image_arrow);
        buttonAtras.setOnClickListener(v ->
                navController.navigate(R.id.action_seleccionarAlumnoFragment_to_seleccionarInsigniaFragment2)
        );

        // Botón cerrar
        ImageButton buttonCerrar = view.findViewById(R.id.imageButton15);
        buttonCerrar.setOnClickListener(v ->
                navController.navigate(R.id.action_seleccionarAlumnoFragment_to_pantallaPrincipalProfesorFragment2)
        );
    }


}

