package repository;

import model.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario> {

    Usuario buscarPorNombreUsuario(String nombreUsuario);
    boolean existeNombreUsuario(String nombreUsuario);
    Usuario buscarPorMail(String email);
    boolean existeMail(String mail);
    boolean existeDni(String dni);

}
