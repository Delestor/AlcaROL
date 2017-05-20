package com.bemen3.albert.alcarol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.bemen3.albert.alcarol.entidades.Usuario;

public class MisEstilosActivity extends AppCompatActivity {

    private TextView tvTesting;
    private Usuario usuarioApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_estilos);

        usuarioApp = GlobalParam.usuarioApp;

        tvTesting = (TextView)findViewById(R.id.titulo_activity);
        tvTesting.setText(tvTesting.getText()+" "+GlobalParam.ID_USUARIO+"\n usuario:"+usuarioApp.getNombre());
    }
}
