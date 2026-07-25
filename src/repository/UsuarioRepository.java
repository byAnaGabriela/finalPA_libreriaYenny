package repository;

import model.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario> {

    Usuario buscarPorNombreUsuario(String nombreUsuario);
    boolean existeMail(String mail);
    boolean existeDni(String dni);

}
