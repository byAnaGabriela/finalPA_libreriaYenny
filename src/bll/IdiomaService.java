package bll;

import dao.IdiomaRepositoryImpl;
import model.Idioma;
import repository.IdiomaRepository;

import java.util.List;

public class IdiomaService implements CrudService<Idioma> {

    private IdiomaRepository idiomaRepository = new IdiomaRepositoryImpl();

    @Override
    public void agregar(Idioma idioma) {
        // Valido si existe o no el idioma antes de insertar
        // De igual manera en la BD a la fila nombre la puse como unique
        if (idiomaRepository.existeNombre(idioma.getNombre())) {
            throw new RuntimeException("Ya existe este idioma");
        }
        idiomaRepository.insertar(idioma);
    }

    @Override
    public void editar(Idioma idioma) {
        Idioma idiomaExistente = idiomaRepository.buscarPorId(idioma.getId()); // Busco el valor actual y lo guardo en la variable
        boolean cambioDeNombre = !idiomaExistente.getNombre().equals(idioma.getNombre()); // Si los nombres son diferentes equals da false, usando el ! lo invierte y me da true, es decir, el nombre si se cambió
        // Si los nombres son iguales da false
        // Si el usuario cambió el nombre también tengo que evaluar que no sea igual a otro que ya exista en la BD
        if (cambioDeNombre && idiomaRepository.existeNombre(idioma.getNombre())) {
            throw new RuntimeException("Ya existe este idioma");
        }
        idiomaRepository.actualizar(idioma);
    }

    @Override
    public void eliminar(Idioma idioma) {
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

}
