package com.example.gestionaudiosonlinemp3;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    protected TextView texto1;
    protected Button boton1;
    protected ListView lista1;
    protected Intent pasarPantalla;
    protected DataBaseSQL gdb;
    protected ArrayList <String> listaAudios = new ArrayList<String>();
    protected ArrayList <InfoAudio> listaInfoAudios = new ArrayList<InfoAudio>();
    protected ArrayAdapter<String> adaptador;
    protected String contenidoItem = "";
    protected String[] partes;
   



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
        boton1 = (Button) findViewById(R.id.boton1_main);
        lista1 = (ListView) findViewById(R.id.lista1_main);

        getSupportActionBar().setTitle(getString(R.string.string_codigoPO)); //Cambio de titulo del menu

        //Mostrar la lista de audio en el listView
        listaInfoAudios = gdb.obtenerInfoAudio();
        listaAudios = gdb.obtenerAudio();
        adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listaAudios);
        lista1.setAdapter(adaptador);

        //LISTA
        lista1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                InfoAudio info= listaInfoAudios.get(i); //Aqui tengo la info del video

                pasarPantalla = new Intent(MainActivity.this, ReproduccionActivity.class);
                pasarPantalla.putExtra("ID",info.getId());
                pasarPantalla.putExtra("NAME",info.getTitulo());
                pasarPantalla.putExtra("URL",info.getUrl());

                startActivity(pasarPantalla);

            }
        });

        //BOTON BORRAR TODAS LAS CANCIONES
        
        boton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               gdb.borrarTodosLosAudios();
               Toast.makeText(MainActivity.this, getString(R.string.string_codigoBorrarTodo), Toast.LENGTH_SHORT).show();
            }
        });

        //TOCAR LARGO PARA BORRAR UN ITEMS
        lista1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                contenidoItem=adapterView.getItemAtPosition(i).toString();
                partes=contenidoItem.split("\\.");

                //DIALOGO PREGUNTA DE ELIMINACION
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("¿Deseas eliminar el audio "+partes[1]+"?")
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // START THE GAME!

                                if(gdb.borrarAudio(partes[0]))
                                {
                                    Toast.makeText(MainActivity.this, "Audio borrado correctamente", Toast.LENGTH_SHORT).show();
                                    adaptador.clear();
                                    listaInfoAudios = gdb.obtenerInfoAudio();
                                    listaAudios = gdb.obtenerAudio();
                                    adaptador.addAll(listaAudios);
                                    adaptador.notifyDataSetChanged();
                                }
                                else {
                                    Toast.makeText(MainActivity.this, "No se ha podido borrar el audio", Toast.LENGTH_SHORT).show();

                                }

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancels the dialog.
                            }
                        });
                // Create the AlertDialog object and return it.
                 builder.create();
                 builder.show();


                return true;
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

