package com.example.proyectouja;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ActividadViewModel extends ViewModel {

    private final MutableLiveData<String> actividad = new MutableLiveData<>();
    private final MutableLiveData<String> insignia = new MutableLiveData<>();

    public void setActividad(String value) {
        actividad.setValue(value);
    }

    public LiveData<String> getActividad() {
        return actividad;
    }

    public void setInsignia(String value) {
        insignia.setValue(value);
    }

    public LiveData<String> getInsignia() {
        return insignia;
    }
}
