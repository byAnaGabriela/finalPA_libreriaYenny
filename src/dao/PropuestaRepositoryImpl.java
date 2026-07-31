package dao;

import model.Propuesta;
import model.Usuario;
import model.enums.EstadoPropuesta;
import repository.PropuestaRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PropuestaRepositoryImpl extends RepositoryBase<Propuesta>  implements PropuestaRepository {

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
        
    }

    @Override
    public void actualizar(Propuesta propuesta) {

    }

    @Override
    public void eliminar(Propuesta propuesta) {

    }

    @Override
    public Propuesta buscarPorId(int id) {
        return null;
    }

    @Override
    public List<Propuesta> listarTodos() {
        return null;
    }

    @Override
    protected Propuesta mapear(ResultSet rs) throws SQLException {
        return null;
    }

}
