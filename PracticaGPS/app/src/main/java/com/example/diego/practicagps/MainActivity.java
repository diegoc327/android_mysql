package com.example.diego.practicagps;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public TextView tvUbicacion;
    public Button btnActualizar;
    public Location location;
    public Button btnMapa;
    public static double latitud;
    public static double longitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvUbicacion = (TextView) findViewById(R.id.tvUbicacion);


        final LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        final MiServicio mlocListener = new MiServicio();
        mlocListener.setMainActivity(this);

        // TODO: Consider calling
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000 * 60, 10, (LocationListener) mlocListener);



        btnMapa = (Button) findViewById(R.id.btnMapa);
        btnMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(MainActivity.this, MapsActivity.class);
                startActivity(i);
            }
        });

        btnActualizar = (Button) findViewById(R.id.btnActualizar);
        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    return;
                }
                location = mlocManager.getLastKnownLocation(mlocManager.GPS_PROVIDER);
                String coor = "Latitud es : " + location.getLatitude() + "\n" + " Longitud es : " + location.getLongitude();
                tvUbicacion.setText(coor + "\n" + "boton");
                latitud=location.getLatitude();
                longitud=location.getLongitude();
            }
        });
    }
}
