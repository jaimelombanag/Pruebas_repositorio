package com.encuesta.rightway.clases;

/**
 * Created by Jaime on 22/04/2016.
 */
public class Vehiculos {

    private String Tipo;
    private String Cilindraje;
    private String Combustible;
    private String Propiedad;
    private String Matricula;
    private String Parqueadero;

    public Vehiculos(String tipo, String cilindraje, String combustible, String propiedad, String matricula, String parqueadero) {
        this.Tipo = tipo;
        this.Cilindraje = cilindraje;
        this.Combustible = combustible;
        this.Propiedad = propiedad;
        this.Matricula = matricula;
        this.Parqueadero = parqueadero;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }

    public String getCilindraje() {
        return Cilindraje;
    }

    public void setCilindraje(String cilindraje) {
        Cilindraje = cilindraje;
    }

    public String getCombustible() {
        return Combustible;
    }

    public void setCombustible(String combustible) {
        Combustible = combustible;
    }

    public String getPropiedad() {
        return Propiedad;
    }

    public void setPropiedad(String propiedad) {
        Propiedad = propiedad;
    }

    public String getMatricula() {
        return Matricula;
    }

    public void setMatricula(String matricula) {
        Matricula = matricula;
    }

    public String getParqueadero() {
        return Parqueadero;
    }

    public void setParqueadero(String parqueadero) {
        Parqueadero = parqueadero;
    }
}
