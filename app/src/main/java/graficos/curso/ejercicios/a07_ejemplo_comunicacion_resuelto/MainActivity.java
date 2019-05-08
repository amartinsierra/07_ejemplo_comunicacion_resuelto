package graficos.curso.ejercicios.a07_ejemplo_comunicacion_resuelto;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends Activity {
    TextView tvContenido;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void cargar(View v){
        tvContenido=this.findViewById(R.id.tvContenido);
        //creamos y lanzamos la tarea asíncrona
        Comunicacion com=new Comunicacion();
        com.execute("http://www.google.es/search?q=vacaciones");

    }



    private class Comunicacion extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... args) {
            String cad = "", aux;
            //conectamos con el servicio google
            BufferedReader bf=null;
            try {
                URL url = new URL(args[0]);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.setRequestMethod("GET");
                //headers
               // con.setRequestProperty("clave","valor");
                //si post:
                /*con.setRequestMethod("post");
                con.setDoOutput(true);
                PrintStream out=new PrintStream(con.getOutputStream());
                out.println("parametro=valor");
                out.flush();*/
                //obtener Stream de entrada para leer los datos
                //enviados desde la URL
                InputStream is = con.getInputStream();

                bf = new BufferedReader(new InputStreamReader(is));
                //vamos leyendo las líneas enviadas por el servicio y las unimos
                //en la variable cad
                while ((aux = bf.readLine()) != null) {
                    cad += aux + "\n";
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
            finally{
                if(bf!=null){
                    try {
                        bf.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return cad;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //volcamos el resultado en el campo de texto
            tvContenido.setText(s);
        }
    }

}
