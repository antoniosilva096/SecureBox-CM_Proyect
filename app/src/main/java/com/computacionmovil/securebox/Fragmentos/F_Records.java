package com.computacionmovil.securebox.Fragmentos;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.computacionmovil.securebox.Adapter.Password_adapter;
import com.computacionmovil.securebox.Modelo.Passwords;
import com.computacionmovil.securebox.OptionsRecords.Add_Record;
import com.example.securebox.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class F_Records extends Fragment {

    private RecyclerView recyclerView_Records;
    private FloatingActionButton FAB_AddButton;
    private Password_adapter passwordAdapter;

    private Dialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_f__records, container, false);

        recyclerView_Records = view.findViewById(R.id.recyclerView_Records);
        FAB_AddButton = view.findViewById(R.id.FAB_AddButton);
        dialog = new Dialog(getActivity());

        cargarRegistros(); // Cargar los registros desde Firestore y configurar el RecyclerView

        FAB_AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Add_Record.class);
                intent.putExtra("MODO_EDICION", false);
                startActivity(intent);

            }
        });

        return view;

    }

    private void cargarRegistros() {
        obtenerRegistrosBD(new FirestoreCallback() {
            @Override
            public void onCallback(ArrayList<Passwords> resultados) {
                if (resultados.isEmpty()) {
                    // No hay documentos en Firestore, no se realiza ninguna acción
                    Log.d("Firestore", "No hay documentos en la colección 'records'");
                    // Puedes mostrar un mensaje o realizar alguna acción apropiada aquí
                } else {
                    // Hay documentos, actualiza el RecyclerView con los resultados
                    Log.d("DATOS", String.valueOf(resultados.get(0).getTitle()));
                    passwordAdapter = new Password_adapter(getActivity(), resultados);
                    recyclerView_Records.setAdapter(passwordAdapter);
                }
            }
        });
    }


    private void obtenerRegistrosBD(final FirestoreCallback firestoreCallback) {
        String nombreColeccion = "records";
        final ArrayList<Passwords> resultados = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(nombreColeccion)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                String id = document.getId();
                                String title = document.getString("Title");
                                String account = document.getString("Account");
                                String username = document.getString("UserName");
                                String password = document.getString("Password");
                                String url = document.getString("Url");
                                String notes = document.getString("Notes");

                                Passwords registro = new Passwords(id, title, account, username, password, url, notes);

                                resultados.add(registro);
                            }
                            // Llama al método de callback con los resultados
                            firestoreCallback.onCallback(resultados);
                        } else {
                            Log.d("Firestore", "Error al obtener documentos: ", task.getException());
                        }
                    }
                });
    }





    private ArrayList<Passwords> busquedaIndexada(String busqueda) {

        ArrayList<Passwords> resultados = new ArrayList<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String nombreColeccion = "records";
        String campoBuscar = "Title";

        db.collection(nombreColeccion)
                .whereEqualTo(campoBuscar, busqueda)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                String id = document.getId();
                                String title = document.getString("Title");
                                String account = document.getString("Account");
                                String username = document.getString("UserName");
                                String password = document.getString("Password");
                                String url = document.getString("Url");
                                String notes = document.getString("Notes");

                                Passwords registro = new Passwords(id, title, account, username, password, url, notes);

                                resultados.add(registro);
                            }

                        } else {
                            Log.d("Firestore", "Error al realizar la búsqueda: ", task.getException());
                            // Llama al callback con una lista vacía en caso de error

                        }
                    }

                });
        return resultados;
    }

    // Interfaz de callback para manejar el resultado de Firestore
    private interface FirestoreCallback {
        void onCallback(ArrayList<Passwords> resultados);
    }

    private void BuscarRegistros(String consulta) {
        ArrayList<Passwords> resultados = busquedaIndexada(consulta);
        passwordAdapter = new Password_adapter(getActivity(),resultados);
        recyclerView_Records.setAdapter(passwordAdapter);


    }



    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragmento_records, menu);
/*
        MenuItem item = menu.findItem(R.id.Buscar_registros);
        SearchView searchView = (SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                BuscarRegistros(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                BuscarRegistros(newText);
                return true;
            }
        });*/

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_numero_registros){

            visualizar_total_registros();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        cargarRegistros();
        super.onResume();
    }

    private void visualizar_total_registros(){
        TextView Total;
        Button BtnOkTotal;

        dialog.setContentView(R.layout.ventana_modal_total_registros);

        Total = dialog.findViewById(R.id.Total);

        BtnOkTotal = dialog.findViewById(R.id.BtnEntendidoTotal);

        //Obtener entero registros

        int total = passwordAdapter.getItemCount();

        String totalString = String.valueOf(total);

        Total.setText(totalString);

        BtnOkTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.setCancelable(false);

    }
}
