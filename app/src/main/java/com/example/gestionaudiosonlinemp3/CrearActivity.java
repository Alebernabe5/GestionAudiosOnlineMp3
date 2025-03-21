package com.example.gestionaudiosonlinemp3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CrearActivity extends AppCompatActivity {

    protected TextView texto1, texto2, texto3;
    protected Button boton1, boton2;
    protected EditText caja1, caja2;
    protected String contenidoCaja1 ="";
    protected String contenidoCaja2 ="";
    protected Intent pasarPantalla;
    protected DataBaseSQL gdb;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_crear);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        gdb = new DataBaseSQL(this);

        texto1 = (TextView) findViewById(R.id.texto1_crear);
        texto2 = (TextView) findViewById(R.id.texto2_crear);
        texto3 = (TextView) findViewById(R.id.texto3_crear);
        boton1 = (Button) findViewById(R.id.boton1_crear);
        boton2 = (Button) findViewById(R.id.boton2_crear);
        caja1 = (EditText) findViewById(R.id.caja1_crear);
        caja2 = (EditText) findViewById(R.id.caja2_crear);

        //BOTON CREAR
        boton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contenidoCaja1 = caja1.getText().toString();
                contenidoCaja2 = caja2.getText().toString();
                if (contenidoCaja1.equalsIgnoreCase("")&& contenidoCaja2.equalsIgnoreCase(""))
                {
                    Toast.makeText(CrearActivity.this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    caja1.setText("");
                    caja2.setText("");
                    if (gdb.insertarAudio(contenidoCaja1,contenidoCaja2)) {
                        Toast.makeText(CrearActivity.this, "Audio guardado correctamente", Toast.LENGTH_SHORT).show();
                        pasarPantalla = new Intent(CrearActivity.this, MainActivity.class);
                        startActivity(pasarPantalla);

                    }
                    else
                    {
                        Toast.makeText(CrearActivity.this, "No se pudo guardar el audio", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        //BOTON VOLVER
        boton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pasarPantalla = new Intent(CrearActivity.this, MainActivity.class);
                startActivity(pasarPantalla);
            }
        });



    }
}