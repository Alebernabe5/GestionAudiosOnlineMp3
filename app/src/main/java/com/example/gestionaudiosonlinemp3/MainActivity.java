package com.example.gestionaudiosonlinemp3;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    protected TextView texto1;
    protected ListView lista1;
    protected Intent pasarPantalla;
    protected DataBaseSQL gdb;
    protected ArrayList <String> listaAudios = new ArrayList<String>();
    protected ArrayAdapter<String> adaptador;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        gdb = new DataBaseSQL(this); //Aqui es donde se crea y conecta la BD

        texto1 = (TextView) findViewById(R.id.texto1_main);
        lista1 = (ListView) findViewById(R.id.lista1_main);

        getSupportActionBar().setTitle("Lista de reprodución Audio MP3"); //Cambio de titulo del menu



        //Mostrar la lista de audio en el listView
        listaAudios = gdb.obtenerAudio();
        adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listaAudios);
        lista1.setAdapter(adaptador);

        //Actualizar la lista tras crear el producto nuevo
        adaptador.clear();
        listaAudios = gdb.obtenerAudio();
        adaptador.addAll(listaAudios);
        adaptador.notifyDataSetChanged();
        
        lista1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, "Cortito", Toast.LENGTH_SHORT).show();
            }
        });




        }
    //MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection.
        int id = item.getItemId();
        if (id == R.id.menu_item_crear)
        {
            pasarPantalla = new Intent(MainActivity.this, CrearActivity.class);
            startActivity(pasarPantalla);
            return true;
        }
        else if (id == R.id.menu_item_salir)
        {
            System.exit(0);
            return true;
        }
        else
        {
            return super.onOptionsItemSelected(item);
        }

        }
    }

