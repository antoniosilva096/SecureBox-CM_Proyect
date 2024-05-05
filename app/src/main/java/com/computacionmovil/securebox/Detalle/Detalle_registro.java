package com.computacionmovil.securebox.Detalle;

import android.annotation.SuppressLint;
import android.app.Dialog;

import android.content.Intent;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.securebox.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Detalle_registro extends AppCompatActivity {



    TextView D_Titulo, D_Cuenta, D_NombreUsuario, D_SitioWeb, D_Nota, D_Tiempo_registro, D_Tiempo_actualizacion;
    EditText D_Password;

    ImageView D_Imagen;
    String id_registro;


    Dialog dialog;

    ImageButton Im_Ir_Web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE , WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_detalle_registro);

        ActionBar actionBar = getSupportActionBar();

        Intent intent = getIntent();
        id_registro = intent.getStringExtra("Id_registro");
        Toast.makeText(this, "Id of record: "+id_registro, Toast.LENGTH_SHORT).show();



        InicializarVariables();
        MostrarInformacionRegistro();

        /*Obtener titulo del registro*/

        String titulo_registro = D_Titulo.getText().toString();
        assert actionBar != null;
        actionBar.setTitle(titulo_registro);
        /*flecha de retroceso*/
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void InicializarVariables(){
        D_Titulo = findViewById(R.id.D_Titulo);
        D_Cuenta = findViewById(R.id.D_Cuenta);
        D_NombreUsuario = findViewById(R.id.D_NombreUsuario);
        D_Password = findViewById(R.id.D_Password);
        D_SitioWeb = findViewById(R.id.D_SitioWeb);
        D_Nota = findViewById(R.id.D_Nota);

        D_Imagen = findViewById(R.id.D_Imagen);


        dialog = new Dialog(this);
    }


    private void MostrarInformacionRegistro(){


        FirebaseFirestore db = FirebaseFirestore.getInstance();


        String coleccion = "records";
        String idDocumento = id_registro;
        DocumentReference docRef = db.collection(coleccion).document(idDocumento);

        // Obtiene el documento y verifica su existencia
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // El documento existe, accede a sus datos

                        String title = document.getString("Title");
                        String account = document.getString("Account");
                        String username = document.getString("UserName");
                        String password = document.getString("Password");
                        String url = document.getString("Url");
                        String notes = document.getString("Notes");


                        /*Setear la información en las vistas*/
                        D_Titulo.setText(title);
                        D_Cuenta.setText(account);
                        D_NombreUsuario.setText(username);
                        D_Password.setText(password);
                        D_Password.setEnabled(false);
                        D_Password.setBackgroundColor(Color.TRANSPARENT);
                        D_Password.setInputType(InputType.TYPE_CLASS_TEXT| InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        D_SitioWeb.setText(url);
                        D_Nota.setText(notes);


                        // Establecemos el título del ActionBar después de obtener los datos
                        if (getSupportActionBar() != null) {
                            getSupportActionBar().setTitle(title);


                        }

                    } else {
                        // El documento no existe
                    }
                } else {
                    // Error al obtener el documento
                    Exception exception = task.getException();
                    if (exception != null) {
                        // Maneja el error adecuadamente
                    }

                }

            }
        });

    }


}
