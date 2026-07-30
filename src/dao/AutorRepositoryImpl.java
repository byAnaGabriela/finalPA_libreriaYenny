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

    }

    @Override
    public void actualizar(Autor autor) {

    }

    @Override
    public void eliminar(Autor autor) {

    }

    @Override
    public Autor buscarPorId(int id) {
        return null;
    }

    @Override
    public List<Autor> listarTodos() {
        return null;
    }

    @Override
    protected Autor mapear(ResultSet rs) throws SQLException {
        return null;
    }

}
