package com.encuesta.rightway.clases;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by jalombanag on 14/05/2016.
 */
public class Global extends Application{




    private Hogar hogar;
    private Familia familia;
    private ArrayList<Vehiculos> lstVehiculos;
    private ArrayList<Integrante> lstIntegrantes;
    private ArrayList<Familia> lstFamilias;
    private SharedPreferences preference;
    private SharedPreferences.Editor editor;
    private Context context;




    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        preference= PreferenceManager.getDefaultSharedPreferences(context);
        editor = preference.edit();
        lstVehiculos = new ArrayList<>();
        lstIntegrantes= new ArrayList<>();
    }


    public Hogar getHogar() {
        return hogar;
    }

    public void setHogar(Hogar hogar) {
        this.hogar = hogar;
    }

    public Familia getFamilia() {
        return familia;
    }

    public void setFamilia(Familia familia) {
        this.familia = familia;
    }

    public ArrayList<Vehiculos> getLstVehiculos() {
        return lstVehiculos;
    }

    public void setLstVehiculos(ArrayList<Vehiculos> lstVehiculos) {
        this.lstVehiculos = lstVehiculos;
    }

    public ArrayList<Integrante> getLstIntegrantes() {
        return lstIntegrantes;
    }

    public void setLstIntegrantes(ArrayList<Integrante> lstIntegrantes) {
        this.lstIntegrantes = lstIntegrantes;
    }

    public SharedPreferences getPreference() {
        return preference;
    }

    public void setPreference(SharedPreferences preference) {
        this.preference = preference;
    }

    public SharedPreferences.Editor getEditor() {
        return editor;
    }

    public void setEditor(SharedPreferences.Editor editor) {
        this.editor = editor;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<Familia> getLstFamilias() {
        return lstFamilias;
    }

    public void setLstFamilias(ArrayList<Familia> lstFamilias) {
        this.lstFamilias = lstFamilias;
    }
}
