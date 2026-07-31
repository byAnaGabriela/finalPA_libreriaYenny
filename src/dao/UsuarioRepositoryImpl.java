package dao;

import model.Usuario;
import model.enums.Rol;
import model.enums.EstadoUsuario;
import repository.UsuarioRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepositoryImpl extends RepositoryBase<Usuario> implements UsuarioRepository {

    //Conexión declarada en la clase padre(RepositoryBase)

    @Override
    public Usuario buscarPorNombreUsuario(String nombreUsuario) {
        String sql = "SELECT * FROM usuario WHERE nombre_usuario = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, nombreUsuario);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
                return  null;
            }

        } catch (SQLException e) {
            throw new RuntimeException("No se pudo buscar el usuario por nombre de usuario", e);
        }
    }

    @Override
    public boolean existeMail(String mail) {
        String sql = "SELECT 1 FROM usuario WHERE mail = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, mail);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            throw new RuntimeException("No se pudo verificar la existencia del mail", e);
        }
    }

    @Override
    public boolean existeDni(String dni) {
        String sql = "SELECT 1 FROM usuario WHERE dni = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, dni);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            throw new RuntimeException("No se pudo verificar la existencia del dni", e);
        }
    }

    @Override
    public void insertar(Usuario usuario) {
        String sql = "INSERT INTO usuario (rol, nombre, apellido, dni, celular, mail, nombre_usuario, contrasena, fecha_registro, estado_usuario) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, usuario.getRol().name());
            ps.setString(2, usuario.getNombre());
            ps.setString(3, usuario.getApellido());
            ps.setString(4, usuario.getDni());
            ps.setString(5, usuario.getCelular());
            ps.setString(6, usuario.getMail());
            ps.setString(7, usuario.getNombreUsuario());
            ps.setString(8, usuario.getContrasena());
            ps.setTimestamp(9, Timestamp.valueOf(usuario.getFechaRegistro()));
            ps.setString(10, usuario.getEstado().name());

            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    usuario.setId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("No se pudo insertar el usuario", e);
        }
    }

    @Override
    public void actualizar(Usuario usuario) {
        String sql = "UPDATE usuario SET rol = ?, nombre = ?, apellido = ?, dni = ?, celular = ?, mail = ?, nombre_usuario = ?, contrasena = ?, estado_usuario = ? WHERE id_usuario = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, usuario.getRol().name());
            ps.setString(2, usuario.getNombre());
            ps.setString(3, usuario.getApellido());
            ps.setString(4, usuario.getDni());
            ps.setString(5, usuario.getCelular());
            ps.setString(6, usuario.getMail());
            ps.setString(7, usuario.getNombreUsuario());
            ps.setString(8, usuario.getContrasena());
            ps.setString(9, usuario.getEstado().name());
            ps.setInt(10, usuario.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("No se pudo actualizar el usuario", e);
        }
    }

    @Override
    public void eliminar(Usuario usuario) {
        // En este caso en vez de eliminar literalmente al usuario de la bd, solo le cambio su estado a "eliminado"
        // Así queda registro en la BD de todos los usuarios del sistema, estén activos o no
        String sql = "UPDATE usuario SET estado_usuario = ? WHERE id_usuario = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, EstadoUsuario.ELIMINADO.name());
            ps.setInt(2, usuario.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("No se pudo eliminar el usuario", e);
        }
    }

    @Override
    public Usuario buscarPorId(int id) {
        String sql = "SELECT * FROM usuario WHERE id_usuario = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
                return  null;
            }

        } catch (SQLException e) {
            throw new RuntimeException("No se pudo buscar el usuario por id", e);
        }
    }

    @Override
    public List<Usuario> listarTodos() {
        String  sql = "SELECT * FROM usuario";
        List<Usuario> usuarios = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                usuarios.add(mapear(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("No se pudo buscar todos los usuarios", e);
        }
        return usuarios;
    }

    @Override
    protected Usuario mapear(ResultSet rs) throws SQLException {
        return new Usuario(
                rs.getInt("id_usuario"),
                Rol.valueOf(rs.getString("rol")),
                rs.getString("nombre"),
                rs.getString("apellido"),
                rs.getString("dni"),
                rs.getString("celular"),
                rs.getString("mail"),
                rs.getString("nombre_usuario"),
                rs.getString("contrasena"),
                rs.getTimestamp("fecha_registro").toLocalDateTime(),
                EstadoUsuario.valueOf(rs.getString("estado_usuario")));
    }

}
