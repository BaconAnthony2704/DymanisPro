package com.example.gestionclientes;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ConexionSQLiteHelper extends SQLiteOpenHelper {
    String query="CREATE TABLE usuarios(nivel integer, visible integer, nombre text, usuario text, password txt);";
    public ConexionSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS usaurios");
    }
    public void abrirdb(){this.getReadableDatabase();}

    public void  cerrardb(){this.close();}

    public void insertarReg(int nivel, int visible, String nombre, String usuario, String password){
        ContentValues valores= new ContentValues();
        valores.put("nivel",nivel);
        valores.put("visible",visible);
        valores.put("",nombre);
        valores.put("",usuario);
        valores.put("",password);
        this.getWritableDatabase().insert("usuarios",null,valores);
    }
}
