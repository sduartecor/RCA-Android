package com.utec.pft.pages.registros;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import lombok.experimental.NonFinal;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AltaRegister extends AppCompatActivity {
    Button listaForm;
    Button listaEstacion;
    Button btnAlta;
    //
    EditText txtNo2;
    EditText txtCo2;
    EditText txtPm25;
    EditText txtPm10;
    EditText txtTemp;
    EditText txtPrep;
    EditText txtFecha;
    Spinner spinner;
    String[] opciones = {"Selecciona una opción", "MANUAL", "AUTOMÁTICO"};
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
    Usuario usuario;
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_registro);
        intent = getIntent().getExtras();
        usuario = (Usuario) intent.get("usuario");
        //
        listaForm = (Button) findViewById(R.id.buttonForm);
        listaEstacion = (Button) findViewById(R.id.buttonEstacion);
        btnAlta = (Button) findViewById(R.id.buttonAlta);
        //
        txtNo2 = (EditText) findViewById(R.id.inputNo2);
        txtCo2 = (EditText) findViewById(R.id.inputCo2);
        txtPm25 = (EditText) findViewById(R.id.inputPm25);
        txtPm10 = (EditText) findViewById(R.id.inputPm10);
        txtTemp = (EditText) findViewById(R.id.inputTemp);
        txtPrep = (EditText) findViewById(R.id.inputPrep);
        txtFecha  = (EditText) findViewById(R.id.inputFecha);
        spinner =  (Spinner) findViewById(R.id.spinnerOpciones);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, opciones);
        spinner.setAdapter(adapter);
        //
        txtForm = (EditText) findViewById(R.id.textForm);
        txtEstacion = (EditText) findViewById(R.id.txtEstacion);
        //
        txtForm.setFocusable(false);
        txtEstacion.setFocusable(false);
        //
        bttnMenu = (Button) findViewById(R.id.buttonMenu);
        //
        if (intent!=null){

            if (intent.get("formulario") != null){
            form = (Formulario) intent.get("formulario");
            txtForm.setText(form.getNombre());
            if (form.getCampos().get("no2")){
                txtNo2.setEnabled(true);

            } else {
                txtNo2.setEnabled(false);
            }
            //
            if (form.getCampos().get("co2")){
                txtCo2.setEnabled(true);
            } else {
                txtCo2.setEnabled(false);
            }
            //
            if (form.getCampos().get("pm2,5")){
                txtPm25.setEnabled(true);
            } else {
                txtPm25.setEnabled(false);
            }
            //
            if (form.getCampos().get("pm10")){
                txtPm10.setEnabled(true);
            } else {
                txtPm10.setEnabled(false);
            }
            //
            if (form.getCampos().get("temperatura")){
                txtTemp.setEnabled(true);
            } else {
                txtTemp.setEnabled(false);
            }
            //
            if (form.getCampos().get("precipitacion")){
                txtPrep.setEnabled(true);
            } else {
                txtPrep.setEnabled(false);
            }

            } if (intent.get("estacion") != null) {
                estacion = (Estacion) intent.get("estacion");
                txtEstacion.setText(estacion.getNombre());
            }

        } else {

            txtNo2.setEnabled(false);
            txtCo2.setEnabled(false);
            txtPm25.setEnabled(false);
            txtPm10.setEnabled(false);
            txtTemp.setEnabled(false);
            txtPrep.setEnabled(false);

        }

        txtFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.inputFecha:
                        showDatePickerDialog();
                        break;
                }
            }
        });

        listaForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intentFormulario = new Intent(AltaRegister.this, ListaFormulario.class);
                    intentFormulario.putExtra("estacion", estacion);
                    intentFormulario.putExtra("usuario", usuario);
                    startActivity(intentFormulario);

            }
        });

        listaEstacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent intentEstacion = new Intent(AltaRegister.this, ListaEstacion.class);
                    intentEstacion.putExtra("formulario",form);
                intentEstacion.putExtra("usuario",usuario);
                    startActivity(intentEstacion);

            }
        });

        btnAlta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    AltaRegistroBD(crearRegistro(), txtFecha.getText().toString());
            }
        });

        bttnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMenu = new Intent(AltaRegister.this, Menu.class);
                intentMenu.putExtra("usuario", usuario);
                startActivity(intentMenu);
            }
        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Calendar selectedCalendar = Calendar.getInstance();
                selectedCalendar.set(year, month, day);

                Calendar currentCalendar = Calendar.getInstance();

                if (selectedCalendar.after(currentCalendar)) {
                    // La fecha seleccionada es mayor que la fecha actual
                    Toast.makeText(getApplicationContext(), "No se puede seleccionar una fecha futura", Toast.LENGTH_SHORT).show();
                    return;
                }

                final String selectedDate;
                if (month + 1 < 10) {
                    selectedDate = year + "-0" + (month + 1) + "-" + day;
                } else {
                    selectedDate = year + "-" + (month + 1) + "-" + day;
                }
                txtFecha.setText(selectedDate);
            }
        });
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }


    public Registro crearRegistro() {
        try {
            //-----------------validaciones----------------------
         if (txtFecha.getText().toString().equals("")) {
            txtFecha.setError("Ingrese una fecha");
            txtFecha.requestFocus();
        } else if (spinner.getSelectedItem() == null || spinner.getSelectedItem().toString().equals("Selecciona una opción")) {
             // La opción seleccionada en el Spinner es nula o "Selecciona una opción"
             TextView errorText = (TextView) spinner.getSelectedView();
             errorText.setError("Seleccione una opción válida");
             errorText.requestFocus();
         } else if (txtForm.toString().equals("")) {
            txtForm.setError("Seleccione un Formulario");
            listaForm.requestFocus();
        } else if (txtEstacion.getText().toString().equals("")){
            txtEstacion.setError("Seleccióne una estacion");
            listaEstacion.requestFocus();
        } else if (form.getCampos().get("no2") && txtNo2.getText().toString().equals("")) {
            txtNo2.setError("Ingrese un valor");
            txtNo2.requestFocus();
        } else if (form.getCampos().get("co2") && txtCo2.getText().toString().equals("")) {
            txtCo2.setError("Ingrese un valor");
            txtCo2.requestFocus();
        } else if (form.getCampos().get("pm2,5") && txtPm25.getText().toString().equals("")) {
            txtPm25.setError("Ingrese un valor");
            txtPm25.requestFocus();
        } else if (form.getCampos().get("pm10") && txtPm10.getText().toString().equals("")) {
            txtPm10.setError("Ingrese un valor");
            txtPm10.requestFocus();
        } else if (form.getCampos().get("temperatura") && txtTemp.getText().toString().equals("")) {
            txtTemp.setError("Ingrese un valor");
            txtTemp.requestFocus();
        } else if (form.getCampos().get("precipitacion") && txtPrep.getText().toString().equals("")) {
            txtPrep.setError("Ingrese un valor");
            txtPrep.requestFocus();
        } else {
            Registro registroNuevo = new Registro();
            //
            registroNuevo.setEstacion(estacion);
            registroNuevo.setFormulario(form);
            //
             String opcionSeleccionada = spinner.getSelectedItem().toString();
             registroNuevo.setMetodo(opcionSeleccionada);
            //
             Map<String, Double> campos = new HashMap<String, Double>();
             if (form.getCampos().get("no2")) {
                 campos.put("no2", Double.valueOf(txtNo2.getText().toString()));
             }

             if (form.getCampos().get("co2")) {

                 campos.put("co2",  Double.valueOf(txtCo2.getText().toString()));
             }

             if (form.getCampos().get("pm2,5")) {
                 campos.put("pm2,5",  Double.valueOf(txtPm25.getText().toString()));
             }

             if (form.getCampos().get("pm10")) {
                 campos.put("pm10",  Double.valueOf(txtPm10.getText().toString()));
             }

             if (form.getCampos().get("temperatura")) {
                 campos.put("temperatura",  Double.valueOf(txtTemp.getText().toString()));
             }

             if (form.getCampos().get("precipitacion")) {
                 campos.put("precipitacion",  Double.valueOf(txtPrep.getText().toString()));
             }
             //
             registroNuevo.setCampos(campos);

             registroNuevo.setUsuario(usuario.getNickname().toString());

             registroNuevo.setActivo(true);

             return registroNuevo;

         }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(AltaRegister.this,"Campos Vacios", Toast.LENGTH_LONG).show();
        }
        return null;
    }



    public void AltaRegistroBD(Registro registro, String fecha) {
        try {
            ServiceApi api = ConnectionRest.getConnection().create(ServiceApi.class);
            Call<Boolean> call = api.altaRegistro(registro, fecha);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful()) {
                        Toast mensaje = Toast.makeText(AltaRegister.this, "ALTA DE REGISTRO EXITOSA",Toast.LENGTH_LONG);
                        mensaje.show();
                        //
                        txtFecha.setText("");
                        txtForm.setText("");
                        txtEstacion.setText("");
                        txtNo2.setText("");
                        txtCo2.setText("");
                        txtPm25.setText("");
                        txtPm10.setText("");
                        txtTemp.setText("");
                        txtPrep.setText("");
                    } else {
                        Toast mensaje = Toast.makeText(AltaRegister.this, "Verifique los datos",Toast.LENGTH_LONG);
                        mensaje.show();
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Toast mensaje = Toast.makeText(AltaRegister.this, "Verifique los datos",Toast.LENGTH_LONG);
                    mensaje.show();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
