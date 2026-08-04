package bll;

import dao.GeneroRepositoryImpl;
import dao.LibroRepositoryImpl;
import model.Genero;
import model.enums.AtributoLibro;
import repository.GeneroRepository;
import repository.LibroRepository;
import util.Validaciones;

import java.util.List;

public class GeneroService implements CrudService<Genero> {

    // Instancio el repositorio que se encargará de interactuar con la BD
    private final GeneroRepository generoRepository = new GeneroRepositoryImpl();
    private final LibroRepository libroRepository = new LibroRepositoryImpl();

    @Override
    public void agregar(Genero genero) {
        validarDatosGenero(genero);
        // Verifico si hay un género con el mismo nombre registrado antes de insertar
        // De igual manera en la BD a la fila nombre la puse como unique
        if (generoRepository.existeNombre(genero.getNombre())) {
            // Si ya existe lanzo la excepción y no se inserta
            throw new RuntimeException("Ya existe un género con el mismo nombre");
        }
        generoRepository.insertar(genero); // Si no existe lo guardo en la BD
    }

    @Override
    public void editar(Genero genero) {
        validarDatosGenero(genero);
        // Busco el valor actual y lo guardo en la variable
        Genero generoExistente = generoRepository.buscarPorId(genero.getId());

        // Con esto compruebo si el nombre que se quiere actualizar es distinto al que ya tenía
        boolean cambioDeNombre = !generoExistente.getNombre().equals(genero.getNombre());
        // Si cambió el nombre, también tengo que evaluar que no sea igual a otro que ya exista en la BD
        if (cambioDeNombre && generoRepository.existeNombre(genero.getNombre())) {
            throw new RuntimeException("Ya existe un género con el mismo nombre");
        }
        // Si pasa las validaciones, se actualizan los datos en la BD
        generoRepository.actualizar(genero);
    }

    @Override
    public void eliminar(Genero genero) {
        boolean enUso = libroRepository.existeLibroAsociado(AtributoLibro.GENERO, genero.getId());
        if (enUso) {
            throw new RuntimeException("No se puede eliminar el género porque hay libros asociados a ella");
        }
        obtenerGeneroExistente(genero.getId());
        generoRepository.eliminar(genero);
    }

    @Override
    public Genero buscarPorId(int id) {
        return generoRepository.buscarPorId(id);
    }

    @Override
    public List<Genero> listarTodos() {
        return generoRepository.listarTodos();
    }

    // Atributos Genero: id, nombre(unique)
    private void validarDatosGenero(Genero genero) {
        if (genero == null) {
            throw new RuntimeException("Los datos del género no pueden estar vacíos");
        }
        if (!Validaciones.esTextoValido(genero.getNombre())) {
            throw new RuntimeException("El nombre de género ingresado no es válido");
        }
    }

    private Genero obtenerGeneroExistente(int idGenero) {
        // Busco el género en la BD con su id
        Genero generoExistente = generoRepository.buscarPorId(idGenero);
        // Si no encuentra ningún registro detengo la ejecución
        if (generoExistente == null) {
            throw new RuntimeException("No se encontró la género");
        }
        // Si el género existe, lo devuelvo
        return generoExistente;
    }

}
