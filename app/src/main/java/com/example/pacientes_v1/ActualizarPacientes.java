package com.example.pacientes_v1;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.pacientes_v1.adaptadores.MyAdaptadorListView;
import com.example.pacientes_v1.objetos.Paciente;
import com.example.pacientes_v1.sql.ConexionSQLiteHelper;
import com.example.pacientes_v1.utilidadesSQL.Utilidades;

import java.util.ArrayList;

public class ActualizarPacientes extends Fragment {

    //Elementos de ui y objetos
    MyAdaptadorListView adaptador;
    ListView listView;
    ArrayList<Paciente> pacientes;

    ConexionSQLiteHelper con;

    public ActualizarPacientes() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_actualizar_pacientes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Conexion BD
        con = new ConexionSQLiteHelper(getActivity(), "bd_pacientes", null, 1);

        //Ubicar listView
        listView = (ListView) view.findViewById(R.id.listViewPacientesAct);

        //Consulta de pacientes a la BD
        consultaPacientes();

        //Establecer adaptador
        adaptador = new MyAdaptadorListView(getActivity(), pacientes);
        listView.setAdapter(adaptador);

        //Establecer contextMenu para eliminar
        registerForContextMenu(listView);

        //Establecer click del listView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Bundle bundle = new Bundle();
                bundle.putInt("id",pacientes.get(position).getId());
                bundle.putString("nombre",pacientes.get(position).getNombre());
                bundle.putString("edad",pacientes.get(position).getEdad());
                bundle.putString("padecimiento",pacientes.get(position).getPadecimiento());
                Navigation.findNavController(view).navigate(R.id.actualizarPacienteFragment, bundle);
            }
        });
    }
    //INSTRUCCIONES SQL
    public void consultaPacientes() {
        SQLiteDatabase db = con.getReadableDatabase();
        int id;
        String nombre, edad, padecimiento;
        //Arreglo pacientes
        pacientes = new ArrayList<>();

        Cursor cursor = db. rawQuery("SELECT * FROM " + Utilidades.TABLA_PACIENTES, null);

        //Llenar array de pacientes
        while(cursor.moveToNext()) {
            id = cursor.getInt(0);
            nombre = cursor.getString(1);
            edad = cursor.getString(2);
            padecimiento = cursor.getString(3);
            pacientes.add(new Paciente(id, nombre, edad, padecimiento));
        }
    }
}