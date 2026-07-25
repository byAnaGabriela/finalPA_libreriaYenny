package repository;

import model.Mensaje;
import model.Propuesta;

import java.util.List;

public interface MensajeRepository extends CrudRepository<Mensaje> {

    List<Mensaje> listarPorPropuesta(int idPropuesta);

}
