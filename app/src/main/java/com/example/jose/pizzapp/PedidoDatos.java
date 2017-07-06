package com.example.jose.pizzapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Santiago on 5/07/2017.
 */

public class PedidoDatos extends SQLiteOpenHelper{
    private Cursor c;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Pedido.db";
    public PedidoDatos(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {//elimina la anterior con drop
       db.execSQL("drop table if exists Pedido");
       db.execSQL("CREATE TABLE " + "Pedido" + " ("
                + "id" + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nombre" + " TEXT NOT NULL,"
                + "cantidad" + " TEXT NOT NULL,"
                + "valor" + " TEXT NOT NULL,"
                + "subtotal" + " TEXT NOT NULL,"
                + "UNIQUE (" + "id" + "))");
       /* c = db.rawQuery("select * from Pedido where", null);
        if (c.moveToFirst()){
            ContentValues registro = new ContentValues();//Es para guardar los datos ingresados
            registro.put("id", "3");//tag debe aparecer igual que en la clase BaseDeDatos
            registro.put("nombre", "Capitan_America");
            registro.put("cantidad", "10");
            registro.put("valor", "15000");
            registro.put("ganancia", "0");
            db.insert("Peluchitos", null, registro);
        }*/
    }

    public void CreateIfNoExists(SQLiteDatabase db){
        db.execSQL("CREATE TABLE " +"IF NOT EXISTS " +"Pedido" + " ("
                + "id" + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nombre" + " TEXT NOT NULL,"
                + "cantidad" + " TEXT NOT NULL,"
                + "valor" + " TEXT NOT NULL,"
                + "subtotal" + " TEXT NOT NULL,"
                + "UNIQUE (" + "id" + "))");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Pedido");//elimina la anterior con drop
        db.execSQL("create table Pedido (identificacion integer primary key ,nombre text , nota integer)");
    }
    public void delete(SQLiteDatabase bd) {
        //bd.execSQL("delete from "+"Pedido"+" where nombre='"+nombre+"'");
        bd.execSQL("drop table if exists Pedido");
    }

    public Cursor ObtainAllRows(SQLiteDatabase bd){
        return bd.rawQuery("select * from Pedido",null);
    }
}
