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
import com.bemen3.albert.alcarol.entidades.Personaje;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Clase que gestiona la vista de Listado de Hojas de Personajes
 * @author Albert Cadena
 * @version 26/05/2017 1.0
 */
public class ListadoHojasPersonajeActivity extends AppCompatActivity {

    private TextView etTituloActivity;
    private Estilo estiloActual;
    private int height;
    private int width;
    private ListView listView;
    private ArrayList<Personaje> listaPersonajes;
    private AdaptadorPersonajes adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_hojas_personaje);

        estiloActual = GlobalParam.estiloActual;
        listaPersonajes = new ArrayList<>();

        etTituloActivity = (TextView)findViewById(R.id.tvTituloListadoHojasPersonaje);
        etTituloActivity.setText(etTituloActivity.getText()+" "+estiloActual.getNombre());

        listView = (ListView)findViewById(R.id.listViewListadoHojasPersonaje);
        adaptarTamanyoListView();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Object o = listView.getItemAtPosition(position);
                Personaje personaje=(Personaje) o;//As you are using Default String Adapter
                GlobalParam.personajeActual = personaje;
                Intent intent = new Intent(getApplicationContext(), EditarPersonajeActivity.class);
                startActivity(intent);
            }
        });

        cargarListaPersonajes();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        listaPersonajes = new ArrayList<>();
        cargarListaPersonajes();
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

    public void crearNuevoPersonaje(View view){
        GlobalParam.estiloActual = estiloActual;
        Intent intent = new Intent(getApplicationContext(), CrearNuevoPersonajeActivity.class);
        startActivity(intent);
    }

    public void cargarListaPersonajes(){

        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        map.put("id_usuario", GlobalParam.ID_USUARIO);
        map.put("id_estilo", GlobalParam.estiloActual.getId());
        map.put("tipoConsulta", "1");

        consultasJSONGet(Constantes.LISTAR_PERSONAJES, map, "1");
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
                                                procesarRespuestaListaPersonajes(response);
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

    private void procesarRespuestaListaPersonajes(JSONObject response) {
        try {
            String stringArray = "resultadoPersonaje";

            boolean final_lectura = false;
            int count = 0;

            do{
                String aux = stringArray+count;
                if(response.has(aux)){
                    //JSONArray arrayActual = response.getJSONArray(aux);
                    JSONObject c = response.getJSONObject(aux);
                    System.out.println("Auxiliar: "+aux);
                    Personaje personaje = new Personaje();
                    personaje.setNombre(c.getString("nombre"));
                    personaje.setId(c.getString("id"));
                    personaje.setUserId(c.getString("id_usuario"));
                    personaje.setEstilo(GlobalParam.estiloActual);

                    personaje.getAtributos().put("carisma", c.has("carisma")?c.getString("carisma"):"");
                    personaje.getAtributos().put("constitucion",c.has("constitucion")?c.getString("constitucion"):"");
                    personaje.getAtributos().put("destreza",c.has("destreza")?c.getString("destreza"):"");
                    personaje.getAtributos().put("fuerza",c.has("fuerza")?c.getString("fuerza"):"");
                    personaje.getAtributos().put("inteligencia",c.has("inteligencia")?c.getString("inteligencia"):"");
                    personaje.getAtributos().put("mana",c.has("mana")?c.getString("mana"):"");
                    personaje.getAtributos().put("vida",c.has("vida")?c.getString("vida"):"");
                    personaje.getAtributos().put("percepcion",c.has("percepcion")?c.getString("percepcion"):"");
                    personaje.getAtributos().put("sabiduria",c.has("sabiduria")?c.getString("sabiduria"):"");

                    listaPersonajes.add(personaje);
                    count++;
                }else{
                    System.out.println("No existe "+aux);
                    if(count == 0)
                        Toast.makeText(getApplicationContext(), "No hay personajes Creados", Toast.LENGTH_SHORT).show();
                    count = 0;
                    final_lectura = true;
                }
            }while(!final_lectura);

            adapter = new AdaptadorPersonajes(this, listaPersonajes);
            listView.setAdapter(adapter);
        } catch (JSONException e) {

            System.out.println("Aqui hay un error, "+e);

        }
    }

}
