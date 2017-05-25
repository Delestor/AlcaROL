package com.bemen3.albert.alcarol;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bemen3.albert.alcarol.entidades.Estilo;
import com.bemen3.albert.alcarol.entidades.Personaje;

import java.util.ArrayList;
import java.util.List;

/**
 * Adaptador que gestiona los elementos del listview de tipo Personaje
 * @author Albert Cadena
 * @version 26/05/2017 1.0
 */

public class AdaptadorPersonajes extends ArrayAdapter<Personaje> {
    Activity contexto;
    private List<Personaje> listaPersonajes;
    int maxEstilos = 0;

    AdaptadorPersonajes(Activity contexto, List<Personaje> listaPersonajes) {
        super(contexto, R.layout.activity_mis_estilos, listaPersonajes);
        this.listaPersonajes = listaPersonajes;
        this.contexto = contexto;
    }

    @Override
    public int getCount() {
        return listaPersonajes.size();
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
    public Personaje getItem(int position) {
        return listaPersonajes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent){
        View item = convertView;
        final VistaTagPersonajes vistaTag;
        if(item == null){
            LayoutInflater inflater = contexto.getLayoutInflater();
            item = inflater.inflate(R.layout.row_design_personaje, null);
            vistaTag = new VistaTagPersonajes();

//            vistaTag.id=0;
            vistaTag.nombre = (TextView)item.findViewById(R.id.lblNombrePersonaje);
//            vistaTag.tipoPlato = (TextView)item.findViewById(R.id.lblTipoPlato);
//            vistaTag.calorias.setTextColor(Color.parseColor("#00FF00"));
//            vistaTag.check = (CheckBox) item.findViewById(R.id.cbCaja);

//            //listener de los checbox
//            vistaTag.checkbox.setOnCheckedChangeListener(checkListener);

            // Guardamos el objeto en el elemento
            item.setTag(vistaTag);
        }else{
            // Si estamos reutilizando una Vista, recuperamos el objeto interno
            vistaTag = (VistaTagPersonajes)item.getTag();
        }

        // Cargamos los datos de las opciones de la matriz de datos

        vistaTag.nombre.setText(listaPersonajes.get(position).getNombre().toString());
//        vistaTag.tipoPlato.setText(listaPlatos.get(position).getTipoPlato());

        // Devolvemos la Vista (nueva o reutilizada) que dibuja la opción
        return(item);
    }
}
class VistaTagPersonajes {
    ImageView imagen;
    TextView nombre;

}