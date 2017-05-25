package com.bemen3.albert.alcarol;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bemen3.albert.alcarol.entidades.Atributo;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que gestiona cada uno de los elementos de un listview de Atributos.
 * @author Albert Cadena
 * @version 26/05/2017 1.0
 */

public class AdaptadorAtributos extends ArrayAdapter<Atributo> {
    Activity contexto;
    private List<Atributo> listaAtributos;
    public static List<Atributo> listaAtributosMarcados;
    int maxAtributos = 0;

    AdaptadorAtributos(Activity contexto, List<Atributo> listaAtributos) {
        super(contexto, R.layout.activity_crear_nuevo_estilo, listaAtributos);
        this.listaAtributos = listaAtributos;
        this.listaAtributosMarcados = new ArrayList<>();
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
    public Atributo getItem(int position) {
        return listaAtributos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent){
        View item = convertView;
        final VistaTag vistaTag;
        if(item == null){
            LayoutInflater inflater = contexto.getLayoutInflater();
            item = inflater.inflate(R.layout.row_design_atributo, null);
            vistaTag = new VistaTag();

//            vistaTag.id=0;
            vistaTag.nombre = (TextView)item.findViewById(R.id.lblNombreAtributo);
            vistaTag.checkBox = (CheckBox) item.findViewById(R.id.checkBoxAtributo);

//            //listener de los checbox
            //vistaTag.checkBox.setOnCheckedChangeListener(checkListener);

            // Guardamos el objeto en el elemento
            item.setTag(vistaTag);
        }else{
            // Si estamos reutilizando una Vista, recuperamos el objeto interno
            vistaTag = (VistaTag)item.getTag();
        }

        // Cargamos los datos de las opciones de la matriz de datos
        vistaTag.nombre.setText(listaAtributos.get(position).getNombre());

        Atributo obj = getItem(position);
        vistaTag.checkBox.setTag(obj);
        vistaTag.checkBox.setChecked(obj.isSeleccionado());

        vistaTag.checkBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    vistaTag.checkBox.setChecked(true);
                    listaAtributos.get(position).setSeleccionado(true);
                    listaAtributosMarcados.add(listaAtributos.get(position));
                }else{
                    vistaTag.checkBox.setChecked(false);
                    listaAtributos.get(position).setSeleccionado(false);
                    listaAtributosMarcados.remove(listaAtributos.get(position));
                    Toast.makeText(contexto,"Atributo "+listaAtributos.get(position).getNombre()+" quitado de la selección.",Toast.LENGTH_LONG).show();
                }
            }
        });

//        vistaTag.tipoPlato.setText(listaPlatos.get(position).getTipoPlato());

        // Devolvemos la Vista (nueva o reutilizada) que dibuja la opción
        return(item);
    }

    public List<Atributo> getListaAtributosMarcados(){
        return listaAtributosMarcados;
    }

}

class VistaTag {
    CheckBox checkBox;
    TextView nombre;

}
