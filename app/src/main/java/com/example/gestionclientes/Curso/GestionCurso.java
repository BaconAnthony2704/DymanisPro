package com.example.gestionclientes.Curso;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.gestionclientes.BorrarActivity;
import com.example.gestionclientes.ConsultarCliente;
import com.example.gestionclientes.EditClientes;
import com.example.gestionclientes.MainActivity;
import com.example.gestionclientes.R;

public class GestionCurso extends AppCompatActivity {
    private ListView list;
    private String[] activities={"Ingresar Cursos","Modificar Curso", "Borrar Cursos"," Consultar Cursos"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_curso);
        list=(ListView) findViewById(R.id.listaView);
        ArrayAdapter<String> adaptador=new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,activities);
        list.setAdapter(adaptador);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(),"Ha pulsado el item: "+position,Toast.LENGTH_LONG).show();
                switch (position) {
                    case 0:
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                }
            }
        });
    }
}
