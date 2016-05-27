package com.encuesta.rightway.encuesta;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.encuesta.rightway.clases.Familia;
import com.encuesta.rightway.clases.Global;
import com.encuesta.rightway.clases.Hogar;
import com.encuesta.rightway.clases.Vehiculos;
import com.encuesta.rightway.clases.database;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class FamiliasActivity extends AppCompatActivity {



    private Spinner Numero_Residentes;
    private Spinner Cantidad_Vehiculos;

    private String st_numeroResidentes;
    private String st_cantidadVehiculos;

    private String[] st_TipoVehiculo= new String[5];
    private String[] st_PropiedadVrhiculo= new String[5];
    private String[] st_MatriculaVehiculo= new String[5];
    private String[] st_ParuqeoVehiculo= new String[5];
    private String[] st_CilindrajeVehiculo= new String[5];
    private String[] st_CombustibleVehiculo= new String[5];
    /****** Layout Vehiculo  ***************/
    private ViewGroup layout_vehiculo;
    private ScrollView scrollView;
    private Spinner Tipo_Vehiculo;
    private Spinner Propiedad_Vehiculo;
    private Spinner Matricula_Vehiculo;
    private Spinner Parqueo_Vehiculo;

    private int contvehiculos;

    private TextView Nfamilia;
    private Global global;


    private database mySQLiteAdapter;
    Cursor cursor;


    /*****Para listar los Vehiculos ******/
    EditText[] otro_tipo = new EditText[5];
    EditText[] otro_combustible= new EditText[5];
    EditText[] otro_parqueo= new EditText[5];
    EditText[] otro_matricula= new EditText[5];
    EditText[] otro_propiedad= new EditText[5];
    LinearLayout[] layout_liviano = new LinearLayout[5];
    LinearLayout[] layout_moto = new LinearLayout[5];
    LinearLayout relativeLayout;
    /**************************************/
    private int flagTipoVehiculo;
    private int flagCombustibleVehiculo;
    private int flagPropiedadVehiculo;
    private int flagParqueoVehiculo;
    private int flagMatriculaVehiculo;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_familias);
        global = (Global) getApplicationContext();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Se debe Guardarla Informacion de la Familia", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


                if(st_numeroResidentes.equalsIgnoreCase("SELECCIONE")){
                    Toast.makeText(getApplicationContext(), "Seleccione Número de Integrantes...", Toast.LENGTH_LONG).show();
                }else{


                    int cantVehiculos = Integer.parseInt(st_cantidadVehiculos);

                    for(int i=0; i< cantVehiculos; i++) {
                        if (st_TipoVehiculo[i].equals("Otro")){
                            if(otro_tipo[i].getText().toString().equals("")){
                                flagTipoVehiculo=3;
                            }else{
                                flagTipoVehiculo=0;
                            }
                        }
                        if (st_CombustibleVehiculo[i].equals("Otro")){
                            if(otro_combustible[i].getText().toString().equals("")){
                                flagCombustibleVehiculo=3;
                            }else{
                                flagCombustibleVehiculo=0;
                            }
                        }
                        if (st_PropiedadVrhiculo[i].equals("Otro")){
                            if(otro_propiedad[i].getText().toString().equals("")){
                                flagPropiedadVehiculo=3;
                            }else{
                                flagPropiedadVehiculo=0;
                            }
                        }
                        if (st_MatriculaVehiculo[i].equals("Otro")){
                            if(otro_matricula[i].getText().toString().equals("")){
                                flagMatriculaVehiculo=3;
                            }else{
                                flagMatriculaVehiculo=0;
                            }
                        }
                        if (st_ParuqeoVehiculo[i]== null)st_ParuqeoVehiculo[i]="";
                        if (st_ParuqeoVehiculo[i].equals("Otro")){
                            if(otro_parqueo[i].getText().toString().equals("")){
                                flagParqueoVehiculo=3;
                            }else{
                                flagParqueoVehiculo=0;
                            }
                        }
                    }


                    if(flagTipoVehiculo==3){
                        Toast.makeText(getApplicationContext(), "Complete Otro tipo de Vehículo...", Toast.LENGTH_LONG).show();
                    }else if(flagCombustibleVehiculo==3){
                        Toast.makeText(getApplicationContext(), "Complete Otro Combustible de Vehículo...", Toast.LENGTH_LONG).show();
                    }else if(flagPropiedadVehiculo==3){
                        Toast.makeText(getApplicationContext(), "Complete Otra Propiedad de Vehículo...", Toast.LENGTH_LONG).show();
                    }else if(flagMatriculaVehiculo==3){
                        Toast.makeText(getApplicationContext(), "Complete Otra Matrícula de Vehículo...", Toast.LENGTH_LONG).show();
                    }else if(flagParqueoVehiculo==3){
                        Toast.makeText(getApplicationContext(), "Complete Otro Parqueo de Vehículo...", Toast.LENGTH_LONG).show();
                    }else{



                        for(int i=0; i< cantVehiculos; i++) {
                            if (st_TipoVehiculo[i].equals("Seleccione")){
                                flagTipoVehiculo=4;
                            }else{
                                flagTipoVehiculo=0;
                            }
                            if (st_CombustibleVehiculo[i].equals("Seleccione")){
                                    flagCombustibleVehiculo=4;
                            }else{
                                flagCombustibleVehiculo=0;
                            }
                            if (st_PropiedadVrhiculo[i].equals("Seleccione")){
                                    flagPropiedadVehiculo=4;
                            }else{
                                flagPropiedadVehiculo=0;
                            }
                            if (st_MatriculaVehiculo[i].equals("Seleccione")){
                                    flagMatriculaVehiculo=4;
                            }else{
                                flagMatriculaVehiculo=0;
                            }
                            if (st_ParuqeoVehiculo[i]== null)st_ParuqeoVehiculo[i]="";
                            if (st_ParuqeoVehiculo[i].equals("Seleccione")){
                                flagParqueoVehiculo=4;
                            }else{
                                flagParqueoVehiculo=0;
                             }
                        }


                        if(flagTipoVehiculo==4){
                            Toast.makeText(getApplicationContext(), "Seleccione tipo de Vehículo...", Toast.LENGTH_LONG).show();
                        }else if(flagCombustibleVehiculo==4){
                            Toast.makeText(getApplicationContext(), "Seleccione Combustible de Vehículo...", Toast.LENGTH_LONG).show();
                        }else if(flagPropiedadVehiculo==4){
                            Toast.makeText(getApplicationContext(), "Seleccione Propiedad de Vehículo...", Toast.LENGTH_LONG).show();
                        }else if(flagMatriculaVehiculo==4){
                            Toast.makeText(getApplicationContext(), "Seleccione Matrícula de Vehículo...", Toast.LENGTH_LONG).show();
                        }else if(flagParqueoVehiculo==4){
                            Toast.makeText(getApplicationContext(), "Complete Otro Parqueo de Vehículo...", Toast.LENGTH_LONG).show();
                        }else {



                            Log.i("Encuesta", " ============N Res===============   " + st_numeroResidentes);
                            Log.i("Encuesta", " ============N Veh===============   " + st_cantidadVehiculos);

                            //global.getFamilia().setNumeroIntegrante(Integer.parseInt(st_numeroResidentes));
                            //global.getFamilia().setCuantosVehiculos(Integer.parseInt(st_cantidadVehiculos));

                            int idFamilia = (int) (Math.random() * (65535 - 0)) + 0;
                            Log.i("Encuesta", " ===========================   " + idFamilia);
                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(Constants.CantidadIntegrantes, st_numeroResidentes);
                            editor.putString(Constants.CantidadVehiculos, st_cantidadVehiculos);
                            editor.putInt(String.valueOf(Constants.ContadorIntegrantes), 1);
                            editor.putString(Constants.IdFamilia, "" + idFamilia);
                            editor.commit();

                            /**********************************************************************************/
                            /***************************    SE GUARDA EN LA BASE DE DATOS    ******************/
                            /**********************************************************************************/
                            mySQLiteAdapter = new database(getApplication());
                            mySQLiteAdapter.openToWrite();
                            cursor = mySQLiteAdapter.HogaresAll();
                            cursor.requery();

                            String IDHogar = sharedPreferences.getString(Constants.IdHogar, "");
                            mySQLiteAdapter.insertFamilia(IDHogar, "" + idFamilia, st_cantidadVehiculos, st_numeroResidentes);


//                if(st_TipoVehiculo[0].equalsIgnoreCase("Otro")){
//                    Toast.makeText(getApplicationContext(), "Selecccione que otro Tipo de Vehiclo # 1", Toast.LENGTH_LONG).show();
//                }else if(st_TipoVehiculo[1].equalsIgnoreCase("Otro")){
//                    Toast.makeText(getApplicationContext(), "Selecccione que otro Tipo de Vehiclo # 2", Toast.LENGTH_LONG).show();
//                }else if(st_TipoVehiculo[2].equalsIgnoreCase("Otro")){
//                    Toast.makeText(getApplicationContext(), "Selecccione que otro Tipo de Vehiclo # 3", Toast.LENGTH_LONG).show();
//                }else if(st_TipoVehiculo[3].equalsIgnoreCase("Otro")){
//                    Toast.makeText(getApplicationContext(), "Selecccione que otro Tipo de Vehiclo # 4", Toast.LENGTH_LONG).show();
//                }else if(st_TipoVehiculo[4].equalsIgnoreCase("Otro")){
//                    Toast.makeText(getApplicationContext(), "Selecccione que otro Tipo de Vehiclo # 5", Toast.LENGTH_LONG).show();
//                }else{


                            Vehiculos vehiculo = null;

                            for (int i = 0; i < cantVehiculos; i++) {

                                if (st_TipoVehiculo[i].equals("Otro"))
                                    st_TipoVehiculo[i] = otro_tipo[i].getText().toString();
                                if (st_CombustibleVehiculo[i].equals("Otro"))
                                    st_CombustibleVehiculo[i] = otro_combustible[i].getText().toString();
                                if (st_PropiedadVrhiculo[i].equals("Otro"))
                                    st_PropiedadVrhiculo[i] = otro_propiedad[i].getText().toString();
                                if (st_MatriculaVehiculo[i].equals("Otro"))
                                    st_MatriculaVehiculo[i] = otro_matricula[i].getText().toString();
                                if (st_ParuqeoVehiculo[i].equals("Otro"))
                                    st_ParuqeoVehiculo[i] = otro_parqueo[i].getText().toString();

                                vehiculo = new Vehiculos(st_TipoVehiculo[i], st_CilindrajeVehiculo[i], st_CombustibleVehiculo[i], st_PropiedadVrhiculo[i], st_MatriculaVehiculo[i], st_ParuqeoVehiculo[i]);
                                global.getFamilia().getArrayVehiculos().add(vehiculo);

                                global.getLstVehiculos().add(vehiculo);

                                Gson gson = new Gson();
                                String json = gson.toJson(global.getLstVehiculos());
                                editor.putString(Constants.JsonVehiculos, json);
                                editor.commit();


                                mySQLiteAdapter.insertVehiculo(IDHogar, "" + idFamilia, st_TipoVehiculo[i], st_MatriculaVehiculo[i], st_ParuqeoVehiculo[i], st_PropiedadVrhiculo[i], st_CombustibleVehiculo[i], st_CilindrajeVehiculo[i]);
                            }
                            /**********************************************************************************/
                            /**********************************************************************************/





                            Intent activity = new Intent();
                            activity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            activity.setClass(getApplicationContext(), IntegranteFamilia.class);
                            getApplicationContext().startActivity(activity);
                            finish();
                        }
                    }

                }

            }
        });


        scrollView = (ScrollView) findViewById(R.id.scrollView);
        Numero_Residentes =(Spinner) findViewById(R.id.spinner_Nresidentes);
        Cantidad_Vehiculos=(Spinner) findViewById(R.id.spinner_CantidadVehiculos);
        layout_vehiculo = (ViewGroup) findViewById(R.id.content);

        Nfamilia = (TextView) findViewById(R.id.Nfamilia);


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Nfamilia.setText("DATOS DE LA FAMILIA # " + sharedPreferences.getString(Constants.SeleccionFamilia, ""));

        /******************************************************************************************/
        /******************************************************************************************/
        /******************************************************************************************/
        ArrayAdapter<String> spinnerNResidentes = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.Numero_Residentes));
        Numero_Residentes.setAdapter(spinnerNResidentes);
        addListenerOnSpinnerItemNumeroResidentes();

        ArrayAdapter<String> spinnerCantidadVehiculos = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.Cantidad_Vehiculos));
        Cantidad_Vehiculos.setAdapter(spinnerCantidadVehiculos);
        addListenerOnSpinnerItemCantidadVehiculos();


    }


    /**********************************************************************************************/
    /**********************************************************************************************/
    /**********************************************************************************************/
    public void addListenerOnSpinnerItemCantidadVehiculos(){
        Cantidad_Vehiculos.setOnItemSelectedListener(new CustomOnItemSelectedListenerCantidadVehiculos());
    }
    public class CustomOnItemSelectedListenerCantidadVehiculos implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
            st_cantidadVehiculos = parent.getItemAtPosition(pos).toString();
            Log.i("Encuesta", " -------------------------------------  "  +  st_cantidadVehiculos);
            if(st_cantidadVehiculos.equalsIgnoreCase("Ninguno")){
                st_cantidadVehiculos = "0";
                layout_vehiculo.removeAllViews();
            }else{
                int cantidad = Integer.parseInt(st_cantidadVehiculos);
                layout_vehiculo.removeAllViews();
                for(int i=0; i<cantidad ; i++){
                    contvehiculos=i;
                    AumentaVehiculo2(contvehiculos);
                }
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

    public void addListenerOnSpinnerItemNumeroResidentes(){
        Numero_Residentes.setOnItemSelectedListener(new CustomOnItemSelectedListenerNumeroResidentes());
    }
    public class CustomOnItemSelectedListenerNumeroResidentes implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
            st_numeroResidentes = parent.getItemAtPosition(pos).toString();
        }
        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

    /**********************************************************************************************/
    /**********************************************************************************************/
    public void AumentaVehiculo2(final int vehiculo){
        contvehiculos = contvehiculos+1;
        LayoutInflater inflater = LayoutInflater.from(this);
        int id = R.layout.layout_vehiculos;


        //LinearLayout relativeLayout = (LinearLayout) inflater.inflate(id, null, false);
        relativeLayout = (LinearLayout) inflater.inflate(id, null, false);

        TextView cantVehiculos = (TextView) relativeLayout.findViewById(R.id.cantVehiculos);
        Spinner tipo = (Spinner) relativeLayout.findViewById(R.id.spinner_tipo);
        Spinner propiedad = (Spinner) relativeLayout.findViewById(R.id.spinner_propiedad);
        Spinner matricula = (Spinner) relativeLayout.findViewById(R.id.spinner_matricula);
        Spinner disponeparqueo = (Spinner) relativeLayout.findViewById(R.id.spinner_parqueadero);
        Spinner cilindraje = (Spinner) relativeLayout.findViewById(R.id.spinner_cilindraje);
        Spinner cilindraje_moto = (Spinner) relativeLayout.findViewById(R.id.spinner_cilindraje_moto);
        Spinner combustible = (Spinner) relativeLayout.findViewById(R.id.spinner_combustible);

        layout_liviano[vehiculo] = (LinearLayout)relativeLayout.findViewById(R.id.layout_cilindraje);
        layout_moto[vehiculo] = (LinearLayout)relativeLayout.findViewById(R.id.layout_cilindraje_moto);


        otro_tipo[vehiculo] = (EditText) relativeLayout.findViewById(R.id.edt_otro_tipo);
        otro_combustible[vehiculo] = (EditText) relativeLayout.findViewById(R.id.edt_otro_combustible);
        otro_parqueo[vehiculo] = (EditText) relativeLayout.findViewById(R.id.edt_otro_parqueo);
        otro_matricula[vehiculo] = (EditText) relativeLayout.findViewById(R.id.edt_otro_matricula);
        otro_propiedad[vehiculo] = (EditText) relativeLayout.findViewById(R.id.edt_otro_propiedad);

//        EditText otro_tipo = (EditText) relativeLayout.findViewById(R.id.edt_otro_tipo);
//        EditText otro_combustible = (EditText) relativeLayout.findViewById(R.id.edt_otro_combustible);
//        EditText otro_parqueo = (EditText) relativeLayout.findViewById(R.id.edt_otro_parqueo);
//        EditText otro_matricula = (EditText) relativeLayout.findViewById(R.id.edt_otro_matricula);
//        EditText otro_propiedad = (EditText) relativeLayout.findViewById(R.id.edt_otro_propiedad);

        cantVehiculos.setText("Vehiculo N°: " + contvehiculos);
        /******************************************************************************************/

        ArrayAdapter spinnertipo = ArrayAdapter.createFromResource(this, R.array.Tipo_Vehiculo, R.layout.spinner_item);
        tipo.setAdapter(spinnertipo);

        OnSpinnerItemClickedTipo  clickTipo = new OnSpinnerItemClickedTipo();
        clickTipo.setVehiculo(vehiculo);
        tipo.setOnItemSelectedListener(clickTipo);


        ArrayAdapter<String> spinnerpropiedad = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.Propiedad_Vehiculo));
        propiedad.setAdapter(spinnerpropiedad);

        OnSpinnerItemClickedPropiedad  clickPropiedad = new OnSpinnerItemClickedPropiedad();
        clickPropiedad.setVehiculo(vehiculo);
        propiedad.setOnItemSelectedListener(clickPropiedad);



        ArrayAdapter spinnermatricula = ArrayAdapter.createFromResource(this, R.array.Matricula_Vehiculo, R.layout.spinner_item);
        matricula.setAdapter(spinnermatricula);

        OnSpinnerItemClickedMatricula  clickMatricula = new OnSpinnerItemClickedMatricula();
        clickMatricula.setVehiculo(vehiculo);
        matricula.setOnItemSelectedListener(clickMatricula);




        ArrayAdapter<String> spinnerparqueo = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.Parqueo));
        disponeparqueo.setAdapter(spinnerparqueo);

        OnSpinnerItemClickedParqueo  clickParqueo = new OnSpinnerItemClickedParqueo();
        clickParqueo.setVehiculo(vehiculo);
        disponeparqueo.setOnItemSelectedListener(clickParqueo);


