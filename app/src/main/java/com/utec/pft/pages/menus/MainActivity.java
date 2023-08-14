package com.utec.pft.pages.menus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.utec.pft.R;
import com.utec.pft.api.ServiceApi;
import com.utec.pft.entites.Usuario;
import com.utec.pft.util.ConnectionRest;

public class MainActivity extends AppCompatActivity {
    EditText userTxt;
    EditText passwdTxt;
    Button bttnLogin;
    String usuarioLog;
    String passwordLog;
    Switch ldap;
 


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userTxt = (EditText) findViewById(R.id.idUsuario);
        passwdTxt = (EditText) findViewById(R.id.idPassword);
        bttnLogin = (Button) findViewById(R.id.buttonLogin);
        ldap = (Switch) findViewById(R.id.switchLdap);


        bttnLogin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {


                usuarioLog = userTxt.getText().toString();
                passwordLog = passwdTxt.getText().toString();
                if (ldap.isChecked()==false) {
                    loginUsuario(usuarioLog, passwordLog);
                } else if (ldap.isChecked()==true){
                    loginLdap(usuarioLog, passwordLog);
                }
            }
        });


    }

 public void loginUsuario(String usuario, String password) {
     try {
         ServiceApi api = ConnectionRest.getConnection().create(ServiceApi.class);
         Call<Usuario> call = api.login(usuario, password);
         call.enqueue(new Callback<Usuario>() {
             @Override
             public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if(response.body()!=null){
                    Usuario usuario = response.body();
                    Log.i("Usuario:",usuario.toString());
                    Intent intent = new Intent(MainActivity.this, Menu.class);
                    intent.putExtra("usuario", usuario);
                    startActivity(intent);
                } else {
                    Toast mensaje = Toast.makeText(MainActivity.this, MainActivity.this.getString(R.string.login_incorrect),Toast.LENGTH_LONG);
                    mensaje.show();
                }
             }

             @Override
             public void onFailure(Call<Usuario> call, Throwable t) {
                 Toast mensaje = Toast.makeText(MainActivity.this, "NO ENTRO REST",Toast.LENGTH_LONG);
                 mensaje.show();

             }
         });

     } catch (Exception e) {
         e.printStackTrace();
     }
 }


 public void loginLdap (String usuario, String password) {
     try {
         ServiceApi api = ConnectionRest.getConnection().create(ServiceApi.class);
         Call<Usuario> call = api.ldap(usuario, password);
         call.enqueue(new Callback<Usuario>() {
             @Override
             public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                 if(response.body()!=null){
                     Usuario usuario = response.body();
                     Log.i("Usuario:",usuario.toString());
                     Intent intent = new Intent(MainActivity.this, Menu.class);
                     intent.putExtra("usuario", usuario);
                     startActivity(intent);
                 } else {
                     Toast mensaje = Toast.makeText(MainActivity.this, MainActivity.this.getString(R.string.login_incorrect),Toast.LENGTH_LONG);
                     mensaje.show();
                 }
             }

             @Override
             public void onFailure(Call<Usuario> call, Throwable t) {
                 Toast mensaje = Toast.makeText(MainActivity.this, "NO ENTRO REST",Toast.LENGTH_LONG);
                 mensaje.show();
             }
         });

     } catch (Exception e) {
         e.printStackTrace();
     }
 }

}