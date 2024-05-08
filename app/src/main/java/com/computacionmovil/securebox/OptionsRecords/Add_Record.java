package com.computacionmovil.securebox.OptionsRecords;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.computacionmovil.securebox.MainActivity;
import com.example.securebox.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Add_Record extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private boolean MODO_EDICION = false;
    EditText EtTitle, EtAccount, EtUserName, EtPassword, EtUrl, EtNotes;

    String  id,titulo,cuenta,nombre_usuario,password,sitio_web,nota;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("");

        inicializarVariables();
        ObtenerInformacion();

    }

    private void inicializarVariables(){
        EtTitle = findViewById(R.id.EtTitle);
        EtAccount = findViewById(R.id.EtAccount);
        EtUserName = findViewById(R.id.EtUserName);
        EtPassword = findViewById(R.id.EtPassword);
        EtUrl = findViewById(R.id.EtUrl);
        EtNotes = findViewById(R.id.EtNotes);

    }

    private void ObtenerInformacion() {
        Intent intent = getIntent();
        MODO_EDICION = intent.getBooleanExtra("MODO_EDICION", false);

        if (MODO_EDICION) {
            //Verdadero
            id = intent.getStringExtra("ID");
            titulo = intent.getStringExtra("TITULO");
            cuenta = intent.getStringExtra("CUENTA");
            nombre_usuario = intent.getStringExtra("NOMBRE_USUARIO");
            password = intent.getStringExtra("PASSWORD");
            sitio_web = intent.getStringExtra("SITIO_WEB");
            nota = intent.getStringExtra("NOTA");

            /*Seteamos la información recuperada en las vistas*/
            EtTitle.setText(titulo);
            EtAccount.setText(cuenta);
            EtUserName.setText(nombre_usuario);
            EtPassword.setText(password);
            EtUrl.setText(sitio_web);
            EtNotes.setText(nota);





        } else {
            //Falso, se agrega un nuevo registro

        }

    }


    private void Agregar_Actualizar_R() {


        if (MODO_EDICION) {
            //Verdadero

            actualizarEnBaseDatos(id);




        } else {
            guardarEnBaseDeDatos();

        }
    }



    private void guardarEnBaseDeDatos(){
        // Obtener el texto de cada campo y eliminar espacios en blanco alrededor
        String title = EtTitle.getText().toString().trim();
        String account = EtAccount.getText().toString().trim();
        String userName = EtUserName.getText().toString().trim();
        String password = EtPassword.getText().toString().trim();
        String url = EtUrl.getText().toString().trim();
        String notes = EtNotes.getText().toString().trim();

        // Verificar si algún campo está vacío
        if (title.isEmpty() || account.isEmpty() || userName.isEmpty() || password.isEmpty()) {
            // Mostrar un mensaje de error indicando que se deben completar todos los campos
            Toast.makeText(Add_Record.this, "Por favor complete los campos obligatorios", Toast.LENGTH_SHORT).show();
        } else {
            // Todos los campos están completos, proceder a guardar en la base de datos
            Map<String, Object> map = new HashMap<>();
            map.put("Title", title);
            map.put("Account", account);
            map.put("UserName", userName);
            map.put("Password", password);
            map.put("Url", url);
            map.put("Notes", notes);

            db.collection("records").add(map)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            // Mostrar mensaje de éxito y finalizar la actividad
                            Toast.makeText(Add_Record.this, "Record save successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Add_Record.this, MainActivity.class));
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Mostrar mensaje de error si falla la operación de guardado
                            Toast.makeText(Add_Record.this, "Error in create record in database", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void actualizarEnBaseDatos(String id){

        FirebaseFirestore db = FirebaseFirestore.getInstance();


        // Obtener el texto de cada campo y eliminar espacios en blanco alrededor
        String title = EtTitle.getText().toString().trim();
        String account = EtAccount.getText().toString().trim();
        String userName = EtUserName.getText().toString().trim();
        String password = EtPassword.getText().toString().trim();
        String url = EtUrl.getText().toString().trim();
        String notes = EtNotes.getText().toString().trim();

        // Verificar si algún campo está vacío
        if (title.isEmpty() || account.isEmpty() || userName.isEmpty() || password.isEmpty()) {
            // Mostrar un mensaje de error indicando que se deben completar todos los campos
            Toast.makeText(Add_Record.this, "Por favor complete los campos obligatorios", Toast.LENGTH_SHORT).show();
        } else {
            // Todos los campos están completos, proceder a guardar en la base de datos
            Map<String, Object> map = new HashMap<>();
            map.put("Title", title);
            map.put("Account", account);
            map.put("UserName", userName);
            map.put("Password", password);
            map.put("Url", url);
            map.put("Notes", notes);

            db.collection("records").document(String.valueOf(id)).update(map)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("Firebase", "Documento actualizado correctamente");
                            startActivity(new Intent(Add_Record.this, MainActivity.class));
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("Firebase", "Error al actualizar documento", e);
                            // Maneja el error en caso de que la actualización falle
                            // Mostrar mensaje de error si falla la operación de guardado
                            Toast.makeText(Add_Record.this, "Error in create record in database", Toast.LENGTH_SHORT).show();
                        }
                    });
            }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_guardar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save_password){
            Agregar_Actualizar_R();
        }
        return super.onOptionsItemSelected(item);
    }


}