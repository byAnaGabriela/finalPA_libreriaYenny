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

    }

    @Override
    public Editorial buscarPorId(int id) {
        return null;
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
        return null;
    }

}
