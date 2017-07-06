package com.example.jose.pizzapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Adaptador para comidas usadas en la sección "Categorías"
 */
public class AdaptadorCategorias
        extends RecyclerView.Adapter<AdaptadorCategorias.ViewHolder> {


    private final List<Comida> items;
    private Context context;
    AlertDialog.Builder dialogo1;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public TextView nombre;
        public TextView precio;
        public ImageView imagen;
        public ViewHolder(View v) {
            super(v);
            nombre = (TextView) v.findViewById(R.id.nombre_comida);
            precio = (TextView) v.findViewById(R.id.precio_comida);
            imagen = (ImageView) v.findViewById(R.id.miniatura_comida);


        }


    }


    public AdaptadorCategorias(List<Comida> items)  {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        context=viewGroup.getContext();
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_lista_categorias, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        final Comida item = items.get(i);


        Glide.with(viewHolder.itemView.getContext())
                .load(item.getIdDrawable())
                .centerCrop()
                .into(viewHolder.imagen);
        viewHolder.nombre.setText(item.getNombre());
        viewHolder.precio.setText("$" + item.getPrecio());
        viewHolder.imagen.setOnClickListener(new View.OnClickListener() {
            String nombre=item.getNombre();
            String precio=Float.toString(item.getPrecio());
            @Override
            public void onClick(View v) {

                dialogo1 = new AlertDialog.Builder(v.getContext());
                dialogo1.setTitle("¿Estas seguro que desas agregar este producto a tu pedido? ");
                dialogo1.setMessage("Producto: "+viewHolder.nombre.getText().toString()+"Valor: "+viewHolder.precio.getText().toString());
                dialogo1.setCancelable(true);
                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        aceptar();
                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        cancelar();
                    }
                });
                dialogo1.show();
            }

            public void aceptar() {
                PedidoDatos Pedido = new PedidoDatos(context);
                SQLiteDatabase bd = Pedido.getWritableDatabase();
                String CantidadActual;
                String query1 = "select * from " + "Pedido" + " WHERE nombre=?";
                Pedido.CreateIfNoExists(bd);
                Cursor c=bd.rawQuery(query1, new String[]{nombre});
                //Cursor c = bd.rawQuery("select * from Pedido where nombre in ('" + nombre+"')",null );
                if(c.moveToFirst()){
                    CantidadActual=c.getString(2);
                    ContentValues registro = new ContentValues();
                    registro.put("cantidad", Integer.parseInt(CantidadActual)+1);
                    registro.put("subtotal",Float.toString(Float.parseFloat(CantidadActual)*Float.parseFloat(precio)));
                    bd.update("Pedido", registro, "nombre= '" + nombre + "'", null);

                    Toast.makeText(context, "Existe el Pedido "+ nombre+" cantidad: "+c.getString(2),
                            Toast.LENGTH_SHORT).show();

                }else{
                    ContentValues registro = new ContentValues();//Es para guardar los datos ingresados
                    registro.put("nombre", nombre);
                    registro.put("cantidad", 1);
                    registro.put("valor", precio);
                    registro.put("subtotal", precio);

                    bd.insert("Pedido", null, registro);
                    Toast.makeText(context, "No existe el Pedido "+ nombre+":(",
                            Toast.LENGTH_SHORT).show();
                }

                bd.close();

            }

            public void cancelar() {

            }
        });
    }


}