package com.bemen3.albert.alcarol.entidades;

import java.util.HashMap;

/**
 * Created by alber on 21/05/2017.
 */

public class Personaje {
    private String id;
    private String userId;
    private Estilo estilo;
    private String nombre;
    private HashMap<String, String> atributos;

    public Personaje() {
        atributos = new HashMap<>();
    }

    public Personaje(String id, String userId, Estilo estilo, String nombre) {
        this.id = id;
        this.userId = userId;
        this.estilo = estilo;
        this.nombre = nombre;
        atributos = new HashMap<>();
    }

    public Personaje(String id, String userId, Estilo estilo, String nombre, HashMap<String, String> atributos) {
        this.id = id;
        this.userId = userId;
        this.estilo = estilo;
        this.nombre = nombre;
        this.atributos = atributos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Estilo getEstilo() {
        return estilo;
    }

    public void setEstilo(Estilo estilo) {
        this.estilo = estilo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public HashMap<String, String> getAtributos() {
        return atributos;
    }

    public void setAtributos(HashMap<String, String> atributos) {
        this.atributos = atributos;
    }
}
