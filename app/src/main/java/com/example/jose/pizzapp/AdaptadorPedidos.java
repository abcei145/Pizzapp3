package com.example.jose.pizzapp;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

/**
 * Adaptador para poblar la lista de direcciones de la sección "Mi Cuenta"
 */
public class AdaptadorPedidos
        extends RecyclerView.Adapter<AdaptadorPedidos.ViewHolder> {
    FragmentManager fragmentManager;
    private Activity acti;
    AlertDialog.Builder dialogo1;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public TextView nombre;
        public TextView precio;
        public TextView cantidad;
        public TextView subtotal;
        public ImageView miniatura;
        public Context context;
        public ViewHolder(final View v) {
            super(v);
            context=v.getContext();
            miniatura=(ImageView)v.findViewById(R.id.miniatura_pedido);


            nombre = (TextView) v.findViewById(R.id.nombre_pedido);
            precio = (TextView) v.findViewById(R.id.precio_pedido);
            cantidad = (TextView) v.findViewById(R.id.cantidad_pedido);
            subtotal = (TextView) v.findViewById(R.id.subtotal_pedido);
        }
    }


    public AdaptadorPedidos(FragmentManager fragmentManager) {
        this.fragmentManager=fragmentManager;
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
    public void onBindViewHolder(ViewHolder viewHolder1, int i) {

        final ViewHolder viewHolder=viewHolder1;
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
            viewHolder.miniatura.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view1) {
                    final View view=view1;
                    dialogo1= new AlertDialog.Builder(view.getContext());
                    dialogo1.setTitle("¿Estas seguro que desas agregar " +
                            "este producto a tu pedido? ");
                    dialogo1.setMessage("Producto: "+viewHolder.nombre.getText().toString()+"Valor: "+viewHolder.precio.getText().toString());
                    dialogo1.setCancelable(true);
                    dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id) {
                            aceptar(view);
                        }
                    });
                    dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id) {
                            cancelar();
                        }
                    });
                    dialogo1.show();
                }
                public void aceptar(View view){
                    PedidoDatos Pedido = new PedidoDatos(MainActivity.getContext());
                    SQLiteDatabase bd = Pedido.getWritableDatabase();
                    Cursor c=Pedido.ObtainAllRows(bd);
                    if(c.moveToFirst()){
                        Pedido.delete(bd);
                        Pedido.CreateIfNoExists(bd);
                        ContentValues registro = new ContentValues();
                        if(!c.getString(1).equals(viewHolder.nombre.getText().toString())) {
                            //Es para guardar los datos ingresados
                            registro.put("nombre", c.getString(1));
                            registro.put("cantidad", c.getString(2));
                            registro.put("valor", c.getString(3));
                            registro.put("subtotal", c.getString(4));
                            bd.insert("Pedido", null, registro);
                        }
                        Toast.makeText(view.getContext(), "This shit is under construction", Toast.LENGTH_SHORT).show();

                        while(c.moveToNext()){
                            registro = new ContentValues();//Es para guardar los datos ingresados
                            if(!c.getString(1).equals(viewHolder.nombre.getText().toString())) {
                                //Es para guardar los datos ingresados
                                registro.put("nombre", c.getString(1));
                                registro.put("cantidad", c.getString(2));
                                registro.put("valor", c.getString(3));
                                registro.put("subtotal", c.getString(4));
                                bd.insert("Pedido", null, registro);
                            }
                        }
                    }
                    bd.close();
                    Fragment fragment;
                    fragment=new FragmentoPedidos();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                }
                public void cancelar(){

                }
            });
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