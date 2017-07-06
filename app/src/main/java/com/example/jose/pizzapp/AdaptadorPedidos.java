package com.example.jose.pizzapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

/**
 * Adaptador para poblar la lista de direcciones de la sección "Mi Cuenta"
 */
public class AdaptadorPedidos
        extends RecyclerView.Adapter<AdaptadorPedidos.ViewHolder> {


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public TextView nombre;
        public TextView precio;
        public TextView cantidad;
        public TextView subtotal;
        public ImageView miniatura;
        public Context context;
        public ViewHolder(View v) {
            super(v);
            context=v.getContext();
            miniatura=(ImageView)v.findViewById(R.id.miniatura_pedido);

            nombre = (TextView) v.findViewById(R.id.nombre_pedido);
            precio = (TextView) v.findViewById(R.id.precio_pedido);
            cantidad = (TextView) v.findViewById(R.id.cantidad_pedido);
            subtotal = (TextView) v.findViewById(R.id.subtotal_pedido);
        }
    }


    public AdaptadorPedidos() {
    }

    @Override
    public int getItemCount() {

        PedidoDatos Pedido = new PedidoDatos(MainActivity.getContext());
        SQLiteDatabase bd = Pedido.getWritableDatabase();

        Cursor c=bd.rawQuery("SELECT * FROM Pedido",null);
        return c.getCount();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_lista_pedidos, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        PedidoDatos Pedido = new PedidoDatos(MainActivity.getContext());
        SQLiteDatabase bd = Pedido.getWritableDatabase();
        String query1 = "select * from " + "Pedido" + " WHERE id=?";
        Cursor c=bd.rawQuery(query1, new String[]{Integer.toString(i+1)});

        /*Pedido item = Pedido.PEDIDOS.get(i);*/
        Glide.with(viewHolder.itemView.getContext())
                .load(R.drawable.pizzaicon2)
                .centerCrop()
                .into(viewHolder.miniatura);
        if(c.moveToFirst()) {
            viewHolder.nombre.setText(c.getString(1));
            viewHolder.precio.setText(c.getString(2));
            viewHolder.cantidad.setText(c.getString(3));
            viewHolder.subtotal.setText(c.getString(4));
        }
    }

    /**
     * Modelo de datos para probar el adaptador
     */
    public static class Pedido {
        public String nombre;
        public String precio;
        public String cantidad;
        public String subtotal;

        public Pedido(String nombre, String precio, String cantidad, String subtotal) {
            this.nombre = nombre;
            this.precio = precio;
            this.cantidad = cantidad;
            this.subtotal = subtotal;
        }

    }


}