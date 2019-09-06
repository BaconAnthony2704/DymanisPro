package com.example.gestionclientes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class EditClientes extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener {
    EditText campoNombre,campoDocumento,campoProfesion;
    Button botonEditar;
    //ProgressDialog progreso;
    ProgressBar progreso;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_clientes);
        campoDocumento=(EditText) findViewById(R.id.txtDocumento);
        campoNombre=(EditText) findViewById(R.id.txtnombre);
        campoProfesion=(EditText) findViewById(R.id.txtProfesion);
        request= Volley.newRequestQueue(this);
        botonEditar=(Button)findViewById(R.id.buttonEdit);


    }


    private void cargarWebService() {
        try{
            progreso=new ProgressBar(getApplicationContext());
            //progreso.drawableHotspotChanged(0,0);
            progreso.setVisibility(View.VISIBLE);


            String ip=getString(R.string.ip);
            String url=ip+"/ejemploBDRemota/wsJSONRegistro.php?documento="+campoDocumento.getText().toString()+
                    "&nombre="+campoNombre.getText().toString()+"&profesion="+campoProfesion.getText().toString();
            //url=url.replace(" ","%20");

            jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,this,this);
            request.add(jsonObjectRequest);
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"No se pudo enlazar la BD "+e,Toast.LENGTH_LONG).show();
        }

    }
    public void registrarCliente(View v){
        cargarWebService();

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.setVisibility(View.INVISIBLE);
        Toast.makeText(getApplicationContext(),"No se pudo registrar "+error,
                Toast.LENGTH_LONG).show();
        Log.i("ERROR", error.toString());

    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(getApplicationContext(),"Se ha registrado correctamente",
                Toast.LENGTH_SHORT).show();
        progreso.setVisibility(View.INVISIBLE);
        campoDocumento.setText("");
        campoNombre.setText("");
        campoProfesion.setText("");
    }
}
