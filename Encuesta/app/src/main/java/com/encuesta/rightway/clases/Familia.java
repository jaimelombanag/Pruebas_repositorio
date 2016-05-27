package com.encuesta.rightway.clases;

import android.app.Application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jaime on 16/04/2016.
 */
public class Familia {


    private int NumeroIntegrante;
    private int CuantosVehiculos;

    //variables declaration
    private  ArrayList<Vehiculos> arrayVehiculos;
    private  ArrayList<Integrante> arrayIntegrante;

    private  ArrayList<Familia> arrayFamilia;

    public Familia(int numeroIntegrante, int cuantosVehiculos, ArrayList<Vehiculos> arrayVehiculos, ArrayList<Integrante> arrayIntegrante) {
        this.NumeroIntegrante = numeroIntegrante;
        this.CuantosVehiculos = cuantosVehiculos;
        this.arrayVehiculos = arrayVehiculos;
        this.arrayIntegrante = arrayIntegrante;
    }

    public Familia() {
        NumeroIntegrante = 0;
        arrayVehiculos=new ArrayList<Vehiculos>();
        arrayIntegrante=new ArrayList<Integrante>();
        arrayFamilia=new ArrayList<Familia>();
    }


    public ArrayList<Integrante> getArrayIntegrante() {
        return arrayIntegrante;
    }

    public void setArrayIntegrante(ArrayList<Integrante> arrayIntegrante) {
        this.arrayIntegrante = arrayIntegrante;
    }

    public ArrayList<Vehiculos> getArrayVehiculos() {
        return arrayVehiculos;
    }

    public void setArrayVehiculos(ArrayList<Vehiculos> arrayVehiculos) {
        this.arrayVehiculos = arrayVehiculos;
    }


    public int getNumeroIntegrante() {
        return NumeroIntegrante;
    }

    public void setNumeroIntegrante(int numeroIntegrante) {
        NumeroIntegrante = numeroIntegrante;
    }


    public int getCuantosVehiculos() {
        return CuantosVehiculos;
    }

    public void setCuantosVehiculos(int cuantosVehiculos) {
        CuantosVehiculos = cuantosVehiculos;
    }

    public ArrayList<Familia> getArrayFamilia() {
        return arrayFamilia;
    }

    public void setArrayFamilia(ArrayList<Familia> arrayFamilia) {
        this.arrayFamilia = arrayFamilia;
    }
}
