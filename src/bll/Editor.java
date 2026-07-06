package bll;

import enums.EstadoUsuario;
import enums.Rol;

import java.time.LocalDateTime;

public class Editor extends Usuario {

    //Constructores☆゜・。。・゜゜・。。・゜★゜・。。・゜゜・。。・゜☆゜・。。・゜゜・。。・゜★
    public Editor() {
    }

    public Editor(String nombre, String apellido, String dni, String celular, String mail, String nombreUsuario, String contrasena, LocalDateTime fechaRegistro, EstadoUsuario estado) {
        super(Rol.EDITOR, nombre, apellido, dni, celular, mail, nombreUsuario, contrasena, fechaRegistro, estado);
    }

    public Editor(int id, Rol rol, String nombre, String apellido, String dni, String celular, String mail, String nombreUsuario, String contrasena, LocalDateTime fechaRegistro, EstadoUsuario estado) {
        super(id, Rol.EDITOR, nombre, apellido, dni, celular, mail, nombreUsuario, contrasena, fechaRegistro, estado);
    }

}