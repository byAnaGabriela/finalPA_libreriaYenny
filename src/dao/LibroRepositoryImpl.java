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
        String sql = "INSERT INTO libro (isbn, titulo, sinopsis, cantidad_paginas, precio, fecha_publicacion, cantidad_disponible, fk_id_autor, fk_id_editorial, fk_id_categoria, fk_id_genero, fk_id_idioma, fk_id_propuesta_origen) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // Preparo la consulta y pido que me devuelva el id generado
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // Asigno los valores básicos
            ps.setString(1, libro.getIsbn());
            ps.setString(2, libro.getTitulo());
            ps.setString(3, libro.getSinopsis());
            ps.setInt(4, libro.getCantidadPaginas());
            ps.setBigDecimal(5, libro.getPrecio());
            ps.setDate(6, Date.valueOf(libro.getFechaPublicacion()));
            ps.setInt(7, libro.getCantidadDisponible());

            // Asigno los id de las relaciones con otras tablas
            ps.setInt(8, libro.getAutor().getId());
            ps.setInt(9, libro.getEditorial().getId());
            ps.setInt(10, libro.getCategoria().getId());
            ps.setInt(11, libro.getGenero().getId());
            ps.setInt(12, libro.getIdioma().getId());

            // No todos los libros provienen de una propuesta publicada por la librería Yenny, con lo cual este parámetro puede ser null
            // Verifico si el libro tienen una propuesta de origen asociada, si existe guardo el id, si no, lo guardo como null
            if (libro.getPropuestaOrigen() != null) {
                ps.setInt(13, libro.getPropuestaOrigen().getId());
            } else {
                ps.setNull(13, Types.INTEGER);
            }

            ps.executeUpdate(); // Ejecuto la inserción en la BD

            // Tomo el id generado y lo asigno en mi objeto
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    libro.setId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("No se pudo insertar el libro", e);
        }
    }

    @Override
    public void actualizar(Libro libro) {
        String sql = "UPDATE libro SET isbn = ?, titulo = ?, sinopsis = ?, cantidad_paginas = ?, precio = ?, fecha_publicacion = ?, cantidad_disponible = ?, fk_id_autor = ?, fk_id_editorial = ?, fk_id_categoria = ?, fk_id_genero = ?, fk_id_idioma = ?, fk_id_propuesta_origen = ? WHERE id_libro ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            // Asigno los nuevos valores del libro
            ps.setString(1, libro.getIsbn());
            ps.setString(2, libro.getTitulo());
            ps.setString(3, libro.getSinopsis());
            ps.setInt(4, libro.getCantidadPaginas());
            ps.setBigDecimal(5, libro.getPrecio());
            ps.setDate(6, Date.valueOf(libro.getFechaPublicacion()));
            ps.setInt(7, libro.getCantidadDisponible());

            // Asigno los id de las relaciones actualizadas
            ps.setInt(8, libro.getAutor().getId());
            ps.setInt(9, libro.getEditorial().getId());
            ps.setInt(10, libro.getCategoria().getId());
            ps.setInt(11, libro.getGenero().getId());
            ps.setInt(12, libro.getIdioma().getId());

            // Si tiene una propuesta de origen la asigno, si no, es null
            if (libro.getPropuestaOrigen() != null) {
                ps.setInt(13, libro.getPropuestaOrigen().getId());
            } else {
                ps.setNull(13, Types.INTEGER);
            }

            // Asigno el id del libro, para saber a que registro se aplican los cambios
            ps.setInt(14, libro.getId());
            ps.executeUpdate(); // Ejecuto la actualización

        } catch (SQLException e) {
            throw new RuntimeException("No se pudo actualizar el libro", e);
        }
    }

    @Override
    public void eliminar(Libro libro) {
        String sql = "DELETE FROM libro WHERE id_libro = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, libro.getId()); // Asigno el id del libro que quiero eliminar
            ps.executeUpdate(); // Ejecuto la eliminación de la BD

        } catch (SQLException e) {
            throw new RuntimeException("No se pudo eliminar el libro", e);
        }
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
