package bll;

import model.Usuario;
import model.enums.EstadoUsuario;
import model.enums.Rol;
import repository.UsuarioRepository;
import util.Hashing;
import util.Sesion;
import util.Validaciones;

import java.time.LocalDateTime;
import java.util.List;

public class UsuarioService implements CrudService<Usuario> {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        // Inicializo el servicio con el repositorio de usuarios para que pueda comunicarse con la BD
        this.usuarioRepository = usuarioRepository;
    }

    // ★゜・。。・゜゜・。。・゜☆ Implementación CrudService ☆゜・。。・゜゜・。。・゜★

    // Este metodo lo van a usar tanto administradores como escritores
    @Override
    public void agregar(Usuario usuario) {

        // Métodos reutilizables para validar nombre, apellido, DNI y celular | mail, nombre de usuario
        validarDatosPersonales(usuario);
        validarDatosCuenta(usuario);

        // Verifica que no sea nula, que tenga mínimo 8 caracteres, 1 letra y 1 número
        if (!Validaciones.esContrasenaSegura(usuario.getContrasena())) {
            throw new RuntimeException("La contraseña debe tener al menos 8 caracteres, con letras y números");
        }

        // Evito duplicación de mail, DNI y nombre de usuario (pq en la BD los asigné como unique)
        if (usuarioRepository.existeMail(usuario.getMail())) {
            throw new RuntimeException("Ya existe un usuario con el mail ingresado");
        }
        if (usuarioRepository.existeDni(usuario.getDni())) {
            throw new RuntimeException("Ya existe un usuario con el dni ingresado");
        }
        if (usuarioRepository.existeNombreUsuario(usuario.getNombreUsuario())) {
            throw new RuntimeException("Ya existe un usuario con el nombre de usuario ingresado");
        }

        /* Luego de pasar todas las validaciones necesarias:
         - hasheo la contraseña
         - setteo los valores por defecto(fecha y hora, estado)
         - inserto al usuario en la BD */
        usuario.setContrasena(Hashing.hash(usuario.getContrasena()));
        usuario.setFechaRegistro(LocalDateTime.now());
        usuario.setEstado(EstadoUsuario.ACTIVO);

        usuarioRepository.insertar(usuario);
    }

    // Este metodo lo usa únicamente el administrador
    @Override
    public void editar(Usuario usuario) {
        validarPermisoAdmin(); // Valido permisos de administrador

        // Metodo reutilizable para validar que el usuario existe
        // Al tomar al usuario no pierdo los datos que no modifico
        Usuario usuarioExistente = obtenerUsuarioExistente(usuario.getId());

        validarDatosPersonales(usuario);
        validarDatosCuenta(usuario);

        // Los duplicados los chequeo si realmente cambiaron
        boolean cambioDni = !usuarioExistente.getDni().equals(usuario.getDni());
        if (cambioDni && usuarioRepository.existeDni(usuarioExistente.getDni())) {
            throw new RuntimeException("Ya existe un usuario con el dni ingresado");
        }

        boolean cambioMail = !usuarioExistente.getMail().equals(usuario.getMail());
        if (cambioMail && usuarioRepository.existeMail(usuario.getMail())) {
            throw new RuntimeException("Ya existe un usuario con el mail ingresado");
        }

        boolean cambioNombreUsuario = !usuarioExistente.getNombreUsuario().equals(usuario.getNombreUsuario());
        if (cambioNombreUsuario && usuarioRepository.existeNombreUsuario(usuario.getNombreUsuario())) {
            throw new RuntimeException("Ya existe un usuario con el nombre de usuario ingresado");
        }

        usuarioExistente.setNombre(usuario.getNombre());
        usuarioExistente.setApellido(usuario.getApellido());
        usuarioExistente.setDni(usuario.getDni());
        usuarioExistente.setCelular(usuario.getCelular());
        usuarioExistente.setMail(usuario.getMail());
        usuarioExistente.setNombreUsuario(usuario.getNombreUsuario());

        usuarioRepository.actualizar(usuarioExistente);
    }

    // Usado únicamente por el administrador para eliminar cuentas de otros usuarios
    @Override
    public void eliminar(Usuario usuario) {
        validarPermisoAdmin(); // Verifico que tenga los permisos de administrador
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

    public Usuario iniciarSesion(String nombreUsuarioOMail, String contrasena) {
        // Busco al usuario en la BD usando su nombre de usuario
        Usuario usuario = usuarioRepository.buscarPorNombreUsuario(nombreUsuarioOMail);
        if (usuario == null) {
            // Si no encuentro al usuario por su nombre de usuario lo busco por su mail
            usuario = usuarioRepository.buscarPorMail(nombreUsuarioOMail);
        }
        if (usuario == null) {
            // Si no encuentro al usuario, lanzo la excepción general (NO específica para máyor seguridad)
            throw new RuntimeException("Usuario o contraseña incorrectos");
        }

        // Verifico que la cuenta este activa
        if (usuario.getEstado() != EstadoUsuario.ACTIVO) {
            throw new RuntimeException("El usuario no está activo");
        }

        // Compruebo que la contraseña hasheada coincide con la de la BD
        if (!Hashing.verificar(contrasena, usuario.getContrasena())) {
            throw new RuntimeException("Usuario o contraseña incorrectos");
        }

        // Si pasó todas las validaciones, guardo al usuario en la sesión global y lo devuelvo
        Sesion.iniciar(usuario);
        return usuario;
    }

    public void cerrarSesion() {
        // Cierro la sesión activa, limpiando los datos del usuario logueado
        Sesion.cerrar();
    }

    // ★゜・。。・゜゜・。。・゜☆ Gestión propia de usuarios ☆゜・。。・゜゜・。。・゜★

    // Acá solo se modifican nombre, apellido, DNI y celular
    public void modificarDatosPersonales(Usuario usuario) {
        Usuario usuarioExistente = obtenerUsuarioExistente(usuario.getId());
        validarDatosPersonales(usuario);

        // Compruebo si el usuario cambió su DNI respecto al que ya tenía guardado
        boolean cambioDni = !usuarioExistente.getDni().equals(usuario.getDni());
        // Si lo modificó, valido que el nuevo no esté registrado ya por otro usuario
        if (cambioDni && usuarioRepository.existeDni(usuario.getDni())) {
            throw new RuntimeException("Ya existe un usuario con el DNI ingresado");
        }

        // Solo edito los campos correspondientes, los que no toco quedan igual a como estaban
        usuarioExistente.setNombre(usuario.getNombre());
        usuarioExistente.setApellido(usuario.getApellido());
        usuarioExistente.setDni(usuario.getDni());
        usuarioExistente.setCelular(usuario.getCelular());

        usuarioRepository.actualizar(usuarioExistente);
    }

    // Acá solo se modifican mail, nombre de usuario y contraseña
    public void modificarDatosCuenta(Usuario usuario) {
        Usuario usuarioExistente = obtenerUsuarioExistente(usuario.getId());
        validarDatosCuenta(usuario);

        boolean cambioMail = !usuarioExistente.getMail().equals(usuario.getMail());
        if (cambioMail && usuarioRepository.existeMail(usuario.getMail())) {
            throw new RuntimeException("Ya existe un usuario con el mail ingresado");
        }
        boolean cambioNombreUsuario = !usuarioExistente.getNombreUsuario().equals(usuario.getNombreUsuario());
        if (cambioNombreUsuario && usuarioRepository.existeNombreUsuario(usuario.getNombreUsuario())) {
            throw new RuntimeException("El nombre de usuario ingresado ya está en uso");
        }

        // Si la contraseña viene vacía, NO se cambiará y quedará la original
        if (usuario.getContrasena() != null && !usuario.getContrasena().isEmpty()) {
            if (!Validaciones.esContrasenaSegura(usuario.getContrasena())) {
                throw new RuntimeException("La contraseña ingresada no tiene un formáto válido");
            }
            usuarioExistente.setContrasena(Hashing.hash(usuario.getContrasena()));
        }

        usuarioExistente.setMail(usuario.getMail());
        usuarioExistente.setNombreUsuario(usuario.getNombreUsuario());

        usuarioRepository.actualizar(usuarioExistente);
    }

    public void restablecerContrasena(int idUsuario, String nuevaContrasena) {
        if (!Validaciones.esContrasenaSegura(nuevaContrasena)) {
            throw new RuntimeException("La contraseña debe tener al menos de 8 caracteres, con letras y números");
        }

        Usuario usuario = usuarioRepository.buscarPorId(idUsuario);
        if (usuario == null) {
            throw new RuntimeException("No se encontró al usuario");
        }

        usuario.setContrasena(Hashing.hash(nuevaContrasena));
        usuarioRepository.actualizar(usuario);
    }

    public void eliminarCuentaPropia() {
        Usuario usuarioActual = Sesion.getUsuarioActual(); // Obtengo al usuario que está logueado
        if (usuarioActual == null) { // Verifico que realmente haya una sesión activa
            throw new RuntimeException("No hay una sesión iniciada");
        }

        usuarioRepository.eliminar(usuarioActual);
        Sesion.cerrar(); // Cierro la sesión automáticamente después de eliminar la cuenta
    }

    // ★゜・。。・゜゜・。。・゜☆ Gestión de los administradores ☆゜・。。・゜゜・。。・゜★

    private void validarPermisoAdmin() {
        // Obtengo al usuario actual y verifico que este logueado y que tenga asignado el rol de administrador
        Usuario usuarioActual = Sesion.getUsuarioActual();
        if (usuarioActual == null || usuarioActual.getRol() != Rol.ADMINISTRADOR) {
            throw new RuntimeException("No tienes permisos para realizar esta acción");
        }
    }

    // Los administradores están encargados de registrar a sus empleados (Vendedor, editor, y otros administradores)
    public void crearUsuario(Usuario nuevoUsuario) {
        validarPermisoAdmin(); // Valido permisos de administrador

        // Reutilizo el metodo agregar con todas sus validaciones para crear este usuario
        agregar(nuevoUsuario);
    }

    public void asignarRol(int idUsuario, Rol nuevoRol) {
        validarPermisoAdmin(); // Valido permisos de administrador
        Usuario usuarioExistente = obtenerUsuarioExistente(idUsuario);

        // Modifico el rol con el recibido
        usuarioExistente.setRol(nuevoRol);
        usuarioRepository.actualizar(usuarioExistente);
    }

    private void cambiarEstado(int idUsuario, EstadoUsuario nuevoEstado) {
        validarPermisoAdmin(); // Valido permisos de administrador
        Usuario usuarioExistente = obtenerUsuarioExistente(idUsuario);

        // Modifico el estado con el recibido
        usuarioExistente.setEstado(nuevoEstado);
        usuarioRepository.actualizar(usuarioExistente);
    }

    public void activarUsuario(int idUsuario) {
        cambiarEstado(idUsuario, EstadoUsuario.ACTIVO);
    }

    public void suspenderUsuario(int idUsuario) {
        cambiarEstado(idUsuario, EstadoUsuario.SUSPENDIDO);
    }

    // ★゜・。。・゜゜・。。・゜☆ Registro para escritores ☆゜・。。・゜゜・。。・゜★

    // Los escritores si pueden registrarse ellos mismos
    public void registrarEscritor (Usuario nuevoEscritor) {
        // Seteo su rol
        nuevoEscritor.setRol(Rol.ESCRITOR);

        // Reutilizo el metodo agregar con todas sus validaciones para crear este usuario
        agregar(nuevoEscritor);
    }

    // ★゜・。。・゜゜・。。・゜☆ Métodos para reutilizar ☆゜・。。・゜゜・。。・゜★

    private void validarDatosPersonales (Usuario usuario) {
        // Verifica si el nombre está vacío o si no coincide con el patrón asignado (letras, acentos, ñ, espacios)
        if (!Validaciones.esTextoValido(usuario.getNombre())) {
            throw new RuntimeException("El nombre ingresado no es válido");
        }

        // Verifica si el apellido está vacío o si no coincide con el patrón asignado (letras, acentos, ñ, espacios)
        if (!Validaciones.esTextoValido(usuario.getApellido())) {
            throw new RuntimeException("El apellido ingresado no es válido");
        }

        // Verifica si el DNI está vacío o si no coincide con el patrón asignado (números, rango: 7-9 caracteres)
        if (!Validaciones.esDniValido(usuario.getDni())) {
            throw new RuntimeException("El DNI ingresado no es válido");
        }

        // Verifica si el celular está vacío o si no coincide con el patrón asignado (números, rango: 8-15 caracteres)
        if (!Validaciones.esCelularValido(usuario.getCelular())) {
            throw new RuntimeException("El celular ingresado no es válido");
        }
    }

    private void validarDatosCuenta (Usuario usuario) {
        // Verifica si el mail está vacío o si no coincide con el patrón asignado (números, rango: 8-15 caracteres)
        if (!Validaciones.esMailValido(usuario.getMail())) {
            throw new RuntimeException("El mail ingresado no tiene un formáto válido");
        }

        // Verifica si el nombre de usuario está vacío o si no coincide con el patrón asignado (letras(a-A), números, guiones bajos, rango: 4-20 caracteres)
        if (!Validaciones.esNombreUsuarioValido(usuario.getNombreUsuario())) {
            throw new RuntimeException("El nombre de usuario debe tener entre 4-20 caracteres, letras, números y guión bajo");
        }
    }

    private Usuario obtenerUsuarioExistente(int idUsuario) {
        // Busco al usuario en la BD con su id
        Usuario usuarioExistente = usuarioRepository.buscarPorId(idUsuario);
        // Si no encuentra ningún registro detengo la ejecución
        if (usuarioExistente == null) {
            throw new RuntimeException("No se encontró al usuario");
        }
        // Si el usuario existe, lo devuelvo
        return usuarioExistente;
    }

}
