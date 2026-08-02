package bll;

import dao.GeneroRepositoryImpl;
import model.Genero;
import repository.GeneroRepository;

import java.util.Collections;
import java.util.List;

public class GeneroService implements CrudService<Genero> {

    // Instancio el repositorio que se encargará de interactuar con la BD
    private GeneroRepository generoRepository = new GeneroRepositoryImpl();

    @Override
    public void agregar(Genero genero) {
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
        // Busco el valor actual y lo guardo en la variable
        Genero idiomaExistente = generoRepository.buscarPorId(genero.getId());

        // Con esto compruebo si el nombre que se quiere actualizar es distinto al que ya tenía
        boolean cambioDeNombre = !idiomaExistente.getNombre().equals(genero.getNombre());

        // Si cambió el nombre, también tengo que evaluar que no sea igual a otro que ya exista en la BD
        if (cambioDeNombre && generoRepository.existeNombre(genero.getNombre())) {
            throw new RuntimeException("Ya existe un género con el mismo nombre");
        }
        // Si pasa las validaciones, se actualizan los datos en la BD
        generoRepository.actualizar(genero);
    }

    @Override
    public void eliminar(Genero genero) {
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

}
