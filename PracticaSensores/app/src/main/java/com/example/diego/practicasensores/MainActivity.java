package com.example.diego.practicasensores;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends Activity implements SensorEventListener {
    private SensorManager mSensorManager;
    private TextView txtAcelerometro;
    private TextView txtOrientacion;
    private TextView txtMagnetic;
    private TextView txtProximity;
    private TextView txtLuminosidad;
    private TextView txtTemperatura;
    private TextView txtGravedad;
    private TextView txtDetecta;
    private TextView txtGiro;
    private TextView txtPresion;

    float max_x = 0;
    float max_y = 0;
    float max_z = 0;

    DecimalFormat dosdecimales = new DecimalFormat("###.###");

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Defino los botones
        Button sensores = (Button) findViewById(R.id.btnLista);
        Button limpia = (Button) findViewById(R.id.btnLimpiar);

        // Defino los TXT para representar los datos de los sensores
        txtAcelerometro = (TextView) findViewById(R.id.txtAcelerometro);
        txtOrientacion = (TextView) findViewById(R.id.txtOrientacion);
        txtMagnetic = (TextView) findViewById(R.id.txtMagnetic);
        txtProximity = (TextView) findViewById(R.id.txtProximity);
        txtLuminosidad = (TextView) findViewById(R.id.txtLuminosidad);
        txtTemperatura = (TextView) findViewById(R.id.txtTemperatura);
        txtGravedad = (TextView) findViewById(R.id.txtGravedad);
        txtDetecta = (TextView) findViewById(R.id.txtDetecta);
        txtGiro = (TextView) findViewById(R.id.txtGiro);
        txtPresion = (TextView) findViewById(R.id.txtPresion);

        // Accedemos al servicio de sensores
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        // Boton que muestra el listado de los sensores disponibles
        sensores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                i.setClass(MainActivity.this, ListaSensoresActivity.class);

                startActivity(i);
            }
        });

        // Limpio el texto de la deteccion
        limpia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtDetecta.setText("");
                txtDetecta.setBackgroundColor(Color.parseColor("#000000"));

            }
        });

    }

    // Metodo para iniciar el acceso a los sensores
    protected void Ini_Sensores() {

        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);

        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_NORMAL);

        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR),
                SensorManager.SENSOR_DELAY_NORMAL);

        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                SensorManager.SENSOR_DELAY_NORMAL);

        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY),
                SensorManager.SENSOR_DELAY_NORMAL);

        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT),
                SensorManager.SENSOR_DELAY_NORMAL);

        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE),
                SensorManager.SENSOR_DELAY_NORMAL);

        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY),
                SensorManager.SENSOR_DELAY_NORMAL);

        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    // Metodo para parar la escucha de los sensores
    private void Parar_Sensores() {

        mSensorManager.unregisterListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));

        mSensorManager.unregisterListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION));

        mSensorManager.unregisterListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD));

        mSensorManager.unregisterListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY));

        mSensorManager.unregisterListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT));

        mSensorManager.unregisterListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE));

        mSensorManager.unregisterListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY));

        mSensorManager.unregisterListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR));

        mSensorManager.unregisterListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE));
    }

    // Metodo que escucha el cambio de sensibilidad de los sensores
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    // Metodo que escucha el cambio de los sensores
    @Override
    public void onSensorChanged(SensorEvent event) {
        String txt = "\n\nSensor: ";

        // Cada sensor puede lanzar un thread que pase por aqui
        // Para asegurarnos ante los accesos simult‡neos sincronizamos esto

        synchronized (this) {
            Log.d("sensor", event.sensor.getName());

            switch (event.sensor.getType()) {
                case Sensor.TYPE_ORIENTATION:
                    txt += "orientation\n";
                    txt += "\n azimut: " + getDireccion(event.values[0]);
                    txt += "\n y: " + event.values[1] + "¼";
                    txt += "\n z: " + event.values[2] + "¼";
                    txtOrientacion.setText(txt);
                    break;

                case Sensor.TYPE_ACCELEROMETER:

                    if (event.values[0] > max_x) {
                        max_x = event.values[0];
                    }
                    if (event.values[1] > max_y) {
                        max_y = event.values[1];
                    }
                    if (event.values[2] > max_z) {
                        max_z = event.values[2];
                    }

                    txt += "acelerometro\n";
                    txt += "\n x: " + dosdecimales.format(event.values[0])
                            + " m/s - Max: " + dosdecimales.format(max_x);
                    txt += "\n y: " + dosdecimales.format(event.values[1])
                            + " m/s - Max: " + dosdecimales.format(max_y);
                    txt += "\n z: " + dosdecimales.format(event.values[2])
                            + " m/s - Max: " + dosdecimales.format(max_z);
                    txtAcelerometro.setText(txt);

                    if ((event.values[0] > 15) || (event.values[1] > 15)
                            || (event.values[2] > 15)) {

                        txtDetecta.setBackgroundColor(Color.parseColor("#cf091c"));
                        txtDetecta.setText("Vibracion Detectada");

                    }

                    break;

                case Sensor.TYPE_ROTATION_VECTOR:
                    txt += "rotation vector\n";
                    txt += "\n x: " + event.values[0];
                    txt += "\n y: " + event.values[1];
                    txt += "\n z: " + event.values[2];

                    // Creo objeto para saber como esta la pantalla
                    Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE))
                            .getDefaultDisplay();
                    int rotation = display.getRotation();

                    // El objeto devuelve 3 estados 0,1 y 3
                    if (rotation == 0) {
                        txt += "\n\n Pos: Vertical";

                    } else if (rotation == 1) {
                        txt += "\n\n Pos: Horizontal Izq.";

                    } else if (rotation == 3) {
                        txt += "\n\n Pos: Horizontal Der";
                    }

                    txt += "\n\n display: " + rotation;

                    txtGiro.setText(txt);

                    break;

                case Sensor.TYPE_GRAVITY:
                    txt += "Gravedad\n";
                    txt += "\n x: " + event.values[0];
                    txt += "\n y: " + event.values[1];
                    txt += "\n z: " + event.values[2];

                    txtGravedad.setText(txt);

                    break;

                case Sensor.TYPE_MAGNETIC_FIELD:
                    txt += "magnetic field\n";
                    txt += "\n" + event.values[0] + " uT";

                    txtMagnetic.setText(txt);

                    break;

                case Sensor.TYPE_PROXIMITY:
                    txt += "proximity\n";
                    txt += "\n" + event.values[0];

                    txtProximity.setText(txt);

                    // Si detecta 0 lo represento
                    if (event.values[0] == 0) {

                        txtDetecta.setBackgroundColor(Color.parseColor("#cf091c"));
                        txtDetecta.setText("Proximidad Detectada");
                    }

                    break;

                case Sensor.TYPE_LIGHT:
                    txt += "Luminosidad\n";
                    txt += "\n" + event.values[0] + " Lux";

                    txtLuminosidad.setText(txt);

                    break;

                case Sensor.TYPE_PRESSURE:
                    txt += "Presion\n";
                    txt += "\n" + event.values[0] + " mBar \n\n";

                    txtPresion.setText(txt);

                    break;

                case Sensor.TYPE_TEMPERATURE:
                    txt += "Temperatura\n";
                    txt += "\n" + event.values[0] + " ºC";

                    txtTemperatura.setText(txt);

                    break;

            }

        }

    }

    private String getDireccion(float values) {
        String txtDirection = "";
        if (values < 22)
            txtDirection = "N";
        else if (values >= 22 && values < 67)
            txtDirection = "NE";
        else if (values >= 67 && values < 112)
            txtDirection = "E";
        else if (values >= 112 && values < 157)
            txtDirection = "SE";
        else if (values >= 157 && values < 202)
            txtDirection = "S";
        else if (values >= 202 && values < 247)
            txtDirection = "SO";
        else if (values >= 247 && values < 292)
            txtDirection = "O";
        else if (values >= 292 && values < 337)
            txtDirection = "NO";
        else if (values >= 337)
            txtDirection = "N";

        return txtDirection;
    }

    @Override
    protected void onStop() {

        Parar_Sensores();

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub

        Parar_Sensores();

        super.onDestroy();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub

        Parar_Sensores();

        super.onPause();
    }

    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub

        Ini_Sensores();

        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Ini_Sensores();

    }
}