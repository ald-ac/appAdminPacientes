package com.example.pacientes_v1;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pacientes_v1.sql.ConexionSQLiteHelper;
import com.example.pacientes_v1.utilidadesSQL.Utilidades;

public class AgregarFragment extends Fragment {

    //Componentes ui
    EditText etx_nombre, etx_edad, etx_padecimiento;
    Button btn_agregar;

    public AgregarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_agregar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        etx_nombre = (EditText)view.findViewById(R.id.etx_nombre);
        etx_edad = (EditText)view.findViewById(R.id.etx_edad);
        etx_padecimiento = (EditText)view.findViewById(R.id.etx_padecimiento);

        //SI SE LLEGA AQUI DESDE LIST VIEW ITEM
        if(getArguments() != null) {
            String nombre, edad, padecimiento;
            nombre = getArguments().getString("nombre");
            edad = getArguments().getString("edad");
            padecimiento = getArguments().getString("padecimiento");

            etx_nombre.setText(nombre);
            etx_edad.setText(edad);
            etx_padecimiento.setText(padecimiento);
        }

        btn_agregar = (Button)view.findViewById(R.id.btn_agregar);

        btn_agregar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Validar editText
                if(etx_nombre.getText().toString().isEmpty() || etx_edad.getText().toString().isEmpty() || etx_padecimiento.getText().toString().isEmpty())
                {
                    Toast.makeText(getActivity(), "ERROR: Existen campos vacios", Toast.LENGTH_LONG).show();
                } else {
                    agregarPaciente();
                    limpiarEditText();
                }
            }
        });
    }

    //METODO AGREGAR PACIENTE EN DB
    public void agregarPaciente() {
        ConexionSQLiteHelper con = new ConexionSQLiteHelper(getActivity(), "bd_pacientes", null, 1);

        SQLiteDatabase db = con.getWritableDatabase();

        String agregarPaciente = "INSERT INTO " + Utilidades.TABLA_PACIENTES
                                                + "(" + Utilidades.CAMPO_NOMBRE
                                                + ", " + Utilidades.CAMPO_EDAD
                                                + ", " + Utilidades.CAMPO_PADECIMIENTO
                                                + ") VALUES ( "
                                                + "'" + etx_nombre.getText().toString() + "',"
                                                + "'" + etx_edad.getText().toString() + "',"
                                                + "'" +etx_padecimiento.getText().toString()
                                                + "')";
        db.execSQL(agregarPaciente);
        db.close();
    }

    public void limpiarEditText() {
        etx_nombre.setText("");
        etx_edad.setText("");
        etx_padecimiento.setText("");
    }
}