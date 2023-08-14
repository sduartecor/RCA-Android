package com.utec.pft.pages.menus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.utec.pft.R;
import com.utec.pft.entites.Usuario;

public class PerfilUsuario extends AppCompatActivity {

    Usuario usuarioIngresado;
    TextView nickname;
    TextView rol;
    Button volverMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        Bundle intent = getIntent().getExtras();
        nickname = (TextView) findViewById(R.id.txtNicknameUser);
        rol = (TextView) findViewById(R.id.txtRolUser);

        if (intent!=null){
            usuarioIngresado = (Usuario) intent.get("usuario");
            nickname.setText(usuarioIngresado.getNickname());
            rol.setText(usuarioIngresado.getRol().name());
        }

        volverMenu = (Button) findViewById(R.id.bttnVolverMenu);

        volverMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PerfilUsuario.this, Menu.class);
                intent.putExtra("usuario", usuarioIngresado);
                startActivity(intent);
            }
        });

    }
}
