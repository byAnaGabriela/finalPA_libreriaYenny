package dao;

import dto.EscritorRendimientoDTO;
import dto.LibroVentaDTO;
import model.DetalleVenta;
import model.Venta;
import repository.VentaRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

public class VentaRepositoryImpl extends RepositoryBase<Venta> implements VentaRepository {

    @Override
    public List<Venta> listarPorVendedor(int idVendedor) {
        return null;
    }

    @Override
    public List<Venta> listarPorFecha(LocalDateTime desde, LocalDateTime hasta) {
        return null;
    }

    @Override
    public List<LibroVentaDTO> obtenerLibrosMasVendidos(int anio, int mes) {
        return null;
    }

    @Override
    public List<LibroVentaDTO> obtenerLibrosMasVendidos() {
        return null;
    }

    @Override
    public List<EscritorRendimientoDTO> obtenerRendimientoAutores() {
        return null;
    }

    @Override
    public List<EscritorRendimientoDTO> obtenerGananciasPorAutor(int idEscritor) {
        return null;
    }

    @Override
    public void insertar(Venta venta) {
        String sqlVenta = "INSERT INTO venta (fecha_venta, precio_total, descuento, metodo_pago, fk_id_vendedor) VALUES (?, ?, ?, ?, ?) ";

        try (PreparedStatement psVenta = connection.prepareStatement(sqlVenta, Statement.RETURN_GENERATED_KEYS)) {
            psVenta.setTimestamp(1, Timestamp.valueOf(venta.getFecha()));
            psVenta.setBigDecimal(2, venta.getPrecioTotal());
            psVenta.setBigDecimal(3, venta.getDescuento());
            psVenta.setString(4, venta.getMetodoPago().name());
            psVenta.setInt(5, venta.getVendedor().getId());
            psVenta.executeUpdate();

            try (ResultSet generatedKeys = psVenta.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    venta.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo insertar la venta", e);
        }

        // Inserto los detalles de la venta en la tabla intermedia (venta_libro)
        // Guardo cada libro que se compró dentro de la venta
        String sqlDetalle = "INSERT INTO venta_libro (fk_id_venta, fk_id_libro, cantidad_vendida, precio_unitario) VALUES (?, ?, ?, ?) ";

        try (PreparedStatement psDetalle = connection.prepareStatement(sqlDetalle)) {
            // Uso for para recorrer cada detalle que contiene la venta (es un detalle por cada libro distinto dentro de la misma venta)
            for (DetalleVenta detalle : venta.getDetalles()) {
                psDetalle.setInt(1, venta.getId());
                psDetalle.setInt(2, detalle.getLibro().getId());
                psDetalle.setInt(3, detalle.getCantidadVendida());
                psDetalle.setBigDecimal(4, detalle.getPrecioUnitario());

                psDetalle.addBatch(); // En vez de hacer un insert por cada libro, el batch va a acumulando los inserts para luego enviarlos todos juntos

            }
            psDetalle.executeBatch(); // Cuando el bucle termina de recorrer los libros, se envía una sola vez lo acumulado (es más eficiente que enviar uno por uno)

        } catch (SQLException e) {
            throw new RuntimeException("No se pudo insertar los detalles de la venta", e);
        }
    }

    @Override
    public void actualizar(Venta venta) {

    }

    @Override
    public void eliminar(Venta venta) {

    }

    @Override
    public Venta buscarPorId(int id) {
        return null;
    }

    @Override
    public List<Venta> listarTodos() {
        return null;
    }

    @Override
    protected Venta mapear(ResultSet rs) throws SQLException {
        return null;
    }

}