//        ArrayAdapter<String> spinnerparqueo = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,
//                getResources().getStringArray(R.array.Parqueo));
//        disponeparqueo.setAdapter(spinnerparqueo);
//
//        OnSpinnerItemClickedParqueo  clickParqueo = new OnSpinnerItemClickedParqueo();
//        clickParqueo.setVehiculo(vehiculo);
//        disponeparqueo.setOnItemSelectedListener(clickParqueo);


        ArrayAdapter<String> spinnercilindraje = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.Cilindraje));
        cilindraje.setAdapter(spinnercilindraje);
        OnSpinnerItemClickedCilindraje  clickCilindraje = new OnSpinnerItemClickedCilindraje();
        clickCilindraje.setVehiculo(vehiculo);
        cilindraje.setOnItemSelectedListener(clickCilindraje);


        ArrayAdapter<String> spinnercilindraje_moto = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.Cilindraje_Moto));
        cilindraje_moto.setAdapter(spinnercilindraje_moto);
        OnSpinnerItemClickedCilindraje_moto  clickCilindraje_moto = new OnSpinnerItemClickedCilindraje_moto();
        clickCilindraje_moto.setVehiculo(vehiculo);
        cilindraje_moto.setOnItemSelectedListener(clickCilindraje);

        ArrayAdapter spinnercombustible = ArrayAdapter.createFromResource(this, R.array.Combustible, R.layout.spinner_item);
        combustible.setAdapter(spinnercombustible);
        OnSpinnerItemClickedCombustible  clickCombustible = new OnSpinnerItemClickedCombustible();
        clickCombustible.setVehiculo(vehiculo);
        combustible.setOnItemSelectedListener(clickCombustible);


        /******************************************************************************************/
        //layout params
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(AbsListView.LayoutParams.WRAP_CONTENT, AbsListView.LayoutParams.WRAP_CONTENT);

        params.topMargin = 15;
        relativeLayout.setPadding(5, 3, 5, 3);
        relativeLayout.setLayoutParams(params);
        ///////

        layout_vehiculo.addView(relativeLayout);

        scrollView.post(new Runnable() {
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    /**********************************************************************************************/
    public class OnSpinnerItemClickedTipo implements AdapterView.OnItemSelectedListener {
        int vehiculo;
        public int getVehiculo() {
            return vehiculo;
        }
        public void setVehiculo(int vehiculo) {
            this.vehiculo = vehiculo;
        }
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            st_TipoVehiculo[vehiculo] = parent.getItemAtPosition(pos).toString();
            if(st_TipoVehiculo[vehiculo].equalsIgnoreCase("Otro")){
                    otro_tipo[vehiculo].setVisibility(View.VISIBLE);
                    layout_liviano[vehiculo].setVisibility(View.GONE);
                    layout_moto[vehiculo].setVisibility(View.GONE);
                    st_CilindrajeVehiculo[vehiculo]="N/A";
            }else{
                otro_tipo[vehiculo].setVisibility(View.GONE);
                if(st_TipoVehiculo[vehiculo].equalsIgnoreCase("Liviano")){
                    layout_liviano[vehiculo].setVisibility(View.VISIBLE);
                    layout_moto[vehiculo].setVisibility(View.GONE);
                }else if(st_TipoVehiculo[vehiculo].equalsIgnoreCase("Motocicleta")){
                    layout_liviano[vehiculo].setVisibility(View.GONE);
                    layout_moto[vehiculo].setVisibility(View.VISIBLE);
                }else if(st_TipoVehiculo[vehiculo].equalsIgnoreCase("Bicicleta")){
                    layout_liviano[vehiculo].setVisibility(View.GONE);
                    layout_moto[vehiculo].setVisibility(View.GONE);
                    st_CilindrajeVehiculo[vehiculo]="N/A";
                }
            }


//            Toast.makeText(parent.getContext(), "Clicked : " +
//                    parent.getItemAtPosition(pos).toString() + " Vehiculo: " + vehiculo, Toast.LENGTH_LONG).show();
        }
        @Override
        public void onNothingSelected(AdapterView parent) {
            // Do nothing.
        }
    }
    public class OnSpinnerItemClickedPropiedad implements AdapterView.OnItemSelectedListener {
        int vehiculo;
        public int getVehiculo() {
            return vehiculo;
        }
        public void setVehiculo(int vehiculo) {
            this.vehiculo = vehiculo;
        }
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            st_PropiedadVrhiculo[vehiculo] = parent.getItemAtPosition(pos).toString();
            if(st_PropiedadVrhiculo[vehiculo].equalsIgnoreCase("Otro")){
                otro_propiedad[vehiculo].setVisibility(View.VISIBLE);
            }else{
                otro_propiedad[vehiculo].setVisibility(View.GONE);
            }
        }
        @Override
        public void onNothingSelected(AdapterView parent) {
            // Do nothing.
        }
    }
    public class OnSpinnerItemClickedMatricula implements AdapterView.OnItemSelectedListener {
        int vehiculo;
        public int getVehiculo() {
            return vehiculo;
        }
        public void setVehiculo(int vehiculo) {
            this.vehiculo = vehiculo;
        }
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            st_MatriculaVehiculo[vehiculo] = parent.getItemAtPosition(pos).toString();
            if(st_MatriculaVehiculo[vehiculo].equalsIgnoreCase("Otro")){
                otro_matricula[vehiculo].setVisibility(View.VISIBLE);
            }else{
                otro_matricula[vehiculo].setVisibility(View.GONE);
            }
        }
        @Override
        public void onNothingSelected(AdapterView parent) {
            // Do nothing.
        }
    }
    public class OnSpinnerItemClickedParqueo implements AdapterView.OnItemSelectedListener {
        int vehiculo;
        public int getVehiculo() {
            return vehiculo;
        }
        public void setVehiculo(int vehiculo) {
            this.vehiculo = vehiculo;
        }
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            st_ParuqeoVehiculo[vehiculo] = parent.getItemAtPosition(pos).toString();
            if(st_ParuqeoVehiculo[vehiculo].equalsIgnoreCase("Otro")){
                otro_parqueo[vehiculo].setVisibility(View.VISIBLE);
            }else{
                otro_parqueo[vehiculo].setVisibility(View.GONE);
            }
        }
        @Override
        public void onNothingSelected(AdapterView parent) {
            // Do nothing.
        }
    }


    public class OnSpinnerItemClickedCilindraje implements AdapterView.OnItemSelectedListener {
        int vehiculo;
        public int getVehiculo() {
            return vehiculo;
        }
        public void setVehiculo(int vehiculo) {
            this.vehiculo = vehiculo;
        }
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            st_CilindrajeVehiculo[vehiculo] = parent.getItemAtPosition(pos).toString();
        }
        @Override
        public void onNothingSelected(AdapterView parent) {
            // Do nothing.
        }
    }


    public class OnSpinnerItemClickedCilindraje_moto implements AdapterView.OnItemSelectedListener {
        int vehiculo;
        public int getVehiculo() {
            return vehiculo;
        }
        public void setVehiculo(int vehiculo) {
            this.vehiculo = vehiculo;
        }
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            st_CilindrajeVehiculo[vehiculo] = parent.getItemAtPosition(pos).toString();
        }
        @Override
        public void onNothingSelected(AdapterView parent) {
            // Do nothing.
        }
    }
    public class OnSpinnerItemClickedCombustible implements AdapterView.OnItemSelectedListener {
        int vehiculo;
        public int getVehiculo() {
            return vehiculo;
        }
        public void setVehiculo(int vehiculo) {
            this.vehiculo = vehiculo;
        }
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            st_CombustibleVehiculo[vehiculo] = parent.getItemAtPosition(pos).toString();
            if(st_CombustibleVehiculo[vehiculo].equalsIgnoreCase("Otro")){
                otro_combustible[vehiculo].setVisibility(View.VISIBLE);
            }else{
                otro_combustible[vehiculo].setVisibility(View.GONE);
            }
        }
        @Override
        public void onNothingSelected(AdapterView parent) {
            // Do nothing.
        }
    }


    /**********************************************************************************************/



