package com.encuesta.rightway.encuesta;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.encuesta.rightway.clases.CordenadasBarrios;
import com.encuesta.rightway.clases.Familia;
import com.encuesta.rightway.clases.Global;
import com.encuesta.rightway.clases.Hogar;
import com.encuesta.rightway.clases.Integrante;
import com.encuesta.rightway.clases.database;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Calendar;

public class ViajesTipicos extends AppCompatActivity {

    private int  mHour, mMinute;
    private int  mHour2, mMinute2;
    private EditText txtTime;
    private EditText txtTime2;

    private Spinner Proposito;
    private Spinner Modo;

    private String st_proposito;
    private String st_modo;
    private String st_tipoTransporte;

    private ImageButton L, M,MI,J,V,S,D;
    private int contL, contM, contMI, contJ, contV, contS, contD;
    private int falgOrigenDestino;
    private EditText Origen;
    private EditText Destino;
    private EditText otro_motivoViaje;

    private LinearLayout Trans_Privado;
    private LinearLayout Trans_Publico;
    private LinearLayout layout_tiempoParada;
    private Spinner spinner_Parqueo;
    private Spinner spinner_CaminoInicio;
    private Spinner spinner_TiempoInicio;
    private Spinner spinner_CaminoFin;
    private RadioGroup radioGroup_Transbordo;
    private RadioGroup radioGroup_Ayuda;
    private Spinner spinner_TiempoTransbordo;
    private Spinner spinner_CostoViaje;
    private Spinner Localidad;
    private Spinner Localidad2;
    private Spinner SpinnerBarrio;
    private Spinner SpinnerBarrio2;



    private String st_parqueo;
    private String st_caminoInicio;
    private String st_TiempoInicio;
    private String st_caminoFin;
    private String st_transbordo;
    private String st_tiempoTransbordo;
    private String st_costoViaje;
    private String st_Localidad;
    private String st_Localidad2;
    private String st_Barrio = "nada";
    private String st_Barrio2 = "nada";
    private String st_NecesitaAyuda = "nada";

    private String sexo;
    private String edad;
    private String estudio;
    private String ocupacion;
    private String economico;
    private String direccion;

    private String Lunes ="NO";
    private String Martes ="NO";
    private String Miercoles ="NO";
    private String Jueves ="NO";
    private String Viernes ="NO";
    private String Sabado ="NO";
    private String Domingo ="NO";


    Familia familia1;
    private TextView integranteFam;
    private Global global;

    private database mySQLiteAdapter;
    Cursor cursor;
    private static final String SAMPLE_DB_NAME = "MY_DATABASE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viajes_tipicos);
        global = (Global) getApplicationContext();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();


                if(st_proposito.equalsIgnoreCase("Otros"))st_proposito = otro_motivoViaje.getText().toString();

