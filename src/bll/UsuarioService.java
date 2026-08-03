package bll;

import dao.UsuarioRepositoryImpl;
import model.Usuario;
import model.enums.EstadoUsuario;
import model.enums.Rol;
import repository.UsuarioRepository;
import util.Hashing;
import util.Sesion;
import util.Validaciones;

import java.util.List;

public class UsuarioService implements CrudService<Usuario> {

    // Instancio el repositorio que se encargará de interactuar con la BD
    private UsuarioRepository usuarioRepository = new UsuarioRepositoryImpl();

    // ★゜・。。・゜゜・。。・゜☆ Implementación CrudService ☆゜・。。・゜゜・。。・゜★
    @Override
    public void agregar(Usuario usuario) {

        if (!Validaciones.esMailValido(usuario.getMail())) {
            throw new RuntimeException("El mail ingresado no tiene un formáto válido");
        }

        if (!Validaciones.esDniValido(usuario.getDni())) {
            throw new RuntimeException("El DNI ingresado no es válido");
        }

        if (!Validaciones.esContrasenaSegura(usuario.getContrasena())) {
            throw new RuntimeException("La contraseña debe tener al menos 8 caracteres, con letras y números");
        }

        if (usuarioRepository.existeMail(usuario.getMail())) {
            throw new RuntimeException("Ya existe un usuario con el mail ingresado");
        }

        if (usuarioRepository.existeDni(usuario.getDni())) {
            throw new RuntimeException("Ya existe un usuario con el dni ingresado");
        }

        // Luego de pasar todas las validaciones necesarias, hasheo la contraseña e inserto al usuario en la BD
        usuario.setContrasena(Hashing.hash(usuario.getContrasena()));
        usuarioRepository.insertar(usuario);
    }

    @Override
    public void editar(Usuario usuario) {
        usuarioRepository.actualizar(usuario);
    }

    @Override
    public void eliminar(Usuario usuario) {
        usuarioRepository.eliminar(usuario);
    }

    @Override
    public Usuario buscarPorId(int id) {
        return usuarioRepository.buscarPorId(id);
    }

    @Override
    public List<Usuario> listarTodos() {
        return usuarioRepository.listarTodos();
    }

    // ★゜・。。・゜゜・。。・゜☆ Inicio y cierre de sesión ☆゜・。。・゜゜・。。・゜★
    public Usuario iniciarSesion(String nombreUsuario, String contrasena) {
        // Busco al usuario en la BD usando su nombre de usuario
        Usuario usuario = usuarioRepository.buscarPorNombreUsuario(nombreUsuario);

        // Si no encuentro al usuario, lanzo la excepción general (NO específica para máyor seguridad)
        if (usuario == null) {
            throw new RuntimeException("Usuario o contraseña incorrectos");
        }

        // Verifico que la cuenta este activa
        if (usuario.getEstado() != EstadoUsuario.ACTIVO) {
            throw new RuntimeException("El usuario no está activo");
        }

        // Compruebo la contraseña contraseña hasheada
        if (!Hashing.verificar(contrasena, usuario.getContrasena())) {
            throw new RuntimeException("Usuario o contraseña incorrectos");
        }

        // Si pasó todas las validaciones, guardo al usuario en la sesión global y lo devuelvo
        Sesion.iniciar(usuario);
        return usuario;
    }

    public void cerrarSesion(Usuario usuario) {
        // Cierro la sesión activa, limpiando los datos
        Sesion.cerrar();
    }

    // ★゜・。。・゜゜・。。・゜☆ Gestión propia de usuarios ☆゜・。。・゜゜・。。・゜★
    public void modificarDatosPersonales(Usuario usuario) {}

    public void modificarDatosCuenta(Usuario usuario) {}

    public void restablecerContrasena(int idUsuario, String nuevaContrasena) {}

    // ★゜・。。・゜゜・。。・゜☆ Gestión de los administradores ☆゜・。。・゜゜・。。・゜★
    public void crearUsuario(Usuario admin, Usuario nuevoUsuario) {}

    public void editarUsuario(Usuario admin, Usuario usuario) {}

    public void eliminarUsuario(Usuario usuario) {}

    public void asignarRol(Usuario admin, int idUsuario, Rol nuevoRol){}

    public void activarUsuario(Usuario admin, int idUsuario) {}

    public void suspenderUsuario(Usuario admin, int idUsuario) {}

    // ★゜・。。・゜゜・。。・゜☆ Registro para escritores ☆゜・。。・゜゜・。。・゜★
    public void registrarEscritor (Usuario nuevoEscritor) {}

}
