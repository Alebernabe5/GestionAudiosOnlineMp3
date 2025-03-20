package com.example.gestionaudiosonlinemp3;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CrearActivity extends AppCompatActivity {

    protected TextView texto1, texto2, texto3;
    protected Button boton1, boton2;
    protected EditText caja1, caja2;



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

        texto1 = (TextView) findViewById(R.id.texto1_crear);
        texto2 = (TextView) findViewById(R.id.texto2_crear);
        texto3 = (TextView) findViewById(R.id.texto3_crear);
        boton1 = (Button) findViewById(R.id.boton1_crear);
        boton2 = (Button) findViewById(R.id.boton2_crear);
        caja1 = (EditText) findViewById(R.id.caja1_crear);
        caja2 = (EditText) findViewById(R.id.caja2_crear);



    }
}