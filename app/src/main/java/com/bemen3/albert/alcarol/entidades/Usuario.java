package com.bemen3.albert.alcarol.entidades;

/**
 * Created by alber on 19/05/2017.
 */

public class Usuario {

    private String id;
    private String nombre;
    private String dni;

    public Usuario() {
    }

    public Usuario(String id, String nombre, String dni) {
        this.id = id;
        this.nombre = nombre;
        this.dni = dni;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }
}
