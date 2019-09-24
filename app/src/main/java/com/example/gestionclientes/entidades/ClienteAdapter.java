package com.example.gestionclientes.entidades;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gestionclientes.R;

import java.util.List;

public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.ClienteHolder> {
    List<Cliente> listaCliente;
    public ClienteAdapter(List<Cliente> listaCliente){

        this.listaCliente=listaCliente;
    }
    @Override
    public ClienteAdapter.ClienteHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View vista= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cliente_list,viewGroup,false);
        RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        return new ClienteHolder(vista);
    }

    @Override
    public void onBindViewHolder(ClienteAdapter.ClienteHolder clienteHolder, int i) {
        clienteHolder.txtNombre.setText(listaCliente.get(i).getNombre());
        clienteHolder.txtApellido.setText(listaCliente.get(i).getApellido());
        clienteHolder.txtDui.setText(listaCliente.get(i).getDui());
        clienteHolder.txtNit.setText(listaCliente.get(i).getNit());
        clienteHolder.txtCurso.setText(listaCliente.get(i).getCurso());
        clienteHolder.txtFecha.setText(listaCliente.get(i).getFecha());

    }

    @Override
    public int getItemCount() {
        return listaCliente.size();
    }

    public class ClienteHolder extends RecyclerView.ViewHolder {
        TextView txtNombre,txtApellido,txtFecha,txtDui,txtNit,txtCurso;
        public ClienteHolder(View vista) {
            super(vista);
            txtNombre=vista.findViewById(R.id.txtNombreCliente);
            txtApellido=vista.findViewById(R.id.txtApellidoCliente);
            txtFecha=vista.findViewById(R.id.txtFechaCliente);
            txtDui=vista.findViewById(R.id.txtDuiCliente);
            txtNit=vista.findViewById(R.id.txtNitCliente);
            txtCurso=vista.findViewById(R.id.txtCursoCliente);
        }
    }
}
