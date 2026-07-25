package repository;

import model.Borrador;

import java.util.List;

public interface BorradorRepository extends CrudRepository<Borrador> {

    List<Borrador> listarPorPropuesta(int idPropuesta);

}
