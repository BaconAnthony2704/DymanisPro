package com.example.gestionclientes.gestion;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.gestionclientes.LoginActivity;
import com.example.gestionclientes.MainActivity;
import com.example.gestionclientes.R;
import com.example.gestionclientes.entidades.Usuario;

import java.io.Serializable;

public class GestionPartnerActivity extends AppCompatActivity implements Serializable {
    private ListView list;

    private String[] activities={"Ingresar Cliente","Mostrar Cursos disponibles","Mostrar mis clientes","Cerrar Sesion"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final AlertDialog.Builder alerta=new AlertDialog.Builder(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_partner);
        list=(ListView) findViewById(R.id.listaGestion);
        ArrayAdapter<String> adaptador=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,activities);
        list.setAdapter(adaptador);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                //Toast.makeText(getApplicationContext(),"Ha pulsado el item: "+position,Toast.LENGTH_LONG).show();
                switch (position){
                    case 0:
                        Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                        Usuario usr=(Usuario)getIntent().getExtras().getSerializable("id");
                        intent.putExtra("id",usr);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent1=new Intent(getApplicationContext(),MostrarCursoActivity.class);
                        startActivity(intent1);
                        break;
                    case 2:
                        Intent intent2=new Intent(getApplicationContext(),MostrarClienteActivity.class);
                        Usuario usr1=(Usuario)getIntent().getExtras().getSerializable("id");
                        intent2.putExtra("id",usr1);
                        startActivity(intent2);
                        break;
                    case 3:

                        alerta.setMessage("Sesion finalizada");
                        alerta.show();
                        LoginActivity.cambiarEstadoBtn(GestionPartnerActivity.this,false);
                        LoginActivity.cambiarEstadoLvl(GestionPartnerActivity.this,0);
                        Intent intent4=new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent4);
                        //onBackPressed();
                        finish();
                        break;
                }
            }
        });
    }
}
