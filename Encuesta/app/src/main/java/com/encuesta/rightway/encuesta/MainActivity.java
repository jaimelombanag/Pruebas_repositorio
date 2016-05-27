package com.encuesta.rightway.encuesta;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.encuesta.rightway.clases.CordenadasBarrios;
import com.encuesta.rightway.clases.Hogar;
import com.encuesta.rightway.clases.database;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import android.os.Environment;

public class MainActivity extends AppCompatActivity {

    private String TAG = "Seguimiento";
    private LocationManager location;
    private Spinner Estrato;
    private Spinner TotalFamilias;
    private Spinner SeleccioneFamlia;
    private Spinner SpinnerCiudad;
    private Spinner SpinnerLocalidad;
    private Spinner SpinnerBarrio;

    private String st_Ciudad = "nada";
    private String st_Localidad = "nada";
    private String st_Barrio = "nada";
    private String st_totalFamilias;
    private String st_estrato;
    private String st_seleccioneFamlia="1";
    private EditText Direccion;
    private int flagDireccion;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    private database mySQLiteAdapter;
    Cursor cursor;
    private static final String SAMPLE_DB_NAME = "MY_DATABASE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        doingVerifications();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Se Guarda la Informacion del Hogar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


                if(st_estrato.equalsIgnoreCase("SELECCIONE")){
                    Toast.makeText(getApplicationContext(), "Seleccione el Estrato...", Toast.LENGTH_LONG).show();
                }else if(st_totalFamilias.equalsIgnoreCase("SELECCIONE")){
                    Toast.makeText(getApplicationContext(), "Seleccione Cuantas Familias...", Toast.LENGTH_LONG).show();
                }else{
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    Date d = new Date();
                    CharSequence fechaHora = android.text.format.DateFormat.format("yyyy-MM-dd hh:mm:ss", d.getTime());

                    String[] separaHora = fechaHora.toString().split("\\ ");
//                Log.i("Encuesta", " ----------------------------------------  " + fechaHora.toString());
//                Log.i("Encuesta", " --------------FECHA  -------------------  " + separaHora[0]);
//                Log.i("Encuesta", " --------------HORA----------------------  " + separaHora[1]);
//
//                Hogar hogar = new Hogar();
//                hogar.setNumeroEncusta(0001);
//                hogar.setFecha(separaHora[0]);
//                hogar.setHoraInicio(separaHora[1]);
//                hogar.setDireccion(Direccion.getText().toString());
//                hogar.setEstrato(st_estrato);
//                hogar.setLatitud(sharedPreferences.getString(Constants.Latitud, ""));
//                hogar.setLongitud(sharedPreferences.getString(Constants.Longitud, ""));
//                hogar.setCantidadFamilias(Integer.parseInt(st_totalFamilias));
//
                    int idHogar = (int)(Math.random()*(65535-0))+0;
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(Constants.NumeroEncuesta, "001");
                    editor.putString(Constants.Fecha, separaHora[0]);
                    editor.putString(Constants.HoraInicio, separaHora[1]);
                    editor.putString(Constants.Direccion, Direccion.getText().toString());
                    editor.putString(Constants.Estrato, st_estrato);
                    editor.putString(Constants.LatitudHogar, sharedPreferences.getString(Constants.Latitud, ""));
                    editor.putString(Constants.LongitudHogar, sharedPreferences.getString(Constants.Longitud, ""));
                    editor.putString(Constants.CantidadFamilias, st_totalFamilias);
                    editor.putString(Constants.DireccionHogar, Direccion.getText().toString());
                    editor.putString(Constants.SeleccionFamilia, st_seleccioneFamlia);
                    editor.putString(Constants.IdHogar, ""+idHogar);
                    editor.commit();





                    Log.i("Encuesta", " ===========================   " + idHogar);
                    Log.i("Encuesta", " ===========================   " + fechaHora.toString());

                    /**********************************************************************************/
                    /***************************    SE GUARDA EN LA BASE DE DATOS    ******************/
                    /**********************************************************************************/
                    mySQLiteAdapter = new database(getApplication());
                    mySQLiteAdapter.openToWrite();
                    cursor = mySQLiteAdapter.HogaresAll();
                    cursor.requery();

                    mySQLiteAdapter.insertHogar("" + idHogar, fechaHora.toString(), Direccion.getText().toString(), st_estrato, st_totalFamilias);
                    /**********************************************************************************/
                    /**********************************************************************************/

                    Intent activity = new Intent();
                    activity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    activity.setClass(getApplicationContext(), FamiliasActivity.class);
                    getApplicationContext().startActivity(activity);
                    finish();
                }

            }
        });


        SpinnerCiudad = (Spinner) findViewById(R.id.spinner_Ciudad);
        SpinnerLocalidad = (Spinner) findViewById(R.id.spinner_Localidad);
        SpinnerBarrio = (Spinner) findViewById(R.id.spinner_Barrio);

        TotalFamilias = (Spinner) findViewById(R.id.spinner_Totalfamilias);
        SeleccioneFamlia = (Spinner) findViewById(R.id.spinner_SelecioneFamilia);
        Direccion = (EditText) findViewById(R.id.edit_DirHogar);
        Estrato = (Spinner) findViewById(R.id.spinner_estrato);

        /******************************************************************************************/
        /******************************************************************************************/
        /******************************************************************************************/
