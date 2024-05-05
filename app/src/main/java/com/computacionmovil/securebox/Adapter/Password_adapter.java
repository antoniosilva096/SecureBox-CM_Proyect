package com.computacionmovil.securebox.Adapter;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.computacionmovil.securebox.Detalle.Detalle_registro;
import com.computacionmovil.securebox.Modelo.Passwords;
import com.example.securebox.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class Password_adapter  extends RecyclerView.Adapter<Password_adapter.HolderPassword>{

    private Context context;
    private ArrayList<Passwords> passwordsList;

    public Password_adapter(Context context, ArrayList<Passwords> passwordsList) {
        this.context = context;
        this.passwordsList = passwordsList;
    }

    @NonNull
    @Override
    public HolderPassword onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /*Inflamos el item, es decir pasamos los datos al objeto xml y lo convertimos en interfaz*/
        View view = LayoutInflater.from(context).inflate(R.layout.item_password, parent, false);

        return new HolderPassword(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderPassword holder, int position) {
        Passwords modelo_password = passwordsList.get(position);
        String id = modelo_password.getId();
        String titulo = modelo_password.getTitle();
        String cuenta = modelo_password.getAccount();
        String nombreUsuario = modelo_password.getUsername();
        String password = modelo_password.getPassword();
        String url = modelo_password.getUrl();
        String nota = modelo_password.getNotes();

        holder.Item_titulo.setText(titulo);
        holder.Item_cuenta.setText(cuenta);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Usuario presiona en el item*/
                Intent intent = new Intent(context, Detalle_registro.class);
                /*Enviamos el dato id a la actividad Detalle_registro*/
                Log.d("identificador",id);
                intent.putExtra("Id_registro", id);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        /*Devolvemos el tamaño de la lista*/
        return passwordsList.size();
    }

    class HolderPassword extends RecyclerView.ViewHolder{

        TextView Item_titulo, Item_cuenta, Item_nombre_usuario, Item_password, Item_sitio_web, Item_nota;


        public HolderPassword(@NonNull View itemView) {
            super(itemView);

            Item_titulo = itemView.findViewById(R.id.Item_titulo);
            Item_cuenta = itemView.findViewById(R.id.Item_cuenta);
            Item_nombre_usuario = itemView.findViewById(R.id.Item_nombre_usuario);
            Item_password = itemView.findViewById(R.id.Item_password);
            Item_sitio_web = itemView.findViewById(R.id.Item_sitio_web);
            Item_nota = itemView.findViewById(R.id.Item_nota);



        }
    }


}
