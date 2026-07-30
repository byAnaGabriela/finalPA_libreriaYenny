package dao;

import model.Idioma;
import repository.IdiomaRepository;

import java.sql.*;
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

        // Preparo la sentencia usando la conexión actual
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

    }

    @Override
    public Idioma buscarPorId(int id) {
        return null;
    }

    @Override
    public List<Idioma> listarTodos() {
        return null;
    }

    @Override
    public boolean existeNombre(String nombre) {
        return false;
    }

    @Override
    protected Idioma mapear(ResultSet rs) throws SQLException {
        return null;
    }

}
