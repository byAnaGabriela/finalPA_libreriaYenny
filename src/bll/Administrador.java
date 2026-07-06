package bll;

import enums.EstadoUsuario;
import enums.Rol;

import java.time.LocalDateTime;

public class Administrador extends Usuario {

    public Administrador() {
    }

    public Administrador(String nombre, String apellido, String dni, String celular, String mail, String nombreUsuario, String contrasena, LocalDateTime fechaRegistro, EstadoUsuario estado) {
        super(Rol.ADMINISTRADOR, nombre, apellido, dni, celular, mail, nombreUsuario, contrasena, fechaRegistro, estado);
    }

    public Administrador(int id, Rol rol, String nombre, String apellido, String dni, String celular, String mail, String nombreUsuario, String contrasena, LocalDateTime fechaRegistro, EstadoUsuario estado) {
        super(id, Rol.ADMINISTRADOR, nombre, apellido, dni, celular, mail, nombreUsuario, contrasena, fechaRegistro, estado);
    }

}