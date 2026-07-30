package dao;

import model.Idioma;
import repository.IdiomaRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IdiomaRepositoryImpl extends RepositoryBase<Idioma> implements IdiomaRepository {

    //Conexión declarada en la clase padre(RepositoryBase)

    @Override
    public void insertar(Idioma idioma) {
        String sql = "INSERT INTO idioma (nombre) VALUES (?)";

        // Preparo la consulta y le pido a la BD que me devulva el id que genere automáticamente
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, idioma.getNombre());
            ps.executeUpdate(); // Ejecuto el insert

            // Solicito el id que generó el insert
            try(ResultSet generatedKeys = ps.getGeneratedKeys()) {
                // Si la BD me devuelve un id nuevo, lo extraigo y lo asigno a mi objeto
                if (generatedKeys.next()) {
                    idioma.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo insertar el idioma", e);
        }
    }

    @Override
    public void actualizar(Idioma idioma) {
        // Actualiza el nombre de un idioma específico usando el id
        String sql = "UPDATE idioma SET nombre = ? WHERE id_idioma = ?";

        // Preparo la sentencia usando la conexión
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, idioma.getNombre());
            ps.setInt(2, idioma.getId());
            ps.executeUpdate(); // Ejecuto despues de reemplazar los parámetros

        } catch (SQLException e) {
            throw new RuntimeException("No se pudo actualizar el idioma", e);
        }
    }

    @Override
    public void eliminar(Idioma idioma) {
        // Consulta para borrar un idioma específico usando el id
        String sql = "DELETE FROM idioma WHERE id_idioma = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idioma.getId()); // Asigno el id al ? de la consulta
            ps.executeUpdate(); // Se ejecuta la instrucción para eliminar el idioma

        } catch (SQLException e) {
            throw new RuntimeException("No se pudo eliminar el idioma", e);
        }
    }

    @Override
    public Idioma buscarPorId(int id) {
        String sql = "SELECT * FROM idioma WHERE id_idioma = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            // Asigno el id que recibo como parámetro, para reemplazar el (?)
            ps.setInt(1, id);

            // Ejecuto la consulta y guardo los resultados obtenidos
            try(ResultSet rs = ps.executeQuery()) {
                // Verifico si la BD me devolvió algún registro
                if (rs.next()) {
                    // Convierto ese registro en un objeto Idioma usando el metodo mapear y lo devuelvo
                    return mapear(rs);
                }
                // Si no encuentro ningún idioma con ese id, devuelvo null
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException("No se pudo buscar el idioma por id", e);
        }
    }

    @Override
    public List<Idioma> listarTodos() {
        String sql = "SELECT * FROM idioma";
        List<Idioma> idiomas = new ArrayList<>(); // Lista vacía para guardar los idiomas que traiga la BD

        // Preparo y ejecuto la consulta
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            // Recorro fila por fila los resultados
            while (rs.next()) {
                // Convierto la fila actual en un objeto usando mapear y lo agrego a la lista
                idiomas.add(mapear(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("No se pudo buscar todos los idiomas", e);
        }
        return idiomas; // Devuelvo los idiomas que se guardaron en la lista (si no había ninguno, estará vacía)
    }

    @Override
    public boolean existeNombre(String nombre) {
        // Si el nombre existe la BD me devuelve un 1, es más eficiente a que me traiga los datos de la tabla
        String sql = "SELECT 1 FROM idioma WHERE nombre = ?";

        // Abro la conexión y preparo la sentencia
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            // Reemplazo el (?) con el nombre que recibí y quiero validar
            ps.setString(1, nombre);

            // Ejecuto la consulta y reviso si obtuve algún resultado
            try (ResultSet rs = ps.executeQuery()) {
                // Si hay un siguiente registro, significa que el nombre ya existe y devuelve true
                return rs.next();
            }

        } catch (SQLException e) {
            throw new RuntimeException("No se pudo validar la existencia del idioma", e);
        }
    }

    @Override
    protected Idioma mapear(ResultSet rs) throws SQLException {
        // Tomo la fila actual de los resultados de la BD y creo un nuevo objeto
        return new Idioma(
                rs.getInt("id_idioma"),
                rs.getString("nombre"));
    }

}
