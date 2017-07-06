package com.example.jose.pizzapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Modelo de datos estático para alimentar la aplicación
 */
public class Comida {
    private float precio;
    private String nombre;
    private int idDrawable;

    public Comida(float precio, String nombre, int idDrawable) {
        this.precio = precio;
        this.nombre = nombre;
        this.idDrawable = idDrawable;
    }

    public static final List<Comida> COMIDAS_POPULARES = new ArrayList<Comida>();
    public static final List<Comida> BEBIDAS = new ArrayList<>();
    public static final List<Comida> POSTRES = new ArrayList<>();
    public static final List<Comida> PLATILLOS = new ArrayList<>();

    static {
        COMIDAS_POPULARES.add(new Comida(5, "Camarones Tismados", R.drawable.pizzaicon2));
        COMIDAS_POPULARES.add(new Comida(3.2f, "Rosca Herbárea", R.drawable.pizzaicon2));
        COMIDAS_POPULARES.add(new Comida(12f, "Sushi Extremo", R.drawable.pizzaicon2));
        COMIDAS_POPULARES.add(new Comida(9, "Sandwich Deli", R.drawable.pizzaicon2));

        PLATILLOS.add(new Comida(5, "Camarones Tismados", R.drawable.pizzaicon2));
        PLATILLOS.add(new Comida(3.2f, "Rosca Herbárea", R.drawable.pizzaicon2));
        PLATILLOS.add(new Comida(12f, "Sushi Extremo", R.drawable.pizzaicon2));
        PLATILLOS.add(new Comida(9, "Sandwich Deli", R.drawable.pizzaicon2));
        PLATILLOS.add(new Comida(34, "Lomo De Cerdo Austral", R.drawable.pizzaicon2));

        BEBIDAS.add(new Comida(3, "Taza de Café",R.drawable.peperoni));
        BEBIDAS.add(new Comida(12, "Coctel Tronchatoro", R.drawable.peperoni));
        BEBIDAS.add(new Comida(5, "Jugo Natural", R.drawable.peperoni));
        BEBIDAS.add(new Comida(24, "Coctel Jordano", R.drawable.peperoni));
        BEBIDAS.add(new Comida(30, "Botella Vino Tinto Darius", R.drawable.peperoni));

    }

    public float getPrecio() {
        return precio;
    }

    public String getNombre() {
        return nombre;
    }

    public int getIdDrawable() {
        return idDrawable;
    }
}
