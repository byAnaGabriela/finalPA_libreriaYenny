package bll;

import dao.CategoriaRepositoryImpl;
import dao.LibroRepositoryImpl;
import model.Categoria;
import model.enums.AtributoLibro;
import repository.CategoriaRepository;
import util.Validaciones;

import java.util.List;

public class CategoriaService implements CrudService<Categoria> {

    // Instancio el repositorio que se encargará de interactuar con la BD
    private final CategoriaRepository categoriaRepository = new CategoriaRepositoryImpl();
    private final LibroRepositoryImpl libroRepository = new LibroRepositoryImpl();

    @Override
    public void agregar(Categoria categoria) {
        validarDatosCategoria(categoria);
        // Verifico si hay una categoría con el mismo nombre registrado antes de insertar
        // De igual manera en la BD a la fila nombre la puse como unique
        if (categoriaRepository.existeNombre(categoria.getNombre())) {
            // Si ya existe lanzo la excepción y no se inserta
            throw new RuntimeException("Ya existe una categoría con el mismo nombre");
        }
        categoriaRepository.insertar(categoria); // Si no existe lo guardo en la BD
    }

    @Override
    public void editar(Categoria categoria) {
        validarDatosCategoria(categoria);
        Categoria categoriaExistente = obtenerCategoriaExistente(categoria.getId());

        // Solo verifico duplicados cuando el nombre fue modificado
        boolean cambioDeNombre = !categoriaExistente.getNombre().equals(categoria.getNombre());
        // Si cambió el nombre, también tengo que evaluar que no sea igual a otro que ya exista en la BD
        if (cambioDeNombre && categoriaRepository.existeNombre(categoria.getNombre())) {
            throw new RuntimeException("Ya existe una categoría con el mismo nombre");
        }

        categoriaRepository.actualizar(categoria);
    }

    @Override
    public void eliminar(Categoria categoria) {
        boolean enUso = libroRepository.existeLibroAsociado(AtributoLibro.CATEGORIA, categoria.getId());
        if (enUso) {
            throw new RuntimeException("No se puede eliminar la categoría porque hay libros asociados a ella");
        }

        obtenerCategoriaExistente(categoria.getId());
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

    // Atributos Categoría: id, nombre(unique)
    private void validarDatosCategoria(Categoria categoria) {
        if (categoria == null) {
            throw new RuntimeException("Los datos de la categoría no pueden estar vacíos");
        }
        if (!Validaciones.esTextoValido(categoria.getNombre())) {
            throw new RuntimeException("El nombre de categoría ingresado no es válido");
        }
    }

    private Categoria obtenerCategoriaExistente(int idCategoria) {
        // Busco la categoría en la BD con su id
        Categoria categoriaExistente = categoriaRepository.buscarPorId(idCategoria);
        // Si no encuentra ningún registro detengo la ejecución
        if (categoriaExistente == null) {
            throw new RuntimeException("No se encontró la categoría");
        }
        // Si la categoría existe, lo devuelvo
        return categoriaExistente;
    }

}
