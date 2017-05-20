package com.bemen3.albert.alcarol.entidades;

/**
 * Created by alber on 20/05/2017.
 */

public class Estilo {
    private String id;
    private String userId;
    private String nombre, vida, mana, destreza, percepcion, fuerza, carisma, constitucion, inteligencia, sabiduria;

    public Estilo() {
    }

    public Estilo(String id, String userId, String nombre) {
        this.id = id;
        this.userId = userId;
        this.nombre = nombre;
    }

    public Estilo(String userId, String nombre) {
        this.userId = userId;
        this.nombre = nombre;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getVida() {
        return vida;
    }

    public void setVida(String vida) {
        this.vida = vida;
    }

    public String getMana() {
        return mana;
    }

    public void setMana(String mana) {
        this.mana = mana;
    }

    public String getDestreza() {
        return destreza;
    }

    public void setDestreza(String destreza) {
        this.destreza = destreza;
    }

    public String getPercepcion() {
        return percepcion;
    }

    public void setPercepcion(String percepcion) {
        this.percepcion = percepcion;
    }

    public String getFuerza() {
        return fuerza;
    }

    public void setFuerza(String fuerza) {
        this.fuerza = fuerza;
    }

    public String getCarisma() {
        return carisma;
    }

    public void setCarisma(String carisma) {
        this.carisma = carisma;
    }

    public String getConstitucion() {
        return constitucion;
    }

    public void setConstitucion(String constitucion) {
        this.constitucion = constitucion;
    }

    public String getInteligencia() {
        return inteligencia;
    }

    public void setInteligencia(String inteligencia) {
        this.inteligencia = inteligencia;
    }

    public String getSabiduria() {
        return sabiduria;
    }

    public void setSabiduria(String sabiduria) {
        this.sabiduria = sabiduria;
    }
}
