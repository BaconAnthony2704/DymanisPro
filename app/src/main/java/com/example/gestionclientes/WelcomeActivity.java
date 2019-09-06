package com.example.gestionclientes;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }
    public void iniciarSesion(View v){
        try{
            Intent intent=new Intent(this,LoginActivity.class);
            startActivity(intent);

        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"No se puede acceder "+e,Toast.LENGTH_LONG).show();
        }
    }
    public void registrarme(View v){
        Intent intent=new Intent(this,RegistrarsePartnerActivity.class);
        startActivity(intent);
    }
    public void info(View v){
        AlertDialog.Builder alerta=new AlertDialog.Builder(this);
        alerta.setMessage("DynamisPro app\n" +
                "Dinamismo ---Dynamism\n" +
                "Pro--- Profesionalismo\n" +
                "CFP-- una institución que estimula y potencia " +
                "la creatividad del ser, con el propósito de crear un " +
                "perfil profesional competitivo con el mercado laboral actual\n" +
                "Un profesional competitivo es un agente integral que se " +
                "destaca por su eficiencia y eficacia como especialista, es creativo, proactivo y dinámico");
        alerta.show();
    }
}
