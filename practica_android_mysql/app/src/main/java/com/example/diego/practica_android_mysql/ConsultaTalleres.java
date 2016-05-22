package com.example.diego.practica_android_mysql;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


public class ConsultaTalleres extends AppCompatActivity  {
    private ListView lvDatos;
    private ArrayList<String> alDatos;
    private ArrayAdapter<String> aaDatos;
    private Button btn;
    public String textJ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_talleres);
        lvDatos = (ListView) findViewById(R.id.lvTalleres);

        String descripcion = "";
        String horas = "";
        String lugar = "";
        String fechai = "";
        String fechaf = "";



        hiloDatos objHilo = new hiloDatos(descripcion,horas,lugar,fechai,fechaf);
        objHilo.execute("http://192.168.1.191:8080/conoperaciones.php");

        lvDatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int arg2, long arg3) {
                //Toast.makeText(getBaseContext(),"Adios",Toast.LENGTH_SHORT).show();
                Intent objEditar = new Intent(getBaseContext(), ModificarTalleres.class);
                objEditar.putExtra("Registro", ((TextView) arg1).getText());
                startActivity(objEditar);

                finish();


            }
        });


    }




    public class hiloDatos extends AsyncTask<String, Void, Void> {
        private InputStream datosEntrada;
        public String text = "";
        private String error = "";
        private static final String TAG_DESCRIPCION = "descripcion";
        private static final String TAG_ID = "id";
        private static final String TAG_HORAS = "horas";
        private static final String TAG_lUGAR = "lugar";
        private static final String TAG_FECHA_I = "fechai";
        private static final String TAG_FECHA_F = "fechaf";
        JSONArray descripcion = null;
        private String Descripcion;
        private String Horas;
        private String Lugar;
        private String FI;
        private String FF;
        private long p;
        private ProgressDialog dialog = new ProgressDialog(ConsultaTalleres.this);

        hiloDatos(String descripcion, String horas, String lugar,String fi,String ff){
            this.Descripcion=descripcion;
            this.Horas=horas;
            this.Lugar=lugar;
            this.FI=fi;
            this.FF=ff;
            this.p=0;
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Enviando datos...");
//            dialog.show();
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
                    pairs.add(new BasicNameValuePair("opcion", "1"));
                    HttpClient client = new DefaultHttpClient();
                    HttpPost post = new HttpPost(url1);
                    post.setEntity(new UrlEncodedFormEntity(pairs));
                    HttpResponse response = client.execute(post);
                    p = response.getEntity().getContentLength();
                    System.out.println(p);

                    datosEntrada = response.getEntity().getContent();

                    boolean result = true;

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
            textJ=text;
            JSONArray jsonArray;
            //JSONObject jObject;
            alDatos = new ArrayList<String>();

            try {
                jsonArray = new JSONArray(textJ);
                //descripcion = jObject.getJSONArray(TAG_DESCRIPCION);
                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject c = jsonArray.getJSONObject(i);
                    //Toast.makeText(ConsultaTalleres.this, Integer.toString(i), Toast.LENGTH_SHORT).show();

                    // Storing each json item in variable
                    String id = c.getString(TAG_ID);
                    String lugar = c.getString(TAG_lUGAR);
                    String horas = c.getString(TAG_HORAS);
                    String descripcion = c.getString(TAG_DESCRIPCION);
                    String fechai = c.getString(TAG_FECHA_I);
                    String fechaf = c.getString(TAG_FECHA_F);

                    String d=descripcion+"\nEn: "+lugar+"\nNumero de horas: "+horas+"\nFecha de Inicio: "+fechai+"\nFecha de culminacion:"+fechaf;
                    alDatos.add(d);

                    //Intent objEditar = new Intent(getBaseContext(), .class);
                    //objEditar.putExtra("Registro", ((TextView) arg1).getText());

                }
                aaDatos = new ArrayAdapter<String>
                        (getBaseContext(), android.R.layout.simple_list_item_1, alDatos);
                lvDatos.setAdapter(aaDatos);




            } catch (JSONException e) {
                e.printStackTrace();
            }


            //Toast.makeText(ConsultaTalleres.this, text, Toast.LENGTH_LONG).show();
        }



    }



    }

