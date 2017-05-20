package com.bemen3.albert.alcarol;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bemen3.albert.alcarol.entidades.Usuario;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class MisEstilosActivity extends AppCompatActivity {

    private TextView tvTesting;
    private Usuario usuarioApp;
    private ArrayList<Object> listaEstilos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_estilos);

        usuarioApp = GlobalParam.usuarioApp;

        tvTesting = (TextView)findViewById(R.id.titulo_activity);
        tvTesting.setText(tvTesting.getText()+" "+GlobalParam.ID_USUARIO+"\n usuario:"+usuarioApp.getNombre());

        listaEstilos = new ArrayList<>();
        cargarListaEstilos();

    }


    public void cargarListaEstilos(){

    }


    public void consultasJSONGet(String enlace, Map map){
        String newURL = enlace + "?";

        newURL +="username="+map.get("username")+"&password="+map.get("password");


        GestionPeticionesHTTP colaPeticiones = null;

        colaPeticiones.getInstance(this).addToRequestQueue(

                new JsonObjectRequest(Request.Method.GET, newURL, null,

                        new Response.Listener<JSONObject>() {

                            @Override

                            public void onResponse(JSONObject response) {
                                //procesarRespuesta(response); // Procesar respuesta Json
                                try { // Procesar respuesta Json
                                    String estado = response.getString("estado");
                                    String mensaje = response.getString("mensaje");

                                    Toast.makeText(getApplicationContext(), mensaje,
                                            Toast.LENGTH_LONG).show();

                                    //Aqui procesamos la respuesta del JSON
                                    if(estado.equals("1")) {
                                        String id_usuario = response.getString("id_usuario");
                                        String nombre = response.getString("nombre");
                                        String dni = response.getString("dni");
                                        GlobalParam.ID_USUARIO = id_usuario;
                                        Usuario user = new Usuario(id_usuario, nombre, dni);
                                        GlobalParam.usuarioApp = user;
                                        Intent intent = new Intent(getApplicationContext(), MisEstilosActivity.class);
                                        startActivity(intent);
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
