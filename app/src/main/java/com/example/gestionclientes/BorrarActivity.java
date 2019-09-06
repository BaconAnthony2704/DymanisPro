package com.example.gestionclientes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class BorrarActivity extends AppCompatActivity {
    EditText codCliente;
    TextView elim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrar);
        codCliente=(EditText)findViewById(R.id.codCliente);
        elim=(TextView)findViewById(R.id.textElim);
        elim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///EL TEXT VIEW LO HICE BOTON
            }
        });
    }

}
