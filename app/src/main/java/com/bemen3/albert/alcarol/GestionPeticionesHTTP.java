package com.bemen3.albert.alcarol;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Albert on 03/04/2017.
 */

public final class GestionPeticionesHTTP {
    // Atributos
    private static GestionPeticionesHTTP singleton;
    private RequestQueue colaPeticiones;
    private static Context context;

    private GestionPeticionesHTTP(Context context) {
        GestionPeticionesHTTP.context = context;
        colaPeticiones = getRequestQueue();
    }

    /*** Retorna la instancia unica del singleton ***/
    public static synchronized GestionPeticionesHTTP getInstance(Context context) {
        if (singleton == null) {
            singleton = new GestionPeticionesHTTP(context.getApplicationContext());
        }
        return singleton;
    }

    /*** Obtiene la instancia de la cola de peticiones***/
    public RequestQueue getRequestQueue() {
        if (colaPeticiones == null) {
            colaPeticiones = Volley.newRequestQueue(context.getApplicationContext());
        }
        return colaPeticiones;
    }
    /*** Añade la petición a la cola ***/
    public <T> void addToRequestQueue(Request<T> req) {
        req.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        getRequestQueue().add(req);
    }
}
