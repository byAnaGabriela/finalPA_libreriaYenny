package dao;

import model.Editorial;
import repository.EditorialRepository;

import java.sql.*;
import java.util.List;

public class EditorialRepositoryImpl extends RepositoryBase<Editorial> implements EditorialRepository {

    //Conexión declarada en la clase padre(RepositoryBase)

    @Override
    public void insertar(Editorial editorial) {
        String sql = "INSERT INTO editorial (nombre) VALUES (?)";

        // Preparo la consulta y le pido que devuelva el id generado automáticamente
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, editorial.getNombre()); // Asigno el parámetro recibido en el ?
            ps.executeUpdate(); // Ejecuto la inserción en la BD

            // Tomo el id que se generó, para asignarlo en el objeto
            try(ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    editorial.setId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("No se pudo insertar la editorial", e);
        }
    }

    @Override
    public void actualizar(Editorial editorial) {
        // Actualiza el nombre de una editorial específica usando el id
        String sql = "UPDATE editorial SET nombre = ? WHERE id_editorial = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, editorial.getNombre()); // Asigno el primer ? (nombre)
            ps.setInt(2, editorial.getId()); // Asigno el segundo ? (id)
            ps.executeUpdate(); // Ejecuto la actualización después de reemplazar los parámetros

        } catch (SQLException e) {
            throw new RuntimeException("No se pudo actualizar la editorial", e);
        }
    }

    @Override
    public void eliminar(Editorial editorial) {
        // Consulta para borrar una editorial específico usando el id
        String sql = "DELETE FROM editorial WHERE id_editorial = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, editorial.getId()); // Asigno el id al ? de la consulta
            ps.executeUpdate(); // Se ejecuta la instrucción para eliminar la editorial

        } catch (SQLException e) {
            throw new RuntimeException("No se pudo eliminar la editorial", e);
        }
    }

    @Override
    public Editorial buscarPorId(int id) {
        String sql = "SELECT * FROM editorial WHERE id_editorial = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id); // Reemplazo el ? con el id que recibo

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) { // Si encuentro un registro con ese id, creo el objeto con mapear y lo devuelvo
                    return mapear(rs);
                }
                return null; // Si no encuentro nada, devuelvo null
            }

        } catch (SQLException e) {
            throw new RuntimeException("No se pudo buscar la editorial por id", e);
        }
    }

    @Override
    public List<Editorial> listarTodos() {
        return null;
    }

    @Override
    public boolean existeNombre(String nombre) {
        return false;
    }

    @Override
    protected Editorial mapear(ResultSet rs) throws SQLException {
        // Convierto los datos que vienen de la BD en un objeto editorial
        return new Editorial(
                rs.getInt("id_editorial"),
                rs.getString("nombre"));
    }

}
