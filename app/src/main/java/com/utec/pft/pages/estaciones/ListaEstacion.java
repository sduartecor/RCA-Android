package com.utec.pft.pages.estaciones;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.utec.pft.R;
import com.utec.pft.api.ServiceApi;
import com.utec.pft.entites.Estacion;
import com.utec.pft.entites.Formulario;
import com.utec.pft.entites.Usuario;
import com.utec.pft.modulo.AdapterEstacion;
import com.utec.pft.pages.registros.AltaRegister;
import com.utec.pft.util.ConnectionRest;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaEstacion extends AppCompatActivity {
    List<Estacion> estaciones = new ArrayList<Estacion>();
    AdapterEstacion adaptador;
    ListView lstEstaciones = null;
    Formulario form;
    Usuario usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estacion);
        Bundle intent = getIntent().getExtras();
        adaptador = new AdapterEstacion(ListaEstacion.this, android.R.layout.activity_list_item, estaciones);
        lstEstaciones = (ListView) findViewById(R.id.lstEstaciones);
        lstEstaciones.setAdapter(adaptador);
        allEstacion();
        adaptador.notifyDataSetChanged();
        //
        form = (Formulario) intent.get("formulario");
        usuario = (Usuario) intent.get("usuario");
        //
        lstEstaciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                Estacion estacion = (Estacion) parent.getItemAtPosition(i);
                Intent intentEstacion = new Intent(ListaEstacion.this, AltaRegister.class);
                intentEstacion.putExtra("estacion", estacion);
                intentEstacion.putExtra("formulario", form);
                intentEstacion.putExtra("usuario", usuario);

                startActivity(intentEstacion);
            }
        });

    }

    public void allEstacion() { //Lista Estaciones
        ServiceApi api = ConnectionRest.getConnection().create(ServiceApi.class);
        Call<List<Estacion>> call = api.listarEstaciones();
        call.enqueue(new Callback<List<Estacion>>() {
            @Override
            public void onResponse(Call<List<Estacion>> call, Response<List<Estacion>> response) {
                if(response.body()!=null){
                    List<Estacion> datos = response.body();
                    estaciones.clear();
                    estaciones.addAll(datos);
                    adaptador.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<List<Estacion>> call, Throwable t) {

            }
        });
    }


}
