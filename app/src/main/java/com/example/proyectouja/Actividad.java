package com.example.proyectouja;

public class Actividad {

    private int id;
    private String nombre;
    private String leyenda;

    public Actividad(int id, String nombre, String leyenda) {
        this.id = id;
        this.nombre = nombre;
        this.leyenda = leyenda;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLeyenda() {
        return leyenda;
    }

    public void setLeyenda(String leyenda) {
        this.leyenda = leyenda;
    }
}
