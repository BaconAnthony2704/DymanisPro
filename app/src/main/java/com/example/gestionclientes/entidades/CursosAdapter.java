package com.example.gestionclientes.entidades;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gestionclientes.R;

import java.util.List;

public class CursosAdapter extends RecyclerView.Adapter<CursosAdapter.CursosHolder> {
    List<Cursos> listaCursos;
    public CursosAdapter(List<Cursos> listaCursos){
        this.listaCursos=listaCursos;
    }
    @Override
    public CursosAdapter.CursosHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View vista= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.curso_list,viewGroup,false);
        RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        return new CursosHolder(vista);
    }

    @Override
    public void onBindViewHolder(CursosAdapter.CursosHolder cursosHolder, int i) {
        cursosHolder.txtNombre.setText(listaCursos.get(i).getNombre());
        cursosHolder.txtDuracion.setText(listaCursos.get(i).getDuracion());
        cursosHolder.txtFacilitador.setText(listaCursos.get(i).getFacilitador());
    }

    @Override
    public int getItemCount() {
        return listaCursos.size();
    }

    public class CursosHolder extends RecyclerView.ViewHolder {
        TextView txtNombre,txtDuracion,txtFacilitador;

        public CursosHolder(View vista) {
            super(vista);
            txtNombre=vista.findViewById(R.id.txtNombreCliente);
            txtDuracion=vista.findViewById(R.id.txtApellidoCliente);
            txtFacilitador=vista.findViewById(R.id.txtFechaCliente);
        }
    }
}
