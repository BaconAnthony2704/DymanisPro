package com.example.gestionclientes;

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

import org.json.JSONObject;

public class RegistrarsePartnerActivity extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener{
    EditText txtusuario,txtnombre,txtcontra,txtconficontra;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    MyProgressDialog progreso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse_partner);
        txtnombre=(EditText) findViewById(R.id.txtnombrepartner);
        txtusuario=(EditText) findViewById(R.id.txtusuariopartner);
        txtcontra=(EditText) findViewById(R.id.txtcontrapartner);
        txtconficontra=(EditText) findViewById(R.id.txtconficontrapartner);
        request= Volley.newRequestQueue(this);

    }

    public void registrarme(View v){
        if(txtcontra.getText().toString().equals(txtconficontra.getText().toString())){
            cargarWebService();
        }
        else{
            Toast.makeText(getApplicationContext(),"La contraseña debe coincidir",Toast.LENGTH_LONG).show();
        }

    }

    private void cargarWebService() {
        try{
            progreso=new MyProgressDialog(this);
            //progreso.drawableHotspotChanged(0,0);
            progreso.show();


            String ip=getString(R.string.ip);
            String url=ip+"/ejemploBDRemota/wsJSONRegistroPartner.php?nombre="+txtnombre.getText().toString().trim()+
                    "&usuario="+txtusuario.getText().toString().trim()+"&password="+txtcontra.getText().toString().trim();
            //url=url.replace(" ","%20");

            jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,this,this);
            request.add(jsonObjectRequest);
           /*Toast.makeText(getApplicationContext(),txtnombre.getText().toString().trim()+"\n" +
                   txtusuario.getText().toString().trim()+"\n" +
                   txtcontra.getText().toString().trim()+"\n" +
                   txtconficontra.getText().toString().trim(),Toast.LENGTH_LONG).show();*/
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"No se pudo enlazar la BD "+e,Toast.LENGTH_LONG).show();
        }

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
        txtusuario.setText("");
        txtnombre.setText("");
        txtcontra.setText("");
        txtconficontra.setText("");
    }
}
