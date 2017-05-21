package com.bemen3.albert.alcarol.entidades;

/**
 * Created by alber on 21/05/2017.
 */

public class Atributo {

    private String nombre;
    private boolean seleccionado;

    public Atributo() {
        seleccionado = false;
    }

    public Atributo(String nombre, boolean seleccionado) {
        this.nombre = nombre;
        this.seleccionado = seleccionado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
    }
}
