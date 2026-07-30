package dao;

import model.Autor;
import model.Usuario;
import repository.AutorRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AutorRepositoryImpl extends RepositoryBase<Autor> implements AutorRepository {

    //Conexión declarada en la clase padre(RepositoryBase)

    @Override
    public Autor buscarPorUsuario(int idUsuario) {
        // Consulta para buscar un autor usando el id del usuario vinculado
        String sql = "SELECT * FROM autor WHERE fk_id_usuario = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idUsuario); // Reemplazo el ? con el id recibido

            // Ejecuto la consulta
            try (ResultSet rs = ps.executeQuery()) {
                // Si encuentro un registro, lo transformo en objeto con mapear y lo devuelvo
                if (rs.next()) {
                    return mapear(rs);
                }
                // Si no encuentro ningún autor con ese usuario, devuelvo null
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo buscar el autor vinculado a usuario", e);
        }
    }

    @Override
    public void insertar(Autor autor) {
        String sql = "INSERT INTO autor (nombre, apellido, fk_id_usuario) VALUES (?, ?, ?)";

        // Preparo la consulta y pido el id generado
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // Asigno el nombre y apellido
            ps.setString(1, autor.getNombre());
            ps.setString(2, autor.getApellido());

            // El vínculo con un usuario del sistema no es obligatorio, puede ser null, entonces verifico
            // Si el autor tiene un usuario vinculado guardo su id, si no, envío null
            if (autor.getEscritorVinculado() != null) {
                ps.setInt(3, autor.getEscritorVinculado().getId());
            } else {
                ps.setNull(3, Types.INTEGER);
            }

            ps.executeUpdate(); // Ejecuto la inserción en la BD

            // Tomo el id generado para asignarlo a mi objeto
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    autor.setId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("No se pudo insertar el autor", e);
        }
    }

    @Override
    public void actualizar(Autor autor) {
        String sql = "UPDATE autor SET nombre = ?, apellido = ?, fk_id_usuario = ? WHERE id_autor = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)){
            // Actualizo el nombre y apellido
            ps.setString(1, autor.getNombre());
            ps.setString(2, autor.getApellido());

            // Si tiene un usuario vinculado lo asigno, si no, lo dejo null
            if (autor.getEscritorVinculado() != null) {
                ps.setInt(3, autor.getEscritorVinculado().getId());
            } else {
                ps.setNull(3, Types.INTEGER);
            }

            // Asigno el id del autor, para saber a que regístro aplicarle los cambios
            ps.setInt(4, autor.getId());
            ps.executeUpdate(); // Ejecuto la actualización

        } catch (SQLException e) {
            throw new RuntimeException("No se pudo actualizar el autor", e);
        }
    }

    @Override
    public void eliminar(Autor autor) {
        // Consulta para borrar un autor específico usando el id
        String sql = "DELETE FROM autor WHERE id_autor = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, autor.getId()); // Asigno el id autor que quiero eliminar
            ps.executeUpdate(); // Ejecuto la eliminación en la BD

        } catch (SQLException e) {
            throw new RuntimeException("No se pudo eliminar el autor", e);
        }
    }

    @Override
    public Autor buscarPorId(int id) {
        String sql = "SELECT * FROM autor WHERE id_autor = ?"; // Busco un autor específico con su id

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id); // Reemplazo el ? con el id recibido

            try (ResultSet rs = ps.executeQuery()) {
                // Si encuentro un registro con ese id, lo transformo en objeto con mapear y lo devuelvo
                if(rs.next()) {
                    return mapear(rs);
                }
                return null; // Si no encuentro nada con ese id, devuelvo null
            }

        } catch (SQLException e) {
            throw new RuntimeException("No se pudo buscar el autor por id", e);
        }
    }

    @Override
    public List<Autor> listarTodos() {
        String sql = "SELECT * FROM autor";
        List<Autor> autores = new ArrayList<>(); // Creo una lista vacía para guardar los autores que encuentre

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) { // Preparo y ejecuto la consulta

            // Recorro cada fila que me da la BD mientras haya registros
            while (rs.next()) {
                autores.add(mapear(rs)); // Transformo cada fila en un objeto autor y lo agrego a la lista
            }

        } catch (SQLException e) {
            throw new RuntimeException("No se pudo buscar todos los autores", e);
        }
        return autores;
    }

    @Override
    protected Autor mapear(ResultSet rs) throws SQLException {
        int idUsuario = rs.getInt("fk_id_usuario"); // Obtengo el id del usuario vinculado desde la BD

        // Inicializo el usuario vinculado como nulo por defecto
        Usuario escritorVinculado = null;
        // Si el valor que trajo la BD no es nulo, busco el usuario correspondiente usando su repositorio
        if (!rs.wasNull()) {
            escritorVinculado = new UsuarioRepositoryImpl().buscarPorId(idUsuario);
        }

        // Construyo y devuelvo el objeto con todos sus datos
        return new Autor(
                rs.getInt("id_autor"),
                rs.getString("nombre"),
                rs.getString("apellido"),
                escritorVinculado); // Puede tener uno vinculado o ser null
    }

}
