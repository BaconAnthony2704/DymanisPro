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
import com.example.gestionclientes.R;

public class GestionPartnerActivity extends AppCompatActivity {
    private ListView list;

    private String[] activities={"Gestionar Cliente","Cerrar Sesion"};
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
                        Intent intent=new Intent(getApplicationContext(), GestionListaClienteActivity.class);
                        startActivity(intent);
                        break;
                    case 1:

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
