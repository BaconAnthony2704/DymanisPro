package com.example.gestionclientes.Curso;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.gestionclientes.R;

public class GestionCurso extends AppCompatActivity {
    private ListView list;
    private String[] activities={"Ingresar Cursos","Modificar Curso", "Borrar Cursos"," Consultar Cursos"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_curso);
    }
}
