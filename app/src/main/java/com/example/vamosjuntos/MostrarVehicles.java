package com.example.vamosjuntos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class MostrarVehicles extends AppCompatActivity {


    //Crea l'activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_vehicles);


        //2) Obtenemos el id del list View (con el id definido en el layout), mas tarde la rellenaremos
        ListView listView = (ListView) findViewById(R.id.listViewCoches);
        //3) Vamos a leer el fichero lista json del a carpeta de assets
        String json = "";
        try {
            InputStream stream = getAssets().open("conductores.json");
            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            json = new String(buffer);
        } catch (Exception e) {
        }

        //4) En el string json ahora tenemos el listado como texto, en la siguente linea vamos a parsear
        //el string y convertirlo en una lista de objetos eventos
        final List<Conductor> listaVehiculos = Arrays.asList(new Gson().fromJson(json, Conductor[].class));

        //5 En la listaVehiculos tenemos todos los objetos (con todos los campos), vamos a quedarnos solo con el campo que queremos
        //mostrar en la lista  por ejemplo el titulo
        String[] arrayTitulos = new String[listaVehiculos.size()]; //Guarda los titulos y fecha
        for (int i = 0; i < listaVehiculos.size(); i++) {
            arrayTitulos[i] = listaVehiculos.get(i).getTipoVehiculo() + "\nEspacios libres: " + listaVehiculos.get(i).getEspaciosLibres();
        }

        //6 Vamos a rellenar el component listView
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayTitulos);
        listView.setAdapter(adapter);

        //En fer click a un item del ListView ens enviara al detall del cotxe.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), DetalleVehicle.class);
                intent.putExtra("vehiculoJson", pasarObjeto(listaVehiculos, position));
                //startActivity(intent);
                startActivityForResult(intent, 0);
            }
        });
    }
    public String pasarObjeto(List<Conductor> listaVehiculos, int i) {

        Conductor conductor = new Conductor(listaVehiculos.get(i).getNomConductor());

        conductor.setNomConductor(listaVehiculos.get(i).getNomConductor());
        conductor.setLocalidad(listaVehiculos.get(i).getLocalidad());
        conductor.setTipoVehiculo(listaVehiculos.get(i).getTipoVehiculo());
        conductor.setDescripcion(listaVehiculos.get(i).getDescripcion());
        conductor.setEspaciosLibres(listaVehiculos.get(i).getEspaciosLibres());


        Gson gson = new Gson();
        String vehiculoJson = gson.toJson(conductor);
        return vehiculoJson;
    }

    public void irAAltaVehicle(View view) {
        Intent intent = new Intent(view.getContext(), AltaVehicle.class);
        startActivity(intent);
    }
}
