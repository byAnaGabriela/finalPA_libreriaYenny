package dao;

import model.Borrador;
import model.Propuesta;
import repository.BorradorRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BorradorRepositoryImpl extends RepositoryBase<Borrador> implements BorradorRepository {

    //Conexión declarada en la clase padre(RepositoryBase)

    @Override
    public List<Borrador> listarPorPropuesta(int idPropuesta) {
        String sql = "SELECT * FROM borrador WHERE fk_id_propuesta = ?";
        List<Borrador> borradores = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idPropuesta);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    borradores.add(mapear(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("No se pudo listar todos los borradores por propuesta", e);
        }
        return borradores;
    }

    @Override
    public void insertar(Borrador borrador) {
        String sql = "INSERT INTO borrador (version, ruta_archivo, fecha_subida, fk_id_propuesta) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, borrador.getVersion());
            ps.setString(2, borrador.getRutaArchivo());
            ps.setTimestamp(3, Timestamp.valueOf(borrador.getFechaSubida()));
            ps.setInt(4, borrador.getPropuesta().getId());

            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    borrador.setId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("No se pudo insertar el borrador", e);
        }
    }

    @Override
    public void actualizar(Borrador borrador) {
        String sql = "UPDATE borrador SET version = ?, ruta_archivo = ?, fk_id_propuesta = ? WHERE id_borrador = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, borrador.getVersion());
            ps.setString(2, borrador.getRutaArchivo());
            ps.setInt(3, borrador.getPropuesta().getId());
            ps.setInt(4, borrador.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("No se pudo actualizar el borrador", e);
        }
    }

    @Override
    public void eliminar(Borrador borrador) {
        String sql = "DELETE FROM borrador WHERE id_borrador = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, borrador.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("No se pudo eliminar el borrador", e);
        }
    }

    @Override
    public Borrador buscarPorId(int id) {
        String sql = "SELECT * FROM borrador WHERE id_borrador = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException("No se pudo buscar el borrador por id", e);
        }
    }

    @Override
    public List<Borrador> listarTodos() {
        String sql = "SELECT * FROM borrador";
        List<Borrador> borradores = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                borradores.add(mapear(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("No se pudo buscar todos los borradores", e);
        }
        return borradores;
    }

    @Override
    protected Borrador mapear(ResultSet rs) throws SQLException {
        Propuesta propuesta = new PropuestaRepositoryImpl().buscarPorId(rs.getInt("fk_id_propuesta"));

        return new Borrador(
                rs.getInt("id_borrador"),
                rs.getInt("version"),
                rs.getString("ruta_archivo"),
                rs.getTimestamp("fecha_subida").toLocalDateTime(),
                propuesta);
    }

}
