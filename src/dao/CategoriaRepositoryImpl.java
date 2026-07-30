package dao;

import model.Categoria;
import repository.CategoriaRepository;

import java.sql.*;
import java.util.List;

public class CategoriaRepositoryImpl extends RepositoryBase<Categoria> implements CategoriaRepository {

    //Conexión declarada en la clase padre(RepositoryBase)

    @Override
    public void insertar(Categoria categoria) {
        String sql = "INSERT INTO categoria (nombre) VALUES (?)";

        // Preparo la consulta y le pido que devuelva el id generado automáticamente
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, categoria.getNombre()); // Asigno el parámetro recibido en el ?
            ps.executeUpdate(); // Ejecuto la inserción en la BD

            // Tomo el id que se generó, para asignarlo en el objeto
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    categoria.setId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("No se pudo insertar la categoría", e);
        }
    }

    @Override
    public void actualizar(Categoria categoria) {
        // Actualiza el nombre de una categoría específica usando el id
        String sql = "UPDATE categoria SET nombre = ? WHERE id_categoria = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, categoria.getNombre()); // Asigno el primer ? (nombre)
            ps.setInt(2, categoria.getId()); // Asigno el segundo ? (id)
            ps.executeUpdate(); // Ejecuto la actualización después de reemplazar los parámetros

        } catch (SQLException e) {
            throw new RuntimeException("No se pudo actualizar la categoría", e);
        }
    }

    @Override
    public void eliminar(Categoria categoria) {
        // Consulta para borrar una categoría específica usando el id
        String sql = "DELETE FROM categoria WHERE id_categoria = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, categoria.getId()); // Asigno el id al ? de la consulta
            ps.executeUpdate(); // Se ejecuta la instrucción para eliminar la editorial

        } catch (SQLException e) {
            throw new RuntimeException("No se pudo eliminar la categoría", e);
        }
    }

    @Override
    public Categoria buscarPorId(int id) {
        String sql = "SELECT * FROM categoria WHERE id_categoria = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id); // Reemplazo el ? con el id que recibo

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) { // Si encuentro un registro con ese id, creo el objeto con mapear y lo devuelvo
                    return mapear(rs);
                }
                return null; // Si no encuentro nada, devuelvo null
            }

        } catch (SQLException e) {
            throw new RuntimeException("No se pudo buscar la categoría por id", e);
        }
    }

    @Override
    public List<Categoria> listarTodos() {
        return null;
    }

    @Override
    public boolean existeNombre(String nombre) {
        return false;
    }

    @Override
    protected Categoria mapear(ResultSet rs) throws SQLException {
        // Convierto los datos que vienen de la BD en un objeto categoria
        return new Categoria(
                rs.getInt("id_categoria"),
                rs.getString("nombre"));
    }

}
