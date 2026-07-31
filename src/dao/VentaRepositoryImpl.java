package dao;

import dto.EscritorRendimientoDTO;
import dto.LibroVentaDTO;
import model.DetalleVenta;
import model.Usuario;
import model.Venta;
import model.enums.MetodoPago;
import repository.DetalleVentaRepository;
import repository.VentaRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
        // La venta como tal no debería modificarse, pero puede que haya equivocación con el metodo de pago asi que eso es lo que se puede actualizar
        String sql = "UPDATE venta SET metodo_pago = ? WHERE id_venta = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, venta.getMetodoPago().name());
            ps.setInt(2, venta.getId());
            ps.executeUpdate();

        }  catch (SQLException e) {
            throw new RuntimeException("No se pudo actualizar el método de pago de la venta", e);
        }
    }

    @Override
    public void eliminar(Venta venta) {
        String sql = "DELETE FROM venta WHERE id_venta = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, venta.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("No se pudo eliminar la venta", e);
        }
    }

    @Override
    public Venta buscarPorId(int id) {
        String sql = "SELECT * FROM venta WHERE id_venta = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    return mapear(rs);
                }
                return null;
            }
        }  catch (SQLException e) {
            throw new RuntimeException("No se pudo buscar la venta por id", e);
        }
    }

    @Override
    public List<Venta> listarTodos() {
        String sql = "SELECT * FROM venta";
        List<Venta> ventas = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ventas.add(mapear(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("No se pudo listar todas las ventas", e);
        }
        return ventas;
    }

    @Override
    protected Venta mapear(ResultSet rs) throws SQLException {
        Usuario vendedor = new UsuarioRepositoryImpl().buscarPorId(rs.getInt("fk_id_vendedor"));
        int idVenta = rs.getInt("id_venta");
        List<DetalleVenta> detalleVentas = new DetalleVentaRepository().listarPorVenta(idVenta);

        return new Venta(
                idVenta,
                rs.getTimestamp("fecha_venta").toLocalDateTime(),
                rs.getBigDecimal("precio_total"),
                rs.getBigDecimal("descuento"),
                MetodoPago.valueOf(rs.getString("metodo_pago")),
                vendedor,
                detalles
        );
    }

}
