package dao;

import model.Propuesta;
import model.Usuario;
import model.enums.EstadoPropuesta;
import repository.PropuestaRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PropuestaRepositoryImpl extends RepositoryBase<Propuesta> implements PropuestaRepository {

    //Conexión declarada en la clase padre(RepositoryBase)

    @Override
    public List<Propuesta> listarPorEscritor(int idEscritor) {
        // Traigo todas las propuestas hechas por un escritor específico con su id
        String sql = "SELECT * FROM propuesta WHERE fk_id_escritor = ?";
        List<Propuesta> propuestas = new ArrayList<>(); // Creo una lista vacía para ir guardando las propuestas que encuentre

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idEscritor); // Reemplazo el ? con el id que recibí

            // Ejecuto la consulta y recorro los resultados obtenidos
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // Transformo cada fila en un objeto con mapear y lo agrego a la lista
                    propuestas.add(mapear(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("No se pudo buscar propuestas por escritor", e);
        }
        return propuestas; // Devuelvo la lista con las propuestas del escritor
    }

    @Override
    public List<Propuesta> listarPorEstado(EstadoPropuesta estado) {
        // Busca las propuestas según el estado que tengan
        String sql = "SELECT * FROM propuesta WHERE estado_propuesta = ?";
        List<Propuesta> propuestas = new ArrayList<>(); // Creo una lista para guardar las propuestas con el estado indicado

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, estado.name()); // Convierto el enum del estado a texto y lo asigno al ?

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    propuestas.add(mapear(rs)); // Transformo cada fila en objetos con mapear y los agrego a la lista
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("No se pudo buscar propuestas por estado", e);
        }
        return propuestas; // Devuelvo la lista
    }

    @Override
    public void insertar(Propuesta propuesta) {
        String sql = "INSERT INTO propuesta (titulo, descripcion, fecha_creacion, estado_propuesta, fk_id_escritor, fk_id_editor) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, propuesta.getTitulo());
            ps.setString(2, propuesta.getDescripcion());
            ps.setTimestamp(3, Timestamp.valueOf(propuesta.getFechaCreacion()));
            ps.setString(4, propuesta.getEstado().name());

            // Asigno los id de las relaciones con otras tablas
            ps.setInt(5, propuesta.getEscritor().getId());
            // Como el editor puede ser nulo pregunto primero si existe para asignarlo y si no lo dejo como null
            if (propuesta.getEditor() != null) {
                ps.setInt(6, propuesta.getEditor().getId());
            } else {
                ps.setNull(6, Types.INTEGER);
            }
            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    propuesta.setId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("No se pudo insertar la propuesta", e);
        }
    }

    @Override
    public void actualizar(Propuesta propuesta) {
        String sql = "UPDATE propuesta SET titulo = ?, descripcion = ?, estado_propuesta = ?, fk_id_editor = ? WHERE id_propuesta = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, propuesta.getTitulo());
            ps.setString(2, propuesta.getDescripcion());
            ps.setString(3, propuesta.getEstado().name());

            if (propuesta.getEditor() != null) {
                ps.setInt(4, propuesta.getEditor().getId());
            } else {
                ps.setNull(4, Types.INTEGER);
            }

            ps.setInt(5, propuesta.getId());
            ps.executeUpdate();

        }  catch (SQLException e) {
            throw new RuntimeException("No se pudo actualizar la propuesta", e);
        }
    }

    @Override
    public void eliminar(Propuesta propuesta) {
        String sql = "DELETE FROM propuesta WHERE id_propuesta = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, propuesta.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("No se pudo eliminar la propuesta", e);
        }
    }

    @Override
    public Propuesta buscarPorId(int id) {
        String sql = "SELECT * FROM propuesta WHERE id_propuesta = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
                return  null;
            }

        } catch (SQLException e) {
            throw new RuntimeException("No se pudo buscar la propuesta por id", e);
        }
    }

    @Override
    public List<Propuesta> listarTodos() {
        String sql = "SELECT * FROM propuesta";
        List<Propuesta> propuestas = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                propuestas.add(mapear(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("No se pudo buscar todas las propuestas", e);
        }
        return propuestas;
    }

    @Override
    protected Propuesta mapear(ResultSet rs) throws SQLException {
        Usuario escritor = new UsuarioRepositoryImpl().buscarPorId(rs.getInt("fk_id_escritor"));

        int idEditor = rs.getInt("fk_id_editor");
        Usuario editor = null;
        if (!rs.wasNull()) {
            editor = new UsuarioRepositoryImpl().buscarPorId(idEditor);
        }

        return new Propuesta(
                rs.getInt("id_propuesta"),
                rs.getString("titulo"),
                rs.getString("descripcion"),
                rs.getTimestamp("fecha_creacion").toLocalDateTime(),
                escritor,
                editor, // Puede ser null
                EstadoPropuesta.valueOf(rs.getString("estado_propuesta")));
    }

}
