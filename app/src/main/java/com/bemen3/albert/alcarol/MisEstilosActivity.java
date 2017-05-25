package com.bemen3.albert.alcarol;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bemen3.albert.alcarol.entidades.Estilo;
import com.bemen3.albert.alcarol.entidades.Usuario;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Clase que gestiona la pantalla de Listado de Estilos
 * @author Albert Cadena
 * @version 26/05/2017 1.0
 */
public class MisEstilosActivity extends AppCompatActivity {

    private TextView tvTesting;
    private Usuario usuarioApp;
    private ArrayList<Estilo> listaEstilos;
    private ListView listView;
    private AdaptadorEstilos adapter;
    private int height;
    private int width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_estilos);



        usuarioApp = GlobalParam.usuarioApp;

        tvTesting = (TextView)findViewById(R.id.titulo_activity);
        tvTesting.setText(tvTesting.getText()+"\n usuario:"+usuarioApp.getNombre()+",id: "+GlobalParam.ID_USUARIO);

        listaEstilos = new ArrayList<>();
        listView = (ListView)findViewById(R.id.listviewEstilos);
        adaptarTamanyoListView();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Object o = listView.getItemAtPosition(position);
                Estilo estilo=(Estilo) o;//As you are using Default String Adapter
                GlobalParam.estiloActual = estilo;
                Intent intent = new Intent(getApplicationContext(), ListadoHojasPersonajeActivity.class);
                startActivity(intent);
            }
        });

        cargarListaEstilos();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        listaEstilos = new ArrayList<>();
        cargarListaEstilos();
    }

    public void adaptarTamanyoListView(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = height/2;
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public void cargarListaEstilos(){
        //case 1: queremos solo cargar id y nombre de cada estilo.

        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        map.put("id_usuario", GlobalParam.ID_USUARIO);
        map.put("tipoConsulta", "1");

        consultasJSONGet(Constantes.METODOS_ESTILOS, map, "1");
    }


    public void consultasJSONGet(String enlace, Map map, final String tipoConsulta){
        String newURL = enlace + "?";

        Iterator it = map.entrySet().iterator();
        boolean isFirst = true;
        while (it.hasNext())
        {
            Map.Entry mapEntry = (Map.Entry) it.next();
            String keyValue = (String) mapEntry.getKey();
            String value = (String) mapEntry.getValue();

            if(isFirst){
                isFirst = false;
                newURL += keyValue+"="+value;
            }else{
                newURL += "&"+keyValue+"="+value;
            }

        }

        //newURL +="username="+map.get("username")+"&password="+map.get("password");


        GestionPeticionesHTTP colaPeticiones = null;

        colaPeticiones.getInstance(this).addToRequestQueue(

                new JsonObjectRequest(Request.Method.GET, newURL, null,

                        new Response.Listener<JSONObject>() {

                            @Override

                            public void onResponse(JSONObject response) {
                                //procesarRespuesta(response); // Procesar respuesta Json
                                try { // Procesar respuesta Json
                                    if(response.has("estado")) {
                                        String estado = response.getString("estado");
                                        String mensaje = response.getString("mensaje");

                                        Toast.makeText(getApplicationContext(), mensaje,
                                                Toast.LENGTH_LONG).show();
                                    }else{
                                        System.out.println("Procesando Respuesta");
                                        switch (tipoConsulta){
                                            case "1":

                                                procesarRespuestaEstilosSoloIDNombre(response);

                                                break;
                                            case "2":
                                                break;
                                        }
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },

                        new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Log.d("--Consultar DB--", "Error Volley: " + error.getMessage());

                            }

                        }

                )

        );
    }

    private void procesarRespuestaEstilosSoloIDNombre(JSONObject response) {
        try {
            String stringArray = "resultadoEstilo";

            boolean final_lectura = false;
            int count = 0;

            do{
                String aux = stringArray+count;
                if(response.has(aux)){
                    //JSONArray arrayActual = response.getJSONArray(aux);
                    JSONObject c = response.getJSONObject(aux);
                    System.out.println("Auxiliar: "+aux);
                    Estilo estilo = new Estilo();
                    estilo.setNombre(c.getString("nombre"));
                    estilo.setId(c.getString("id"));
                    estilo.setUserId(c.getString("id_usuario"));

                    estilo.setCarisma(c.has("carisma")?c.getString("carisma"):"N");
                    estilo.setConstitucion(c.has("constitucion")?c.getString("constitucion"):"N");
                    estilo.setDestreza(c.has("destreza")?c.getString("destreza"):"N");
                    estilo.setFuerza(c.has("fuerza")?c.getString("fuerza"):"N");
                    estilo.setInteligencia(c.has("inteligencia")?c.getString("inteligencia"):"N");
                    estilo.setMana(c.has("mana")?c.getString("mana"):"N");
                    estilo.setVida(c.has("vida")?c.getString("vida"):"N");
                    estilo.setPercepcion(c.has("percepcion")?c.getString("percepcion"):"N");
                    estilo.setSabiduria(c.has("sabiduria")?c.getString("sabiduria"):"N");

                    listaEstilos.add(estilo);
                    count++;
                }else{
                    System.out.println("No existe "+aux);
                    if(count==0)
                        Toast.makeText(getApplicationContext(), "No hay Estilos Creados", Toast.LENGTH_SHORT).show();
                    count = 0;
                    final_lectura = true;
                }
            }while(!final_lectura);

            adapter = new AdaptadorEstilos(this, listaEstilos);
            listView.setAdapter(adapter);
        } catch (JSONException e) {

            System.out.println("Aqui hay un error, "+e);

        }
    }

    public void crearNuevoEstilo(View view){
        Intent intent = new Intent(getApplicationContext(), CrearNuevoEstiloActivity.class);
        startActivity(intent);
    }

}
