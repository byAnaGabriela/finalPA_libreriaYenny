package bll;

import dao.IdiomaRepositoryImpl;
import dao.LibroRepositoryImpl;
import model.Idioma;
import model.enums.AtributoLibro;
import repository.IdiomaRepository;
import repository.LibroRepository;
import util.Validaciones;

import java.util.List;

public class IdiomaService implements CrudService<Idioma> {

    // Instancio el repositorio que se encargará de interactuar con la BD
    private final IdiomaRepository idiomaRepository = new IdiomaRepositoryImpl();
    private final LibroRepository libroRepository = new LibroRepositoryImpl();

    @Override
    public void agregar(Idioma idioma) {
        validarDatosIdioma(idioma);
        // Verifico si hay un idioma con el mismo nombre registrado antes de insertar
        // De igual manera en la BD a la fila nombre la puse como unique
        if (idiomaRepository.existeNombre(idioma.getNombre())) {
            // Si ya existe lanzo la excepción y no se inserta
            throw new RuntimeException("Ya existe un idioma con el mismo nombre");
        }
        idiomaRepository.insertar(idioma); // Si no existe lo guardo en la BD
    }

    @Override
    public void editar(Idioma idioma) {
        validarDatosIdioma(idioma);
        // Busco el valor actual y lo guardo en la variable
        Idioma idiomaExistente = idiomaRepository.buscarPorId(idioma.getId());

        // Si los nombres son diferentes equals da false, usando el ! lo invierte y me da true, es decir, el nombre si cambió / Si los nombres son iguales da false
        // Con esto compruebo si el nombre que se quiere actualizar es distinto al que ya tenía
        boolean cambioDeNombre = !idiomaExistente.getNombre().equals(idioma.getNombre());

        // Si cambió el nombre, también tengo que evaluar que no sea igual a otro que ya exista en la BD
        if (cambioDeNombre && idiomaRepository.existeNombre(idioma.getNombre())) {
            throw new RuntimeException("Ya existe un idioma con el mismo nombre");
        }
        // Si pasa las validaciones, se actualizan los datos en la BD
        idiomaRepository.actualizar(idioma);
    }

    @Override
    public void eliminar(Idioma idioma) {
        boolean enUso = libroRepository.existeLibroAsociado(AtributoLibro.IDIOMA, idioma.getId());
        if (enUso) {
            throw new RuntimeException("No se puede eliminar el idioma porque hay libros asociados a ella");
        }
        obtenerIdiomaExistente(idioma.getId());
        idiomaRepository.eliminar(idioma);
    }

    @Override
    public Idioma buscarPorId(int id) {
        return idiomaRepository.buscarPorId(id);
    }

    @Override
    public List<Idioma> listarTodos() {
        return idiomaRepository.listarTodos();
    }

    // Atributos Idioma: id, nombre(unique)
    private void validarDatosIdioma(Idioma idioma) {
        if (idioma == null) {
            throw new RuntimeException("Los datos del idioma no pueden estar vacíos");
        }
        if (!Validaciones.esTextoValido(idioma.getNombre())) {
            throw new RuntimeException("El nombre de idioma ingresado no es válido");
        }
    }

    private Idioma obtenerIdiomaExistente(int idIdioma) {
        // Busco el idioma en la BD con su id
        Idioma idiomaExistente = idiomaRepository.buscarPorId(idIdioma);
        // Si no encuentra ningún registro detengo la ejecución
        if (idiomaExistente == null) {
            throw new RuntimeException("No se encontró el idioma");
        }
        // Si el idioma existe, lo devuelvo
        return idiomaExistente;
    }

}
