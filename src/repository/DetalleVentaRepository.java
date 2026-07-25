package repository;

import model.DetalleVenta;

import java.util.List;

public interface DetalleVentaRepository extends CrudRepository<DetalleVenta>{

    List<DetalleVenta> listarPorVenta(int idVenta);

}
