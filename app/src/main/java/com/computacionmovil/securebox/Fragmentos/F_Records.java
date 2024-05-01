package com.computacionmovil.securebox.Fragmentos;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.computacionmovil.securebox.OptionsRecords.Add_Record;
import com.example.securebox.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class F_Records extends Fragment {

    FloatingActionButton FAB_AddButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_f__records, container,false); //Lamamos a la vista del fragmento de registros


        FAB_AddButton = view.findViewById(R.id.FAB_AddButton);

        FAB_AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Add_Record.class)); //Vinculamos el evento de hacer click en el botón flotante con invocar a la actividad de agregar registro
            }
        });
        return view;
    }
}