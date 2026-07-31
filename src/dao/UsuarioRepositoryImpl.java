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
    public void insertar(Usuario entidad) {

    }

    @Override
    public void actualizar(Usuario entidad) {

    }

    @Override
    public void eliminar(Usuario entidad) {

    }

    @Override
    public Usuario buscarPorId(int id) {
        return null;
    }

    @Override
    public List<Usuario> listarTodos() {
        return  null;
    }

    @Override
    protected Usuario mapear(ResultSet rs) throws SQLException {
        return null;
    }

}
