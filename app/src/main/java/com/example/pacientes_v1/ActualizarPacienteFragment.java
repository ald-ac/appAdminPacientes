package com.example.pacientes_v1;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pacientes_v1.sql.ConexionSQLiteHelper;
import com.example.pacientes_v1.utilidadesSQL.Utilidades;

public class ActualizarPacienteFragment extends Fragment {

    //Componentes ui
    EditText etx_nombre, etx_edad, etx_padecimiento;
    Button btn_actualizar;
    //id del objeto recibido seleccionado para actualizar SQL
    int id=0;

    public ActualizarPacienteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_actualizar_paciente, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etx_nombre = (EditText)view.findViewById(R.id.etx_nombreAct);
        etx_edad = (EditText)view.findViewById(R.id.etx_edadAct);
        etx_padecimiento = (EditText)view.findViewById(R.id.etx_padecimientoAct);

        //SI SE LLEGA AQUI DESDE LIST VIEW ITEM
        if(getArguments() != null) {
            String nombre, edad, padecimiento;
            id = getArguments().getInt("id");
            nombre = getArguments().getString("nombre");
            edad = getArguments().getString("edad");
            padecimiento = getArguments().getString("padecimiento");

            etx_nombre.setText(nombre);
            etx_edad.setText(edad);
            etx_padecimiento.setText(padecimiento);
        }

        btn_actualizar = (Button)view.findViewById(R.id.btn_actualizar);

        btn_actualizar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(etx_nombre.getText().toString().isEmpty() || etx_edad.getText().toString().isEmpty() || etx_padecimiento.getText().toString().isEmpty())
                {
                    Toast.makeText(getActivity(), "ERROR: Existen campos vacios", Toast.LENGTH_LONG).show();
                } else {
                    //Actualizar en SQL
                    actualizarPaciente();
                    //Redirigir actualizar pacientes
                    Navigation.findNavController(view).navigate(R.id.actualizarPacientes);
                }
            }
        });
    }

    public void actualizarPaciente() {
        ConexionSQLiteHelper con = new ConexionSQLiteHelper(getActivity(), "bd_pacientes", null, 1);

        SQLiteDatabase db = con.getWritableDatabase();

        String actualizarPaciente = "UPDATE " + Utilidades.TABLA_PACIENTES
                + " SET " + Utilidades.CAMPO_NOMBRE + "='" + etx_nombre.getText().toString() + "'"
                + ", " + Utilidades.CAMPO_EDAD + "='" + etx_edad.getText().toString() + "'"
                + ", " + Utilidades.CAMPO_PADECIMIENTO + "='" + etx_padecimiento.getText().toString() + "'"
                + " WHERE " + Utilidades.CAMPO_ID + "=" + id;
        db.execSQL(actualizarPaciente);
        db.close();
    }
}