package com.example.pacientes_v1.utilidadesSQL;

public class Utilidades {

    //Constantes campos usuarios
    public static final String TABLA_PACIENTES = "pacientes";
    public static final String CAMPO_ID = "id";
    public static final String CAMPO_NOMBRE = "nombre";
    public static final String CAMPO_EDAD = "edad";
    public static final String CAMPO_PADECIMIENTO = "padecimiento";

    //Query creacion tabla y campos PACIENTES
    public static final String CREAR_TABLA_PACIENTES = "CREATE TABLE " + TABLA_PACIENTES
                                                        +"(" + CAMPO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                                                        + CAMPO_NOMBRE + " TEXT, "
                                                        + CAMPO_EDAD + " TEXT, "
                                                        + CAMPO_PADECIMIENTO + " TEXT)";

}
