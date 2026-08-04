package bll;

import dao.AutorRepositoryImpl;
import dao.LibroRepositoryImpl;
import model.Autor;
import model.enums.AtributoLibro;
import model.enums.Rol;
import repository.AutorRepository;
import repository.LibroRepository;
import util.Validaciones;

import java.util.List;

public class AutorService implements CrudService<Autor> {

    // Instancio el repositorio que se encargará de interactuar con la BD
    private final AutorRepository autorRepository = new AutorRepositoryImpl();
    private final LibroRepository libroRepository = new LibroRepositoryImpl();

    @Override
    public void agregar(Autor autor) {
        validarDatosAutor(autor);
        autorRepository.insertar(autor);
    }

    @Override
    public void editar(Autor autor) {
        validarDatosAutor(autor);
        obtenerAutorExistente(autor.getId());
        autorRepository.actualizar(autor);
    }

    @Override
    public void eliminar(Autor autor) {
        boolean enUso = libroRepository.existeLibroAsociado(AtributoLibro.AUTOR, autor.getId());
        if (enUso) {
            throw new RuntimeException("No se puede eliminar el autor porque hay libros asociados a ella");
        }
        obtenerAutorExistente(autor.getId());
        autorRepository.eliminar(autor);
    }

    @Override
    public Autor buscarPorId(int id) {
        return autorRepository.buscarPorId(id);
    }

    @Override
    public List<Autor> listarTodos() {
        return autorRepository.listarTodos();
    }

    //Atributos Autor: id, nombre, apellido, escritorVinculado (Ninguno es unique)
    private void validarDatosAutor(Autor autor) {
        if (autor == null) {
            throw new RuntimeException("Los datos del autor no pueden estar vacíos");
        }
        if (!Validaciones.esTextoValido(autor.getNombre())) {
            throw new RuntimeException("El nombre del autor no es válido");
        }
        if (!Validaciones.esTextoValido(autor.getApellido())) {
            throw new RuntimeException("El apellido del autor no es válido");
        }
        // Si el autor tienen un usuario vinculado debe tener rol ESCRITOR
        if (autor.getEscritorVinculado() != null && autor.getEscritorVinculado().getRol() != Rol.ESCRITOR) {
            throw new RuntimeException("El usuario vinculado debe ser un escritor");
        }
        // Se registran autores sin usuario vinculado y también con ESCRITOR vinculado
    }

    private Autor obtenerAutorExistente(int idAutor) {
        // Busco el autor en la BD con su id
        Autor autorExistente = autorRepository.buscarPorId(idAutor);
        // Si no encuentra ningún registro detengo la ejecución
        if (autorExistente == null) {
            throw new RuntimeException("No se encontró al autor");
        }
        // Si el autor existe, lo devuelvo
        return autorExistente;
    }

}
