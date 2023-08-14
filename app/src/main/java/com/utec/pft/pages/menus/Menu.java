package com.utec.pft.pages.menus;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.utec.pft.R;
import com.utec.pft.api.ServiceApi;
import com.utec.pft.entites.Registro;
import com.utec.pft.entites.Rol;
import com.utec.pft.entites.Usuario;
import com.utec.pft.modulo.AdapterRegistro;
import com.utec.pft.pages.registros.AltaRegister;
import com.utec.pft.pages.registros.EditRegister;
import com.utec.pft.util.ConnectionRest;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class Menu extends AppCompatActivity{
    Button bttnPerfil;
    Usuario usuario;
    //
    Button bttnAlta;
    Button bttnLogout;
    //
    List<Registro> registros = new ArrayList<>();
    AdapterRegistro adaptador;
    ListView lstRegistros = null;
    //
    private Rol admin = Rol.Administrador;

    private Rol inv= Rol.Investigador;

    private Rol afic= Rol.Aficionado;
    //
    Button bttnExportar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Bundle intent = getIntent().getExtras();
        bttnPerfil = (Button)  findViewById(R.id.buttonPerfil);
        bttnAlta = (Button) findViewById(R.id.buttonAlta);
        bttnLogout = (Button) findViewById(R.id.buttonLogout);
        bttnExportar = (Button) findViewById(R.id.buttonExportar);
        if (intent!=null){
            usuario = (Usuario) intent.get("usuario");
            bttnPerfil.setText(usuario.getNickname());
        } else {
            Intent intentTwo = new Intent(Menu.this, MainActivity.class);
            startActivity(intentTwo);
        }

        bttnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(Menu.this, PerfilUsuario.class);
                    intent.putExtra("usuario", usuario);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        bttnExportar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkWriteStoragePermission();
            }
        });

        bttnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, MainActivity.class);
                startActivity(intent);
            }
        });

        bttnAlta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(Menu.this, AltaRegister.class);
                    intent.putExtra("usuario", usuario);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        //
        adaptador = new AdapterRegistro(Menu.this, android.R.layout.activity_list_item, registros);
        lstRegistros = (ListView) findViewById(R.id.lstRegistros);
        lstRegistros.setAdapter(adaptador);
        allRegistros();
        adaptador.notifyDataSetChanged();
        //
        lstRegistros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                Registro registroItem = (Registro) parent.getItemAtPosition(i);
                //



                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(Menu.this);
                dialogo1.setTitle("OPCIÓNES");
                dialogo1.setMessage("Que quiere realizar con el siguiente RegistroCA: " + "ID: " + registroItem.getId());
                dialogo1.setCancelable(false);
                if (usuario.getRol().equals(admin) || usuario.getRol().equals(inv)) {
                    dialogo1.setNegativeButton("Eliminar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id) {
                            bajaRegistro(registroItem.getId());
                            allRegistros();
                        }
                    });

                }
                if (registroItem.getUsuario().equals(usuario.getNickname().toUpperCase()) || usuario.getRol().equals(admin)) {
                    dialogo1.setPositiveButton("Modificar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id) {
                            Intent intent = new Intent(Menu.this, EditRegister.class);
                            intent.putExtra("registro", registroItem);
                            intent.putExtra("usuario", usuario);
                            startActivity(intent);

                        }
                    });

                }

                dialogo1.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        dialogo1.cancel();
                    }
                });
                dialogo1.show();
            }

        });


    }

    public void allRegistros() { //Lista Registros
        ServiceApi api = ConnectionRest.getConnection().create(ServiceApi.class);
        Call<List<Registro>> call = api.listarRegistros();
        call.enqueue(new Callback<List<Registro>>() {
            @Override
            public void onResponse(Call<List<Registro>> call, Response<List<Registro>> response) {
                if(response.body()!=null){
                    List<Registro> datos = response.body();
                    registros.clear();
                    registros.addAll(datos);
                    adaptador.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<List<Registro>> call, Throwable t) {

            }
        });
    }

    public void bajaRegistro(Long registro) { //Baja Registros
        try {
            ServiceApi api = ConnectionRest.getConnection().create(ServiceApi.class);
            Call<Boolean> call = api.bajaRegistro(registro);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful()) {
                        Toast mensaje = Toast.makeText(Menu.this, "BAJA DE REGISTRO EXITOSA", Toast.LENGTH_LONG);
                        mensaje.show();
                        allRegistros();

                    }

                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Toast mensaje = Toast.makeText(Menu.this, "NO ENTRO REST", Toast.LENGTH_LONG);
                    mensaje.show();

                }
            });
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void exportarExcel() {
        // Verificar si se puede escribir en el almacenamiento externo
        if (isExternalStorageWritable()) {
            // Crear el archivo Excel en la carpeta de descargas
            String fileName = "registros.xls";
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);

            try {
                // Crear un libro de trabajo y una hoja en el archivo Excel
                WritableWorkbook workbook = Workbook.createWorkbook(file);
                WritableSheet sheet = workbook.createSheet("Registros", 0);

                // Agregar encabezados de columnas
                sheet.addCell(new Label(0, 0, "METODO"));
                sheet.addCell(new Label(1, 0, "FECHA"));
                sheet.addCell(new Label(2, 0, "ESTACION"));
                sheet.addCell(new Label(3, 0, "FORMULARIO"));
                sheet.addCell(new Label(4, 0, "NO2"));
                sheet.addCell(new Label(5, 0, "CO2"));
                sheet.addCell(new Label(6, 0, "PM2,5"));
                sheet.addCell(new Label(7, 0, "PM10"));
                sheet.addCell(new Label(8, 0, "TEMPERATURA"));
                sheet.addCell(new Label(9, 0, "PRECIPITACION"));
                sheet.addCell(new Label(10, 0, "USUARIO"));

                // Agregar los datos de cada registro a las celdas
                for (int i = 0; i < registros.size(); i++) {
                    Registro registro = registros.get(i);
                    sheet.addCell(new Label(0, i + 1, registro.getMetodo()));
                    //
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    String fechaRegistro = formatter.format(registro.getFecha());
                    sheet.addCell(new Label(1, i + 1, fechaRegistro));
                    //
                    sheet.addCell(new Label(2, i + 1, registro.getEstacion().getNombre()));
                    sheet.addCell(new Label(3, i + 1, registro.getFormulario().getNombre()));
                    if (registro.getCampos().get("no2") != null) {
                        sheet.addCell(new Label(4, i + 1, registro.getCampos().get("no2").toString()));
                    }
                    if (registro.getCampos().get("co2") != null) {
                        sheet.addCell(new Label(5, i + 1, registro.getCampos().get("co2").toString()));
                    }
                    if (registro.getCampos().get("pm2,5") != null) {
                        sheet.addCell(new Label(6, i + 1, registro.getCampos().get("pm2,5").toString()));
                    }
                    if (registro.getCampos().get("pm10") != null) {
                        sheet.addCell(new Label(7, i + 1, registro.getCampos().get("pm10").toString()));
                    }
                    if (registro.getCampos().get("temperatura") != null) {
                        sheet.addCell(new Label(8, i + 1, registro.getCampos().get("temperatura").toString()));
                    }
                    if (registro.getCampos().get("precipitacion") != null) {
                        sheet.addCell(new Label(9, i + 1, registro.getCampos().get("precipitacion").toString()));
                    }
                    sheet.addCell(new Label(10, i + 1, registro.getUsuario()));
                }

                // Escribir y cerrar el libro de trabajo
                workbook.write();
                workbook.close();

                // Mostrar mensaje de éxito
                Toast.makeText(this, "Archivo Excel exportado con éxito", Toast.LENGTH_SHORT).show();
            } catch (IOException | WriteException e) {
                e.printStackTrace();
                // Mostrar mensaje de error
                Toast.makeText(this, "Error al exportar el archivo Excel", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Mostrar mensaje de error si no se puede acceder al almacenamiento externo
            Toast.makeText(this, "No se puede acceder al almacenamiento externo", Toast.LENGTH_SHORT).show();
        }
    }

    // Método auxiliar para verificar si se puede escribir en el almacenamiento externo
    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    private static final int REQUEST_WRITE_STORAGE = 1;

    // Verificar y solicitar permisos de escritura en almacenamiento externo
    private void checkWriteStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_STORAGE);
        } else {
            // Permiso ya otorgado, realizar la exportación del archivo Excel
            exportarExcel();
        }
    }

    // Manejar el resultado de la solicitud de permisos
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_WRITE_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso otorgado, realizar la exportación del archivo Excel
                exportarExcel();
            } else {
                // Permiso denegado, mostrar un mensaje de error
                Toast.makeText(this, "Permiso de escritura en almacenamiento externo denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }




    public void cancelar() {
        finish();
    }









}
