package com.example.diego.practicagps;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

/**
 * Created by Diego on 20/06/2016.
 */
public class MiServicio implements LocationListener {

    MainActivity mainActivity;

    public MainActivity getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void onLocationChanged(Location location) {
        location.getLatitude();
        location.getLongitude();
        String coor = "Latitud es : "+location.getLatitude()+"\n"+" Longitud es : "+ location.getLongitude();
        this.mainActivity.tvUbicacion.setText(coor);
        this.mainActivity.latitud=location.getLatitude();
        this.mainActivity.longitud=location.getLongitude();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        mainActivity.tvUbicacion.setText("GPS Desactivado");

    }

    @Override
    public void onProviderEnabled(String provider) {
        mainActivity.tvUbicacion.setText("GPS Activado");

    }

    @Override
    public void onProviderDisabled(String provider) {


    }
}
