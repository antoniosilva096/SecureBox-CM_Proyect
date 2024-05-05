package com.computacionmovil.securebox.Fragmentos;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.securebox.R;


public class F_info extends Fragment {

    ImageView Ir_facebook, Ir_Instagram, Ir_Youtube, Ir_Twitter;
    Button Ir__github_web;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_f_info, container, false);


        Ir__github_web = view.findViewById(R.id.Ir_github_web);


        Ir__github_web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ir_p_github = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/antoniosilva096/SecureBox-CM_Proyect"));
                startActivity(ir_p_github);
            }
        });

        return view;
    }
}