package repository;

import model.Libro;

import java.util.List;

public interface LibroRepository extends CrudRepository<Libro> {

    List<Libro> buscarPorTitulo(String titulo);
    List<Libro> buscarPorAutor(int idAutor);
    boolean existeIsbn(int isbn);

}
