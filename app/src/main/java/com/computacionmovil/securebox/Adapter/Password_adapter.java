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
import com.computacionmovil.securebox.Fragmentos.F_Records;
import com.computacionmovil.securebox.MainActivity;
import com.computacionmovil.securebox.Modelo.Passwords;
import com.computacionmovil.securebox.OptionsRecords.Add_Record;
import com.example.securebox.R;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.firestore.FirebaseFirestore;


import java.util.ArrayList;


public class Password_adapter  extends RecyclerView.Adapter<Password_adapter.HolderPassword>{

    private Context context;
    private ArrayList<Passwords> passwordsList;
    Dialog dialogo;

    public Password_adapter(Context context, ArrayList<Passwords> passwordsList) {
        this.context = context;
        this.passwordsList = passwordsList;
        this.dialogo = new Dialog(context);
    }

    @NonNull
    @Override
    public HolderPassword onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /*Inflamos el item, es decir pasamos los datos al objeto xml y lo convertimos en interfaz*/
        View view = LayoutInflater.from(context).inflate(R.layout.item_password, parent, false);

        return new HolderPassword(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderPassword holder, @SuppressLint("RecyclerView") int position) {
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


        holder.Ib_mas_opciones.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Opciones_Editar_Eliminar(
                        ""+ position,
                        ""+ id,
                        ""+ titulo,
                        ""+ cuenta,
                        ""+ nombreUsuario,
                        ""+ password,
                        ""+ url,
                        ""+ nota

                );
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
        ImageButton Ib_mas_opciones;


        public HolderPassword(@NonNull View itemView) {
            super(itemView);

            Item_titulo = itemView.findViewById(R.id.Item_titulo);
            Item_cuenta = itemView.findViewById(R.id.Item_cuenta);
            Item_nombre_usuario = itemView.findViewById(R.id.Item_nombre_usuario);
            Item_password = itemView.findViewById(R.id.Item_password);
            Item_sitio_web = itemView.findViewById(R.id.Item_sitio_web);
            Item_nota = itemView.findViewById(R.id.Item_nota);
            Ib_mas_opciones = itemView.findViewById(R.id.Ib_mas_opciones); // Inicializa Ib_mas_opciones



        }
    }

    private  void eliminarRegistro(String id){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("records").document(id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Registro eliminado exitosamente
                        Toast.makeText(context, "Registro eliminado correctamente", Toast.LENGTH_SHORT).show();
                        // Aquí puedes realizar acciones adicionales después de la eliminación
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Error al intentar eliminar el registro
                        Toast.makeText(context,"Error al eliminar el registro", Toast.LENGTH_SHORT).show();
                        Log.e("Firebase", "Error al eliminar el registro", e);
                    }
                });
    }

    private void Opciones_Editar_Eliminar(String posicion, String id, String titulo, String cuenta,
                                          String nombre_usuario, String password, String sitio_web,
                                          String nota){
        Button Btn_Editar_Registro, Btn_Eliminar_Registro;

        dialogo.setContentView(R.layout.cuadro_dialogo_editar_eliminar);

        Btn_Editar_Registro = dialogo.findViewById(R.id.Btn_Editar_Registro);
        Btn_Eliminar_Registro = dialogo.findViewById(R.id.Btn_Eliminar_Registro);

        Btn_Editar_Registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Add_Record.class);
                intent.putExtra("POSICION", posicion);
                intent.putExtra("ID", id);
                intent.putExtra("TITULO", titulo);
                intent.putExtra("CUENTA", cuenta);
                intent.putExtra("NOMBRE_USUARIO", nombre_usuario);
                intent.putExtra("PASSWORD", password);
                intent.putExtra("SITIO_WEB", sitio_web);
                intent.putExtra("NOTA", nota);
                intent.putExtra("MODO_EDICION", true);
                context.startActivity(intent);
                dialogo.dismiss();
            }
        });

        Btn_Eliminar_Registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarRegistro(id);
                Intent intent = new Intent(context, MainActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                Toast.makeText(context, "Registro eliminado", Toast.LENGTH_SHORT).show();
                dialogo.dismiss();
            }
        });

        dialogo.show();
        dialogo.setCancelable(true);
    }


}
