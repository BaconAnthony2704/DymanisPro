package com.example.gestionclientes.gestion;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.gestionclientes.LoginActivity;
import com.example.gestionclientes.MainActivity;
import com.example.gestionclientes.R;

public class GestionAdministradorActivity extends AppCompatActivity {
    private ListView list;


    private String[] activities={"Gestionar Cliente","Gestionar Cursos", "Gestionar Credenciales","Gestionar Promociones","Cerrar Sesion"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final AlertDialog.Builder alerta=new AlertDialog.Builder(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_administrador);
        list=(ListView) findViewById(R.id.listaGestion);
        ArrayAdapter<String> adaptador=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,activities);
        list.setAdapter(adaptador);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                //Toast.makeText(getApplicationContext(),"Ha pulsado el item: "+position,Toast.LENGTH_LONG).show();
                switch (position){
                    case 0:
                        Intent intent=new Intent(getApplicationContext(), GestionListaClienteActivity.class);
                        startActivity(intent);
                        break;
                    case 4:
                        alerta.setMessage("Sesion finalizada");
                        alerta.show();
                        LoginActivity.cambiarEstadoBtn(GestionAdministradorActivity.this,false);
                        LoginActivity.cambiarEstadoLvl(GestionAdministradorActivity.this,0);
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
