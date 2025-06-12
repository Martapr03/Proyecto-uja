package com.example.proyectouja;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class SeleccionarInsigniaFragment extends Fragment {

    private ActividadViewModel viewModel;

    public SeleccionarInsigniaFragment() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_seleccionar_insignia, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(ActividadViewModel.class);
        NavController navController = Navigation.findNavController(view);

        // Intentar obtener de ViewModel
        String actividad = viewModel.getActividad().getValue();

        // Si no hay valor en el ViewModel, intentar obtener desde argumentos
        if (actividad == null && getArguments() != null) {
            actividad = getArguments().getString("actividad");
            if (actividad != null) {
                viewModel.setActividad(actividad); // Guardar en ViewModel para consistencia
            }
        }

        // Botón volver
        ImageButton buttonAtras = view.findViewById(R.id.imageButton2);
        buttonAtras.setOnClickListener(v ->
                navController.navigate(R.id.action_seleccionarInsigniaFragment_to_seleccionarActividadFragment)
        );

        if (actividad == null) {
            Toast.makeText(getContext(), "No se seleccionó actividad", Toast.LENGTH_SHORT).show();
            return;
        }

        // Mostrar insignias según actividad
        switch (actividad) {
            case "zona_entrenamiento":
                mostrarInsignias(view, navController, "Maestro Capturador", "Pokémon Salvaje", "Pokémon Fugitivo");
                break;
            case "gimnasio":
                mostrarInsignias(view, navController, "Líder de Gimnasio", "Medalla de Valor", "Derrota Temporal");
                break;
            case "cueva":
                mostrarInsignias(view, navController, "Explorador Legendario", "Fósil Descubierto", "Cueva Oscura");
                break;
            case "liga":
                mostrarInsignias(view, navController, "Campeón de la Liga", "Finalista de la Liga", "Entrenador Novato");
                break;
        }
    }


    private void mostrarInsignias(View view, NavController navController, String oro, String plata, String bronce) {
        ImageButton buttonOro = view.findViewById(R.id.imageButton4);
        ImageButton buttonPlata = view.findViewById(R.id.imageButton11);
        ImageButton buttonBronce = view.findViewById(R.id.imageButton12);

        // Asignar imágenes
        buttonOro.setImageResource(getDrawableIdByInsignia(oro));
        buttonPlata.setImageResource(getDrawableIdByInsignia(plata));
        buttonBronce.setImageResource(getDrawableIdByInsignia(bronce));

        // Mostrar botones
        buttonOro.setVisibility(View.VISIBLE);
        buttonPlata.setVisibility(View.VISIBLE);
        buttonBronce.setVisibility(View.VISIBLE);

        // Listener común
        View.OnClickListener listener = v -> {
            String insigniaSeleccionada = "";
            if (v.getId() == R.id.imageButton4) insigniaSeleccionada = oro;
            else if (v.getId() == R.id.imageButton11) insigniaSeleccionada = plata;
            else if (v.getId() == R.id.imageButton12) insigniaSeleccionada = bronce;

            viewModel.setInsignia(insigniaSeleccionada);

            // Navegar a siguiente fragmento
            navController.navigate(R.id.action_seleccionarInsigniaFragment_to_seleccionarAlumnoFragment);
        };

        buttonOro.setOnClickListener(listener);
        buttonPlata.setOnClickListener(listener);
        buttonBronce.setOnClickListener(listener);
    }

    private int getDrawableIdByInsignia(String insignia) {
        switch (insignia) {
            case "Maestro Capturador": return R.drawable.maestro_capturador;
            case "Pokémon Salvaje": return R.drawable.pokemon_salvaje;
            case "Pokémon Fugitivo": return R.drawable.pokemon_fugitivo;
            case "Líder de Gimnasio": return R.drawable.lider_gimnasio;
            case "Medalla de Valor": return R.drawable.medalla_valor;
            case "Derrota Temporal": return R.drawable.derrota_temporal;
            case "Explorador Legendario": return R.drawable.explorador_legendario;
            case "Fósil Descubierto": return R.drawable.fosil_descubierto;
            case "Cueva Oscura": return R.drawable.cueva_oscura;
            case "Campeón de la Liga": return R.drawable.campeon_liga;
            case "Finalista de la Liga": return R.drawable.finalista_liga;
            case "Entrenador Novato": return R.drawable.entrenador_novato;
            default: return R.drawable.ic_launcher_foreground;
        }
    }
}




