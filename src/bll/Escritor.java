package bll;

import enums.EstadoUsuario;
import enums.Rol;

import java.time.LocalDateTime;

public class Escritor extends Usuario {

    //Constructores☆゜・。。・゜゜・。。・゜★゜・。。・゜゜・。。・゜☆゜・。。・゜゜・。。・゜★
    public Escritor() {
    }

    public Escritor(String nombre, String apellido, String dni, String celular, String mail, String nombreUsuario, String contrasena, LocalDateTime fechaRegistro, EstadoUsuario estado) {
        super(Rol.ESCRITOR, nombre, apellido, dni, celular, mail, nombreUsuario, contrasena, fechaRegistro, estado);
    }

    public Escritor(int id, Rol rol, String nombre, String apellido, String dni, String celular, String mail, String nombreUsuario, String contrasena, LocalDateTime fechaRegistro, EstadoUsuario estado) {
        super(id, Rol.ESCRITOR, nombre, apellido, dni, celular, mail, nombreUsuario, contrasena, fechaRegistro, estado);
    }

}