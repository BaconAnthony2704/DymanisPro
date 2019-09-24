package com.example.gestionclientes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
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
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class LoginActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener, Serializable {
    EditText txtusuario,txtpass;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    MyProgressDialog progreso;
    Usuario usr=null;
    RadioButton RSesion;
    boolean isActivateRadioButton;
    public static final String Preference_Estado_Button="estado.button";
    public static final String String_Preferences="SharedButton";
    public static final String Preference_Estado_Lvl="estado.lvl";
    public static final String String_Preferences_Lvl="SharedLevel";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (obtenerEstadoBtn()== true && getLvl()==1){
            Intent i = new Intent(LoginActivity.this,GestionAdministradorActivity.class);
            startActivity(i);
            finish();
        }else if(obtenerEstadoBtn()== true && getLvl()==3){
            Intent i = new Intent(LoginActivity.this,GestionPartnerActivity.class);
            Gson gson=new Gson();
            SharedPreferences  mPrefs = getPreferences(MODE_PRIVATE);
            String json=mPrefs.getString("usr","");
            Usuario usr=gson.fromJson(json,Usuario.class);
            i.putExtra("id",usr);
            startActivity(i);
            finish();
        }
        txtusuario=(EditText) findViewById(R.id.txtUsuario);
        txtpass=(EditText) findViewById(R.id.txtPass);
        request= Volley.newRequestQueue(this);

        RSesion= (RadioButton) findViewById(R.id.RBSecion);
        isActivateRadioButton=RSesion.isChecked();
        RSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isActivateRadioButton){
                    RSesion.setChecked(false);
                }
                isActivateRadioButton=RSesion.isChecked();
            }
        });

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
        //usr=null;
        JSONArray json=response.optJSONArray("login_partner");
        JSONObject jsonObject=null;

        try {
            usr=new Usuario();
            jsonObject=json.getJSONObject(0);
                usr.setUsuario(jsonObject.optString("usuario"));
                usr.setPassword(jsonObject.optString("password"));
                usr.setNivel(jsonObject.optInt("nivel"));
                usr.setVisible(jsonObject.optInt("estado"));
                usr.setId_partner(jsonObject.optInt("id_partner"));



        } catch (JSONException e) {
            e.printStackTrace();
        }
        try{
            if(!usr.getUsuario().equals("no registra")&& !usr.getPassword().equals("no registra") && usr.getNivel()==1){
                guardarEstadoBtn();
                guardarEstadoLvl(1);
                Intent intent=new Intent(this, GestionAdministradorActivity.class);
                startActivity(intent);
                finish();
            }else if(!usr.getUsuario().equals("no registra")&& !usr.getPassword().equals("no registra") && usr.getNivel()==3){
                guardarEstadoBtn();
                guardarEstadoLvl(3);
                guardarUsuario();
                Intent intent=new Intent(this, GestionPartnerActivity.class);
                intent.putExtra("id",usr);
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

    private void guardarUsuario() {
        SharedPreferences mPrefs=getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(usr);
        prefsEditor.putString("usr", json);
        prefsEditor.commit();
    }


    public void loginUsuario(View v){
        //usr=null;
        if (this.txtusuario.getText().toString().equals("") && this.txtpass.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(),"Debe completar los campos",Toast.LENGTH_SHORT).show();
        }
        else if(this.txtusuario.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"Debe ingresar su usuario",Toast.LENGTH_SHORT).show();
        }else if(this.txtpass.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"Debe ingresar su contraseña",Toast.LENGTH_SHORT).show();
        } else{
            cargarWebServicePartner();
        }

    }

    private void cargarWebServicePartner(){
        try{
            progreso=new MyProgressDialog(this);
            //progreso.drawableHotspotChanged(0,0);
            progreso.show();
            String ip=getString(R.string.ip);
            String url=ip+"/ejemploBDRemota/wsJSONLoginPartner.php?"+
                    "usuario="+txtusuario.getText().toString().trim()+"&password="+txtpass.getText().toString().trim();
            jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,this,this);
            request.add(jsonObjectRequest);
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"No se pudo enlazar la BD "+e,Toast.LENGTH_LONG).show();
            progreso.dismiss();
        }
    }
    public void registrarme(View v){
        Intent i=new Intent(this,RegistrarsePartnerActivity.class);
        startActivity(i);

    }

    public void guardarEstadoBtn(){
        SharedPreferences preferences= getSharedPreferences(String_Preferences,MODE_PRIVATE);
        preferences.edit().putBoolean(Preference_Estado_Button,RSesion.isChecked()).apply();
    }
    public boolean obtenerEstadoBtn(){
        SharedPreferences preferences= getSharedPreferences(String_Preferences,MODE_PRIVATE);
        return preferences.getBoolean(Preference_Estado_Button,false);
    }
    public static void cambiarEstadoBtn(Context c , boolean b){
        SharedPreferences preferences= c.getSharedPreferences(String_Preferences,MODE_PRIVATE);
        preferences.edit().putBoolean(Preference_Estado_Button,b).apply();
    }
    public Integer getLvl(){
        SharedPreferences preferences= getSharedPreferences(String_Preferences_Lvl,MODE_PRIVATE);
        Integer i = preferences.getInt(Preference_Estado_Lvl,0);
        return i;
    }
    public static void cambiarEstadoLvl(Context c , Integer num){
        SharedPreferences preferences= c.getSharedPreferences(String_Preferences_Lvl,MODE_PRIVATE);
        preferences.edit().putInt(Preference_Estado_Lvl,num).apply();
    }
    public void guardarEstadoLvl(Integer lvl) {
        SharedPreferences preferences = getSharedPreferences(String_Preferences_Lvl, MODE_PRIVATE);
        preferences.edit().putInt(Preference_Estado_Lvl, lvl).apply();
    }


}
