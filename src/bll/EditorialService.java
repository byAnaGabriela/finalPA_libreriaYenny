package bll;

import dao.EditorialRepositoryImpl;
import model.Editorial;
import repository.EditorialRepository;

import java.util.List;

public class EditorialService implements CrudService<Editorial> {

    // Instancio el repositorio que se encargará de interactuar con la BD
    private EditorialRepository editorialRepository = new EditorialRepositoryImpl();

    @Override
    public void agregar(Editorial editorial) {
        // Verifico si hay una editorial con el mismo nombre registrado antes de insertar
        // De igual manera en la BD a la fila nombre la puse como unique
        if (editorialRepository.existeNombre(editorial.getNombre())) {
            // Si ya existe lanzo la excepción y no se inserta
            throw new RuntimeException("Ya existe un género con el mismo nombre");
        }
        editorialRepository.insertar(editorial); // Si no existe lo guardo en la BD
    }

    @Override
    public void editar(Editorial editorial) {
        // Busco el valor actual y lo guardo en la variable
        Editorial editorialExistente = editorialRepository.buscarPorId(editorial.getId());

        // Con esto compruebo si el nombre que se quiere actualizar es distinto al que ya tenía
        boolean cambioDeNombre = !editorialExistente.getNombre().equals(editorial.getNombre());

        // Si cambió el nombre, también tengo que evaluar que no sea igual a otro que ya exista en la BD
        if (cambioDeNombre && editorialRepository.existeNombre(editorial.getNombre())) {
            throw new RuntimeException("Ya existe un género con el mismo nombre");
        }
        // Si pasa las validaciones, se actualizan los datos en la BD
        editorialRepository.actualizar(editorial);
    }

    @Override
    public void eliminar(Editorial editorial) {
        editorialRepository.eliminar(editorial);
    }

    @Override
    public Editorial buscarPorId(int id) {
        return editorialRepository.buscarPorId(id);
    }

    @Override
    public List<Editorial> listarTodos() {
        return editorialRepository.listarTodos();
    }

}
