package com.utec.pft.pages.registros;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.utec.pft.R;
import com.utec.pft.api.ServiceApi;
import com.utec.pft.entites.Estacion;
import com.utec.pft.entites.Formulario;
import com.utec.pft.entites.Registro;
import com.utec.pft.entites.Usuario;
import com.utec.pft.modulo.DatePickerFragment;
import com.utec.pft.pages.estaciones.ListaEstacion;
import com.utec.pft.pages.formularios.ListaFormulario;
import com.utec.pft.pages.menus.Menu;
import com.utec.pft.util.ConnectionRest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;



import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditRegister extends AppCompatActivity {
    Button listaForm;
    Button listaEstacion;
    Button btnModificar;
    //
    EditText txtNo2;
    EditText txtCo2;
    EditText txtPm25;
    EditText txtPm10;
    EditText txtTemp;
    EditText txtPrep;
    EditText txtFecha;
    //
    Spinner spinner;
    String[] opciones = {"MANUAL", "AUTOMÁTICO"};
    //
    EditText txtForm;
    EditText txtEstacion;
    //
    Formulario form;
    Estacion estacion;
    //
    Bundle intent;
    //
    Button bttnMenu;
    //
    Registro registroEdit;
    String fechaRegistro;
    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_registro);
        intent = getIntent().getExtras();
        usuario = (Usuario) intent.get("usuario");

        listaForm = (Button) findViewById(R.id.buttonForm);
        listaForm.setEnabled(false);
        listaEstacion = (Button) findViewById(R.id.buttonEstacion);
        listaEstacion.setEnabled(false);
        btnModificar = (Button) findViewById(R.id.buttonModificar);
        //
        txtNo2 = (EditText) findViewById(R.id.inputNo2);
        txtCo2 = (EditText) findViewById(R.id.inputCo2);
        txtPm25 = (EditText) findViewById(R.id.inputPm25);
        txtPm10 = (EditText) findViewById(R.id.inputPm10);
        txtTemp = (EditText) findViewById(R.id.inputTemp);
        txtPrep = (EditText) findViewById(R.id.inputPrep);
        txtFecha  = (EditText) findViewById(R.id.inputFecha);
        spinner =  (Spinner) findViewById(R.id.spinnerOpcionesEdit);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, opciones);
        spinner.setAdapter(adapter);
        //
        txtForm = (EditText) findViewById(R.id.textForm);
        txtEstacion = (EditText) findViewById(R.id.txtEstacion);
        //
        txtForm.setFocusable(false);
        txtEstacion.setFocusable(false);
        //
        bttnMenu = (Button) findViewById(R.id.buttonMenuEdit);
//
        if (intent!=null){

       
            registroEdit = (Registro) intent.get("registro");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
             fechaRegistro = formatter.format(registroEdit.getFecha());

            //
            txtFecha.setText(fechaRegistro);
            //
            int selectedIndex = -1;
            for (int i = 0; i < opciones.length; i++) {
                if (registroEdit.getMetodo().equals(opciones[i])) {
                    selectedIndex = i;
                    break;
                }
            }

            // Establece la selección en el Spinner
            if (selectedIndex != -1) {
                spinner.setSelection(selectedIndex);
            }
            //
            txtForm.setText(registroEdit.getFormulario().getNombre());
            txtEstacion.setText(registroEdit.getEstacion().getNombre());
            if (registroEdit.getCampos().get("no2") != null){
                txtNo2.setEnabled(true);
                txtNo2.setText(registroEdit.getCampos().get("no2").toString());

            } else {
                txtNo2.setEnabled(false);
            }
            //
            if (registroEdit.getCampos().get("co2") != null){
                txtCo2.setEnabled(true);
                txtCo2.setText(registroEdit.getCampos().get("co2").toString());
            } else {
                txtCo2.setEnabled(false);
            }
            //
            if (registroEdit.getCampos().get("pm2,5") != null){
                txtPm25.setEnabled(true);
                txtPm25.setText(registroEdit.getCampos().get("pm2,5").toString());

            } else {
                txtPm25.setEnabled(false);
            }
            //
            if (registroEdit.getCampos().get("pm10") != null){
                txtPm10.setEnabled(true);
                txtPm10.setText(registroEdit.getCampos().get("pm10").toString());
            } else {
                txtPm10.setEnabled(false);
            }
            //
            if (registroEdit.getCampos().get("temperatura") != null){
                txtTemp.setEnabled(true);
                txtTemp.setText(registroEdit.getCampos().get("temperatura").toString());
            } else {
                txtTemp.setEnabled(false);
            }
            //
            if (registroEdit.getCampos().get("precipitacion") != null){
                txtPrep.setEnabled(true);
                txtPrep.setText(registroEdit.getCampos().get("precipitacion").toString());


            } else {
                txtPrep.setEnabled(false);
            }
        }


        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                modificarRegistroBD(editRegistro(), fechaRegistro);
            }
        });

        bttnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMenu = new Intent(EditRegister.this, Menu.class);
                intentMenu.putExtra("usuario", usuario);
                startActivity(intentMenu);
            }
        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    public Registro editRegistro() {
        try {
            //-----------------validaciones----------------------

                //

            String opcionSeleccionada = spinner.getSelectedItem().toString();
            registroEdit.setMetodo(opcionSeleccionada);
                //
                Map<String, Double> campos = new HashMap<String, Double>();
                if (registroEdit.getCampos().get("no2") != null) {
                    campos.put("no2", Double.valueOf(txtNo2.getText().toString()));

                }

                if (registroEdit.getCampos().get("co2") != null) {

                    campos.put("co2",  Double.valueOf(txtCo2.getText().toString()));
                }

                if (registroEdit.getCampos().get("pm2,5") != null) {
                    campos.put("pm2,5",  Double.valueOf(txtPm25.getText().toString()));
                }

                if (registroEdit.getCampos().get("pm10") != null ) {
                    campos.put("pm10",  Double.valueOf(txtPm10.getText().toString()));
                }

                if (registroEdit.getCampos().get("temperatura") != null ) {
                    campos.put("temperatura",  Double.valueOf(txtTemp.getText().toString()));
                }

                if (registroEdit.getCampos().get("precipitacion") != null ) {
                    campos.put("precipitacion",  Double.valueOf(txtPrep.getText().toString()));
                }
                //
                registroEdit.setCampos(campos);

                registroEdit.setFecha(null);



            return registroEdit;



        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(EditRegister.this,"Campos Vacios", Toast.LENGTH_LONG).show();
        }
        return null;
    }

    public void modificarRegistroBD(Registro registro, String fecha) {
        try {
            ServiceApi api = ConnectionRest.getConnection().create(ServiceApi.class);
            Call<Boolean> call = api.modificarRegistro(registro, fecha);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful()) {
                        Toast mensaje = Toast.makeText(EditRegister.this, "MODIFICACIÓN DE REGISTRO EXITOSA",Toast.LENGTH_LONG);
                        mensaje.show();
                        //

                    } else {
                        Toast mensaje = Toast.makeText(EditRegister.this, "MODIFICACIÓN DE REGISTRO FAIL",Toast.LENGTH_LONG);
                        mensaje.show();
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Toast mensaje = Toast.makeText(EditRegister.this, "NO ENTRO REST",Toast.LENGTH_LONG);
                    mensaje.show();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
