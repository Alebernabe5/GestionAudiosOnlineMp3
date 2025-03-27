package com.example.gestionaudiosonlinemp3;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;

public class ReproduccionActivity extends AppCompatActivity {

    protected TextView texto1, texto2, texto3;
    protected ImageButton imaBoton1Pause, imaBoton2Play, imaBoton3Stop;
    protected Button boton1;
    protected Intent pasarPantalla;
    protected String paqueteNombre="";
    protected String paqueteUrl="";
    protected Bundle extras;
    protected MediaPlayer mp;
    protected float miliseg = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reproduccion);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        texto1 = (TextView) findViewById(R.id.texto1_reproduccion);
        texto2 = (TextView) findViewById(R.id.texto2_reproduccion);
        texto3 = (TextView) findViewById(R.id.texto3_reproduccion);
        imaBoton1Pause = (ImageButton) findViewById(R.id.imaBoton1_reproduccion);
        imaBoton2Play = (ImageButton) findViewById(R.id.imaBoton2_reproduccion);
        imaBoton3Stop = (ImageButton) findViewById(R.id.imaBoton3_reproduccion);
        boton1 = (Button) findViewById(R.id.boton1_reproduccion);



        extras = getIntent().getExtras();
        if (extras!=null)
        {
            paqueteNombre = extras.getString("NAME");
            paqueteUrl = extras.getString("URL");
            texto2.setText(getString(R.string.string_texto2_reproduccion)+" "+paqueteNombre);
            texto3.setText(getString(R.string.string_texto3_reproduccion)+" "+paqueteUrl);
        }

        //BOTON PAUSE
        imaBoton1Pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                miliseg = mp.getCurrentPosition(); //Guarda mil donde se reproduce el audio
                mp.pause();
                imaBoton1Pause.setVisibility(View.INVISIBLE);
            }
        });

        //BOTON PLAY
        imaBoton2Play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (miliseg>0)
                {
                    mp.start();
                    imaBoton1Pause.setVisibility(View.VISIBLE);
                } else if (miliseg<0) {
                    //Stop
                    mp.prepareAsync();
                    mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mediaPlayer) {
                            mp.start();
                        }
                    });

                } else
                {
                    try {

                        mp = new MediaPlayer(); //Instanciar mp
                        mp.setDataSource(paqueteUrl);
                        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        mp.prepareAsync(); //Hasta que no este el buffer relleno no se reproduce
                        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mediaPlayer) {
                                mp.start();
                            }
                        });


                    } catch (IOException e) {
                        Toast.makeText(ReproduccionActivity.this, "Imposible reproducir el audio", Toast.LENGTH_SHORT).show();
                        throw new RuntimeException(e);

                    }
                }

            }
        });

        //BOTON STOP
        imaBoton3Stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                miliseg = -1;
                mp.stop();
            }
        });

        //BOTON VOLVER
        boton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pasarPantalla = new Intent(ReproduccionActivity.this, MainActivity.class);
                startActivity(pasarPantalla);
            }
        });

       getSupportActionBar().setTitle(getString(R.string.string_codigoRA)+" ("+ paqueteNombre+")"); //Cambio de titulo del menu

    }
}