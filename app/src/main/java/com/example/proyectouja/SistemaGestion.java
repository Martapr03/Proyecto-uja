package com.example.proyectouja;

import java.util.List;

public class SistemaGestion {

    private List<Alumno> alumnos;
    private List<Profesor> profesores;
    private List<Asignatura> asignaturas;
    private List<Actividad> actividades;
    private List<Insignia> insignias;

    public SistemaGestion(List<Alumno> alumnos, List<Profesor> profesores, List<Asignatura> asignaturas, List<Actividad> actividades, List<Insignia> insignias) {
        this.alumnos = alumnos;
        this.profesores = profesores;
        this.asignaturas = asignaturas;
        this.actividades = actividades;
        this.insignias = insignias;
    }
    public List<Alumno> getAlumnos() {
        return alumnos;
    }

    public List<Profesor> getProfesores() {
        return profesores;
    }

    public List<Asignatura> getAsignaturas() {
        return asignaturas;
    }

    public List<Actividad> getActividades() {
        return actividades;
    }

    public List<Insignia> getInsignias() {
        return insignias;
    }

}
