package com.example.gestionclientes.gestion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.gestionclientes.BorrarActivity;
import com.example.gestionclientes.ConsultarCliente;
import com.example.gestionclientes.EditClientes;
import com.example.gestionclientes.MainActivity;
import com.example.gestionclientes.R;

import static android.widget.AdapterView.OnItemClickListener;

public class GestionListaClienteActivity extends AppCompatActivity {
    private ListView list;
    private String[] activities={"Ingresar Cliente","Modificar Cliente", "Borrar Cliente"," Consultar Cliente"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_lista_cliente);
        list=(ListView) findViewById(R.id.listaView);
        ArrayAdapter<String> adaptador=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,activities);
        list.setAdapter(adaptador);
        list.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(),"Ha pulsado el item: "+position,Toast.LENGTH_LONG).show();
                switch (position) {
                    case 0:
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        //finish();
                        //onBackPressed(); Lo comenté porque es medio raro que me envie a dos activity atras
                        break;
                    case 1:
                        Intent intent1 = new Intent(getApplicationContext(), EditClientes.class);
                        startActivity(intent1);
                        break;
                    case 2:
                        Intent i = new Intent(getApplicationContext(), BorrarActivity.class);
                        startActivity(i);
                        break;
                    case 3:
                        Intent e = new Intent(getApplicationContext(), ConsultarCliente.class);
                        startActivity(e);
                        break;
                }
            }
        });
    }
}
