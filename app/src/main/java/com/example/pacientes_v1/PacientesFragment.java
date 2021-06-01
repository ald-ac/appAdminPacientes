package com.example.pacientes_v1;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.pacientes_v1.adaptadores.MyAdaptadorListView;
import com.example.pacientes_v1.objetos.Paciente;
import com.example.pacientes_v1.sql.ConexionSQLiteHelper;
import com.example.pacientes_v1.utilidadesSQL.Utilidades;

import java.util.ArrayList;

public class PacientesFragment extends Fragment {

    //Elementos de ui y objetos
    MyAdaptadorListView adaptador;
    ListView listView;
    ArrayList<Paciente> pacientes;

    ConexionSQLiteHelper con;

    public PacientesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true); //Establecer que hay un optionsmenu
        super.onCreate(savedInstanceState);
    }

    //Options Menu
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.borrar_todo_pacientes:
                //Eliminar pacientes de la BD
                eliminarPacientes();
                //Limpiar arreglo pacienes y refrescar listView con adaptador
                pacientes.clear();
                adaptador.notifyDataSetChanged();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_pacientes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Conexion BD
        con = new ConexionSQLiteHelper(getActivity(), "bd_pacientes", null, 1);

        //Ubicar listView
        listView = (ListView) view.findViewById(R.id.listViewPacientes);

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
                bundle.putString("nombre",pacientes.get(position).getNombre());
                bundle.putString("edad",pacientes.get(position).getEdad());
                bundle.putString("padecimiento",pacientes.get(position).getPadecimiento());
                Navigation.findNavController(view).navigate(R.id.agregarFragment, bundle);
            }
        });
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = new MenuInflater(getActivity());
        inflater.inflate(R.menu.main_context_menu, menu);

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo informacion = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.borrar_paciente:
                //Obtener id del objeto seleccionado con base en la posicion del menu -> array de pacientes
                eliminarPaciente(pacientes.get(informacion.position).getId());
                pacientes.remove(informacion.position);
                adaptador.notifyDataSetChanged();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
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

    public void eliminarPaciente(int idPaciente) {
        SQLiteDatabase db = con.getWritableDatabase();

        String eliminarPaciente = "DELETE FROM " + Utilidades.TABLA_PACIENTES
                + " WHERE " + Utilidades.CAMPO_ID
                + "=" + idPaciente+"";
        db.execSQL(eliminarPaciente);
        db.close();
    }

    public void eliminarPacientes() {
        ConexionSQLiteHelper con = new ConexionSQLiteHelper(getActivity(), "bd_pacientes", null, 1);

        SQLiteDatabase db = con.getWritableDatabase();

        String eliminarPacientes = "DELETE FROM " + Utilidades.TABLA_PACIENTES;
        db.execSQL(eliminarPacientes);
        db.close();
    }
}