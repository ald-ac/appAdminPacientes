package com.example.pacientes_v1.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pacientes_v1.R;
import com.example.pacientes_v1.objetos.Paciente;

import java.util.ArrayList;
//Establecer que extiende de un ArrayAdapter<Paciente>
public class MyAdaptadorListView extends ArrayAdapter<Paciente> {
    Context context;
    ArrayList<Paciente> Pacientes;

    public MyAdaptadorListView(Context c, ArrayList<Paciente> pacientes) {
        super(c, R.layout.row_list_view, R.id.textView1, pacientes);
        this.context = c;
        this.Pacientes = pacientes;
    }

    @NonNull
    @Override //Renderizando vista con row_list_view.xml
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.row_list_view, parent, false);
        TextView miNombre = row.findViewById(R.id.textView1);
        TextView miEdad = row.findViewById(R.id.textView2);
        TextView miPadecimiento = row.findViewById(R.id.textView3);

        miNombre.setText(Pacientes.get(position).getNombre());
        miEdad.setText(Pacientes.get(position).getEdad());
        miPadecimiento.setText(Pacientes.get(position).getPadecimiento());

        return row;
    }
}
