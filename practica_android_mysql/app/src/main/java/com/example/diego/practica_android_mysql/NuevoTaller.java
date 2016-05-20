package com.example.diego.practica_android_mysql;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class NuevoTaller extends AppCompatActivity implements View.OnClickListener{

    private Button btnGuardar;
    private DatePicker dpFi, dpFf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_taller);

        dpFi=(DatePicker)findViewById(R.id.dtFI);
        dpFf=(DatePicker)findViewById(R.id.dtFF);
        btnGuardar=(Button)findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

    }

    private class hiloDatos extends AsyncTask<String, Void, Void> {
        private InputStream datosEntrada;
        private String text = "";
        private String error = "";

        private String Descripcion;
        private String Horas;
        private String Lugar;
        private String FI;
        private String FF;

        private ProgressDialog dialog = new ProgressDialog(NuevoTaller.this);

        hiloDatos(String descripcion, String horas, String lugar,String fi,String ff){
            this.Descripcion=descripcion;
            this.Horas=horas;
            this.Lugar=lugar;
            this.FI=fi;
            this.FF=ff;
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Enviando datos...");
            dialog.show();
        }

        //Boolean result = false;



        @Override
        protected Void doInBackground(String... urls) {

            for(String url1 : urls){
                try {
                    ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
                    pairs.add(new BasicNameValuePair("Descripcion", Descripcion));
                    pairs.add(new BasicNameValuePair("Horas", Horas));
                    pairs.add(new BasicNameValuePair("Lugar", Lugar));
                    pairs.add(new BasicNameValuePair("FechaI", FI));
                    pairs.add(new BasicNameValuePair("FechaF", FF));
                    pairs.add(new BasicNameValuePair("opcion", "0"));
                    HttpClient client = new DefaultHttpClient();
                    HttpPost post = new HttpPost(url1);
                    post.setEntity(new UrlEncodedFormEntity(pairs));
                    HttpResponse response = client.execute(post);
                    datosEntrada = response.getEntity().getContent();

                    //result = true;

                } catch (ClientProtocolException e) {
                    error += "\nClientProtocolException: " + e.getMessage();
                } catch (IOException e) {
                    error += "\nClientProtocolException: " + e.getMessage();
                }

                BufferedReader reader;

                try {
                    reader = new BufferedReader(new InputStreamReader(datosEntrada ,"iso-8859-1"),8);
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
            if(dialog.isShowing()){
                dialog.dismiss();
            }
            text = text.trim();
            Toast.makeText(NuevoTaller.this, text, Toast.LENGTH_LONG).show();
        }

    }

}
