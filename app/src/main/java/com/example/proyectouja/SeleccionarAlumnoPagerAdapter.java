package com.example.proyectouja;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class SeleccionarAlumnoPagerAdapter extends FragmentStateAdapter {

    private final String actividad;
    private final String insignia;

    public SeleccionarAlumnoPagerAdapter(@NonNull FragmentActivity fragmentActivity, String actividad, String insignia) {
        super(fragmentActivity);
        this.actividad = actividad;
        this.insignia = insignia;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return position == 0 ? new ListaAlumnosAsignarFragment() : new ListaAlumnosQuitarFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}











