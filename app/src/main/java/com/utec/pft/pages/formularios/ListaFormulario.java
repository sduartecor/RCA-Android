package com.utec.pft.pages.formularios;

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
import com.utec.pft.modulo.AdapterForm;
import com.utec.pft.pages.registros.AltaRegister;
import com.utec.pft.util.ConnectionRest;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaFormulario extends AppCompatActivity {
    List<Formulario> formularios = new ArrayList<Formulario>();
    AdapterForm adaptador;
    ListView lstFormularios = null;
    Estacion estacion;
    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        Bundle intent = getIntent().getExtras();
        adaptador = new AdapterForm(ListaFormulario.this, android.R.layout.activity_list_item, formularios);
        lstFormularios = (ListView) findViewById(R.id.lstFormularios);
        lstFormularios.setAdapter(adaptador);
        allForm();
        adaptador.notifyDataSetChanged();
        //
        estacion = (Estacion) intent.get("estacion");
        usuario = (Usuario) intent.get("usuario");
        //
        lstFormularios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {

                    Formulario form = (Formulario) parent.getItemAtPosition(i);
                    Intent intentAlta = new Intent(ListaFormulario.this, AltaRegister.class);
                    intentAlta.putExtra("formulario", form);
                    intentAlta.putExtra("estacion", estacion);
                intentAlta.putExtra("usuario", usuario);

                    startActivity(intentAlta);

            }
        });




    }

    public void allForm() { //Lista Formularios
        ServiceApi api = ConnectionRest.getConnection().create(ServiceApi.class);
        Call<List<Formulario>> call = api.listarFormularios();
        call.enqueue(new Callback<List<Formulario>>() {
            @Override
            public void onResponse(Call<List<Formulario>> call, Response<List<Formulario>> response) {
                if(response.body()!=null){
                    List<Formulario> datos = response.body();
                    formularios.clear();
                    formularios.addAll(datos);
                    adaptador.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<List<Formulario>> call, Throwable t) {
            }
        });
    }
}
