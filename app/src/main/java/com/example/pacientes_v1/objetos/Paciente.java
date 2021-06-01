package com.example.pacientes_v1.objetos;

public class Paciente {
    private int id;
    private String nombre;
    private String edad;
    private String padecimiento;

    public Paciente(int id, String nombre, String edad, String padecimiento) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.padecimiento = padecimiento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getPadecimiento() {
        return padecimiento;
    }

    public void setPadecimiento(String padecimiento) {
        this.padecimiento = padecimiento;
    }
}
