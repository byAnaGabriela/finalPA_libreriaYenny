package repository;

import dto.EscritorRendimientoDTO;
import dto.LibroVentaDTO;
import model.Venta;

import java.time.LocalDateTime;
import java.util.List;

public interface VentaRepository extends CrudRepository<Venta> {

    List<Venta> listarPorVendedor(int idVendedor);
    List<Venta> listarPorFecha(LocalDateTime desde, LocalDateTime hasta);
    List<LibroVentaDTO> obtenerLibrosMasVendidos(int anio, int mes);
    List<LibroVentaDTO> obtenerLibrosMasVendidos();
    List<EscritorRendimientoDTO> obtenerRendimientoAutores();
    List<EscritorRendimientoDTO> obtenerGananciasPorAutor(int idEscritor);

}
