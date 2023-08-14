package com.utec.pft.modulo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.utec.pft.R;
import com.utec.pft.entites.Estacion;

import java.util.List;

public class AdapterEstacion extends ArrayAdapter<Estacion> {
    private Context conext;
    private List<Estacion> estaciones;

    public AdapterEstacion(@NonNull Context context, int resource, @NonNull List<Estacion> estaciones) {
        super(context, resource, estaciones);
        this.conext = context;
        this.estaciones = estaciones;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) conext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.activity_list_item_estacion, parent, false);

        TextView txtId = (TextView) row.findViewById(R.id.txtListID);
        TextView txtNombre = (TextView) row.findViewById(R.id.txtListNombre);
        TextView txtDesc = (TextView) row.findViewById(R.id.txtListDesc);
        TextView txtCiudad = (TextView) row.findViewById(R.id.txtListCiudad);
        TextView txtLat = (TextView) row.findViewById(R.id.txtListLat);
        TextView txtLng = (TextView) row.findViewById(R.id.txtListLng);



        txtId.setText("ID: " + estaciones.get(position).getId());
        txtNombre.setText("NOMBRE: " + estaciones.get(position).getNombre());
        txtDesc.setText("DESCRIPCIÓN: " + estaciones.get(position).getDescripcion());
        txtCiudad.setText("CIUDAD: " + estaciones.get(position).getCiudad().getNombre());
        txtLat.setText("LAT: " + estaciones.get(position).getLatitud());
        txtLng.setText("LNG: " + estaciones.get(position).getLongitud());



        return row;
    }
}
