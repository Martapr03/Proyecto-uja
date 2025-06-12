
package com.example.proyectouja;

public class Insignia {

    private String tipo; // Solo uno: Oro, Plata o Bronce
    private String leyenda;
    private int valor;
    private String imagen;

    public Insignia(String tipo, String leyenda, String imagen) {
        this.tipo = tipo;
        this.leyenda = leyenda;
        this.imagen = imagen;

        // Asignamos valor automáticamente según el tipo
        switch (tipo) {
            case "Oro":
                this.valor = 3;
                break;
            case "Plata":
                this.valor = 2;
                break;
            case "Bronce":
                this.valor = 1;
                break;
            default:
                this.valor = 0;
                break;
        }
    }

    // Getters y Setters
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;

        // Actualiza el valor si se cambia el tipo
        switch (tipo) {
            case "Oro":
                this.valor = 3;
                break;
            case "Plata":
                this.valor = 2;
                break;
            case "Bronce":
                this.valor = 1;
                break;
            default:
                this.valor = 0;
                break;
        }
    }

    public String getLeyenda() {
        return leyenda;
    }

    public void setLeyenda(String leyenda) {
        this.leyenda = leyenda;
    }

    public int getValor() {
        return valor;
    }

    // No se necesita setValor porque el valor se define por el tipo

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
