package util;

import org.mindrot.jbcrypt.BCrypt;

// Uso la librería BCrypt para hashear las contraseñas
public interface Hashing {

    // Proceso de hasheo, lo uso al guardar una contraseña nueva (al crear usuario o restablecer)
    static String hash(String contrasena) {
        return BCrypt.hashpw(contrasena, BCrypt.gensalt());
    }

    // La uso al iniciar sesión, para comparar la contraseña recibida con el hash de la BD
    static boolean verificar(String contrasena, String hash) {
        return BCrypt.checkpw(contrasena, hash);
    }

}
