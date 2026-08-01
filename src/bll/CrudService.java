package bll;

import java.util.List;

public interface CrudService<T> {

    void agregar(T entidad);
    void editar(T entidad);
    void eliminar(T entidad);
    T buscarPorId(int id);
    List<T> listarTodos();

}
