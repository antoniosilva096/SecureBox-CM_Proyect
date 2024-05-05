package com.computacionmovil.securebox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar; //Importante importar el mismo toolbar que en el xml

import com.computacionmovil.securebox.Fragmentos.F_Records;
import com.computacionmovil.securebox.Fragmentos.F_Settings;
import com.computacionmovil.securebox.Fragmentos.F_info;
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

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new F_Records()).commit(); //Establecemos el fragmento que muestra los registros como el que se muestra por defecto

            navigationView.setCheckedItem(R.id.Option_All);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        //Aquí controlaremos los fragmentos
        //Vamos a asociar cada fragmentocon el evento de pulsar uno de los "botones"(items) del menú lateral
        //Estó modificará la vista que se presenta ya que solo se visualizará el fragmento seleccionado que se haya vinculado al item

        int id = menuItem.getItemId();
        if (id == R.id.Option_All){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new F_Records()).commit();
        }

        if(id == R.id.Option_Settings){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new F_Settings()).commit();
        }

        if(id == R.id.Option_Info){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new F_info()).commit();
        }

        if (id == R.id.Option_Exit){
            Toast.makeText(this, "Logout...", Toast.LENGTH_SHORT).show();
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

}