                if (Lunes.equalsIgnoreCase("NO") && Martes.equalsIgnoreCase("NO") && Miercoles.equalsIgnoreCase("NO") && Jueves.equalsIgnoreCase("NO") &&
                        Viernes.equalsIgnoreCase("NO") && Sabado.equalsIgnoreCase("NO") && Domingo.equalsIgnoreCase("NO")) {

                    Toast.makeText(getApplicationContext(), "Seleccione los dias de la semana que realiza el viale...", Toast.LENGTH_LONG).show();


                }else if(Origen.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(getApplicationContext(), "Seleccione dirección de Origen..", Toast.LENGTH_LONG).show();
                }else if(Destino.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(), "Seleccione dirección Destino..", Toast.LENGTH_LONG).show();
                }else if(txtTime.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(), "Seleccione Hora Inicio..", Toast.LENGTH_LONG).show();
                }else if(txtTime2.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(), "Seleccione Hora Llegada..", Toast.LENGTH_LONG).show();
                }else if(st_modo.equalsIgnoreCase("Seleccione")){
                    Toast.makeText(getApplicationContext(), "Seleccione Modo de Transporte..", Toast.LENGTH_LONG).show();
                }else if(st_proposito.equalsIgnoreCase("Seleccione")||st_proposito.equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(), "Seleccione Motivo Viaje..", Toast.LENGTH_LONG).show();
                } else if(st_costoViaje.equalsIgnoreCase("Seleccione")) {
                    Toast.makeText(getApplicationContext(), "Seleccione Costo Viaje..", Toast.LENGTH_LONG).show();
                }else if(st_NecesitaAyuda.equalsIgnoreCase("nada")){
                    Toast.makeText(getApplicationContext(), "Seleccione Si necesita Ayuda..", Toast.LENGTH_LONG).show();
                }else if(txtTime.getText().toString().equalsIgnoreCase(txtTime2.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Las Hora de Inicio tiene que ser diferente a la hora de llegada..", Toast.LENGTH_LONG).show();
                }else {

                    String diasTotales = Lunes + "|" + Martes + "|" + Miercoles + "|" + Jueves + "|" + Viernes + "|" + Sabado + "|" + Domingo;
                    String datosTransporte = "";

                    Integrante integrante = new Integrante(sexo, edad, estudio, ocupacion, economico, direccion, Origen.getText().toString(),
                            txtTime.getText().toString(), Destino.getText().toString(), txtTime2.getText().toString(), st_proposito, st_modo,
                            diasTotales, st_tipoTransporte, datosTransporte,st_NecesitaAyuda,  st_costoViaje);


                    global.getFamilia().getArrayIntegrante().add(integrante);
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


                    global.getLstIntegrantes().add(integrante);
                    Gson gson = new Gson();
                    String json = gson.toJson(global.getLstIntegrantes());




                    Log.i("Encuesta", " -----------Integrantes-----------------------  " + sharedPreferences.getInt(String.valueOf(Constants.ContadorIntegrantes), 0));
                    int cont = sharedPreferences.getInt(String.valueOf(Constants.ContadorIntegrantes), 0);
                    cont++;

                    Log.i("Encuesta", " -----------Integrantes-----------------------  " + cont);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt(String.valueOf(Constants.ContadorIntegrantes), cont);
                    editor.putString(Constants.JsonIntegrantes, json);
                    editor.commit();

                    int CantIntegrantes = Integer.parseInt(sharedPreferences.getString(Constants.CantidadIntegrantes, ""));

                    /**********************************************************************************/
                    /***************************    SE GUARDA EN LA BASE DE DATOS    ******************/
                    /**********************************************************************************/
                    mySQLiteAdapter = new database(getApplication());
                    mySQLiteAdapter.openToWrite();
                    cursor = mySQLiteAdapter.HogaresAll();
                    cursor.requery();

                    String IDHogar = sharedPreferences.getString(Constants.IdHogar, "");
                    String IDFamilia = sharedPreferences.getString(Constants.IdFamilia, "");

                    mySQLiteAdapter.insertIntegrante(IDHogar, IDFamilia, sexo, edad, estudio, ocupacion, economico, direccion, Origen.getText().toString(),
                            txtTime.getText().toString(), Destino.getText().toString(), txtTime2.getText().toString(), st_proposito, st_modo,
                            diasTotales, st_tipoTransporte, datosTransporte, st_NecesitaAyuda, st_costoViaje);
                    /**********************************************************************************/
                    /**********************************************************************************/
                    if (sharedPreferences.getInt(String.valueOf(Constants.ContadorIntegrantes), 0) <= CantIntegrantes) {
                        Toast.makeText(getApplicationContext(), " Se debe seguir agregando Integrantes", Toast.LENGTH_LONG).show();

                        Intent activity = new Intent();
                        activity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        activity.setClass(getApplicationContext(), IntegranteFamilia.class);
                        getApplicationContext().startActivity(activity);
                        finish();

                    } else {
                        editor.putInt(String.valueOf(Constants.ContadorIntegrantes), 0);
                        editor.commit();
                        int cantFamilias = Integer.parseInt(sharedPreferences.getString(Constants.CantidadFamilias, ""));
                        int famSelec = Integer.parseInt(sharedPreferences.getString(Constants.SeleccionFamilia, ""));


                        if (famSelec < cantFamilias) {
                            Toast.makeText(getApplicationContext(), " Se debe Revisar si hay mas Familias", Toast.LENGTH_LONG).show();
                            int selfam = famSelec + 1;
                            editor.putString(Constants.SeleccionFamilia, "" + selfam);
                            editor.commit();



                            Familia familia = new Familia();
                            familia.setCuantosVehiculos(Integer.parseInt(sharedPreferences.getString(Constants.CantidadVehiculos, "")));
                            familia.setNumeroIntegrante(Integer.parseInt(sharedPreferences.getString(Constants.CantidadIntegrantes, "")));
                            familia.setArrayVehiculos(global.getLstVehiculos());
                            familia.setArrayIntegrante(global.getLstIntegrantes());

                            global.getFamilia().getArrayFamilia().add(familia);



                            Gson gson2 = new Gson();
                            String json2 = gson2.toJson(global.getFamilia());
                            editor.putString(Constants.JsonFamilia, json2);

                            global.getLstFamilias().clear();
                            global.getLstIntegrantes().clear();
                            global.getLstVehiculos().clear();

                            editor.putString(Constants.CantidadVehiculos, "0");
                            editor.putString(Constants.CantidadIntegrantes, "0");
                            editor.putString(Constants.CantidadIntegrantes, "0");





                            editor.commit();

                            Intent activity = new Intent();
                            activity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            activity.setClass(getApplicationContext(), FamiliasActivity.class);
                            getApplicationContext().startActivity(activity);
                            finish();

                        } else {
                            editor.putString(Constants.CantidadFamilias, "0");
                            editor.putString(Constants.SeleccionFamilia, "0");
                            editor.putInt(String.valueOf(Constants.ContadorIntegrantes), 0);
                            editor.commit();

                            EnviarInformacion();

                            Toast.makeText(getApplicationContext(), " Se Finaliza el Hogar Y se exporta la Base de Datos", Toast.LENGTH_LONG).show();
                            exportDB();
                            finish();
                        }


                    }

                }

                //ConfirmaAccion();
                /***********************************************************************************/
                /**********************************************************************************/
            }
        });


        Bundle bundle = getIntent().getExtras();
        sexo = bundle.getString("Sexo");
        edad= bundle.getString("Edad");
        estudio= bundle.getString("Estudio");
        ocupacion= bundle.getString("Ocupacion");
        economico= bundle.getString("Economico");
        direccion= bundle.getString("Direccion");


        txtTime = (EditText) findViewById(R.id.txtTime);
        txtTime2 = (EditText) findViewById(R.id.txtTime2);
        Origen = (EditText) findViewById(R.id.editDirOrigen);
        Destino= (EditText) findViewById(R.id.editDirDestino);
        Proposito = (Spinner) findViewById(R.id.spinner_proposito);
        otro_motivoViaje=(EditText) findViewById(R.id.edt_otro_motivoViaje);
        Modo = (Spinner) findViewById(R.id.spinner_modo);
        L = (ImageButton)findViewById(R.id.buttonL);
        M = (ImageButton)findViewById(R.id.buttonM);
        MI = (ImageButton)findViewById(R.id.buttonMI);
        J = (ImageButton)findViewById(R.id.buttonJ);
        V = (ImageButton)findViewById(R.id.buttonV);
        S = (ImageButton)findViewById(R.id.buttonS);
        D = (ImageButton)findViewById(R.id.buttonD);


        Trans_Privado = (LinearLayout) findViewById(R.id.layout_privado);
        Trans_Publico = (LinearLayout) findViewById(R.id.layout_publico);
        layout_tiempoParada= (LinearLayout) findViewById(R.id.layout_tiempoParada);
        spinner_Parqueo = (Spinner) findViewById(R.id.spinner_parqueo);
        spinner_CaminoInicio = (Spinner) findViewById(R.id.spinner_caminoinicio);
        spinner_TiempoInicio = (Spinner) findViewById(R.id.spinner_timpoespera);
        spinner_CaminoFin = (Spinner) findViewById(R.id.spinner_caminofin);
        radioGroup_Transbordo= (RadioGroup) findViewById(R.id.radioTransbordo);
        radioGroup_Ayuda= (RadioGroup) findViewById(R.id.radioAyuda);
        spinner_TiempoTransbordo = (Spinner) findViewById(R.id.spinner_tiempotransbordo);
        spinner_CostoViaje = (Spinner) findViewById(R.id.spinner_costoViaje);
        Localidad= (Spinner) findViewById(R.id.spinner_localidad);
        Localidad2= (Spinner) findViewById(R.id.spinner_localidad2);
        SpinnerBarrio = (Spinner) findViewById(R.id.spinner_Barrio);
        SpinnerBarrio2 = (Spinner) findViewById(R.id.spinner_Barrio2);

        integranteFam = (TextView) findViewById(R.id.integranteFam);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Log.i("Encuesta", " ------------------------------------  " + sharedPreferences.getInt(String.valueOf(Constants.ContadorIntegrantes), 0));

        integranteFam.setText("INTEGRANTE # " + sharedPreferences.getInt(String.valueOf(Constants.ContadorIntegrantes), 0) + "\n" +
                "DE LA FAMILIA # " + sharedPreferences.getString(Constants.SeleccionFamilia, ""));

        /******************************************************************************************/
        /******************************************************************************************/
        /******************************************************************************************/
        ArrayAdapter spinnerProposito = ArrayAdapter.createFromResource(this, R.array.Proposito, R.layout.spinner_item);
        Proposito.setAdapter(spinnerProposito);
        addListenerOnSpinnerItemProposito();

        ArrayAdapter spinnerModo = ArrayAdapter.createFromResource(this, R.array.Modo, R.layout.spinner_item);
        Modo.setAdapter(spinnerModo);
        addListenerOnSpinnerItemModo();

        ArrayAdapter spinnerspinner_Parqueo = ArrayAdapter.createFromResource(this, R.array.Parqueo, R.layout.spinner_item);
        spinner_Parqueo.setAdapter(spinnerspinner_Parqueo);
        addListenerOnSpinnerItemspinner_Parqueo();


        ArrayAdapter<String> spinnerspinner_CaminoInicio = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.Camino_Destino_metros));
        spinner_CaminoInicio.setAdapter(spinnerspinner_CaminoInicio);
        addListenerOnSpinnerItemsspinner_CaminoInicio();

        ArrayAdapter<String> spinnerspinner_TiempoInicio = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.Camino_Destino_minutos));
        spinner_TiempoInicio.setAdapter(spinnerspinner_TiempoInicio);
        addListenerOnSpinnerItemsspinner_TiempoInicio();

        ArrayAdapter<String> spinnerspinner_CaminoFin = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.Camino_Destino_metros));
        spinner_CaminoFin.setAdapter(spinnerspinner_CaminoFin);
        addListenerOnSpinnerItemsspinner_CaminoFin();

        ArrayAdapter<String> spinnerspinner_TiempoTransbordo = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.Camino_Destino_minutos));
        spinner_TiempoTransbordo.setAdapter(spinnerspinner_TiempoTransbordo);
        addListenerOnSpinnerItemsspinner_TiempoTransbordo();


        ArrayAdapter spinnerspinner_CostoViaje = ArrayAdapter.createFromResource(this, R.array.Costo_Viaje, R.layout.spinner_item);
        spinner_CostoViaje.setAdapter(spinnerspinner_CostoViaje);
        addListenerOnSpinnerItemsspinner_CostoViaje();


        ArrayAdapter spinnerLocalidad = ArrayAdapter.createFromResource(this, R.array.Localidad_Neiva, R.layout.spinner_item);
        Localidad.setAdapter(spinnerLocalidad);
        addListenerOnSpinnerItemLocalidad();

        ArrayAdapter spinnerLocalidad2 = ArrayAdapter.createFromResource(this, R.array.Localidad_Neiva, R.layout.spinner_item);
        Localidad2.setAdapter(spinnerLocalidad2);
        addListenerOnSpinnerItemLocalidad2();
        /******************************************************************************************/
        /******************************************************************************************/
        radioGroup_Transbordo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioTransbordoSI) {
                    st_tipoTransporte = "SI";
                    layout_tiempoParada.setVisibility(View.VISIBLE);
                } else if (checkedId == R.id.radioTransbordoNO) {
                    st_tipoTransporte = "NO";
                    layout_tiempoParada.setVisibility(View.GONE);
                }
            }
        });
        radioGroup_Ayuda.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioAyudaSI) {
                    st_NecesitaAyuda = "SI";
                } else if (checkedId == R.id.radioAyudaNO) {
                    st_NecesitaAyuda = "NO";
                }
            }
        });

    }

    /**********************************************************************************************/
    public void ConfirmaAccion(){
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(ViajesTipicos.this);
        builderSingle.setIcon(R.drawable.common_plus_signin_btn_icon_light_normal);
        builderSingle.setTitle("Que desea Realizar?");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ViajesTipicos.this,
                android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("Agregar Integrante");
        arrayAdapter.add("Agregar Familia");
        arrayAdapter.add("Finalizar Hogar");

        builderSingle.setNegativeButton("cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builderSingle.setAdapter(
                arrayAdapter,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String strName = arrayAdapter.getItem(which);
                        AlertDialog.Builder builderInner = new AlertDialog.Builder(ViajesTipicos.this);
                        builderInner.setMessage(strName);
                        builderInner.setTitle("Su seleccion es:");
                        builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(strName.equalsIgnoreCase("Agregar Integrante")){
                                    Toast.makeText(getApplicationContext(), "Se debe Agregar Integrante", Toast.LENGTH_LONG).show();


                                    Log.i("Encuesta", " -----------Integrantes-----------------------  " + familia1.getNumeroIntegrante());
                                    Log.i("Encuesta", " -----------Integrantes-----------------------  " + familia1.getCuantosVehiculos());

                                    for(int i=0; i< familia1.getCuantosVehiculos(); i++){
                                        Log.i("Encuesta", " -----------TipoV-----------------------  " + familia1.getArrayVehiculos().get(i).getTipo());
                                        Log.i("Encuesta", " -----------TipoM-----------------------  " + familia1.getArrayVehiculos().get(i).getMatricula());
                                        Log.i("Encuesta", " -----------TipoP-----------------------  " + familia1.getArrayVehiculos().get(i).getParqueadero());
                                        Log.i("Encuesta", " -----------TipoP-----------------------  " + familia1.getArrayVehiculos().get(i).getPropiedad());
                                    }


                                    for(int i=0; i< familia1.getArrayIntegrante().size();i++){
                                        Log.i("Encuesta", " -----------IntegranteS-----------------------  " + familia1.getArrayIntegrante().get(i).getSexo());
                                        Log.i("Encuesta", " -----------IntegranteE-----------------------  " + familia1.getArrayIntegrante().get(i).getEdad());
                                        Log.i("Encuesta", " -----------IntegranteD-----------------------  " + familia1.getArrayIntegrante().get(i).getDias());
                                    }





                                    Intent activity = new Intent();
                                    activity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    activity.setClass(getApplicationContext(), IntegranteFamilia.class);
                                    getApplicationContext().startActivity(activity);
                                    finish();

                                }else if(strName.equalsIgnoreCase("Agregar Familia")){
                                    Toast.makeText(getApplicationContext(), "Se debe Agregar Familia", Toast.LENGTH_LONG).show();


                                    Familia familia = new Familia(familia1.getNumeroIntegrante(),familia1.getCuantosVehiculos(),
                                            familia1.getArrayVehiculos(), familia1.getArrayIntegrante());


                                    familia1.getArrayFamilia().add(familia);


                                    for(int i=0; i< familia1.getArrayFamilia().size(); i++){

                                        Log.i("Encuesta", " -----------Familias-----------------------  " + familia1.getArrayFamilia().get(i).getCuantosVehiculos());
                                    }


                                    Intent activity = new Intent();
                                    activity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    activity.setClass(getApplicationContext(), FamiliasActivity.class);
                                    getApplicationContext().startActivity(activity);
                                    finish();

                                }else if(strName.equalsIgnoreCase("Finalizar Hogar")){
                                    Toast.makeText(getApplicationContext(), "Se debe Finalizar Hogar", Toast.LENGTH_LONG).show();


                                    Familia familia = new Familia(familia1.getNumeroIntegrante(),familia1.getCuantosVehiculos(),
                                            familia1.getArrayVehiculos(), familia1.getArrayIntegrante());
                                    familia1.getArrayFamilia().add(familia);


                                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                                    Log.i("Encuesta", " -----------Datos del Hogar-----------------------  " + sharedPreferences.getString(Constants.DireccionGoogle, ""));
                                    Log.i("Encuesta", " -----------El Hogar Tiene-----------------------  " + familia1.getArrayFamilia().size() + " Familias");
                                    for(int i=0; i< familia1.getArrayFamilia().size(); i++){
                                        Log.i("Encuesta", " -----------El Hogar Tiene-----------------------  " + familia1.getArrayFamilia().get(i).getNumeroIntegrante());
                                        Log.i("Encuesta", " -----------El Hogar Tiene-----------------------  " + familia1.getArrayFamilia().get(i).getCuantosVehiculos());


                                        Log.i("Encuesta", " -----------Numero de Vehiculos-----------------------  " + familia1.getArrayFamilia().get(i).getArrayVehiculos().size());

                                        for(int j=0; j<  familia1.getArrayFamilia().get(i).getArrayVehiculos().size(); j++){
                                            Log.i("Encuesta", " ----------------VEHICULOS ------------------------  " + familia1.getArrayVehiculos().get(j).getTipo());
                                            Log.i("Encuesta", " ----------------VEHICULOS ------------------------  " + familia1.getArrayVehiculos().get(j).getPropiedad());
                                            Log.i("Encuesta", " ----------------VEHICULOS ------------------------  " + familia1.getArrayVehiculos().get(j).getMatricula());
                                            Log.i("Encuesta", " ----------------VEHICULOS ------------------------  " + familia1.getArrayVehiculos().get(j).getParqueadero());
                                        }

                                        //Log.i("Encuesta", " ----------La Familia:   " + i + " Tiene "  + familia1.getArrayFamilia().get(i).getArrayVehiculos().size() );
                                        //Log.i("Encuesta", " ----------La Familia:   " + i + " Tiene "  + familia1.getArrayFamilia().get(i).getArrayIntegrante().size() + " Integrantes");
                                    }

                                    finish();
                                }

                                dialog.dismiss();
                            }
                        });
                        builderInner.show();
                    }
                });
        builderSingle.show();


    }
    /**********************************************************************************************/
    /**********************************************************************************************/
    public void addListenerOnSpinnerItemspinner_Parqueo(){
        spinner_Parqueo.setOnItemSelectedListener(new CustomOnItemSelectedListenerspinner_Parqueo());
    }
    public class CustomOnItemSelectedListenerspinner_Parqueo implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
            st_parqueo = parent.getItemAtPosition(pos).toString();
        }
        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

    public void addListenerOnSpinnerItemsspinner_CaminoInicio(){
        spinner_CaminoInicio.setOnItemSelectedListener(new CustomOnItemSelectedListenerspinner_CaminoInicio());
    }
    public class CustomOnItemSelectedListenerspinner_CaminoInicio implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
            st_caminoInicio = parent.getItemAtPosition(pos).toString();
        }
        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

    public void addListenerOnSpinnerItemsspinner_TiempoInicio(){
        spinner_TiempoInicio.setOnItemSelectedListener(new CustomOnItemSelectedListenerspinner_TiempoInicio());
    }
    public class CustomOnItemSelectedListenerspinner_TiempoInicio implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
            st_TiempoInicio = parent.getItemAtPosition(pos).toString();
        }
        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

    public void addListenerOnSpinnerItemsspinner_CaminoFin(){
        spinner_CaminoFin.setOnItemSelectedListener(new CustomOnItemSelectedListenerspinner_CaminoFin());
    }
    public class CustomOnItemSelectedListenerspinner_CaminoFin implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
            st_caminoFin = parent.getItemAtPosition(pos).toString();
        }
        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

    public void addListenerOnSpinnerItemsspinner_TiempoTransbordo(){
        spinner_TiempoTransbordo.setOnItemSelectedListener(new CustomOnItemSelectedListenerspinner_TiempoTransbordo());
    }
    public class CustomOnItemSelectedListenerspinner_TiempoTransbordo implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
            st_tiempoTransbordo = parent.getItemAtPosition(pos).toString();
        }
        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }


    public void addListenerOnSpinnerItemProposito(){
        Proposito.setOnItemSelectedListener(new CustomOnItemSelectedListenerProposito());
    }
    public class CustomOnItemSelectedListenerProposito implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
            st_proposito = parent.getItemAtPosition(pos).toString();
            if(st_proposito.equalsIgnoreCase("Otros")){
                otro_motivoViaje.setVisibility(View.VISIBLE);
            }else{
                otro_motivoViaje.setVisibility(View.GONE);
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }
    public void addListenerOnSpinnerItemModo(){
        Modo.setOnItemSelectedListener(new CustomOnItemSelectedListenerModo());
    }
    public class CustomOnItemSelectedListenerModo implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
            st_modo = parent.getItemAtPosition(pos).toString();
        }
        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }
    public void addListenerOnSpinnerItemsspinner_CostoViaje(){
        spinner_CostoViaje.setOnItemSelectedListener(new CustomOnItemSelectedListenerCostoViaje());
    }
    public class CustomOnItemSelectedListenerCostoViaje implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
            st_costoViaje = parent.getItemAtPosition(pos).toString();
        }
        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }


    public void addListenerOnSpinnerItemLocalidad() {
        Localidad.setOnItemSelectedListener(new CustomOnItemSelectedListenerLocalidad());
    }
    public class CustomOnItemSelectedListenerLocalidad implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            st_Localidad = parent.getItemAtPosition(pos).toString();

            if (!st_Localidad.equalsIgnoreCase("SELECCIONE")) {

                /*
                    Bellavista
                    2.909425, -75.271137
                    Canaima
                    2.901877, -75.276203
                    Prado Alto
                    2.929984, -75.268054
                    Las Catleyas
                    2.936552, -75.266457
                    Los Andes
                    2.945127, -75.284925
                    Tercer Milenio
                    2.968186, -75.290137
                 */

                String Latitud = "";
                String Longitud = "";
                if (st_Localidad.equalsIgnoreCase("NOROCCIDENTAL")) {

                    ArrayAdapter<String> spinner_Barrio = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,
                            getResources().getStringArray(R.array.Noroccidental));
                    SpinnerBarrio.setAdapter(spinner_Barrio);
                    addListenerOnSpinnerItemSelectionSpiner_Barrio();


                } else if (st_Localidad.equalsIgnoreCase("NORORIENTAL")) {
                    ArrayAdapter<String> spinner_Barrio = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,
                            getResources().getStringArray(R.array.Nororiental));
                    SpinnerBarrio.setAdapter(spinner_Barrio);
                    addListenerOnSpinnerItemSelectionSpiner_Barrio();
                } else if (st_Localidad.equalsIgnoreCase("ENTRE RIOS")) {
                    ArrayAdapter<String> spinner_Barrio = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,
                            getResources().getStringArray(R.array.Entre_Rios));
                    SpinnerBarrio.setAdapter(spinner_Barrio);
                    addListenerOnSpinnerItemSelectionSpiner_Barrio();
                } else if (st_Localidad.equalsIgnoreCase("CENTRAL")) {
                    ArrayAdapter<String> spinner_Barrio = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,
                            getResources().getStringArray(R.array.Central));
                    SpinnerBarrio.setAdapter(spinner_Barrio);
                    addListenerOnSpinnerItemSelectionSpiner_Barrio();
                } else if (st_Localidad.equalsIgnoreCase("ORIENTAL")) {
                    ArrayAdapter<String> spinner_Barrio = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,
                            getResources().getStringArray(R.array.Oriental));
                    SpinnerBarrio.setAdapter(spinner_Barrio);
                    addListenerOnSpinnerItemSelectionSpiner_Barrio();
                } else if (st_Localidad.equalsIgnoreCase("SUR")) {
                    ArrayAdapter<String> spinner_Barrio = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,
                            getResources().getStringArray(R.array.Sur));
                    SpinnerBarrio.setAdapter(spinner_Barrio);
                    addListenerOnSpinnerItemSelectionSpiner_Barrio();
                } else if (st_Localidad.equalsIgnoreCase("CENTRO ORIENTAL")) {
                    ArrayAdapter<String> spinner_Barrio = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,
                            getResources().getStringArray(R.array.Centro_Oriental));
                    SpinnerBarrio.setAdapter(spinner_Barrio);
                    addListenerOnSpinnerItemSelectionSpiner_Barrio();
                } else if (st_Localidad.equalsIgnoreCase("SUR ORIENTAL")) {
                    ArrayAdapter<String> spinner_Barrio = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,
                            getResources().getStringArray(R.array.Sur_Oriental));
                    SpinnerBarrio.setAdapter(spinner_Barrio);
                    addListenerOnSpinnerItemSelectionSpiner_Barrio();
                } else if (st_Localidad.equalsIgnoreCase("NORTE")) {
                    ArrayAdapter<String> spinner_Barrio = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,
                            getResources().getStringArray(R.array.Norte));
                    SpinnerBarrio.setAdapter(spinner_Barrio);
                    addListenerOnSpinnerItemSelectionSpiner_Barrio();
                } else if (st_Localidad.equalsIgnoreCase("LAS PALMAS")) {
                    ArrayAdapter<String> spinner_Barrio = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,
                            getResources().getStringArray(R.array.Las_Palmas));
                    SpinnerBarrio.setAdapter(spinner_Barrio);
                    addListenerOnSpinnerItemSelectionSpiner_Barrio();
                }


            }


        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {

        }
    }

    public void addListenerOnSpinnerItemSelectionSpiner_Barrio() {
        SpinnerBarrio.setOnItemSelectedListener(new CustomOnItemSelectedListenerBarrio());
    }

    public class CustomOnItemSelectedListenerBarrio implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            st_Barrio = parent.getItemAtPosition(pos).toString();
            String Latitud = "";
            String Longitud = "";


            if (!st_Barrio.equalsIgnoreCase("SELECCIONE")) {

                CordenadasBarrios cordenadasBarrios = new CordenadasBarrios();
                String Cordenadas = cordenadasBarrios.RetornaCordenadas(st_Barrio);

                Log.i("Seguimiento", "  ============Cordenadas:  " + Cordenadas);


                String[] cordenadas1 = st_Barrio.split("\\(");
                //String[] cordenadas2 = cordenadas1[1].split("\\,");
                String[] cordenadas2 = Cordenadas.split("\\,");

                Latitud = cordenadas2[1];
                Latitud = Latitud.replace(")", "");

                Longitud = cordenadas2[0];
                Longitud = Longitud.replace("(", "");


                Log.i("Seguimiento", "  ============Latitud:  " + Latitud);
                Log.i("Seguimiento", "  ============Longitud:  " + Longitud);
                falgOrigenDestino=1;
                Intent activity = new Intent();
                activity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.setClass(getApplicationContext(), MapsActivityDireccion.class);
                activity.putExtra("Latitud", Latitud);
                activity.putExtra("Longitud", Longitud);
                getApplicationContext().startActivity(activity);
            }


        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

    public void addListenerOnSpinnerItemLocalidad2() {
        Localidad2.setOnItemSelectedListener(new CustomOnItemSelectedListenerLocalidad2());
    }
    public class CustomOnItemSelectedListenerLocalidad2 implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            st_Localidad2 = parent.getItemAtPosition(pos).toString();

            if (!st_Localidad2.equalsIgnoreCase("SELECCIONE")) {

                /*
                    Bellavista
                    2.909425, -75.271137
                    Canaima
                    2.901877, -75.276203
                    Prado Alto
                    2.929984, -75.268054
                    Las Catleyas
                    2.936552, -75.266457
                    Los Andes
                    2.945127, -75.284925
                    Tercer Milenio
                    2.968186, -75.290137
                 */

                String Latitud = "";
                String Longitud = "";
                if (st_Localidad2.equalsIgnoreCase("NOROCCIDENTAL")) {

                    ArrayAdapter<String> spinner_Barrio = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,
                            getResources().getStringArray(R.array.Noroccidental));
                    SpinnerBarrio2.setAdapter(spinner_Barrio);
                    addListenerOnSpinnerItemSelectionSpiner_Barrio2();


                } else if (st_Localidad2.equalsIgnoreCase("NORORIENTAL")) {
                    ArrayAdapter<String> spinner_Barrio = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,
                            getResources().getStringArray(R.array.Nororiental));
                    SpinnerBarrio2.setAdapter(spinner_Barrio);
                    addListenerOnSpinnerItemSelectionSpiner_Barrio2();
                } else if (st_Localidad2.equalsIgnoreCase("ENTRE RIOS")) {
                    ArrayAdapter<String> spinner_Barrio = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,
                            getResources().getStringArray(R.array.Entre_Rios));
                    SpinnerBarrio2.setAdapter(spinner_Barrio);
                    addListenerOnSpinnerItemSelectionSpiner_Barrio2();
                } else if (st_Localidad2.equalsIgnoreCase("CENTRAL")) {
                    ArrayAdapter<String> spinner_Barrio = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,
                            getResources().getStringArray(R.array.Central));
                    SpinnerBarrio2.setAdapter(spinner_Barrio);
                    addListenerOnSpinnerItemSelectionSpiner_Barrio2();
                } else if (st_Localidad2.equalsIgnoreCase("ORIENTAL")) {
                    ArrayAdapter<String> spinner_Barrio = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,
                            getResources().getStringArray(R.array.Oriental));
                    SpinnerBarrio2.setAdapter(spinner_Barrio);
                    addListenerOnSpinnerItemSelectionSpiner_Barrio2();
                } else if (st_Localidad2.equalsIgnoreCase("SUR")) {
                    ArrayAdapter<String> spinner_Barrio = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,
                            getResources().getStringArray(R.array.Sur));
                    SpinnerBarrio2.setAdapter(spinner_Barrio);
                    addListenerOnSpinnerItemSelectionSpiner_Barrio2();
                } else if (st_Localidad2.equalsIgnoreCase("CENTRO ORIENTAL")) {
                    ArrayAdapter<String> spinner_Barrio = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,
                            getResources().getStringArray(R.array.Centro_Oriental));
                    SpinnerBarrio2.setAdapter(spinner_Barrio);
                    addListenerOnSpinnerItemSelectionSpiner_Barrio2();
                } else if (st_Localidad2.equalsIgnoreCase("SUR ORIENTAL")) {
                    ArrayAdapter<String> spinner_Barrio = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,
                            getResources().getStringArray(R.array.Sur_Oriental));
                    SpinnerBarrio2.setAdapter(spinner_Barrio);
                    addListenerOnSpinnerItemSelectionSpiner_Barrio2();
                } else if (st_Localidad2.equalsIgnoreCase("NORTE")) {
                    ArrayAdapter<String> spinner_Barrio = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,
                            getResources().getStringArray(R.array.Norte));
                    SpinnerBarrio2.setAdapter(spinner_Barrio);
                    addListenerOnSpinnerItemSelectionSpiner_Barrio2();
                } else if (st_Localidad2.equalsIgnoreCase("LAS PALMAS")) {
                    ArrayAdapter<String> spinner_Barrio = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,
                            getResources().getStringArray(R.array.Las_Palmas));
                    SpinnerBarrio2.setAdapter(spinner_Barrio);
                    addListenerOnSpinnerItemSelectionSpiner_Barrio2();
                }


            }


        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {

        }
    }

    public void addListenerOnSpinnerItemSelectionSpiner_Barrio2() {
        SpinnerBarrio2.setOnItemSelectedListener(new CustomOnItemSelectedListenerBarrio2());
    }

    public class CustomOnItemSelectedListenerBarrio2 implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            st_Barrio = parent.getItemAtPosition(pos).toString();
            String Latitud = "";
            String Longitud = "";


            if (!st_Barrio.equalsIgnoreCase("SELECCIONE")) {

                CordenadasBarrios cordenadasBarrios = new CordenadasBarrios();
                String Cordenadas = cordenadasBarrios.RetornaCordenadas(st_Barrio);

                Log.i("Seguimiento", "  ============Cordenadas:  " + Cordenadas);


                String[] cordenadas1 = st_Barrio.split("\\(");
                //String[] cordenadas2 = cordenadas1[1].split("\\,");
                String[] cordenadas2 = Cordenadas.split("\\,");

                Latitud = cordenadas2[1];
                Latitud = Latitud.replace(")", "");

                Longitud = cordenadas2[0];
                Longitud = Longitud.replace("(", "");


                Log.i("Seguimiento", "  ============Latitud:  " + Latitud);
                Log.i("Seguimiento", "  ============Longitud:  " + Longitud);
                falgOrigenDestino=2;
                Intent activity = new Intent();
                activity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.setClass(getApplicationContext(), MapsActivityDireccion.class);
                activity.putExtra("Latitud", Latitud);
                activity.putExtra("Longitud", Longitud);
                getApplicationContext().startActivity(activity);
            }


        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

    /**********************************************************************************************/
    public void HoraInicio (View v){
        // Process to get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog tpd = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        // Display Selected time in textbox

                        if(hourOfDay < 10){
                            if(minute< 10){
                                txtTime.setText("0"+hourOfDay + ":" +"0"+ minute);
                            }else{
                                txtTime.setText("0"+hourOfDay + ":" + minute);
                            }
                        }else{
                            if(minute< 10){
                                txtTime.setText(hourOfDay + ":" +"0"+ minute);
                            }else{
                                txtTime.setText(hourOfDay + ":" + minute);
                            }
                        }


                    }
                }, mHour, mMinute, false);
        tpd.show();
    }

    public void HoraLlegada (View v){
        // Process to get Current Time
        final Calendar c2 = Calendar.getInstance();
        mHour2 = c2.get(Calendar.HOUR_OF_DAY);
        mMinute2 = c2.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog tpd2 = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // Display Selected time in textbox
                        if(hourOfDay < 10){
                            if(minute< 10){
                                txtTime2.setText("0"+hourOfDay + ":" +"0"+ minute);
                            }else{
                                txtTime2.setText("0"+hourOfDay + ":" + minute);
                            }
                        }else{
                            if(minute< 10){
                                txtTime2.setText(hourOfDay + ":" +"0"+ minute);
                            }else{
                                txtTime2.setText(hourOfDay + ":" + minute);
                            }
                        }
                    }
                }, mHour2, mMinute2, false);
        tpd2.show();
    }

    public void BuscarDireccionOrigen(View v){
        falgOrigenDestino=1;
        Intent activity = new Intent();
        activity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.setClass(getApplicationContext(), MapsActivityDireccion.class);
        activity.putExtra("Latitud", "2.909425");
        activity.putExtra("Longitud", "-75.271137");
        getApplicationContext().startActivity(activity);
    }
    public void BuscarDireccionDestino(View v){
        falgOrigenDestino=2;
        Intent activity = new Intent();
        activity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.setClass(getApplicationContext(), MapsActivityDireccion.class);
        activity.putExtra("Latitud", "2.909425" );
        activity.putExtra("Longitud", "-75.271137");
        getApplicationContext().startActivity(activity);
    }

    public void DirCasaO(View v){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Origen.setText(sharedPreferences.getString(Constants.DireccionHogar, ""));
    }
    public void DirOfO(View v){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Origen.setText(sharedPreferences.getString(Constants.DireccionOfEst, ""));
    }
    public void DirCasaD(View v){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Destino.setText(sharedPreferences.getString(Constants.DireccionHogar, ""));
    }
    public void DirOfD(View v){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Destino.setText(sharedPreferences.getString(Constants.DireccionOfEst, ""));
    }






    public void L(View v){
        contL++;
        if(contL==1){
            Lunes="SI";
            L.setImageDrawable(this.getResources().getDrawable(R.drawable.luneson));
        }else if(contL>1){
            contL=0;
            Lunes="NO";
            L.setImageDrawable(this.getResources().getDrawable(R.drawable.lunesoff));
        }
    }
    public void M(View v){
        contM++;
        if(contM==1){
            Martes="SI";
            M.setImageDrawable(this.getResources().getDrawable(R.drawable.marteson));
        }else if(contM>1){
            contM=0;
            Martes="NO";
            M.setImageDrawable(this.getResources().getDrawable(R.drawable.martesoff));
        }
    }
    public void MI(View v){
        contMI++;
        if(contMI==1){
            Miercoles="SI";
            MI.setImageDrawable(this.getResources().getDrawable(R.drawable.miercoleson));
        }else if(contMI>1){
            contMI=0;
            Miercoles="NO";
            MI.setImageDrawable(this.getResources().getDrawable(R.drawable.miercolesoff));
        }
    }
    public void J(View v){
        contJ++;
        if(contJ==1){
            Jueves="SI";
            J.setImageDrawable(this.getResources().getDrawable(R.drawable.jueveson));
        }else if(contJ>1){
            contJ=0;
            Jueves="NO";
            J.setImageDrawable(this.getResources().getDrawable(R.drawable.juevesoff));
        }
    }
    public void V(View v){
        contV++;
        if(contV==1){
            Viernes="SI";
            V.setImageDrawable(this.getResources().getDrawable(R.drawable.vierneson));
        }else if(contV>1){
            contV=0;
            Viernes="NO";
            V.setImageDrawable(this.getResources().getDrawable(R.drawable.viernesoff));
        }
    }
    public void S(View v){
        contS++;
        if(contS==1){
            Sabado="SI";
            S.setImageDrawable(this.getResources().getDrawable(R.drawable.sabadoon));
        }else if(contS>1){
            contS=0;
            Sabado="NO";
            S.setImageDrawable(this.getResources().getDrawable(R.drawable.sabadooff));
        }
    }
    public void D(View v){
        contD++;
        if(contD==1){
            Domingo="SI";
            D.setImageDrawable(this.getResources().getDrawable(R.drawable.domingoon));
        }else if(contD>1){
            contD=0;
            Domingo="NO";
            D.setImageDrawable(this.getResources().getDrawable(R.drawable.domingooff));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("Encuesta", "onResume");
        try {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

            if(falgOrigenDestino==1 || falgOrigenDestino==2) {
                if (falgOrigenDestino == 1) {
                    Origen.setText(sharedPreferences.getString(Constants.DireccionGoogle, ""));
                } else {
                    Destino.setText(sharedPreferences.getString(Constants.DireccionGoogle, ""));
                }
            }else{
                Origen.setText("");
                Destino.setText("");
            }
        }catch (Exception e){

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(startMain);
                return true;
            case KeyEvent.KEYCODE_HOME:
                Log.i("Encuesta","Se Oprimio el Boton de Back");
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exportDB(){
        File sd = Environment.getExternalStorageDirectory();
        File data = Environment.getDataDirectory();
        FileChannel source=null;
        FileChannel destination=null;
        String currentDBPath = "/data/"+ "com.encuesta.rightway.encuesta" +"/databases/"+SAMPLE_DB_NAME;
        String backupDBPath = SAMPLE_DB_NAME;
        File currentDB = new File(data, currentDBPath);
        File backupDB = new File(sd, backupDBPath);
        try {
            source = new FileInputStream(currentDB).getChannel();
            destination = new FileOutputStream(backupDB).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();
            Toast.makeText(this, "DB Exported!", Toast.LENGTH_LONG).show();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void EnviarInformacion(){

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        Familia familia = new Familia();
        familia.setCuantosVehiculos(Integer.parseInt(sharedPreferences.getString(Constants.CantidadVehiculos, "")));
        familia.setNumeroIntegrante(Integer.parseInt(sharedPreferences.getString(Constants.CantidadIntegrantes, "")));




        Hogar hogar = new Hogar();
        hogar.setCantidadFamilias(Integer.parseInt(sharedPreferences.getString(Constants.CantidadIntegrantes, "")));
        hogar.setEstrato(sharedPreferences.getString(Constants.Estrato, ""));
        hogar.setDireccion(sharedPreferences.getString(Constants.Direccion, ""));
        hogar.setNumeroEncusta(Integer.parseInt(sharedPreferences.getString(Constants.NumeroEncuesta, "")));
        hogar.setFecha(sharedPreferences.getString(Constants.Fecha, ""));
        hogar.setHoraInicio(sharedPreferences.getString(Constants.HoraInicio, ""));
        hogar.setIdHogar(Integer.parseInt(sharedPreferences.getString(Constants.IdHogar, "")));
        hogar.setLatitud(sharedPreferences.getString(Constants.LatitudHogar, ""));
        hogar.setLongitud(sharedPreferences.getString(Constants.LongitudHogar, ""));
        hogar.setFamilia(global.getFamilia());


        Gson gson3 = new Gson();
        String finalhogar = gson3.toJson(hogar);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.JsonHogar, finalhogar);
        editor.commit();


        Log.i("Encuesta", " ===========================  " + sharedPreferences.getString(Constants.JsonHogar, ""));

    }
}
