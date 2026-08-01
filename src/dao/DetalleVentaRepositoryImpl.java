package dao;

import model.DetalleVenta;
import model.Libro;
import repository.DetalleVentaRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DetalleVentaRepositoryImpl extends RepositoryBase<DetalleVenta> implements DetalleVentaRepository {

    @Override
    public List<DetalleVenta> listarPorVenta(int idVenta) {
        String sql = "SELECT * FROM vente_libro WHERE fk_id_venta = ?";
        List<DetalleVenta> detalles = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idVenta);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    detalles.add(mapear(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo listar los detalles por venta", e);
        }
        return detalles;
    }

    @Override
    public void insertar(DetalleVenta detalleVenta) {
        String sql = "INSERT INTO vente_libro (fk_id_venta, fk_id_libro, cantidad_vendida, precio_unitario) VALUES (?, ?, ?, ?)"; // Tabla intermedia entre venta y libro en la BD

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, detalleVenta.getVenta().getId());
            ps.setInt(2, detalleVenta.getLibro().getId());
            ps.setInt(3, detalleVenta.getCantidadVendida());
            ps.setBigDecimal(4, detalleVenta.getPrecioUnitario());

            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    detalleVenta.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo insertar los detalles de la venta", e);
        }
    }

    @Override
    public void actualizar(DetalleVenta detalleVenta) {
        String sql = "UPDATE venta_libro SET cantidad_vendida = ?, precio_unitario = ? WHERE id_venta_libro = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, detalleVenta.getCantidadVendida());
            ps.setBigDecimal(2, detalleVenta.getPrecioUnitario());
            ps.setInt(3, detalleVenta.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("No se pudo actualizar el detalle de la venta", e)
        }
    }

    @Override
    public void eliminar(DetalleVenta detalleVenta) {
        String sql = "DELETE FROM venta_libro WHERE id_venta_libro = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, detalleVenta.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("No se pudo eliminar el detalle de la venta", e);
        }
    }

    @Override
    public DetalleVenta buscarPorId(int id) {
        return null;
    }

    @Override
    public List<DetalleVenta> listarTodos() {
        return null;
    }

    @Override
    protected DetalleVenta mapear(ResultSet rs) throws SQLException {
        return null;
    }

}
