package com.example.diego.practica_android_mysql;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

public class ModificarTalleres extends AppCompatActivity {
    private EditText etDescripcion, etHoras, etLugar;
    private Button btnActualizar, btnEliminar;
    private DatePicker dtFI, dtFF;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_talleres);

        etDescripcion = (EditText)findViewById(R.id.etDescripcion);
        etHoras = (EditText)findViewById(R.id.etHoras);
        etLugar = (EditText)findViewById(R.id.etLugar);
        dtFI=(DatePicker)findViewById(R.id.dtFI);
        dtFF=(DatePicker)findViewById(R.id.dtFF);
        btnActualizar= (Button)findViewById(R.id.btnActualizar);
        btnEliminar=(Button)findViewById(R.id.btnEliminar);

        String[] dato = getIntent().getExtras().getString("Registro").split(":");
        System.out.println(dato.length);
        System.out.println(dato[0]);
        System.out.println(dato[1]);
        for (String s:dato
             ) {

            System.out.println(s+"fore");

        }
        //etDescripcion.setText(dato[1]);
        //etHoras.setText(dato[2]);
        //etLugar.setText(dato[3]);
        //System.out.println(dato[4]);
        //dtFI.updateDate();
    }
}