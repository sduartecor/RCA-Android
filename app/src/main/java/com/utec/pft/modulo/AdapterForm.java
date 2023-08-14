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
import com.utec.pft.entites.Formulario;

import java.util.List;

public class AdapterForm extends ArrayAdapter<Formulario> {

    private Context conext;
    private List<Formulario> formularios;

    public AdapterForm(@NonNull Context context, int resource, @NonNull List<Formulario> formularios) {
        super(context, resource, formularios);
        this.conext = context;
        this.formularios = formularios;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) conext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.activty_list_item_form, parent, false);

        TextView txtId = (TextView) row.findViewById(R.id.txtListID);
        TextView txtNombre = (TextView) row.findViewById(R.id.txtListNombre);
        TextView txtNo2 = (TextView) row.findViewById(R.id.txtListNo2);
        TextView txtCo2 = (TextView) row.findViewById(R.id.txtListCo2);
        TextView txtPm25 = (TextView) row.findViewById(R.id.txtListPm25);
        TextView txtPm10 = (TextView) row.findViewById(R.id.txtListPm10);
        TextView txtTemp = (TextView) row.findViewById(R.id.txtListTemp);
        TextView txtPrep = (TextView) row.findViewById(R.id.txtListPrep);

        txtId.setText("ID: " + formularios.get(position).getId());
        txtNombre.setText("NOMBRE: " + formularios.get(position).getNombre());
        try{
            if (formularios.get(position).getCampos().get("no2")) {
                txtNo2.setText("No2: " + "X");
            } else {
                txtNo2.setText("No2: " + "");
            }
            //
            if (formularios.get(position).getCampos().get("co2")) {
                txtCo2.setText("Co2: " + "X");
            } else {
                txtCo2.setText("Co2: " + "");
            }
            //
            if (formularios.get(position).getCampos().get("pm2,5")) {
                txtPm25.setText("Pm2,5: " + "X");
            } else {
                txtPm25.setText("Pm2,5: " + "");
            }
            //
            if (formularios.get(position).getCampos().get("pm10")) {
                txtPm10.setText("Pm10: " + "X");
            } else {
                txtPm10.setText("Pm10: " + "");
            }
            //
            if (formularios.get(position).getCampos().get("temperatura")) {
                txtTemp.setText("Tempertatura: " + "X");
            } else {
                txtTemp.setText("Temperatura: " + "");
            }
            //
            if (formularios.get(position).getCampos().get("precipitacion")) {
                txtPrep.setText("Precipitacion: " + "X");
            } else {
                txtPrep.setText("Precipitacion: " + "");
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        return row;
    }
}
