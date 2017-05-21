package com.bemen3.albert.alcarol;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.bemen3.albert.alcarol.entidades.Estilo;

public class ListadoHojasPersonajeActivity extends AppCompatActivity {

    private TextView etTituloActivity;
    private Estilo estiloActual;
    private int height;
    private int width;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_hojas_personaje);

        estiloActual = GlobalParam.estiloActual;

        etTituloActivity = (TextView)findViewById(R.id.tvTituloListadoHojasPersonaje);
        etTituloActivity.setText(etTituloActivity.getText()+" "+estiloActual.getNombre());

        listView = (ListView)findViewById(R.id.listViewListadoHojasPersonaje);
        adaptarTamanyoListView();

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

}
