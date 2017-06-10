package com.bemen3.albert.alcarol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bemen3.albert.alcarol.entidades.Estilo;
import com.bemen3.albert.alcarol.entidades.Personaje;
import com.bemen3.albert.alcarol.entidades.Usuario;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class EditarPersonajeActivity extends AppCompatActivity {

    private ListView listView;
    Personaje personaje;
    private Estilo estiloActual;
    private Usuario usuarioApp;
    private ArrayList<HashMap<String, String>> listaAtributos;
    private int height;
    private int width;
    private AdaptadorAtributosPersonaje adapter;
    private EditText etNombrePersonaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_personaje);

        estiloActual = GlobalParam.estiloActual;
        usuarioApp = GlobalParam.usuarioApp;
        etNombrePersonaje = (EditText) findViewById(R.id.etNombrePersonaje);


        listView = (ListView) findViewById(R.id.listviewAtributosPersonaje);
        adaptarTamanyoListView();
        cargarListView();
    }

    public void cargarListView() {
        personaje = GlobalParam.personajeActual;
        etNombrePersonaje.setText(personaje.getNombre());
        //personaje.setUserId(usuarioApp.getId());
        //personaje.setEstilo(estiloActual);
        listaAtributos = new ArrayList<>();

        inicializarAtributosPersonaje();

        adapter = new AdaptadorAtributosPersonaje(this, listaAtributos);
        listView.setAdapter(adapter);

    }

    public void adaptarTamanyoListView() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = height / 2;
        listView.setLayoutParams(params);
        listView.requestLayout();
    }


    public void inicializarAtributosPersonaje() {

        Personaje personajeAux = new Personaje();

        if (estiloActual.getCarisma().equalsIgnoreCase("S")) {
            personajeAux.getAtributos().put("carisma", personaje.getAtributos().get("carisma"));
        }
        if (estiloActual.getConstitucion().equalsIgnoreCase("S")) {
            personajeAux.getAtributos().put("constitucion", personaje.getAtributos().get("constitucion"));
        }
        if (estiloActual.getDestreza().equalsIgnoreCase("S")) {
            personajeAux.getAtributos().put("destreza", personaje.getAtributos().get("destreza"));
        }
        if (estiloActual.getFuerza().equalsIgnoreCase("S")) {
            personajeAux.getAtributos().put("fuerza", personaje.getAtributos().get("fuerza"));
        }
        if (estiloActual.getInteligencia().equalsIgnoreCase("S")) {
            personajeAux.getAtributos().put("inteligencia", personaje.getAtributos().get("inteligencia"));
        }
        if (estiloActual.getMana().equalsIgnoreCase("S")) {
            personajeAux.getAtributos().put("mana", personaje.getAtributos().get("mana"));
        }
        if (estiloActual.getPercepcion().equalsIgnoreCase("S")) {
            personajeAux.getAtributos().put("percepcion", personaje.getAtributos().get("percepcion"));
        }
        if (estiloActual.getSabiduria().equalsIgnoreCase("S")) {
            personajeAux.getAtributos().put("sabiduria", personaje.getAtributos().get("sabiduria"));
        }
        if (estiloActual.getVida().equalsIgnoreCase("S")) {
            personajeAux.getAtributos().put("vida", personaje.getAtributos().get("vida"));
        }

        Iterator it = personajeAux.getAtributos().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry mapEntry = (Map.Entry) it.next();
            String keyValue = (String) mapEntry.getKey();
            String value = (String) mapEntry.getValue();

            System.out.println("Key: " + keyValue + ",value: " + value);

            HashMap<String, String> newMap = new HashMap<>();
            newMap.put(keyValue, value);
            listaAtributos.add(newMap);

        }

    }

    public void guardarDatosPersonaje(View view) {

        String nombrePJ = etNombrePersonaje.getText().toString();
        if (!nombrePJ.equalsIgnoreCase("")) {
            for (HashMap<String, String> auxMap :
                    listaAtributos) {
                Iterator it = auxMap.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry mapEntry = (Map.Entry) it.next();
                    String keyValue = (String) mapEntry.getKey();
                    String value = (String) mapEntry.getValue();

                    System.out.println("Key: " + keyValue + ",value: " + value);
                    personaje.getAtributos().put(keyValue, value);
                }
            }

            personaje.setNombre(nombrePJ);

            HashMap<String, String> map = new HashMap<String, String>();

            map.put("id_usuario", personaje.getUserId());
            map.put("id_estilo", personaje.getEstilo().getId());
            map.put("nombre", personaje.getNombre());
            map.put("id_personaje", personaje.getId());
            map.putAll(personaje.getAtributos());

            consultasJSONGet(Constantes.UPDATE_PERSONAJE, map, "0");

        } else {
            System.out.println("No se puede guardar la modificacion");
            Toast.makeText(getApplicationContext(), "Falta un nombre para el personaje", Toast.LENGTH_LONG).show();
        }
    }

    public void consultasJSONGet(String enlace, Map map, final String tipoConsulta) {
        String newURL = enlace + "?";

        Iterator it = map.entrySet().iterator();
        boolean isFirst = true;
        while (it.hasNext()) {
            Map.Entry mapEntry = (Map.Entry) it.next();
            String keyValue = (String) mapEntry.getKey();
            String value = (String) mapEntry.getValue();

            if (isFirst) {
                isFirst = false;
                newURL += keyValue + "=" + value;
            } else {
                newURL += "&" + keyValue + "=" + value;
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
                                    if (response.has("estado")) {
                                        String estado = response.getString("estado");
                                        String mensaje = response.getString("mensaje");

                                        Toast.makeText(getApplicationContext(), mensaje,
                                                Toast.LENGTH_LONG).show();

                                        if(estado.equalsIgnoreCase("1"))EditarPersonajeActivity.super.onBackPressed();
                                    } else {
                                        System.out.println("Procesando Respuesta");
                                        switch (tipoConsulta) {
                                            case "1":
                                                //procesarRespuestaListaPersonajes(response);
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
}
