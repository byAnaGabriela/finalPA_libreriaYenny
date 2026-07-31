package dao;

import model.Mensaje;
import model.Propuesta;
import model.Usuario;
import repository.MensajeRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MensajeRepositoryImpl extends RepositoryBase<Mensaje> implements MensajeRepository {

    //Conexión declarada en la clase padre(RepositoryBase)

    @Override
    public List<Mensaje> listarPorPropuesta(int idPropuesta) {
        String sql = "SELECT * FROM mensaje WHERE fk_id_propuesta = ?";
        List<Mensaje> mensajes = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idPropuesta);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    mensajes.add(mapear(rs));
                }
            }
        }  catch (SQLException e) {
            throw new RuntimeException("No se pudo buscar los mensajes de la propuesta", e);
        }
        return mensajes;
    }

    @Override
    public void insertar(Mensaje mensaje) {
        String sql = "INSERT INTO mensaje (texto, fecha_envio, fk_id_propuesta, fk_id_usuario, fk_id_mensaje_padre) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, mensaje.getTexto());
            ps.setTimestamp(2, Timestamp.valueOf(mensaje.getFechaEnvio()));
            ps.setInt(3, mensaje.getPropuesta().getId());
            ps.setInt(4, mensaje.getUsuario().getId());

            if (mensaje.getMensajePadre() != null) {
                ps.setInt(5, mensaje.getMensajePadre().getId());
            } else {
                ps.setNull(5, Types.INTEGER);
            }

            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    mensaje.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo insertar el mensaje", e);
        }
    }

    @Override
    public void actualizar(Mensaje mensaje) {
        String sql = "UPDATE mensaje SET texto = ? WHERE id_mensaje = ?"; // Solo se podrá actualizar el texto del mensaje y nada más

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, mensaje.getTexto());
            ps.setInt(2, mensaje.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("No se pudo actualizar el mensaje", e);
        }
    }

    @Override
    public void eliminar(Mensaje mensaje) {
        String sql = "DELETE FROM mensaje WHERE id_mensaje = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, mensaje.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("No se pudo eliminar el mensaje", e);
        }
    }

    @Override
    public Mensaje buscarPorId(int id) {
        String sql = "SELECT * FROM mensaje WHERE id_mensaje = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
                return  null;
            }

        } catch (SQLException e) {
            throw new RuntimeException("No se pudo buscar el mensaje por id", e);
        }
    }

    @Override
    public List<Mensaje> listarTodos() {
        String sql = "SELECT * FROM mensaje";
        List<Mensaje> mensajes = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                mensajes.add(mapear(rs));
            }

        }  catch (SQLException e) {
            throw new RuntimeException("No se pudo buscar todos los mensajes", e);
        }
        return mensajes;
    }

    @Override
    protected Mensaje mapear(ResultSet rs) throws SQLException {
        Propuesta propuesta = new PropuestaRepositoryImpl().buscarPorId(rs.getInt("fk_id_propuesta"));
        Usuario usuario = new UsuarioRepositoryImpl().buscarPorId(rs.getInt("fk_id_usuario"));

        int idMensajePadre = rs.getInt("fk_id_mensaje_padre");
        Mensaje mendajePadre = null;
        if (!rs.wasNull()) {
            mendajePadre = buscarPorId(idMensajePadre);
        }

        return new Mensaje(
                rs.getInt("id_mensaje"),
                rs.getString("texto"),
                rs.getTimestamp("fecha_envio").toLocalDateTime(),
                usuario,
                propuesta,
                mendajePadre);
    }

}
