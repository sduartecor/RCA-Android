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
import com.utec.pft.entites.Formulario;
import com.utec.pft.entites.Registro;

import java.util.List;

public class AdapterRegistro extends ArrayAdapter<Registro> {

    private Context conext;
    private List<Registro> registros;

    public AdapterRegistro(@NonNull Context context, int resource, @NonNull List<Registro> registros) {
        super(context, resource, registros);
        this.conext = context;
        this.registros = registros;
    }

    public List<Registro> getRegistros() {
        return registros;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) conext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.activity_list_item_registro, parent, false);

        TextView txtComentario = (TextView) row.findViewById(R.id.txtListComentario);
        TextView txtFormulario = (TextView) row.findViewById(R.id.txtFormulario);
        TextView txtEstacion = (TextView) row.findViewById(R.id.txtEstacion);
        TextView txtNo2 = (TextView) row.findViewById(R.id.txtListNo2);
        TextView txtCo2 = (TextView) row.findViewById(R.id.txtListCo2);
        TextView txtPm25 = (TextView) row.findViewById(R.id.txtListPm25);
        TextView txtPm10 = (TextView) row.findViewById(R.id.txtListPm10);
        TextView txtTemp = (TextView) row.findViewById(R.id.txtListTemp);
        TextView txtPrep = (TextView) row.findViewById(R.id.txtListPrep);
        TextView txtUser = (TextView) row.findViewById(R.id.txtListUsuario);

        txtComentario.setText("METODO: " + registros.get(position).getMetodo());
        txtFormulario.setText("FORMULARIO: " + registros.get(position).getFormulario().getNombre());
        txtEstacion.setText("ESTACIÓN: " + registros.get(position).getEstacion().getNombre());
        txtUser.setText("USUARIO: " + registros.get(position).getUsuario());
        try{
            if (registros.get(position).getCampos().get("no2") != null) {
                txtNo2.setText("No2: " + registros.get(position).getCampos().get("no2"));
            } else {
                txtNo2.setText("No2: " + "");
            }
            //
            if (registros.get(position).getCampos().get("co2") != null) {
                txtCo2.setText("Co2: " + registros.get(position).getCampos().get("co2"));
            } else {
                txtCo2.setText("Co2: " + "");
            }
            //
            if (registros.get(position).getCampos().get("pm2,5") != null) {
                txtPm25.setText("Pm2,5: " + registros.get(position).getCampos().get("pm2,5"));
            } else {
                txtPm25.setText("Pm2,5: " + "");
            }
            //
            if (registros.get(position).getCampos().get("pm10") != null) {
                txtPm10.setText("Pm10: " + registros.get(position).getCampos().get("pm10"));
            } else {
                txtPm10.setText("Pm10: " + "");
            }
            //
            if (registros.get(position).getCampos().get("temperatura") != null) {
                txtTemp.setText("Tempertatura: " + registros.get(position).getCampos().get("temperatura"));
            } else {
                txtTemp.setText("Temperatura: " + "");
            }
            //
            if (registros.get(position).getCampos().get("precipitacion") != null) {
                txtPrep.setText("Precipitacion: " + registros.get(position).getCampos().get("precipitacion"));
            } else {
                txtPrep.setText("Precipitacion: " + "");
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        return row;
    }
}
