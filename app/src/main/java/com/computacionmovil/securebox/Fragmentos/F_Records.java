package com.computacionmovil.securebox.Fragmentos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.securebox.R;

public class F_Records extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_f__records, container,false); //Lamamos a la vista del fragmento de registros
        return inflater.inflate(R.layout.fragment_f__records, container, false);
    }
}