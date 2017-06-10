package com.bemen3.albert.alcarol;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bemen3.albert.alcarol.entidades.Atributo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Clase que gestiona los elementos de un listView que Mostrarán un listado de los atributos
 * para cada personaje, y guardarán los valores introducidos por el usuario para cada atributo.
 * @author Albert Cadena
 * @version 26/05/2017 1.0
 */

public class AdaptadorAtributosPersonaje extends ArrayAdapter<HashMap<String,String>> {

    Activity contexto;
    private List<HashMap<String,String>> listaAtributos;
    int maxAtributos = 0;

    AdaptadorAtributosPersonaje(Activity contexto, List<HashMap<String,String>> listaAtributos) {
        super(contexto, R.layout.activity_crear_nuevo_personaje, listaAtributos);
        this.listaAtributos = listaAtributos;
        this.contexto = contexto;
    }

    @Override
    public int getCount() {
        return listaAtributos.size();
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
    public HashMap<String,String> getItem(int position) {
        return listaAtributos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent){
        View item = convertView;
        final VistaTagPersonaje vistaTag;
        if(item == null){
            LayoutInflater inflater = contexto.getLayoutInflater();
            item = inflater.inflate(R.layout.row_design_atributos_personaje, null);
            vistaTag = new VistaTagPersonaje();

//            vistaTag.id=0;
            vistaTag.nombre = (TextView)item.findViewById(R.id.textoAtributoPersonaje);
            vistaTag.valor = (EditText)item.findViewById(R.id.etValorAtributoPersonaje);

//            //listener de los checbox
            //vistaTag.checkBox.setOnCheckedChangeListener(checkListener);

            // Guardamos el objeto en el elemento
            item.setTag(vistaTag);
        }else{
            // Si estamos reutilizando una Vista, recuperamos el objeto interno
            vistaTag = (VistaTagPersonaje)item.getTag();
        }

        // Cargamos los datos de las opciones de la matriz de datos

        Iterator it = listaAtributos.get(position).entrySet().iterator();
        while (it.hasNext())
        {
            final Map.Entry mapEntry = (Map.Entry) it.next();
            String keyValue = (String) mapEntry.getKey();
            String value = (String) mapEntry.getValue();

            vistaTag.nombre.setText(keyValue);
            vistaTag.valor.setText(value);

            vistaTag.valor.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    String valorText = vistaTag.valor.getText().toString();
                    mapEntry.setValue(valorText);
                }
            });
        }


//        vistaTag.tipoPlato.setText(listaPlatos.get(position).getTipoPlato());

        // Devolvemos la Vista (nueva o reutilizada) que dibuja la opción
        return(item);
    }

}

class VistaTagPersonaje {

    TextView nombre;
    EditText valor;

}
