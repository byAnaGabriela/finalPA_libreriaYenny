package dao;

import model.Borrador;
import model.Propuesta;
import repository.BorradorRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BorradorRepositoryImpl extends RepositoryBase<Borrador> implements BorradorRepository {

    //Conexión declarada en la clase padre(RepositoryBase)

    @Override
    public List<Borrador> listarPorPropuesta(int idPropuesta) {
        String sql = "SELECT * FROM borrador WHERE fk_id_propuesta = ?";
        List<Borrador> borradores = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idPropuesta);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    borradores.add(mapear(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("No se pudo listar todos los borradores por propuesta", e);
        }
        return borradores;
    }

    @Override
    public void insertar(Borrador borrador) {

    }

    @Override
    public void actualizar(Borrador borrador) {

    }

    @Override
    public void eliminar(Borrador borrador) {

    }

    @Override
    public Borrador buscarPorId(int id) {
        return null;
    }

    @Override
    public List<Borrador> listarTodos() {
        return null;
    }

    @Override
    protected Borrador mapear(ResultSet rs) throws SQLException {
        return null;
    }

}
