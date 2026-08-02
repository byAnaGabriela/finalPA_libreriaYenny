package bll;

import dao.CategoriaRepositoryImpl;
import model.Categoria;
import repository.CategoriaRepository;

import java.util.List;

public class CategoriaService implements CrudService<Categoria> {

    // Instancio el repositorio que se encargará de interactuar con la BD
    private CategoriaRepository categoriaRepository = new CategoriaRepositoryImpl();

    @Override
    public void agregar(Categoria categoria) {
        // Verifico si hay una categoría con el mismo nombre registrado antes de insertar
        // De igual manera en la BD a la fila nombre la puse como unique
        if (categoriaRepository.existeNombre(categoria.getNombre())) {
            // Si ya existe lanzo la excepción y no se inserta
            throw new RuntimeException("Ya existe un género con el mismo nombre");
        }
        categoriaRepository.insertar(categoria); // Si no existe lo guardo en la BD
    }

    @Override
    public void editar(Categoria categoria) {
        // Busco el valor actual y lo guardo en la variable
        Categoria categoriaExistente = categoriaRepository.buscarPorId(categoria.getId());

        // Con esto compruebo si el nombre que se quiere actualizar es distinto al que ya tenía
        boolean cambioDeNombre = !categoriaExistente.getNombre().equals(categoria.getNombre());

        // Si cambió el nombre, también tengo que evaluar que no sea igual a otro que ya exista en la BD
        if (cambioDeNombre && categoriaRepository.existeNombre(categoria.getNombre())) {
            throw new RuntimeException("Ya existe un género con el mismo nombre");
        }
        // Si pasa las validaciones, se actualizan los datos en la BD
        categoriaRepository.actualizar(categoria);
    }

    @Override
    public void eliminar(Categoria categoria) {
        categoriaRepository.eliminar(categoria);
    }

    @Override
    public Categoria buscarPorId(int id) {
        return categoriaRepository.buscarPorId(id);
    }

    @Override
    public List<Categoria> listarTodos() {
        return categoriaRepository.listarTodos();
    }

}
