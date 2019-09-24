package com.example.gestionclientes.gestion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.gestionclientes.MyProgressDialog;
import com.example.gestionclientes.R;
import com.example.gestionclientes.entidades.Cliente;
import com.example.gestionclientes.entidades.ClienteAdapter;
import com.example.gestionclientes.entidades.Cursos;
import com.example.gestionclientes.entidades.CursosAdapter;
import com.example.gestionclientes.entidades.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MostrarClienteActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{
    RecyclerView recyclerCliente;
    ArrayList<Cliente> listaCliente;
    MyProgressDialog progreso;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    Usuario usr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_cliente);
        listaCliente=new ArrayList<>();
        recyclerCliente=(RecyclerView) findViewById(R.id.idReclyclerCliente);
        recyclerCliente.setLayoutManager(new LinearLayoutManager(this));
        recyclerCliente.setHasFixedSize(true);
        request= Volley.newRequestQueue(this);
        cargarWebService();
    }

    private void cargarWebService() {
        usr=(Usuario)getIntent().getExtras().getSerializable("id");
        String id_partner=String.valueOf(usr.getId_partner());
        progreso=new MyProgressDialog(this);
        progreso.show();
        String ip=getString(R.string.ip);
        String url=ip+"/ejemploBDRemota/wsJSONConsultarListaCliente.php?id_partner="+id_partner;
        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.dismiss();
        Toast.makeText(getApplicationContext(),"No se pudo conectar "+error,Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onResponse(JSONObject response) {
        Cliente cliente=null;
        try {
            JSONArray json=response.optJSONArray("cliente");
            for(int i=0;i<json.length();i++){
                cliente=new Cliente();
                JSONObject jsonObject=null;
                jsonObject=json.getJSONObject(i);
                cliente.setNombre(jsonObject.optString("nombre"));
                cliente.setApellido(jsonObject.optString("apellido"));
                cliente.setFecha(jsonObject.optString("fechaNacimiento"));
                cliente.setDui(jsonObject.optString("dui"));
                cliente.setNit(jsonObject.optString("nit"));
                cliente.setCurso(jsonObject.getString("cursos"));
                listaCliente.add(cliente);
            }
            progreso.dismiss();
            ClienteAdapter adapter=new ClienteAdapter(listaCliente);
            recyclerCliente.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"No se pudo establecer la conexion "+e,Toast.LENGTH_SHORT).show();
        }

    }
}
