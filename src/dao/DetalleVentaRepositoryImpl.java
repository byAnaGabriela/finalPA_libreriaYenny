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
    public void insertar(DetalleVenta entidad) {

    }

    @Override
    public void actualizar(DetalleVenta entidad) {

    }

    @Override
    public void eliminar(DetalleVenta entidad) {

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
