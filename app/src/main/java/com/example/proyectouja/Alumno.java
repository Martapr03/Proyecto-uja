package com.example.proyectouja;

import java.util.List;

public class Alumno {
    private String dni;
    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private String correo1;
    private String planDelAlumno;
    private String nivel;
    private int puntuacion;
    private boolean seleccionado;

    private List<String> insignias;

    // 🔹 Constructor vacío: Obligatorio para Firebase
    public Alumno() {}

    // 🔹 Constructor completo (opcional, útil para pruebas locales)
    public Alumno(String dni, String nombre, String primerApellido, String segundoApellido, String correo1,
                  String planDelAlumno, String nivel, int puntuacion, boolean seleccionado, List<String> insignias) {
        this.dni = dni;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.correo1 = correo1;
        this.planDelAlumno = planDelAlumno;
        this.nivel = nivel;
        this.puntuacion = puntuacion;
        this.seleccionado = seleccionado;
        this.insignias = insignias;
    }

    // 🔹 Getters y setters
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getPrimerApellido() { return primerApellido; }
    public void setPrimerApellido(String primerApellido) { this.primerApellido = primerApellido; }

    public String getSegundoApellido() { return segundoApellido; }
    public void setSegundoApellido(String segundoApellido) { this.segundoApellido = segundoApellido; }

    public String getCorreo1() { return correo1; }
    public void setCorreo1(String correo1) { this.correo1 = correo1; }

    public String getPlanDelAlumno() { return planDelAlumno; }
    public void setPlanDelAlumno(String planDelAlumno) { this.planDelAlumno = planDelAlumno; }

    public String getNivel() { return nivel; }
    public void setNivel(String nivel) { this.nivel = nivel; }

    public int getPuntuacion() { return puntuacion; }
    public void setPuntuacion(int puntuacion) { this.puntuacion = puntuacion; }

    public boolean isSeleccionado() { return seleccionado; }
    public void setSeleccionado(boolean seleccionado) { this.seleccionado = seleccionado; }

    public List<String> getInsignias() { return insignias; }
    public void setInsignias(List<String> insignias) { this.insignias = insignias; }

    // 🔹 Método útil para mostrar el "nivel" según puntuación
    public String obtenerNivel() {
        if (puntuacion <= 5) {
            return "Entrenador Novato";
        } else if (puntuacion <= 10) {
            return "Entrenador Intermedio";
        } else if (puntuacion <= 20) {
            return "Entrenador Experto";
        } else {
            return "Maestro Pokémon";
        }
    }
}


