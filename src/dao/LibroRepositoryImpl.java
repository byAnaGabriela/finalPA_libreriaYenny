package dao;

import model.*;
import repository.LibroRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibroRepositoryImpl extends RepositoryBase<Libro>  implements LibroRepository {

    //Conexión declarada en la clase padre(RepositoryBase)

    @Override
    public List<Libro> buscarPorTitulo(String titulo) {
        // Uso LIKE para buscar libros donde el titulo coincida parcialmente con el texto busco
        String sql = "SELECT * FROM libro WHERE titulo LIKE ?";
        List<Libro> libros = new ArrayList<>(); // Creo una lista vacía donde guardaré los libros que coincidan

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + titulo + "%"); // Coloco los % envolviendo el título para buscar coincidencias en cualquier parte del texto

            try (ResultSet rs = ps.executeQuery()) { // Ejecuto la consulta
                // Recorro cada fila encontrada y voy agregando a la lista los objetos transformados con mapear
                while (rs.next()) {
                    libros.add(mapear(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("No se pudo buscar el libro por título", e);
        }
        return libros; // Devuelvo la lista con los libros encontrados
    }

    @Override
    public List<Libro> listarPorAutor(int idAutor) {
        // Trae libros escritos por un autor específico usando el id
        String sql = "SELECT * FROM libro WHERE fk_id_autor = ?";
        List<Libro> libros = new ArrayList<>(); // Creo una lista vacía para ir guardando los libros del autor

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idAutor); // Reemplazo el ? con el id que recibí

            // Ejecuto y recorro los resultados
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    libros.add(mapear(rs)); // Transformo cada fila en objeto con mapear y lo agrego a la lista
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("No se pudo buscar el libro por autor", e);
        }
        return libros;
    }

    @Override
    public boolean existeIsbn(String isbn) {
        // Si el isbn existe la BD me devuelve un 1, es más eficiente a que me traiga los datos de la tabla
        String sql = "SELECT 1 FROM libro WHERE isbn = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, isbn); // Reemplazo el ? con el isbn que quiero validar

            // Ejecuto la consulta y reviso si obtuve algún resultado
            try (ResultSet rs = ps.executeQuery()) {
                // Si hay un registro, el isbn existe y devuelve true, si no, devuelve false
                return rs.next();
            }

        } catch (SQLException e) {
            throw new RuntimeException("No se pudo verificar la existencia del isbn", e);
        }
    }

    @Override
    public void insertar(Libro libro) {

    }

    @Override
    public void actualizar(Libro libro) {

    }

    @Override
    public void eliminar(Libro libro) {

    }

    @Override
    public Libro buscarPorId(int id) {
        return null;
    }

    @Override
    public List<Libro> listarTodos() {
        return null;
    }

    @Override
    protected Libro mapear(ResultSet rs) throws SQLException {
        return null;
    }

}
