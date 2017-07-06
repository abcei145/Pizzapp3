package com.example.jose.pizzapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> Pedido;
    private static final String PEDIDO
            = "com.restaurantericoparico.FragmentoCategoriasTab.extra.PEDIDO";

    public static String getPEDIDOKey() {
        return PEDIDO;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Pedido=new ArrayList<>();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        selectItem("Inicio");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Adding orders not available yet", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
                selectItem("Menu");

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(getApplicationContext(), "This shit is under construction", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        PedidoDatos Pedido = new PedidoDatos(getApplicationContext());
        SQLiteDatabase bd = Pedido.getWritableDatabase();
        bd.execSQL("drop table if exists Pedido");
        super.onDestroy();
    }

    private void selectItem(String FragmentName) {

        // Reemplazar el contenido del layout principal por un fragmento
        FragmentManager fragmentManager;
        Fragment fragment;
        fragmentManager = getSupportFragmentManager();
        switch(FragmentName)
        {
            case "Inicio":
                fragment=new InicioFragment();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                break;
            case "Menu":
                fragment=new FragmentoCategorias();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                break;
        }

        // Se actualiza el item seleccionado y el título, después de cerrar el drawer

    }

}
