package dao;

import model.Editorial;
import repository.EditorialRepository;

import java.sql.*;
import java.util.ArrayList;
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
        String sql = "SELECT * FROM editorial";
        List<Editorial> editoriales = new ArrayList<>(); // Creo la lista vacía donde se guardarán las editoriales

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) { // Preparo y ejecuto la consulta

            // Recorro cada fila que devuelve la BD mientras haya registros
            while (rs.next()) {
                editoriales.add(mapear(rs)); // Transformo cada fila en un objeto con mapear y lo agrego a la BD
            }

        } catch (SQLException e) {
            throw new RuntimeException("No se pudo buscar todas las editoriales", e);
        }
        return editoriales; // Devuelvo la lista con los géneros agregados (Si no había registros va a estar vacía)
    }

    @Override
    public boolean existeNombre(String nombre) {
        // Si el nombre existe la BD me devuelve un 1, es más eficiente a que me traiga los datos de la tabla
        String sql = "SELECT 1 FROM editorial WHERE nombre = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, nombre); // Reemplazo el ? con el nombre que recibo y quiero validar

            // Ejecuto la consulta y reviso si obtuve algún resultado
            try (ResultSet rs = ps.executeQuery()) {
                // Si hay un registro con ese nombre, devuelve true, de lo contrario devuelve false
                return rs.next();
            }

        } catch (SQLException e) {
            throw new RuntimeException("No se pudo validar la existencia de la editorial", e);
        }
    }

    @Override
    protected Editorial mapear(ResultSet rs) throws SQLException {
        // Convierto los datos que vienen de la BD en un objeto editorial
        return new Editorial(
                rs.getInt("id_editorial"),
                rs.getString("nombre"));
    }

}
