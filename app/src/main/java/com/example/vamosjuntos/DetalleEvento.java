package com.example.vamosjuntos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class DetalleEvento extends AppCompatActivity implements OnMapReadyCallback {
    TextView detallNomEvent, detallLlocEvent, diaHoraEvent, bioEvent;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalle_evento);

        detallNomEvent = findViewById(R.id.detallNomEvent);
        detallLlocEvent = findViewById(R.id.detallLloc);
        diaHoraEvent = findViewById(R.id.detallDiaFecha);
        bioEvent = findViewById(R.id.detallBiografia);

        recibirDatos(); //Llamo al metodo antes de que busque el mapa

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    public void recibirDatos() {

        Bundle extras = getIntent().getExtras();
        String json = extras.getString("eventoJson"); //Recoge el parametro de texto jSon serializado, luego lo vuelve a convetir en un objeto

        Gson gson = new Gson();
        Evento evento = gson.fromJson(json, Evento.class); //De este jSon transformalo a Evento

        detallNomEvent.setText(evento.getNomEvent());
        detallLlocEvent.setText("Evento en: " + evento.getDireccio());
        diaHoraEvent.setText("El " + evento.getFecha() + " a las " + evento.getHora());
        bioEvent.setText(evento.getDescripcio());


//        String nom = extras.getString("nom");
//        dir = extras.getString("dir");
//        String fecha = extras.getString("fecha");
//        String hora = extras.getString("hora");
//        String desc = extras.getString("desc");

//        detallNomEvent.setText(nom);
//        detallLlocEvent.setText("Evento en: " + dir);
//        diaHoraEvent.setText("El " + fecha + " a les " + hora);
//        bioEvent.setText(desc);
    }

    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        try {
            Geocoder geocoder = new Geocoder(this);
            List<Address> addressList = geocoder.getFromLocationName(detallLlocEvent.getText().toString().substring(9), 1); //Substring para ignorar "Evento en:"

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

    public void irAMostrarVehicles(View view) {
        Intent intent = new Intent(view.getContext(), MostrarVehicles.class);
        startActivity(intent);

    }

}
