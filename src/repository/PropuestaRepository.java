package repository;

import model.Propuesta;
import model.enums.EstadoPropuesta;

import java.util.List;

public interface PropuestaRepository extends CrudRepository<Propuesta> {

    List<Propuesta> buscarPorEscritor(int idEscritor);
    List<Propuesta> buscarPorEstado(EstadoPropuesta estado);

}
