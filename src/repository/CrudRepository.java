package repository;

import java.util.List;

public interface CrudRepository<T> {

    void insertar(T entidad);
    void actualizar(T entidad);
    void eliminar(int id);
    T buscarPorId(int id);
    List<T> buscarTodos();

}
