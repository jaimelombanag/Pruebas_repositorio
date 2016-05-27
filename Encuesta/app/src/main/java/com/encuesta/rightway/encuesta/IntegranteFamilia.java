package com.encuesta.rightway.encuesta;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.encuesta.rightway.clases.CordenadasBarrios;
import com.encuesta.rightway.clases.Familia;

public class IntegranteFamilia extends AppCompatActivity {


    private RadioGroup Sexo;
    private Spinner Edad;
    private Spinner NivelEstudio;
    private Spinner SituacionProfecional;
    private Spinner SectorEconomico;
    private Spinner SpinnerLocalidad;
    private Spinner SpinnerBarrio;
    private EditText Direccion;

    private String st_sexo="nada";
    private String st_edad="nada";
    private String st_NivelEstudio="nada";
    private String st_SituacionProfecional= "nada";
    private String st_sectorEconomico="";
    private String st_Localidad = "nada";
    private String st_Barrio = "nada";

    private TextView integranteFam;
    private LinearLayout layout_sector_economico;
    private EditText otro_sector_economico;
    private int flagDireccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integrante_familia);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        Sexo = (RadioGroup) findViewById(R.id.groupSexo);
        Edad = (Spinner) findViewById(R.id.spinner_Edad);
        NivelEstudio = (Spinner) findViewById(R.id.spinner_NEstudio);
        SituacionProfecional = (Spinner) findViewById(R.id.spinner_SProfecional);
        SectorEconomico = (Spinner) findViewById(R.id.spinner_SEconomico);
        Direccion = (EditText) findViewById(R.id.edit_Direccion);
        SpinnerLocalidad = (Spinner) findViewById(R.id.spinner_Localidad);
        SpinnerBarrio = (Spinner) findViewById(R.id.spinner_Barrio);
        integranteFam = (TextView) findViewById(R.id.integranteFam);
        layout_sector_economico = (LinearLayout)findViewById(R.id.layout_sector_economico);
        otro_sector_economico= (EditText)findViewById(R.id.edt_otro_sectoreconomico);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Log.i("Encuesta", " ------------------------------------  " + sharedPreferences.getInt(String.valueOf(Constants.ContadorIntegrantes), 0));
        integranteFam.setText("INTEGRANTE # " + sharedPreferences.getInt(String.valueOf(Constants.ContadorIntegrantes), 0) + "\n" +
                "DE LA FAMILIA # " + sharedPreferences.getString(Constants.SeleccionFamilia, ""));


        Sexo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioMasculino) {
                    st_sexo = ("Masculino");
                } else if (checkedId == R.id.radioFemenino) {
                    st_sexo = ("Femenino");
                }
            }
        });
        /******************************************************************************************/
        /******************************************************************************************/
        /******************************************************************************************/

        ArrayAdapter spinnerEdad = ArrayAdapter.createFromResource(this, R.array.Edad, R.layout.spinner_item);
        Edad.setAdapter(spinnerEdad);
        addListenerOnSpinnerItemEdad();


        ArrayAdapter spinnerNivelEstudio = ArrayAdapter.createFromResource(this, R.array.Nivel_Estudio, R.layout.spinner_item);
        NivelEstudio.setAdapter(spinnerNivelEstudio);
        addListenerOnSpinnerItemNivelEstudio();

        ArrayAdapter spinnerSituacionProfecional = ArrayAdapter.createFromResource(this, R.array.Situacion_Profesional, R.layout.spinner_item);
        SituacionProfecional.setAdapter(spinnerSituacionProfecional);
        addListenerOnSpinnerItemSituacionProfecional();

        ArrayAdapter spinnerSectorEconomico = ArrayAdapter.createFromResource(this, R.array.Sector_Economico, R.layout.spinner_item);
        SectorEconomico.setAdapter(spinnerSectorEconomico);
        addListenerOnSpinnerItemSectorEconomico();

        ArrayAdapter spinnerLocalidad = ArrayAdapter.createFromResource(this, R.array.Localidad_Neiva, R.layout.spinner_item);
        SpinnerLocalidad.setAdapter(spinnerLocalidad);
        addListenerOnSpinnerItemSelectionLocalidad();



    }

    /**********************************************************************************************/
    public void addListenerOnSpinnerItemEdad(){
        Edad.setOnItemSelectedListener(new CustomOnItemSelectedListenerEdad());
    }
    public class CustomOnItemSelectedListenerEdad implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
            st_edad = parent.getItemAtPosition(pos).toString();
        }
        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }
    public void addListenerOnSpinnerItemNivelEstudio(){
        NivelEstudio.setOnItemSelectedListener(new CustomOnItemSelectedListenerNivelEstudio());
    }
    public class CustomOnItemSelectedListenerNivelEstudio implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
            st_NivelEstudio = parent.getItemAtPosition(pos).toString();
        }
        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }
    public void addListenerOnSpinnerItemSituacionProfecional(){
        SituacionProfecional.setOnItemSelectedListener(new CustomOnItemSelectedListenerSituacionProfecional());
    }
    public class CustomOnItemSelectedListenerSituacionProfecional implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
            st_SituacionProfecional = parent.getItemAtPosition(pos).toString();

            if(st_SituacionProfecional.contains("Trabaja")){
                layout_sector_economico.setVisibility(View.VISIBLE);
            }else{
                layout_sector_economico.setVisibility(View.GONE);
            }

        }
        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }
    public void addListenerOnSpinnerItemSectorEconomico(){
        SectorEconomico.setOnItemSelectedListener(new CustomOnItemSelectedListenerSectorEconomico());
    }
    public class CustomOnItemSelectedListenerSectorEconomico implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
            st_sectorEconomico = parent.getItemAtPosition(pos).toString();
            if(st_sectorEconomico.equalsIgnoreCase("Otro")){
                otro_sector_economico.setVisibility(View.VISIBLE);
            }else{
                otro_sector_economico.setVisibility(View.GONE);
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

    public void addListenerOnSpinnerItemSelectionLocalidad() {
        SpinnerLocalidad.setOnItemSelectedListener(new CustomOnItemSelectedListenerLocalidad());
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

                flagDireccion=1;
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
    public void BuscarDireccion(View v){
        Intent activity = new Intent();
        activity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.setClass(getApplicationContext(), MapsActivityDireccion.class);
        activity.putExtra("Latitud", "2.909425" );
        activity.putExtra("Longitud", "-75.271137");
        getApplicationContext().startActivity(activity);
    }

    public void AgregarViaje(View v){

        if(st_sexo.equalsIgnoreCase("nada")){
            Toast.makeText(getApplicationContext(), "Seleccione Tipo de Sexo...", Toast.LENGTH_LONG).show();
        }else if(st_edad.equalsIgnoreCase("Seleccione")){
            Toast.makeText(getApplicationContext(), "Seleccione Edad...", Toast.LENGTH_LONG).show();
        }else if(st_NivelEstudio.equalsIgnoreCase("Seleccione")){
            Toast.makeText(getApplicationContext(), "Seleccione Nivel de Estudios...", Toast.LENGTH_LONG).show();
        }else if(st_SituacionProfecional.equalsIgnoreCase("Seleccione")){
            Toast.makeText(getApplicationContext(), "Seleccione Ocupacion...", Toast.LENGTH_LONG).show();
        }else if(Direccion.getText().toString().equalsIgnoreCase("")){
            Toast.makeText(getApplicationContext(), "Seleccione Dirección...", Toast.LENGTH_LONG).show();
        }else if(st_SituacionProfecional.contains("Trabaja")) {
            if(st_sectorEconomico.equalsIgnoreCase("Otro")){
                if(otro_sector_economico.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(getApplicationContext(), "Seleccione Otro Sector Económico...", Toast.LENGTH_LONG).show();
                }else{
                    GuardaInformacion();
                }
            }else{
                GuardaInformacion();
            }
        }else {
            GuardaInformacion();
        }
    }

    public void GuardaInformacion(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.DireccionOfEst, Direccion.getText().toString());
        editor.commit();

        Intent activity = new Intent();
        activity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.setClass(getApplicationContext(), ViajesTipicos.class);
        activity.putExtra("Sexo", st_sexo);
        activity.putExtra("Edad", st_edad);
        activity.putExtra("Estudio", st_NivelEstudio);
        activity.putExtra("Ocupacion", st_SituacionProfecional);
        activity.putExtra("Economico", st_sectorEconomico);
        activity.putExtra("Direccion", Direccion.getText().toString());
        getApplicationContext().startActivity(activity);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("Encuesta", "onResume");
        try {
            if (flagDireccion == 1) {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Direccion.setText(sharedPreferences.getString(Constants.DireccionGoogle, ""));
            }else{
                Direccion.setText("");
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

}
