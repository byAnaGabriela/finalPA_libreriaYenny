package dao;

import model.Genero;
import repository.GeneroRepository;

import java.sql.*;
import java.util.List;

public class GeneroRepositoryImpl extends RepositoryBase<Genero> implements GeneroRepository {

    //Conexión declarada en la clase padre(RepositoryBase)

    @Override
    public void insertar(Genero genero) {
        String sql = "INSERT INTO genero (nombre) VALUES (?)";

        // Preparo la consulta y le pido que devuelva el id generado automáticamente
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, genero.getNombre()); // Asigno el parámetro recibido en el ?
            ps.executeUpdate(); // Ejecuto la insercción en la BD

            // Tomo el id que se generó y para asignarlo en el objeto
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    genero.setId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("No se pudo insertar el género", e);
        }
    }

    @Override
    public void actualizar(Genero genero) {
        // Sentencia para actualizar usando el id
        String sql = "UPDATE genero SET nombre = ? WHERE id_genero = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, genero.getNombre()); // Asigno el primer ? (nombre)
            ps.setInt(2, genero.getId()); // Asigno el segundo ? (id)
            ps.executeUpdate(); // Ejecuto la actualización en la BD

        } catch (SQLException e) {
            throw new RuntimeException("No se pudo actualizar el género", e);
        }
    }

    @Override
    public void eliminar(Genero genero) {
        String sql = "DELETE FROM genero WHERE id_genero = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, genero.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("No se pudo eliminar el género", e);
        }
    }

    @Override
    public Genero buscarPorId(int id) {
        String sql = "SELECT * FROM genero WHERE id_genero = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id); // Reemplazo el ? con el id que recibo

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) { // Si encuentro un registro con ese id, creo el objeto con mapear y lo devuelvo
                    return mapear(rs);
                }
                return null; // Si no encuentro nada, devuelvo null
            }

        } catch (SQLException e) {
            throw new RuntimeException("No se pudo buscar el género por id", e);
        }
    }

    @Override
    public List<Genero> listarTodos() {
        return null;
    }

    @Override
    public boolean existeNombre(String nombre) {
        return false;
    }

    @Override
    protected Genero mapear(ResultSet rs) throws SQLException {
        // Convierto los datos que vienen de la BD en un objeto género
        return new Genero(
                rs.getInt("id_genero"),
                rs.getString("nombre"));
    }

}
