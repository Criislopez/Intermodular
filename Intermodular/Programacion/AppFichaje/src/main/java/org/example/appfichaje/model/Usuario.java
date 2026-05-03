package org.example.appfichaje.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    private int id;
    private String nombre, apellidos, correo, DNI, pass, telefono, puestoTrabajo, proyectoAsignado;
    private LocalDate fechaAlta, fechaBaja;
    private TipoJornada jornada;
    private Rol rol;

    public Usuario(String nombre, String apellido, String correo, String DNI, String pass, String telefono, LocalDate fechaAlta,TipoJornada jornada, Rol rol) {
        this.nombre = nombre;
        this.apellidos = apellido;
        this.correo = correo;
        this.DNI = DNI;
        this.pass = pass;
        this.telefono = telefono;
        this.fechaAlta = fechaAlta;
        this.jornada = jornada;
        this.rol = rol;
    }

    @Override
    public String toString() {
        return nombre + " " + apellidos;
    }

}
