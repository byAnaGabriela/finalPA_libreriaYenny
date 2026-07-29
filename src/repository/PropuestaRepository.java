package repository;

import model.Propuesta;
import model.enums.EstadoPropuesta;

import java.util.List;

public interface PropuestaRepository extends CrudRepository<Propuesta> {

    List<Propuesta> listarPorEscritor(int idEscritor);
    List<Propuesta> listarPorEstado(EstadoPropuesta estado);

}
