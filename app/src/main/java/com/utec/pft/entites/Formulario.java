package com.utec.pft.entites;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Formulario implements Serializable {

    private Long id;
    private String nombre;
    private boolean activo;
    private Map<String, Boolean> campos = new HashMap<String, Boolean>();

    public Formulario() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Map<String, Boolean> getCampos() {
        return campos;
    }

    public void setCampos(Map<String, Boolean> campos) {
        this.campos = campos;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "Formulario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", activo=" + activo +
                ", campos=" + campos +
                '}';
    }
}

