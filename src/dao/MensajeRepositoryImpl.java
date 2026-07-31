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

    }

    @Override
    public void actualizar(Mensaje mensaje) {

    }

    @Override
    public void eliminar(Mensaje mensaje) {

    }

    @Override
    public Mensaje buscarPorId(int id) {
        return null;
    }

    @Override
    public List<Mensaje> listarTodos() {
        return null;
    }

    @Override
    protected Mensaje mapear(ResultSet rs) throws SQLException {
        return null;
    }

}
