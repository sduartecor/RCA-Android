package com.utec.pft.entites;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Registro implements Serializable {
    private Long id;
    private Formulario formulario;
    private Estacion estacion;
    private String metodo;
    private Ciudad ciudad;
    private String usuario;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date fecha;
    private Map<String, Double> campos = new HashMap<String, Double>();
    private boolean activo;

    public Registro() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Formulario getFormulario() {
        return formulario;
    }

    public void setFormulario(Formulario formulario) {
        this.formulario = formulario;
    }

    public Estacion getEstacion() {
        return estacion;
    }

    public void setEstacion(Estacion estacion) {
        this.estacion = estacion;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Map<String, Double> getCampos() {
        return campos;
    }

    public void setCampos(Map<String, Double> campos) {
        this.campos = campos;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "Registro{" +
                "id=" + id +
                ", formulario=" + formulario +
                ", estacion=" + estacion +
                ", metodo='" + metodo + '\'' +
                ", ciudad=" + ciudad +
                ", usuario='" + usuario + '\'' +
                ", fecha=" + fecha +
                ", campos=" + campos +
                ", activo=" + activo +
                '}';
    }
}
