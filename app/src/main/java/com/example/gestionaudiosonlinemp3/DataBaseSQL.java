package com.example.gestionaudiosonlinemp3;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DataBaseSQL extends SQLiteOpenHelper {


    public DataBaseSQL(@Nullable Context context) {
        super(context, "audio", null, 1);
    } //Crea BD audio

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE media (id INTEGER PRIMARY KEY AUTOINCREMENT, titulo TEXT, url TEXT)"); //Consulta crear tabla media

        //Ejecuciones de muestra para tener en BBDD
        db.execSQL("INSERT INTO media VALUES(null,'Gorila','https://cdn.pixabay.com/download/audio/2025/03/19/audio_56ae1dae5f.mp3?filename=gorila-315977.mp3')");
        db.execSQL("INSERT INTO media VALUES(null,'AMALGAM','https://cdn.pixabay.com/download/audio/2024/06/14/audio_0e2636099d.mp3?filename=amalgam-217007.mp3')");
        db.execSQL("INSERT INTO media VALUES(null,'Summer Walk','https://cdn.pixabay.com/download/audio/2023/06/06/audio_cbcfcb18e6.mp3?filename=summer-walk-152722.mp3')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
/*
    //BORRAR AUDIOS
    public boolean borrarAudio (String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM media WHERE id="+id);

        if (existeIdAudio(id))
        {
            return false;
        }
        return true;
    }
    */

    //BORRAR TODAS LOS AUDIOS
    public void borrarTodosLosAudios ()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM media WHERE id>=0");

    }

//EXISTE AUDIO
public boolean existeAudio(String titulo)
{
    SQLiteDatabase db = this.getReadableDatabase(); //Referencia a la BBDD
    Cursor cur = db.rawQuery("SELECT * FROM media WHERE titulo='"+titulo+"'",null);

    if (cur!=null)
    {
        cur.moveToLast(); //Me pongo en primera posic
        if (cur.getCount()>0) {
            return true;
        }
    }
    return false;
}

    //INSERTAR AUDIO
    public boolean insertarAudio (String titulo, String url)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        if (!existeAudio(titulo))
        {
            db.execSQL("INSERT INTO media VALUES(null,'"+titulo+"','"+url+"')");

            return true;
        }
        return false;
       }



    //OBTENER INFOAUDIO
    public ArrayList<InfoAudio> obtenerInfoAudio()
    {
        ArrayList <InfoAudio> salidaInfo = new ArrayList<InfoAudio>();
        SQLiteDatabase db = this.getReadableDatabase(); //Referencia a la BBDD
        Cursor cur = db.rawQuery("SELECT * FROM media",null);
        if (cur!=null)
        {
            cur.moveToLast();
            if (cur.getCount()>0)
            {
                cur.moveToFirst();
                while(!cur.isAfterLast())
                {
                    //Obtengo el valor de cada campo
                    int id = cur.getInt(0);
                    String titulo = cur.getString(1);
                    String url = cur.getString(2);
                    //Creo el obj infoVideo
                    InfoAudio info = new InfoAudio(id, titulo, url);
                    salidaInfo.add(info); //meto el objeto en el array
                    cur.moveToNext();
                }
            }

        }
        return  salidaInfo;
    }

    //OBTENER INFOAUDIO
    public ArrayList<String> obtenerAudio()
    {
        ArrayList <String> salidaInfo = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase(); //Referencia a la BBDD
        Cursor cur = db.rawQuery("SELECT * FROM media",null);
        if (cur!=null)
        {
            cur.moveToLast();
            if (cur.getCount()>0)
            {
                cur.moveToFirst();
                while(!cur.isAfterLast())
                {
                    //Obtengo el valor de cada campo
                    int id = cur.getInt(0);
                    String titulo = cur.getString(1);
                    String url = cur.getString(2);

                    salidaInfo.add(id + ". " +titulo); //meto el objeto en el array
                    cur.moveToNext();
                }
            }

        }
        return  salidaInfo;
    }




}
