package dao;

import model.Genero;
import repository.GeneroRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GeneroRepositoryImpl extends RepositoryBase<Genero> implements GeneroRepository {

    //Conexión declarada en la clase padre(RepositoryBase)

    @Override
    public void insertar(Genero genero) {
        String sql = "INSERT INTO genero (nombre) VALUES (?)";

        // Preparo la consulta y le pido que devuelva el id generado automáticamente
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, genero.getNombre()); // Asigno el parámetro recibido en el ?
            ps.executeUpdate(); // Ejecuto la inserción en la BD

            // Tomo el id que se generó, para asignarlo en el objeto
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
        // Consulta para borrar un idioma específico usando el id
        String sql = "DELETE FROM genero WHERE id_genero = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, genero.getId()); // Asigno el id al ? de la consulta
            ps.executeUpdate(); // Se ejecuta la instrucción para eliminar el idioma

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
        String sql = "SELECT * FROM genero";
        List<Genero> generos = new ArrayList<>(); // Creo la lista vacía donde se guardarán los generos

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) { // Preparo y ejecuto la consulta

            // Recorro cada fila que devuelve la BD mientras haya registros
            while (rs.next()) {
                generos.add(mapear(rs)); // Transformo cada fila en un objeto con mapear y lo agrego a la BD
            }

        } catch (SQLException e) {
            throw new RuntimeException("No se pudo buscar todos los géneros", e);
        }
        return generos; // Devuelvo la lista con los géneros agregados (Si no había registros va a estar vacía)
    }

    @Override
    public boolean existeNombre(String nombre) {
        // Si el nombre existe la BD me devuelve un 1, es más eficiente a que me traiga los datos de la tabla
        String sql = "SELECT 1 FROM genero WHERE nombre = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, nombre); // Reemplazo el ? con el nombre que recibo y quiero validar

            // Ejecuto la consulta y reviso si obtuve algún resultado
            try (ResultSet rs = ps.executeQuery()) {
                // Si hay un registro con ese nombre, devuelve true, de lo contrario devuelve false
                return rs.next();
            }

        } catch (SQLException e) {
            throw new RuntimeException("No se pudo validar la existencia del genero", e);
        }
    }

    @Override
    protected Genero mapear(ResultSet rs) throws SQLException {
        // Convierto los datos que vienen de la BD en un objeto género
        return new Genero(
                rs.getInt("id_genero"),
                rs.getString("nombre"));
    }

}