//    public void AumentaVehiculo(final int vehiculo){
//
//        Log.i("Encuesta", " --------------Crea Layout------------------------"  + contvehiculos);
//        contvehiculos = contvehiculos+1;
//        LayoutInflater inflater = LayoutInflater.from(this);
//        int id = R.layout.layout_vehiculos2;
//
//
//        LinearLayout relativeLayout = (LinearLayout) inflater.inflate(id, null, false);
//
//        TextView cantVehiculos = (TextView) relativeLayout.findViewById(R.id.cantVehiculos);
//        RadioGroup tipo = (RadioGroup) relativeLayout.findViewById(R.id.radioGroup_tipo);
//        RadioGroup propiedad = (RadioGroup) relativeLayout.findViewById(R.id.radioGroup_propiedad);
//        RadioGroup matricula = (RadioGroup) relativeLayout.findViewById(R.id.radioGroup_matricula);
//        RadioGroup parqueo = (RadioGroup) relativeLayout.findViewById(R.id.radioGroup_parqueo);
//        RadioGroup cilindraje = (RadioGroup) relativeLayout.findViewById(R.id.radioGroup_cilindraje);
//        RadioGroup combustible = (RadioGroup) relativeLayout.findViewById(R.id.radioGroup_combustible);
//
//
//        cantVehiculos.setText("Vehiculo N°: " + contvehiculos);
//
//        tipo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                // TODO Auto-generated method stub
//                if (checkedId == R.id.radioParticular) {
//                    st_TipoVehiculo[vehiculo] = ("Particular");
//                } else if (checkedId == R.id.radioMoto) {
//                    st_TipoVehiculo[vehiculo] = ("Motocicleta");
//                } else if (checkedId == R.id.radioBici) {
//                    st_TipoVehiculo[vehiculo] = ("Bicicleta");
//                }else if (checkedId == R.id.radioTipoOtro) {
//                    st_TipoVehiculo[vehiculo] = ("Otro");
//                }
//            }
//        });
//        propiedad.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                // TODO Auto-generated method stub
//                if (checkedId == R.id.radio_propio) {
//                    st_PropiedadVrhiculo[vehiculo] = ("Propio");
//                } else if (checkedId == R.id.radio_empresa) {
//                    st_PropiedadVrhiculo[vehiculo] = ("Empresa");
//                }else if (checkedId == R.id.radio_gobierno) {
//                    st_PropiedadVrhiculo[vehiculo]= ("Gobierno/Oficial");
//                }else if (checkedId == R.id.radio_PropOtro) {
//                    st_PropiedadVrhiculo[vehiculo]= ("Otro");
//                }else if (checkedId == R.id.radio_taxi) {
//                    st_PropiedadVrhiculo[vehiculo]= ("Taxi");
//                }
//            }
//        });
//        matricula.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                // TODO Auto-generated method stub
//                if (checkedId == R.id.radio_Neiva) {
//                    st_MatriculaVehiculo[vehiculo] = ("Neiva");
//                } else if (checkedId == R.id.radioMatOtro) {
//                    st_MatriculaVehiculo[vehiculo] = ("Otro");
//                }
//            }
//        });
//        parqueo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                // TODO Auto-generated method stub
//                if (checkedId == R.id.radio_vivienda) {
//                    st_ParuqeoVehiculo[vehiculo] = ("Garaje en Vivienda");
//                } else if (checkedId == R.id.radio_publico) {
//                    st_ParuqeoVehiculo[vehiculo] = ("Parqueadero Publico");
//                }else if (checkedId == R.id.radio_envia) {
//                    st_ParuqeoVehiculo[vehiculo] = ("Sobre la Via");
//                }
//            }
//        });
//
//        cilindraje.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                // TODO Auto-generated method stub
//                if (checkedId == R.id.radio_hasta1000) {
//                    st_CilindrajeVehiculo[vehiculo] = ("Hasta 1000cc");
//                } else if (checkedId == R.id.radio_de1000) {
//                    st_CilindrajeVehiculo[vehiculo] = ("De 1000cc a 1600cc");
//                }else if (checkedId == R.id.radio_de1600) {
//                    st_CilindrajeVehiculo[vehiculo] = ("De 1600cc a 2000cc");
//                }else if (checkedId == R.id.radio_mayorde2000) {
//                    st_CilindrajeVehiculo[vehiculo] = ("Mayor de 2000cc");
//                }
//            }
//        });
//
//        combustible.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                // TODO Auto-generated method stub
//                if (checkedId == R.id.radio_gasolina) {
//                    st_CombustibleVehiculo[vehiculo] = ("Gasolina");
//                } else if (checkedId == R.id.radio_disel) {
//                    st_CombustibleVehiculo[vehiculo] = ("Disel");
//                }else if (checkedId == R.id.radio_gas) {
//                    st_CombustibleVehiculo[vehiculo] = ("Gas Natural");
//                }else if (checkedId == R.id.radio_electrico) {
//                    st_CombustibleVehiculo[vehiculo] = ("Electrico");
//                }else if (checkedId == R.id.radio_hibrido) {
//                    st_CombustibleVehiculo[vehiculo] = ("Hibrido");
//                }else if (checkedId == R.id.radio_otroCombustible) {
//                    st_CombustibleVehiculo[vehiculo] = ("Otro");
//                }
//            }
//        });
//
//        //TextView textView = (TextView) relativeLayout.findViewById(R.id.textViewDate);
//        //textView.setText(String.valueOf(System.currentTimeMillis()));
//
//        //layout params
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(AbsListView.LayoutParams.WRAP_CONTENT, AbsListView.LayoutParams.WRAP_CONTENT);
//
//        params.topMargin = 15;
//        relativeLayout.setPadding(5, 3, 5, 3);
//        relativeLayout.setLayoutParams(params);
//        ///////
//
//        layout_vehiculo.addView(relativeLayout);
//
//        scrollView.post(new Runnable() {
//            public void run() {
//                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
//            }
//        });
//    }


    public void addListenerOnSpinnerItemTipoVehiculo(){
        Numero_Residentes.setOnItemSelectedListener(new CustomOnItemSelectedListenerTipoVehiculo());
    }
    public class CustomOnItemSelectedListenerTipoVehiculo implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
           Log.d("Encuesta", " =============================   " + parent.getItemAtPosition(pos).toString());

        }
        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }


    public void SpinnerTipoVehiculo(){
        List<String> list = new ArrayList<String>();


        list.add("Jaime");
        list.add("Andres");
        list.add("Lombana");
        list.add("Gonzalexz");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Tipo_Vehiculo.setAdapter(dataAdapter);

        // Spinner item selection Listener
        //addListenerOnSpinnerItemSelectionTipoResidencia();
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
