package com.encuesta.rightway.encuesta;

import android.content.SharedPreferences;
import android.location.Address;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.encuesta.rightway.com.encuesta.right.services.GeocoderHandler;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public class MapsActivityDireccion extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnCameraChangeListener {

    private GoogleMap mMap;
    private String Latitud;
    private String Longitud;
    private Address currentAddress;
    private EditText Direccion;
    private String DireccionOriginal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_activity_direccion);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Direccion = (EditText) findViewById(R.id.edt_direccion);
        Bundle bundle = getIntent().getExtras();
        Latitud = bundle.getString("Latitud");
        Longitud= bundle.getString("Longitud");


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        // LatLng sydney = new LatLng(4.6726565, -74.11356556);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setTrafficEnabled(true);
        mMap.setOnCameraChangeListener(this);
        // mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


        double Lat = Double.parseDouble(Latitud);
        double Lon = Double.parseDouble(Longitud);

        Log.i("Seguimiento", "  ============Latitud:  " + Lat);
        Log.i("Seguimiento" ,  "  ============Longitud:  "  + Lon);

        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(Lat, Lon));
        this.mMap.moveCamera(center);
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);
        this.mMap.animateCamera(zoom);

    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        GeocoderHandler geocode = new GeocoderHandler(cameraPosition.target.latitude, cameraPosition.target.longitude, handler, getApplicationContext());
        geocode.start();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            currentAddress = (Address) msg.obj;
            if(currentAddress!=null) {
                String direccionOriginal = currentAddress.getAddressLine(0);
                String direccionModificada = direccionOriginal.replaceAll("( a ).*", "");
                Direccion.setText("  " + direccionModificada);
                Direccion.setSelection(Direccion.getText().length());
                DireccionOriginal = direccionModificada;
                Log.d("Encuesta", " ----------------------    " + direccionModificada);


            }else{

            }

        }
    };

    public void SeleccionDireccion(View v){

        Log.d("Encuesta", " ----------------------    "  + Direccion.getText().toString());
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.DireccionGoogle, Direccion.getText().toString());
        editor.commit();
        finish();
    }
}