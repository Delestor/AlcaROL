package com.bemen3.albert.alcarol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bemen3.albert.alcarol.entidades.Atributo;
import com.bemen3.albert.alcarol.entidades.Estilo;
import com.bemen3.albert.alcarol.entidades.Personaje;
import com.bemen3.albert.alcarol.entidades.Usuario;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CrearNuevoPersonajeActivity extends AppCompatActivity {

    private ListView listView;
    Personaje personaje;
    private Estilo estiloActual;
    private Usuario usuarioApp;
    private ArrayList<HashMap<String, String>> listaAtributos;
    private int height;
    private int width;
    private AdaptadorAtributosPersonaje adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_nuevo_personaje);

        estiloActual = GlobalParam.estiloActual;
        usuarioApp = GlobalParam.usuarioApp;


        listView = (ListView)findViewById(R.id.listviewAtributosCreacionPersonaje);
        adaptarTamanyoListView();
        cargarListView();
    }

    public void cargarListView(){
        personaje = new Personaje();
        personaje.setUserId(usuarioApp.getId());
        personaje.setEstilo(estiloActual);
        listaAtributos = new ArrayList<>();

        inicializarAtributosPersonaje();

        adapter = new AdaptadorAtributosPersonaje(this, listaAtributos);
        listView.setAdapter(adapter);

        listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                //listView.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
                //Object o = listView.getItemAtPosition(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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

    public void inicializarAtributosPersonaje(){


        if(estiloActual.getCarisma().equalsIgnoreCase("S")){
            personaje.getAtributos().put("carisma","");
        }
        if(estiloActual.getConstitucion().equalsIgnoreCase("S")){
            personaje.getAtributos().put("constitucion","");
        }
        if(estiloActual.getDestreza().equalsIgnoreCase("S")){
            personaje.getAtributos().put("destreza","");
        }
        if(estiloActual.getFuerza().equalsIgnoreCase("S")){
            personaje.getAtributos().put("fuerza","");
        }
        if(estiloActual.getInteligencia().equalsIgnoreCase("S")){
            personaje.getAtributos().put("inteligencia","");
        }
        if(estiloActual.getMana().equalsIgnoreCase("S")){
            personaje.getAtributos().put("mana","");
        }
        if(estiloActual.getPercepcion().equalsIgnoreCase("S")){
            personaje.getAtributos().put("percepcion","");
        }
        if(estiloActual.getSabiduria().equalsIgnoreCase("S")){
            personaje.getAtributos().put("sabiduria","");
        }
        if(estiloActual.getVida().equalsIgnoreCase("S")){
            personaje.getAtributos().put("vida","");
        }

        Iterator it = personaje.getAtributos().entrySet().iterator();
        while (it.hasNext())
        {
            Map.Entry mapEntry = (Map.Entry) it.next();
            String keyValue = (String) mapEntry.getKey();
            String value = (String) mapEntry.getValue();

            System.out.println("Key: "+keyValue+",value: "+value);

            HashMap<String, String> newMap = new HashMap<>();
            newMap.put(keyValue,value);
            listaAtributos.add(newMap);

        }

    }

    public void guardarDatosNewPersonaje(View view){
        for (HashMap<String, String> auxMap :
                listaAtributos) {
            Iterator it = auxMap.entrySet().iterator();
            while (it.hasNext())
            {
                Map.Entry mapEntry = (Map.Entry) it.next();
                String keyValue = (String) mapEntry.getKey();
                String value = (String) mapEntry.getValue();

                System.out.println("Key: "+keyValue+",value: "+value);
                personaje.getAtributos().put(keyValue,value);
            }
        }
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
                                            case "2":

                                                //procesarRespuestaEstilos(response);

                                                break;
                                            case "3":
                                                Toast.makeText(getApplicationContext(), "Estilo guardado satisfactóriamente.", Toast.LENGTH_SHORT).show();
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



}