//        ArrayAdapter<String> spinnerCiudad = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,
//                getResources().getStringArray(R.array.Ciudades));
//        spinnerCiudad.setDropDownViewResource(R.layout.spinner_item);

        ArrayAdapter spinnerCiudad = ArrayAdapter.createFromResource(this, R.array.Ciudades, R.layout.spinner_item);
        SpinnerCiudad.setAdapter(spinnerCiudad);
        addListenerOnSpinnerItemSelectionCiudad();

//        ArrayAdapter<String> spinnerLocalidad = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,
//                getResources().getStringArray(R.array.Localidad_Neiva));
//        SpinnerLocalidad.setAdapter(spinnerLocalidad);

        ArrayAdapter spinnerLocalidad = ArrayAdapter.createFromResource(this, R.array.Localidad_Neiva, R.layout.spinner_item);
        SpinnerLocalidad.setAdapter(spinnerLocalidad);
        addListenerOnSpinnerItemSelectionLocalidad();

//        ArrayAdapter<String> spinnerFamilias = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,
//                getResources().getStringArray(R.array.Total_Familias));
//        TotalFamilias.setAdapter(spinnerFamilias);


        ArrayAdapter spinnerFamilias = ArrayAdapter.createFromResource(this, R.array.Total_Familias, R.layout.spinner_item);
        TotalFamilias.setAdapter(spinnerFamilias);
        addListenerOnSpinnerItemTotalFamilias();


