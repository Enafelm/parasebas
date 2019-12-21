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

public class MostrarEvents extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Crea l'activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_events);

        //2) Obtenemos el id del list View (con el id definido en el layout), mas tarde la rellenaremos
        ListView listView = (ListView) findViewById(R.id.listViewEventos);
        //3) Vamos a leer el fichero lista json del a carpeta de assets
        String json = "";
        try {
            InputStream stream = getAssets().open("eventos.json");
            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            json = new String(buffer);
        } catch (Exception e) {
        }

        //4) En el string json ahora tenemos el listado como texto, en la siguente linea vamos a parsear
        //el string y convertirlo en una lista de objetos eventos
        final List<Evento> listaEventos = Arrays.asList(new Gson().fromJson(json, Evento[].class));

        //5 En la listaEventos tenemos todos los objetos (con todos los campos), vamos a quedarnos solo con el campo que queremos
        //mostrar en la lista  por ejemplo el titulo
        String[] arrayTitulos = new String[listaEventos.size()]; //Guarda los titulos y fecha
        for (int i = 0; i < listaEventos.size(); i++) {
            arrayTitulos[i] = listaEventos.get(i).getFecha() + "\n" + listaEventos.get(i).getNomEvent();
        }

        //6 Vamos a rellenar el component listView
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayTitulos);
        listView.setAdapter(adapter);

        //En fer click a un item del ListView ens enviara al detall del event.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), DetalleEvento.class);
                intent.putExtra("eventoJson", pasarObjeto(listaEventos, position));
                //startActivity(intent);
                startActivityForResult(intent, 0);
            }
        });
    }

    //Para que inicie el activity DetalleEvento si o si debemos pasarle datos!!
    //Recibe la lista y los datos de la posicion de la lista!
    public String pasarObjeto(List<Evento> listaEventos, int i) {

        Evento event = new Evento(listaEventos.get(i).getNomEvent());

        event.setNomEvent(listaEventos.get(i).getNomEvent());
        event.setDireccio(listaEventos.get(i).getDireccio());
        event.setFecha(listaEventos.get(i).getFecha());
        event.setHora(listaEventos.get(i).getHora());
        event.setDescripcio(listaEventos.get(i).getDescripcio());


        Gson gson = new Gson();
        String eventoJson = gson.toJson(event);
        return eventoJson;
    }

    public void irACrearEvento(View view) {
        Intent intent = new Intent(view.getContext(), AltaEvento.class);
        startActivity(intent);

    }


}