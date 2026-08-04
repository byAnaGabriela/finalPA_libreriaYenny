package bll;

import dao.EditorialRepositoryImpl;
import dao.LibroRepositoryImpl;
import model.Editorial;
import model.enums.AtributoLibro;
import repository.EditorialRepository;
import repository.LibroRepository;
import util.Validaciones;

import java.util.List;

public class EditorialService implements CrudService<Editorial> {

    // Instancio el repositorio que se encargará de interactuar con la BD
    private final EditorialRepository editorialRepository = new EditorialRepositoryImpl();
    private final LibroRepository libroRepository = new LibroRepositoryImpl();

    @Override
    public void agregar(Editorial editorial) {
        validarDatosEditorial(editorial);
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
        validarDatosEditorial(editorial);
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
        boolean enUso = libroRepository.existeLibroAsociado(AtributoLibro.EDITORIAL, editorial.getId());
        if (enUso) {
            throw new RuntimeException("No se puede eliminar la editorial porque hay libros asociados a ella");
        }
        obtenerEditorialExistente(editorial.getId());
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

    // Atributos Editorial: id, nombre(unique)
    private void validarDatosEditorial(Editorial editorial) {
        if (editorial == null) {
            throw new RuntimeException("Los datos de la editorial no pueden estar vacíos");
        }
        if (!Validaciones.esTextoValido(editorial.getNombre())) {
            throw new RuntimeException("El nombre de editorial ingresado no es válido");
        }
    }

    private Editorial obtenerEditorialExistente(int idEditorial) {
        // Busco la editorial en la BD con su id
        Editorial editorialExistente = editorialRepository.buscarPorId(idEditorial);
        // Si no encuentra ningún registro detengo la ejecución
        if (editorialExistente == null) {
            throw new RuntimeException("No se encontró la editorial");
        }
        // Si la editorial existe, lo devuelvo
        return editorialExistente;
    }

}