//        ArrayAdapter<String> spinnerEstratod = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,
//                getResources().getStringArray(R.array.Estrato));
//        Estrato.setAdapter(spinnerEstratod);


        ArrayAdapter spinnerEstratod = ArrayAdapter.createFromResource(this, R.array.Estrato, R.layout.spinner_item);
        Estrato.setAdapter(spinnerEstratod);
        addListenerOnSpinnerItemEstrato();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();


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


    /**
     * Realiza comprobaciones previas para certificar que el dispositivo esta configurado
     * y apto para el uso de la aplicación
     *
     * Valida GPS activo
     * Valida registro de usuario
     */
    protected void doingVerifications() {
        if (! isGPSActivated()) {
            Log.i(TAG, "GPS no Activado, el usuario debe de activarlo");
            showWindowEnabledGPS();
        }

        if (! isGPSActivated2()) {
            Log.i(TAG, "GPS no Activado, el usuario debe de activarlo");
            showWindowEnabledGPS();
        }
    }

    /**
     * Consulta los Proveedores de Ubicacion disponibles, validadndo que este el GPS
     *
     * @return {@link Boolean}
     */

    private boolean isGPSActivated2() {
        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        location = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Log.i(getClass().getSimpleName(), "Los Proveedores de Ubicacion son: " + provider);

        return location.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
    private boolean isGPSActivated() {
        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        location = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Log.i(getClass().getSimpleName(), "Los Proveedores de Ubicacion son: " + provider);

        return location.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    private void showWindowEnabledGPS() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle(getString(R.string.app_name));
        alertBuilder.setMessage(getString(R.string.text_gps_disabled));
        alertBuilder.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                Log.i(getClass().getSimpleName(), "Detectada la tecla de retroceder en la pantalla de dialogo");
                Log.i(getClass().getSimpleName(), "Se da por terminada la App");

                finish();
            }
        });
        alertBuilder.setPositiveButton("SI", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i(getClass().getSimpleName(), "Ejecutando la Pantalla de configuracion del sistema para activar el GPS");

                Intent setting = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(setting, 0);
            }
        });

        AlertDialog dialog = alertBuilder.create();
        dialog.show();
    }

    /**********************************************************************************************/
    /**********************************************************************************************/
    /**********************************************************************************************/

    public void addListenerOnSpinnerItemSelectionCiudad() {
        SpinnerCiudad.setOnItemSelectedListener(new CustomOnItemSelectedListenerCiudad());
    }

    public class CustomOnItemSelectedListenerCiudad implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            st_Ciudad = parent.getItemAtPosition(pos).toString();

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

                flagDireccion= 1;
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



    public void addListenerOnSpinnerItemTotalFamilias() {
        TotalFamilias.setOnItemSelectedListener(new CustomOnItemSelectedListenerTotalFamilias());
    }
    public class CustomOnItemSelectedListenerTotalFamilias implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            st_totalFamilias = parent.getItemAtPosition(pos).toString();
            SpinnerFamilas();
        }
        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

    public void addListenerOnSpinnerItemEstrato() {
        Estrato.setOnItemSelectedListener(new CustomOnItemSelectedListenerEstrato());
    }
    public class CustomOnItemSelectedListenerEstrato implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            st_estrato = parent.getItemAtPosition(pos).toString();
        }
        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

    public void addListenerOnSpinnerItemSelectionSeleccioneFamlia() {
        SeleccioneFamlia.setOnItemSelectedListener(new CustomOnItemSelectedListenerSelecionf());
    }
    public class CustomOnItemSelectedListenerSelecionf implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            st_seleccioneFamlia = parent.getItemAtPosition(pos).toString();
        }
        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }



    /**********************************************************************************************/
    /**********************************************************************************************/
    /**********************************************************************************************/


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.encuesta.rightway.encuesta/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.encuesta.rightway.encuesta/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }




    public void SpinnerFamilas() {

        if(!st_totalFamilias.equalsIgnoreCase("SELECCIONE")){
            List<String> list = new ArrayList<String>();
            try {
                int cantFamilias = Integer.parseInt(st_totalFamilias);

                for (int i = 1; i <= cantFamilias; i++) {
                    list.add("" + i);
                }

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
                dataAdapter.setDropDownViewResource
                        (android.R.layout.simple_spinner_dropdown_item);

                SeleccioneFamlia.setAdapter(dataAdapter);
                // Spinner item selection Listener
                addListenerOnSpinnerItemSelectionSeleccioneFamlia();
            }catch (Exception e){

            }

        }
    }

    public void BuscarDireccion(View v) {
        Intent activity = new Intent();
        activity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.setClass(getApplicationContext(), MapsActivityDireccion.class);
//        activity.putExtra("Latitud", Latitud);
//        activity.putExtra("Longitud", Longitud);
        getApplicationContext().startActivity(activity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//
//            //Toast.makeText(getApplicationContext(), "Holaaaaaaaaaaaaaaaaaaaa" , Toast.LENGTH_LONG).show();
//
//            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
//            alertBuilder.setTitle("Exportar Base de Datos");
//            alertBuilder.setMessage("Desea Exportar la base de Datos al Telefono o Tablet?");
//            alertBuilder.setOnCancelListener(new DialogInterface.OnCancelListener() {
//
//                @Override
//                public void onCancel(DialogInterface dialog) {
//
//                }
//            });
//            alertBuilder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
//
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    exportDB();
//                }
//            });
//
//            alertBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
//
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//
//                }
//            });
//
//            AlertDialog dialog = alertBuilder.create();
//            dialog.show();
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("Encuesta", "onResume");
        try {
            if(flagDireccion==1){
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Direccion.setText(sharedPreferences.getString(Constants.DireccionGoogle, ""));
            }else{
                Direccion.setText("");
            }
        } catch (Exception e) {

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
                Log.i(TAG,"Se Oprimio el Boton de Back");
        }
        return super.onKeyDown(keyCode, event);
    }
}
