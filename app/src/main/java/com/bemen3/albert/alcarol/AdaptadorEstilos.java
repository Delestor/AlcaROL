package com.bemen3.albert.alcarol;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bemen3.albert.alcarol.entidades.Estilo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jandro on 01/02/2017.
 */

public class AdaptadorEstilos extends ArrayAdapter<Estilo> {

    Activity contexto;
    private List<Estilo> listaEstilos;
    public static List<String> listaEstilosMarcados;
    int maxEstilos = 0;

    AdaptadorEstilos(Activity contexto, List<Estilo> listaEstilos) {
        super(contexto, R.layout.activity_mis_estilos, listaEstilos);
        this.listaEstilos = listaEstilos;
        this.listaEstilosMarcados = new ArrayList<>();
        this.contexto = contexto;
    }

    @Override
    public int getCount() {
        return listaEstilos.size();
    }


    @Override
    public int getViewTypeCount() {
        if (getCount() != 0)
            return getCount();
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public Estilo getItem(int position) {
        return listaEstilos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent){
        View item = convertView;
        final VistaTagMetre vistaTag;
        if(item == null){
            LayoutInflater inflater = contexto.getLayoutInflater();
            item = inflater.inflate(R.layout.row_design_estilo, null);
            vistaTag = new VistaTagMetre();

//            vistaTag.id=0;
            vistaTag.nombre = (TextView)item.findViewById(R.id.lblNombreEstilo);
//            vistaTag.tipoPlato = (TextView)item.findViewById(R.id.lblTipoPlato);
//            vistaTag.calorias.setTextColor(Color.parseColor("#00FF00"));
//            vistaTag.check = (CheckBox) item.findViewById(R.id.cbCaja);

//            //listener de los checbox
//            vistaTag.checkbox.setOnCheckedChangeListener(checkListener);

            // Guardamos el objeto en el elemento
            item.setTag(vistaTag);
        }else{
            // Si estamos reutilizando una Vista, recuperamos el objeto interno
            vistaTag = (VistaTagMetre)item.getTag();
        }

        // Cargamos los datos de las opciones de la matriz de datos

        vistaTag.nombre.setText(listaEstilos.get(position).getNombre().toString());
//        vistaTag.tipoPlato.setText(listaPlatos.get(position).getTipoPlato());

        // Devolvemos la Vista (nueva o reutilizada) que dibuja la opción
        return(item);
    }


}

class VistaTagMetre {
    ImageView imagen;
    TextView nombre;

}

