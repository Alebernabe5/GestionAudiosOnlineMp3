package com.example.gestionaudiosonlinemp3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ReproductorActivity extends AppCompatActivity {

    protected TextView texto1, texto2, texto3;
    protected ImageButton imaBoton1, imaBoton2, imaBoton3;
    protected Button boton1;
    protected Intent pasarPantalla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reproductor);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        texto1 = (TextView) findViewById(R.id.texto1_reproductor);
        texto2 = (TextView) findViewById(R.id.texto2_reproductor);
        texto3 = (TextView) findViewById(R.id.texto3_reproductor);
        imaBoton1 = (ImageButton) findViewById(R.id.imaboton1_reproductor);
        imaBoton2 = (ImageButton) findViewById(R.id.imaboton2_reproductor);
        imaBoton3 = (ImageButton) findViewById(R.id.imaboton3_reproductor);
        boton1 = (Button) findViewById(R.id.boton1_reproductor);



    }
}