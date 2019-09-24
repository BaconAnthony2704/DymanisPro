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
import com.example.gestionclientes.entidades.Cursos;
import com.example.gestionclientes.entidades.CursosAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MostrarCursoActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{
    RecyclerView recyclerCurso;
    ArrayList<Cursos> listaCursos;
    MyProgressDialog progreso;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_curso);
        listaCursos=new ArrayList<>();
        recyclerCurso=(RecyclerView) findViewById(R.id.idRecycler);
        recyclerCurso.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        recyclerCurso.setHasFixedSize(true);
        request= Volley.newRequestQueue(this);
        cargarWebService();

    }

    private void cargarWebService() {
        progreso=new MyProgressDialog(this);
        progreso.show();
        String ip=getString(R.string.ip);
        String url=ip+"/ejemploBDRemota/wsJSONConsultarListaCurso.php";
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
        Cursos curso=null;
        try {
        JSONArray json=response.optJSONArray("cursos");
        for(int i=0;i<json.length();i++){
            curso=new Cursos();
            JSONObject jsonObject=null;
                jsonObject=json.getJSONObject(i);
                curso.setNombre(jsonObject.optString("nombre"));
                curso.setDuracion(jsonObject.optString("duracion"));
                curso.setFacilitador(jsonObject.optString("facilitador"));
                listaCursos.add(curso);
            }
        progreso.dismiss();
            CursosAdapter adapter=new CursosAdapter(listaCursos);
            recyclerCurso.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"No se pudo establecer la conexion "+e,Toast.LENGTH_SHORT).show();
        }

    }
}
