package com.encuesta.rightway.clases;

import android.app.Application;

/**
 * Created by Jaime on 16/04/2016.
 */
public class Hogar {

    private int NumeroEncusta;
    private String Fecha;
    private String HoraInicio;
    private String Direccion;
    private String Estrato;
    private String Latitud;
    private String Longitud;
    private int CantidadFamilias;


    private int idHogar;
    private Familia familia;


    public Hogar(int numeroEncusta, String fecha, String horaInicio, String direccion,String estrato, String latitud, String longitud, int cantidadFamilias, int idHogar,Familia familia) {
        NumeroEncusta = numeroEncusta;
        Fecha = fecha;
        HoraInicio = horaInicio;
        Direccion = direccion;
        Estrato = estrato;
        Latitud = latitud;
        Longitud = longitud;
        CantidadFamilias = cantidadFamilias;
        this.idHogar = idHogar;
        this.familia = familia;
    }

    public Hogar() {

    }

    public int getIdHogar() {
        return idHogar;
    }

    public void setIdHogar(int idHogar) {
        this.idHogar = idHogar;
    }

    public int getNumeroEncusta() {
        return NumeroEncusta;
    }

    public void setNumeroEncusta(int numeroEncusta) {
        NumeroEncusta = numeroEncusta;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getHoraInicio() {
        return HoraInicio;
    }

    public void setHoraInicio(String horaInicio) {
        HoraInicio = horaInicio;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }


    public String getEstrato() {
        return Estrato;
    }

    public void setEstrato(String estrato) {
        Estrato = estrato;
    }

    public String getLatitud() {
        return Latitud;
    }

    public void setLatitud(String latitud) {
        Latitud = latitud;
    }

    public String getLongitud() {
        return Longitud;
    }

    public void setLongitud(String longitud) {
        Longitud = longitud;
    }

    public int getCantidadFamilias() {
        return CantidadFamilias;
    }

    public void setCantidadFamilias(int cantidadFamilias) {
        CantidadFamilias = cantidadFamilias;
    }

    public Familia getFamilia(Familia familia) {
        return familia;
    }

    public void setFamilia(Familia familia) {
        this.familia = familia;
    }
}
