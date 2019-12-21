package com.example.vamosjuntos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

public class AltaEvento extends AppCompatActivity {
    EditText nomEvent, dirEvent, fechaEvent, horaEvent, descEvent;
    Toast error;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_evento);
        nomEvent = findViewById(R.id.nomEvent_editText);
        dirEvent = findViewById(R.id.dirEvent_editText3);
        fechaEvent = findViewById(R.id.fecha_editText2);
        horaEvent = findViewById(R.id.hora_EditText);
        descEvent = findViewById(R.id.descripcio_editText4);

//Error no es podden deixar espais en blanc:
        comprobarCampos();

    }

    public void irAEvento(View view) {

        if (nomEvent.getText().toString().isEmpty() || dirEvent.getText().toString().isEmpty() || fechaEvent.getText().toString().isEmpty() || horaEvent.getText().toString().isEmpty() || descEvent.getText().toString().isEmpty()) {
            comprobarCampos();
            error = Toast.makeText(getApplicationContext(), "Faltan datos por añadir", Toast.LENGTH_SHORT);
            error.show();
        } else {
            pasarDatos(view);
        }
    }

    public void pasarDatos(View view) {
        Intent intent = new Intent(this, DetalleEvento.class);

        intent.putExtra("eventoJson", pasarObjeto()); //Esto llama a pasar objeto
        startActivity(intent);


//        i.putExtra("nom", nomEvent.getText().toString());
//        i.putExtra("dir", dirEvent.getText().toString());
//        i.putExtra("fecha", fechaEvent.getText().toString());
//        i.putExtra("hora", horaEvent.getText().toString());
//        i.putExtra("desc", descEvent.getText().toString());
//        startActivity(i);
    }

    //Guarda en un objeto los datos
    public String pasarObjeto() {

        Evento event = new Evento(nomEvent.getText().toString());

        //Mete en cada parte del objeto un string
        event.setNomEvent(nomEvent.getText().toString());
        event.setDireccio(dirEvent.getText().toString());
        event.setFecha(fechaEvent.getText().toString());
        event.setHora(horaEvent.getText().toString());
        event.setDescripcio(descEvent.getText().toString());

        //Coge el objeto y lo serializa, lo transforma en un texto para pasarlo a travez de la variable
        Gson gson = new Gson();
        String eventoJson = gson.toJson(event); //Coje el event i serializalo, lo pasa a un String

        return eventoJson;

    }

    public void comprobarCampos() {
        if (nomEvent.getText().toString().isEmpty()) {
            nomEvent.setError("No puede estar vacio");
        }
        if (dirEvent.getText().toString().isEmpty()) {
            dirEvent.setError("No puede estar vacio");
        }
        if (fechaEvent.getText().toString().isEmpty()) {
            fechaEvent.setError("No puede estar vacio");
        }
        if (horaEvent.getText().toString().isEmpty()) {
            horaEvent.setError("No puede estar vacio");
        }
        if (descEvent.getText().toString().isEmpty()) {
            descEvent.setError("No puede estar vacio");
        }
    }
}
