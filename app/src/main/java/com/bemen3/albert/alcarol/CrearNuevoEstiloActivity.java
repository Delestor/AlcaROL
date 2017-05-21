package com.bemen3.albert.alcarol;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bemen3.albert.alcarol.entidades.Atributo;
import com.bemen3.albert.alcarol.entidades.Estilo;
import com.bemen3.albert.alcarol.entidades.Usuario;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CrearNuevoEstiloActivity extends AppCompatActivity {

    private ArrayList<Atributo> listaAtributos;
    private AdaptadorAtributos adapter;
    private ListView listView;
    private Usuario usuarioApp;
    private EditText etNombreEstilo;
    private int height;
    private int width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_nuevo_estilo);

        usuarioApp = GlobalParam.usuarioApp;

        listaAtributos = new ArrayList<>();
        etNombreEstilo = (EditText)findViewById(R.id.etNombreNuevoEstilo);
        listView = (ListView)findViewById(R.id.listviewAtributosEstilos);
        adaptarTamanyoListView();

        listarTipoAtributosEstilo();
    }

    public void listarTipoAtributosEstilo(){
        //Opcion 2:
        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        map.put("id_usuario", GlobalParam.ID_USUARIO);
        map.put("tipoConsulta", "2");

        consultasJSONGet(Constantes.METODOS_ESTILOS, map, "2");
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

                                                procesarRespuestaEstilos(response);

                                                break;
                                            case "3":
                                                Toast.makeText(getApplicationContext(), "Estilo guardado satisfactóriamente.", Toast.LENGTH_SHORT).show();
                                                CrearNuevoEstiloActivity.super.onBackPressed();
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

    private void procesarRespuestaEstilos(JSONObject response) {
        try {
            String stringArray = "resultadoAtributo";

            boolean final_lectura = false;
            int count = 0;

            do{
                String aux = stringArray+count;
                if(response.has(aux)){
                    //JSONArray arrayActual = response.getJSONArray(aux);
                    JSONObject c = response.getJSONObject(aux);
                    System.out.println("Auxiliar: "+aux+", nombre: "+c.getString("nombre"));
                    Atributo atributo = new Atributo();
                    atributo.setNombre(c.getString("nombre"));
                    listaAtributos.add(atributo);
                    count++;
                }else{
                    System.out.println("No existe "+aux);
                    count = 0;
                    final_lectura = true;
                }
            }while(!final_lectura);

            adapter = new AdaptadorAtributos(this, listaAtributos);
            listView.setAdapter(adapter);
        } catch (JSONException e) {

            System.out.println("Aqui hay un error, "+e);

        }
    }


    public void guardarAtributosNuevoEstilo(View view){
        String nombreEstilo = etNombreEstilo.getText().toString();
        if(!nombreEstilo.equalsIgnoreCase("")){
            ejecutarConsultaParaGuardarEstilo();
        }else{
            Toast.makeText(getApplicationContext(), "Falta poner un nombre al nuevo Estilo", Toast.LENGTH_LONG);
        }

    }

    public void ejecutarConsultaParaGuardarEstilo(){
        String nombreEstilo = etNombreEstilo.getText().toString();
        List<Atributo> listaMarcados = adapter.getListaAtributosMarcados();

        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        map.put("id_usuario", GlobalParam.ID_USUARIO);
        map.put("tipoConsulta", "3");
        map.put("nombre", nombreEstilo);

        for (Atributo aux :
                listaMarcados) {
            map.put(aux.getNombre(), aux.isSeleccionado()?"S":"N");
        }

        consultasJSONGet(Constantes.INSERTAR_ESTILO, map, "3");
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
}
