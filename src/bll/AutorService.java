package bll;

import dao.AutorRepositoryImpl;
import model.Autor;
import model.enums.Rol;
import repository.AutorRepository;

import java.util.List;

public class AutorService implements CrudService<Autor> {

    // Instancio el repositorio que se encargará de interactuar con la BD
    private AutorRepository autorRepository = new AutorRepositoryImpl();

    @Override
    public void agregar(Autor autor) {
        // Si el autor tienen un usuario vinculado debe tener rol ESCRITOR
        if (autor.getEscritorVinculado() != null && autor.getEscritorVinculado().getRol() != Rol.ESCRITOR) {
            throw new RuntimeException("El usuario vinculado deber ser un escritor");
        }
        // Se registran autores sin usuario vinculado y con ESCRITOR vinculado
        autorRepository.insertar(autor);
    }

    @Override
    public void editar(Autor autor) {
        // Pasa lo mismo que con agregar
        if (autor.getEscritorVinculado() != null && autor.getEscritorVinculado().getRol() != Rol.ESCRITOR) {
            throw new RuntimeException("El usuario vinculado deber ser un escritor");
        }
        autorRepository.actualizar(autor);
    }

    @Override
    public void eliminar(Autor autor) {
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

}
