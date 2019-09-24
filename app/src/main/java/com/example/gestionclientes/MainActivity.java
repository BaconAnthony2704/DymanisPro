package com.example.gestionclientes;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.gestionclientes.entidades.Cursos;
import com.example.gestionclientes.entidades.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener, Serializable {
    Button botonRegistro;
    //ProgressDialog progreso;
    MyProgressDialog progreso;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    Spinner listaCurso;

    //Parametros para fecha
    private static final String CERO = "0";
    private static final String BARRA = "/";

    //Calendario para obtener fecha & hora
    public final Calendar c = Calendar.getInstance();

    //Variables para obtener la fecha
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);

    //Widgets
    TextView etFecha;
    EditText nombre,apellido,dui,nit;
    ImageButton ibObtenerFecha;
    ArrayList<String> listaC=new ArrayList<String>();
    ArrayAdapter<String> arrayAdapter;
    Usuario usr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etFecha = (TextView) findViewById(R.id.et_mostrar_fecha_picker);
        //Widget ImageButton del cual usaremos el evento clic para obtener la fecha
        ibObtenerFecha = (ImageButton) findViewById(R.id.ib_obtener_fecha);
        nombre=(EditText) findViewById(R.id.txtNombres);
        apellido=(EditText) findViewById(R.id.txtApellidos);
        dui=(EditText) findViewById(R.id.txtDUI);
        nit=(EditText) findViewById(R.id.txtNIT);
        listaCurso=(Spinner) findViewById(R.id.spinner);


        request= Volley.newRequestQueue(this);
        cargarCursoWebService();


    }
    private boolean validarNombre(String nombre){
        Pattern patron=Pattern.compile("^[a-zA-Z ]+$");
        return patron.matcher(nombre).matches() || nombre.length()>5;
    }
    private boolean validarApellidos(String apellido){
        Pattern patron=Pattern.compile("^[a-zA-Z ]+$");
        return patron.matcher(apellido).matches() || apellido.length()>5;
    }
    private boolean validarDUI(String dui){
        Pattern patron=Pattern.compile("^[0-9]{8}-[0-9]{1}");
        return patron.matcher(dui).matches() || dui.length()>=9;
    }
    private boolean validarNIT(String nit){
        Pattern patron=Pattern.compile("^[0-9]{4}-[0-9]{6}-[0-9]{3}-[0-9]{1}");
        return patron.matcher(nit).matches();
    }
    private void cargarCursoWebService(){
        progreso=new MyProgressDialog(this);
        progreso.show();
        String ip=getString(R.string.ip);
        String url=ip+"/ejemploBDRemota/wsJSONConsultarListaCurso.php";
        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);


    }

    private void cargarWebService() {
        try{

            String nombre=this.nombre.getText().toString().trim().toUpperCase();
            String apellido=this.apellido.getText().toString().trim().toUpperCase();
            String dui=this.dui.getText().toString().trim();
            String nit=this.nit.getText().toString().trim();
            String fecha=this.etFecha.getText().toString().trim();
            if(nombre.equals("")){
                Toast.makeText(getApplicationContext(),"Ingrese el nombre.",Toast.LENGTH_SHORT).show();
            }else if(nombre.equals("")){
                Toast.makeText(getApplicationContext(),"Ingrese el apellido.",Toast.LENGTH_SHORT).show();
            }else if(dui.equals("")){
                Toast.makeText(getApplicationContext(),"Ingrese el dui.",Toast.LENGTH_SHORT).show();
            }else if(nit.equals("")){
                Toast.makeText(getApplicationContext(),"Ingrese el nit.",Toast.LENGTH_SHORT).show();
            }else if(fecha.equals("")){
                Toast.makeText(getApplicationContext(),"Ingrese la fecha de nacimiento.",Toast.LENGTH_SHORT).show();
            }else if(nombre.equals("") && apellido.equals("") && dui.equals("")
            && nit.equals("") && fecha.equals("")){
                Toast.makeText(getApplicationContext(),"Complete los campos.",Toast.LENGTH_SHORT).show();
            }else{
                /*Toast.makeText(getApplicationContext(),"Nombre:"+nombre+"\n" +
                        "Apellido:"+apellido+"\n" +
                        "Fecha nacimiento:"+fecha+
                        "DUI:"+dui+"\n" +
                        "NIT:"+nit+"\n" +
                        "Curso: "+String.valueOf(
                        listaCurso.getSelectedItemPosition()+1),Toast.LENGTH_LONG).show();*/
                if(validarNombre(nombre)&& validarApellidos(apellido)
                && validarDUI(dui) && validarNIT(nit)){
                    progreso=new MyProgressDialog(this);
                    progreso.show();
                    String ip=getString(R.string.ip);
                    usr=(Usuario)getIntent().getExtras().getSerializable("id");
                    String id_partner=String.valueOf(usr.getId_partner());
                    String url=ip+"/ejemploBDRemota/wsJSONRegistroCliente.php?nombre="+nombre+
                            "&apellido="+apellido+"&dui="+dui+"&nit="+nit+"&fechaNacimiento="+fecha+
                            "&idCurso="+String.valueOf(listaCurso.getSelectedItemPosition()+1)+
                            "&id_partner="+id_partner;
                    jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,this,this);
                    request.add(jsonObjectRequest);
                }else{
                    Toast.makeText(getApplicationContext(),"Campos no validos",Toast.LENGTH_SHORT).show();
                }


            }

        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"No se pudo enlazar la BD "+e,Toast.LENGTH_LONG).show();
            progreso.dismiss();
        }

    }
    public void registrarCliente(View v){

        cargarWebService();
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

        try {

            if(response.optJSONArray("cliente")!=null){
                usr=(Usuario)getIntent().getExtras().getSerializable("id");
                Toast.makeText(getApplicationContext(),"Se ha ingresado con exito "+usr.getId_partner(),
                        Toast.LENGTH_LONG).show();
                limpiarDatos();
            }else{
                JSONArray json=response.optJSONArray("cursos");
                for(int i=0;i<json.length();i++){
                    JSONObject jsonObject=null;
                    jsonObject=json.getJSONObject(i);
                    listaC.add(jsonObject.optString("nombre"));
                }
                arrayAdapter = new ArrayAdapter<String>(
                        this, android.R.layout.simple_expandable_list_item_1,listaC);
                listaCurso.setAdapter(arrayAdapter);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),""+e,Toast.LENGTH_LONG).show();
        }


    }

    private void limpiarDatos() {
        this.nombre.setText("");
        this.apellido.setText("");
        this.dui.setText("");
        this.nit.setText("");
        this.etFecha.setText("Click en el boto de calendario");
    }

    public void obtenerFecha(View v){
        DatePickerDialog recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
                final int mesActual = month + 1;
                //Formateo el día obtenido: antepone el 0 si son menores de 10
                String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                //Formateo el mes obtenido: antepone el 0 si son menores de 10
                String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);
                //Muestro la fecha con el formato deseado
                etFecha.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);


            }
            //Estos valores deben ir en ese orden, de lo contrario no mostrara la fecha actual
            /**
             *También puede cargar los valores que usted desee
             */
        },anio, mes, dia);
        //Muestro el widget
        recogerFecha.show();

    }

}
