package bll;

import enums.EstadoUsuario;
import enums.Rol;

import java.time.LocalDateTime;

public class Vendedor extends Usuario {

    public Vendedor() {
    }

    public Vendedor(String nombre, String apellido, String dni, String celular, String mail, String nombreUsuario, String contrasena, LocalDateTime fechaRegistro, EstadoUsuario estado) {
        super(Rol.VENDEDOR, nombre, apellido, dni, celular, mail, nombreUsuario, contrasena, fechaRegistro, estado);
    }

}
