package com.example.font_segundo_parcial.ui;

import static java.util.Objects.isNull;

import android.content.Intent;
import android.os.Bundle;

import com.example.font_segundo_parcial.MainActivity;
import com.example.font_segundo_parcial.api.Datos;
import com.example.font_segundo_parcial.api.Persona;
import com.example.font_segundo_parcial.api.RetrofitUtil;
import com.example.font_segundo_parcial.databinding.ActivityLogin2Binding;
import com.example.font_segundo_parcial.ui.home.HomeFragment;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.font_segundo_parcial.R;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class login extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityLogin2Binding binding;
    EditText usuario;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLogin2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        usuario=findViewById(R.id.textNombreUsuario);
        password=findViewById(R.id.textPassword);

    }

    public void btnEventoIngresar(View v) {

        Call<Datos<Persona>> callApi = null;
        try {
            callApi = RetrofitUtil.getUsuarioService().getPersonas(new JSONObject("{soloUsuariosDelSistema:true}"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        callApi.enqueue(new Callback<Datos<Persona>>() {
            @Override
            public void onResponse(Call<Datos<Persona>> call, Response<Datos<Persona>> response) {
                Persona[] arrayUsuarios = response.body().getLista();
                String user= usuario.getText().toString();
                Boolean bandera =true;
                for( int i=0; i<arrayUsuarios.length;i++)
                {
                    if(!isNull( arrayUsuarios[i].getUsuarioLogin())) {
                        if (user.equalsIgnoreCase(arrayUsuarios[i].getUsuarioLogin().toString())) {
                            Intent principalIntent = new Intent(v.getContext (), MainActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("usuario", usuario.getText().toString());
                            principalIntent.putExtras(bundle);
                            startActivity(principalIntent);
                            bandera= false;
                        }
                    }
                }
                if(bandera) {
                    Toast.makeText(v.getContext(), R.string.credencialNoValida, Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Datos<Persona>> call, Throwable t) {
                Log.e("s", "Fallido");
            }
        });
    }}
