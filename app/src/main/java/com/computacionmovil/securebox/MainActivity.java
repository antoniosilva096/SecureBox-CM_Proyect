package com.computacionmovil.securebox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar; //Importante importar el mismo toolbar que en el xml

import com.example.securebox.R;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Creamos un la barra de opciones (toolbar)

        Toolbar toolbar = findViewById(R.id.toolbar);

        //Lo pasamos como parámetro al oncreate action-bar

        setSupportActionBar(toolbar);

        //Definimos srawerlayout (menú despleglable)
        drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null); //Para respetar nuestra configuración personalizada de iconos (como los colores)

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close); //Creamos el botón de abrir cerrar el menú lateral

        drawer.addDrawerListener(toggle); //Lo pasamos por parámetro al menú

        toggle.syncState();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }
}