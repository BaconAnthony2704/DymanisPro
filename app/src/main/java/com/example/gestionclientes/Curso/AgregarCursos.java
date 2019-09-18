package com.example.gestionclientes.Curso;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.gestionclientes.MyProgressDialog;
import com.example.gestionclientes.R;

import org.json.JSONObject;

public class AgregarCursos extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener{
    EditText codigo,nombre,duracion,facilitador;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    MyProgressDialog progreso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_cursos);
        codigo=(EditText) findViewById(R.id.CodigoCurso);
        nombre=(EditText) findViewById(R.id.NomCurso);
        duracion=(EditText) findViewById(R.id.DuracionCurso);
        facilitador=(EditText) findViewById(R.id.DocenteCurso);
        request= Volley.newRequestQueue(this);
    }
    public String generarCodigo(String codigo){
        String cod=null;
        if(codigo.length()>0){
            cod=codigo.substring(0,2)+"115";
        }else{
            Toast.makeText(getApplicationContext(),"No se pudo generar el codigo",Toast.LENGTH_SHORT).show();
        }
        return cod;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.dismiss();
        Toast.makeText(getApplicationContext(),"No se pudo registrar "+error,
                Toast.LENGTH_LONG).show();
        Log.i("ERROR", error.toString());

    }

    @Override
    public void onResponse(JSONObject response) {
        progreso.dismiss();
        Toast.makeText(getApplicationContext(),"Se ha registrado correctamente",
                Toast.LENGTH_SHORT).show();
        limpiarDatos();

    }

    private void limpiarDatos() {
        codigo.setText("");
        nombre.setText("");
        duracion.setText("");
        facilitador.setText("");

    }

    public void registrarCurso(View v){
        cargarWebService();
    }

    private void cargarWebService() {
        try{
            progreso=new MyProgressDialog(this);
            progreso.show();
            String ip=getString(R.string.ip);
            String url=ip+"/ejemploBDRemota/wsJSONRegistroCurso.php?codigo="+codigo.getText().toString().trim()
                    +"&nombre="+
                    nombre.getText().toString().trim()+"&duracion="+
                    duracion.getText().toString().trim()+"&facilitador="+facilitador.getText().toString().trim();
            jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,this,this);
            request.add(jsonObjectRequest);
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "No se pudo enlaza con la BD "+e, Toast.LENGTH_SHORT).show();
        }
    }
    public void obtenerCodigo(View v){
        if(nombre.getText().toString().equals("")){
            //Toast.makeText(getApplicationContext(),"No se pudo obtener el codigo",Toast.LENGTH_SHORT).show();
            codigo.setText("Ingresar el nombre del curso");
        }else{
            codigo.setText(generarCodigo(nombre.getText().toString()));
        }
    }
}
