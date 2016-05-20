package com.example.diego.practica_android_mysql;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Menu extends AppCompatActivity implements OnClickListener {


    private Button btnNuevo, btnConsulta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnNuevo = (Button) findViewById(R.id.btnNuevo);
        btnNuevo.setOnClickListener(this);
        btnConsulta = (Button) findViewById(R.id.btnConsulta);
        btnConsulta.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btnConsulta) {
            Intent i = new Intent(this, ConsultaTalleres.class);
            startActivity(i);


        }
        if (v.getId() == R.id.btnNuevo) {
            Intent i = new Intent(this, NuevoTaller.class);
            startActivity(i);


        }

    }
}
