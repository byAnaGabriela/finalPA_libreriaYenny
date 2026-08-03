package util;

import model.Usuario;

// Clase para gestionar la sesión actual del usuario autenticado
public class Sesion {

    // Lo guardo de manera estática para que esté disponible de forma global
    private static Usuario usuarioActual;

    // No se instancia
    private Sesion() {
    }

    public static void iniciar(Usuario usuario) {
        // Asigno el usuario que se loguea
        usuarioActual = usuario;
    }

    public static void cerrar() {
        // Limpio la variable dejándola nula
        usuarioActual = null;
    }

    public static Usuario getUsuarioActual() {
        // Devuelvo quién está logueado
        return usuarioActual;
    }

    public static boolean hayUsuarioLogueado() {
        // Verifico si hay una sesión activa, si es null no hay usuario logueado
        return usuarioActual != null;
    }

}
