package com.example.jose.pizzapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import static android.R.id.message;

/**
 * Fragmento que representa el contenido de cada pestaña dentro de la sección "Categorías"
 */
public class FragmentoCategoria extends Fragment {

    private static final String INDICE_SECCION
            = "com.restaurantericoparico.FragmentoCategoriasTab.extra.INDICE_SECCION";

    private RecyclerView reciclador;
    private LinearLayoutManager layoutManager;
    private AdaptadorCategorias adaptador;

    public static FragmentoCategoria nuevaInstancia(int indiceSeccion) {
        FragmentoCategoria fragment = new FragmentoCategoria();
        Bundle args = new Bundle();
        args.putInt(INDICE_SECCION, indiceSeccion);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmento_grupo_items, container, false);

        reciclador = (RecyclerView) view.findViewById(R.id.reciclador);
        layoutManager = new LinearLayoutManager(getContext());
        reciclador.setLayoutManager(layoutManager);
        int indiceSeccion = getArguments().getInt(INDICE_SECCION);
        switch (indiceSeccion) {
            case 0:
                adaptador = new AdaptadorCategorias(Comida.PLATILLOS);
                break;
            case 1:
                adaptador = new AdaptadorCategorias(Comida.BEBIDAS);
                break;
            case 2:
                adaptador = new AdaptadorCategorias(Comida.COMIDAS_POPULARES);
                break;
        }

        reciclador.setAdapter(adaptador);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
