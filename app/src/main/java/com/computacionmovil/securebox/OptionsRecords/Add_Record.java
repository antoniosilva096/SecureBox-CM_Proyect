package com.computacionmovil.securebox.OptionsRecords;

import android.os.Bundle;
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

import com.example.securebox.R;

public class Add_Record extends AppCompatActivity {

    EditText EtTitle, EtAccount, EtUserName, EtPassword, EtUrl, EtNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("");

        inicializarVariables();
    }

    private void inicializarVariables(){
        EtTitle = findViewById(R.id.EtTitle);
        EtAccount = findViewById(R.id.EtAccount);
        EtUserName = findViewById(R.id.EtUserName);
        EtPassword = findViewById(R.id.EtPassword);
        EtUrl = findViewById(R.id.EtUrl);
        EtNotes = findViewById(R.id.EtNotes);

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
            Toast.makeText(this, "Save record", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}