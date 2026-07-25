package repository;

import model.Autor;

public interface AutorRepository extends CrudRepository<Autor> {

    Autor buscarPorUsuario(int idUsuario);

}
