package com.example.gestionclientes;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.gestionclientes.entidades.Usuario;
import com.example.gestionclientes.gestion.GestionAdministradorActivity;
import com.example.gestionclientes.gestion.GestionListaClienteActivity;
import com.example.gestionclientes.gestion.GestionPartnerActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {
    EditText txtusuario,txtpass;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    MyProgressDialog progreso;
    Usuario usr=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtusuario=(EditText) findViewById(R.id.txtUsuario);
        txtpass=(EditText) findViewById(R.id.txtPass);
        request= Volley.newRequestQueue(this);



    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.dismiss();
        Toast.makeText(getApplicationContext(),"No se pudo consultar "+error,
                Toast.LENGTH_LONG).show();
        Log.i("ERROR", error.toString());
    }

    @Override
    public void onResponse(JSONObject response) {
        progreso.dismiss();
        AlertDialog.Builder alerta=new AlertDialog.Builder(this);
        //Toast.makeText(getApplicationContext(),"Mensaje: "+response,Toast.LENGTH_LONG).show();
        usr=null;
        JSONArray json=response.optJSONArray("login_partner");
        JSONObject jsonObject=null;
        try {
            usr=new Usuario();
            jsonObject=json.getJSONObject(0);
                usr.setUsuario(jsonObject.optString("usuario"));
                usr.setPassword(jsonObject.optString("password"));
                usr.setNivel(jsonObject.optInt("nivel"));
                usr.setVisible(jsonObject.optInt("estado"));


        } catch (JSONException e) {
            e.printStackTrace();
        }
        try{
            if(!usr.getUsuario().equals("no registra")&& !usr.getPassword().equals("no registra") && usr.getNivel()==1){
                Intent intent=new Intent(this, GestionAdministradorActivity.class);
                startActivity(intent);
                finish();
            }else if(!usr.getUsuario().equals("no registra")&& !usr.getPassword().equals("no registra") && usr.getNivel()==3){
                Intent intent=new Intent(this, GestionPartnerActivity.class);
                startActivity(intent);
                finish();
            }
            else{
                alerta.setMessage("No tiene credenciales");
                alerta.show();
                //Toast.makeText(getApplicationContext(),"No tiene credenciales.",Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"No puede ingresar "+e,Toast.LENGTH_LONG).show();
        }

    }
    public void loginUsuario(View v){
            usr=null;
            cargarWebServicePartner();
    }
    private void cargarWebServicePartner(){
        try{
            progreso=new MyProgressDialog(this);
            //progreso.drawableHotspotChanged(0,0);
            progreso.show();
            String ip=getString(R.string.ip);
            String url=ip+"/ejemploBDRemota/wsJSONLoginPartner.php?"+
                    "usuario="+txtusuario.getText().toString().trim()+"&password="+txtpass.getText().toString().trim();
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
}
