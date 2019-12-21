package com.example.vamosjuntos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

public class AltaVehicle extends AppCompatActivity {
    EditText nomConductor, localidad, descripcion, espacios, modeloVehiculo;
    Toast error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_vehicle);
        nomConductor = findViewById(R.id.nomConductor_editText);
        localidad = findViewById(R.id.localidad_editText);
        descripcion = findViewById(R.id.descripcion_editText);
        espacios = findViewById(R.id.espacios_editText);
        modeloVehiculo = findViewById(R.id.modeloV_editText);

        comprobarCampos();

    }

    public void irADetalle(View view) {
        if (nomConductor.getText().toString().isEmpty() || localidad.getText().toString().isEmpty() || descripcion.getText().toString().isEmpty() || espacios.getText().toString().isEmpty() || modeloVehiculo.getText().toString().isEmpty()) {
            comprobarCampos();
            error = Toast.makeText(getApplicationContext(), "Faltan datos por añadir", Toast.LENGTH_SHORT);
            error.show();
        } else {
            pasarDatos(view);
        }
    }

    public void pasarDatos(View view) {
        Intent intent = new Intent(this, DetalleVehicle.class);

        intent.putExtra("vehiculoJson", pasarObjeto()); //Esto llama a pasar objeto
        startActivity(intent);
    }

    //Guarda en un objeto los datos
    public String pasarObjeto() {

        Conductor conductor = new Conductor(nomConductor.getText().toString());

        //Mete en cada parte del objeto un string
        conductor.setNomConductor(nomConductor.getText().toString());
        conductor.setDescripcion(descripcion.getText().toString());
        conductor.setEspaciosLibres(espacios.getText().toString());
        conductor.setLocalidad(localidad.getText().toString());
        conductor.setTipoVehiculo(modeloVehiculo.getText().toString());


        //Coge el objeto y lo serializa, lo transforma en un texto para pasarlo a travez de la variable
        Gson gson = new Gson();
        String vehiculoJson = gson.toJson(conductor); //Coje el event i serializalo, lo pasa a un String

        return vehiculoJson;

    }

    public void comprobarCampos() {
        if (nomConductor.getText().toString().isEmpty()) {
            nomConductor.setError("No puede estar vacio");
        }
        if (localidad.getText().toString().isEmpty()) {
            localidad.setError("No puede estar vacio");
        }
        if (descripcion.getText().toString().isEmpty()) {
            descripcion.setError("No puede estar vacio");
        }
        if (espacios.getText().toString().isEmpty()) {
            espacios.setError("No puede estar vacio");
        }
        if (modeloVehiculo.getText().toString().isEmpty()) {
            modeloVehiculo.setError("No puede estar vacio");
        }
    }
}
