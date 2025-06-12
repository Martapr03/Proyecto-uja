package com.example.proyectouja;

import java.util.ArrayList;
import java.util.List;

public class AlumnoRepository {
    private static List<Alumno> alumnosSeleccionados = new ArrayList<>();

    public static void setAlumnosSeleccionados(List<Alumno> alumnos) {
        alumnosSeleccionados = alumnos;
    }

    public static List<Alumno> getAlumnosSeleccionados() {
        return alumnosSeleccionados;
    }
}
