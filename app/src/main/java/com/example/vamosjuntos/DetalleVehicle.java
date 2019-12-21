package com.example.vamosjuntos;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

public class DetalleVehicle extends AppCompatActivity implements OnMapReadyCallback {
    TextView nomConductor, biografia, modelo, localidad, plazas;
    Integer num;
    private GoogleMap mMap;
    boolean enCoche = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_vehicle);

        nomConductor = findViewById(R.id.nomConductor_TextView);
        biografia = findViewById(R.id.biografia_TextView);
        modelo = findViewById(R.id.voyEn_TextView);
        localidad = findViewById(R.id.localidad_TextView);
        plazas = findViewById(R.id.plazas_TextView);

        recibirDatos();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void recibirDatos() {

        Bundle extras = getIntent().getExtras();
        String json = extras.getString("vehiculoJson"); //Recoge el parametro de texto jSon serializado, luego lo vuelve a convetir en un objeto

        Gson gson = new Gson();
        Conductor conductor = gson.fromJson(json, Conductor.class); //De este jSon transformalo a Evento

        nomConductor.setText(conductor.getNomConductor());
        localidad.setText(conductor.getLocalidad());
        biografia.setText(conductor.getDescripcion());
        modelo.setText("Vamos en: " + conductor.getTipoVehiculo());
        num = Integer.parseInt(conductor.getEspaciosLibres());
        plazas.setText("Asientos libres: " + num);
    }

    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        try {
            Geocoder geocoder = new Geocoder(this);
            List<Address> addressList = geocoder.getFromLocationName(localidad.getText().toString(), 1);//.substring(9), 1); //Substring para ignorar "Evento en:"

            if (addressList.size() > 0) {
                Address address = addressList.get(0);
                LatLng localizacion = new LatLng(address.getLatitude(), address.getLongitude());
                mMap.addMarker(new MarkerOptions().position(localizacion));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(localizacion, 15.0f));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Add a marker in Sydney and move the camera
//        LatLng barcelona = new LatLng(41.3887901, 2.1589899);
//        mMap.addMarker(new MarkerOptions().position(barcelona).title("Barcelona"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(barcelona));
    }


    public void entrarAlCoche(View view) {
        if (num > 0 && !enCoche) {
            enCoche = true;
            Toast.makeText(getApplicationContext(), "Has entrado al vehiculo", Toast.LENGTH_SHORT).show();
            num--;
            plazas.setText("Plazas disponibles: " + num);

        } else if (enCoche) {
            Toast.makeText(getApplicationContext(), "Ya estas en el vehiculo :)", Toast.LENGTH_SHORT).show();
        } else if (num == 0) {
            Toast.makeText(getApplicationContext(), "No quedan plazas", Toast.LENGTH_SHORT).show();
        }
    }
}
