package com.example.diego.practica_android_mysql;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class ModificarTalleres extends AppCompatActivity implements View.OnClickListener {
    private EditText etDescripcion, etHoras, etLugar;
    private Button btnActualizar, btnEliminar;
    private DatePicker dtFI, dtFF;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_talleres);

        etDescripcion = (EditText) findViewById(R.id.etDescripcion);
        etHoras = (EditText) findViewById(R.id.etHoras);
        etLugar = (EditText) findViewById(R.id.etLugar);
        dtFI = (DatePicker) findViewById(R.id.dtFI);
        dtFF = (DatePicker) findViewById(R.id.dtFF);
        btnActualizar = (Button) findViewById(R.id.btnActualizar);
        btnEliminar = (Button) findViewById(R.id.btnEliminar);


        btnActualizar.setOnClickListener(this);
        btnEliminar.setOnClickListener(this);

        String[] dato = getIntent().getExtras().getString("Registro").split("\n");

        //System.out.println(dato.length);
        etDescripcion.setText(dato[0]);
        etHoras.setText(dato[2].split(":")[1]);
        etLugar.setText(dato[1].split(":")[1]);
        dtFI.updateDate(Integer.parseInt(dato[3].split(":")[1].split("/")[2]), (Integer.parseInt(dato[3].split(":")[1].split("/")[1])) - 1, Integer.parseInt(dato[3].split(":")[1].split("/")[0]));
        dtFF.updateDate(Integer.parseInt(dato[4].split(":")[1].split("/")[2]), (Integer.parseInt(dato[4].split(":")[1].split("/")[1])) - 1, Integer.parseInt(dato[4].split(":")[1].split("/")[0]));
        id = dato[5].split(":")[1].trim();







    }

    @Override
    public void onClick(View v) {

        String descripcion = etDescripcion.getText().toString();
        String horas = String.valueOf(etHoras.getText().toString());
        String lugar = etLugar.getText().toString();
        String fechai = dtFI.getDayOfMonth()+"/"+(dtFI.getMonth()+1)+"/"+dtFI.getYear();
        String fechaf = dtFF.getDayOfMonth()+"/"+(dtFF.getMonth()+1)+"/"+dtFF.getYear();
        String idC=id;

        //System.out.println(id);
        if (v.getId() == R.id.btnActualizar) {

            String opcion="2";
            hiloDatos objHilo = new hiloDatos(descripcion,horas,lugar,fechai,fechaf,idC,opcion);
            objHilo.execute("http://192.168.43.236:8080/conoperaciones.php");

            Intent i = new Intent(this, ConsultaTalleres.class);
            startActivity(i);


        }else{
            String opcion="3";

            hiloDatos objHilo = new hiloDatos(descripcion,horas,lugar,fechai,fechaf,idC,opcion);
            objHilo.execute("http://192.168.43.236:8080/conoperaciones.php");

            Intent i = new Intent(this, ConsultaTalleres.class);
            startActivity(i);

        }
    }

    public class hiloDatos extends AsyncTask<String, Void, Void> {
        private InputStream datosEntrada;
        private String text = "";
        private String error = "";


        private String Descripcion;
        private String Horas;
        private String Lugar;
        private String FI;
        private String FF;
        private String ID;
        private String OPCION;
        private ProgressDialog dialog = new ProgressDialog(ModificarTalleres.this);

        hiloDatos(String descripcion, String horas, String lugar, String fi, String ff,String id,String opcion) {
            this.Descripcion = descripcion;
            this.Horas = horas;
            this.Lugar = lugar;
            this.FI = fi;
            this.FF = ff;
            this.ID = id;
            this.OPCION=opcion;
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Enviando datos...");
            dialog.show();
        }

        //Boolean result = false;


        @Override
        protected Void doInBackground(String... urls) {

            for (String url1 : urls) {
                try {
                    ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
                    pairs.add(new BasicNameValuePair("Descripcion", Descripcion));
                    pairs.add(new BasicNameValuePair("Horas", Horas));
                    pairs.add(new BasicNameValuePair("Lugar", Lugar));
                    pairs.add(new BasicNameValuePair("FechaI", FI));
                    pairs.add(new BasicNameValuePair("FechaF", FF));
                    pairs.add(new BasicNameValuePair("opcion", OPCION));
                    pairs.add(new BasicNameValuePair("id", ID));
                    HttpClient client = new DefaultHttpClient();
                    HttpPost post = new HttpPost(url1);
                    post.setEntity(new UrlEncodedFormEntity(pairs));
                    HttpResponse response = client.execute(post);
                    //p = response.getEntity().getContentLength();
                    System.out.println(ID);

                    datosEntrada = response.getEntity().getContent();

                    boolean result = true;

                } catch (ClientProtocolException e) {
                    error += "\nClientProtocolException: " + e.getMessage();
                } catch (IOException e) {
                    error += "\nClientProtocolException: " + e.getMessage();
                }

                BufferedReader reader;

                try {
                    reader = new BufferedReader(new InputStreamReader(datosEntrada, "iso-8859-1"), 8);
                    String line = null;

                    while ((line = reader.readLine()) != null) {
                        text += line + "\n";
                    }
                } catch (UnsupportedEncodingException e) {
                    error += "\nClientProtocolException: " + e.getMessage();
                } catch (IOException e) {
                    error += "\nClientProtocolException: " + e.getMessage();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void arg0) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            text = text.trim();
            Toast.makeText(ModificarTalleres.this, text, Toast.LENGTH_LONG).show();
        }
    }
}
