package dao;

import dto.EscritorRendimientoDTO;
import dto.LibroVentaDTO;
import model.*;
import model.enums.MetodoPago;
import repository.DetalleVentaRepository;
import repository.VentaRepository;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VentaRepositoryImpl extends RepositoryBase<Venta> implements VentaRepository {

    @Override
    public List<Venta> listarPorVendedor(int idVendedor) {
        String sql = "SELECT * FROM venta WHERE fk_id_vendedor = ?";
        List<Venta> ventas = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idVendedor);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ventas.add(mapear(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo listar las ventas por vendedor", e);
        }
        return ventas;
    }

    @Override
    public List<Venta> listarPorFecha(LocalDateTime desde, LocalDateTime hasta) {
        String sql = "SELECT * FROM venta WHERE fecha_venta BETWEEN ? AND ?"; // Busca en un rango específico
        List<Venta> ventas = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setTimestamp(1, Timestamp.valueOf(desde));
            ps.setTimestamp(2, Timestamp.valueOf(hasta));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ventas.add(mapear(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo listar las ventas por fecha", e);
        }
        return ventas;
    }

    @Override
    public List<LibroVentaDTO> obtenerLibrosMasVendidos(int anio, int mes) {
        // YEAR y MONTH son funciones de MySQL que extraen el año y el mes de una columna de fecha
        String sql = "SELECT l.id_libro, SUM(vl.cantidad_vendida) AS cantidad, " +
                     "SUM(vl.cantidad_vendida * vl.precio_unitario) AS ganancia " +
                     "FROM venta_libro vl " +
                     "JOIN venta v ON vl.fk_id_venta = v.id_venta " +
                     "JOIN libro l ON vl.fk_id_libro = l.id_libro " +
                     "WHERE YEAR(v.fecha_venta) = ? AND MONTH(v.fecha_venta) = ? " +
                     "GROUP BY l.id_libro " +
                     "ORDER BY cantidad DESC";

        List<LibroVentaDTO> resultado = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, anio);
            ps.setInt(2, mes);

            try (ResultSet rs = ps.executeQuery()) {
                LibroRepositoryImpl libroRepository = new LibroRepositoryImpl();
                // Recorro cada registro obtenido de la bd
                while (rs.next()) {
                    Libro libro = libroRepository.buscarPorId(rs.getInt("id_libro"));
                    resultado.add(new LibroVentaDTO(
                            libro,
                            rs.getInt("cantidad"),
                            rs.getBigDecimal("ganancia")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo buscar los libros más vendidos por fecha", e);
        }
        return resultado;
    }

    @Override
    public List<LibroVentaDTO> obtenerLibrosMasVendidos() {
        // Es la misma consulta que la de arriba pero no voy a filtrar por fecha específica, mostrará un ranking del más vendido al menos vendido
        String sql = "SELECT l.id_libro, SUM(vl.cantidad_vendida) AS cantidad, " +
                     "SUM(vl.cantidad_vendida * vl.precio_unitario) AS ganancia " +
                     "FROM venta_libro vl " +
                     "JOIN libro l ON vl.fk_id_libro = l.id_libro " +
                     "GROUP BY l.id_libro " +
                     "ORDER BY cantidad DESC";

        List<LibroVentaDTO> resultado = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            LibroRepositoryImpl libroRepository = new LibroRepositoryImpl();
            while (rs.next()) {
                Libro libro = libroRepository.buscarPorId(rs.getInt("id_libro"));
                resultado.add(new LibroVentaDTO(
                        libro,
                        rs.getInt("cantidad"),
                        rs.getBigDecimal("ganancia")
                ));
            }
        }  catch (SQLException e) {
            throw new RuntimeException("No se pudo buscar los libros más vendidos", e);
        }
        return resultado;
    }

    @Override
    // Traigo el rendimiento de todos los autores registrados en el sistema, ordenados de mayor a menos según las ganancias totales
    public List<EscritorRendimientoDTO> obtenerRendimientoAutores() {
        String sql = "SELECT a.id_autor, SUM(vl.cantidad_vendida) AS cantidad, " +
                     "SUM(vl.cantidad_vendida * vl.precio_unitario) AS ganancia " +
                     "FROM venta_libro vl " +
                     "JOIN libro l ON vl.fk_id_libro = l.id_libro " +
                     "JOIN autor a ON l.fk_id_autor = a.id_autor " +
                     "GROUP BY a.id_autor " +
                     "ORDER BY ganancia DESC";

        List<EscritorRendimientoDTO> resultado = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

                 AutorRepositoryImpl autorRepository = new AutorRepositoryImpl();
                 while (rs.next()) {
                     int idAutor = rs.getInt("id_autor");
                     Autor autor = autorRepository.buscarPorId(idAutor);
                     // Construyo el objeto dto combinando los datos generales del autor y el detalle por cada uno de sus libros
                     resultado.add(new EscritorRendimientoDTO(
                        autor,
                        rs.getInt("cantidad"),
                        rs.getBigDecimal("ganancia"),
                        obtenerDetallePorLibroDeAutor(idAutor)
                     ));
                 }
        } catch (SQLException e) {
                 throw new RuntimeException("No se pudo obtener el rendimiento de ventas de autores", e);
        }
        return resultado;
    }

    // Traigo el rendimiento de un único autor buscándolo con el id
    @Override
    public List<EscritorRendimientoDTO> obtenerGananciasPorAutor(int idEscritor) {
        Autor autor = new AutorRepositoryImpl().buscarPorUsuario(idEscritor);
        List<EscritorRendimientoDTO> resultado = new ArrayList<>();

        // Si no encuentro el autor asociado, devuelvo la lista vacía directamente
        if (autor == null) {
            return resultado;
        }

        // Libros que pertenecen únicamente a UN autor
        String sql = "SELECT SUM(vl.cantidad_vendida) AS cantidad, " +
                     "SUM(vl.cantidad_vendida * vl.precio_unitario) AS ganancia " +
                     "FROM venta_libro vl " +
                     "JOIN libro l ON vl.fk_id_libro = l.id_libro " +
                     "WHERE l.fk_id_autor = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, autor.getId());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Si el autor no tienen ninguna venta todavía devuelve null, entonces le asigno un 0
                    int cantidad = rs.getInt("cantidad");
                    if (rs.wasNull()) {
                        cantidad = 0;
                    }

                    // Si es null la reemplazo por 0
                    BigDecimal ganancia = rs.getBigDecimal("ganancia");
                    if (ganancia == null) {
                        ganancia = BigDecimal.ZERO;
                    }

                    // Construyo la lista con la información obtenida
                    resultado.add(new EscritorRendimientoDTO(
                            autor,
                            cantidad,
                            ganancia,
                            obtenerDetallePorLibroDeAutor(autor.getId())
                    ));
                }
            }
        } catch (SQLException e) {
            throw  new RuntimeException("No se pudo obtener el ganancias del autor", e);
        }
        return resultado;
    }


    private List<LibroVentaDTO> obtenerDetallePorLibroDeAutor(int idAutor) {
        // Consulta auxiliar para calcular cuantas unidades se vendieron de cada libro en particular y cuánto dinero generó cada uno, filtrado por el autor que recibo por parámetro
        String sql = "SELECT l.id_libro, SUM(vl.cantidad_vendida) AS cantidad, " +
                     "SUM(vl.cantidad_vendida * vl.precio_unitario) AS ganancia " +
                     "FROM venta_libro vl " +
                     "JOIN libro l ON vl.fk_id_libro = l.id_libro " +
                     "WHERE l.fk_id_autor = ? " +
                     "GROUP BY l.id_libro";
        List<LibroVentaDTO> detalle = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idAutor);

            try (ResultSet rs = ps.executeQuery()) {
                LibroRepositoryImpl libroRepository = new LibroRepositoryImpl();
                // Recorro cada fila que representa un libro del autor
                while (rs.next()) {
                    // Busco el libro completo con el id
                    Libro libro = libroRepository.buscarPorId(rs.getInt("id_libro"));
                    // Creo el objeto con todos los detalles
                    detalle.add(new LibroVentaDTO(
                            libro,
                            rs.getInt("cantidad"),
                            rs.getBigDecimal("ganancia")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo obtener detalle por libro del autor", e);
        }
        return detalle;
    }

    @Override
    public void insertar(Venta venta) {
        String sqlVenta = "INSERT INTO venta (fecha_venta, precio_total, descuento, metodo_pago, fk_id_vendedor) VALUES (?, ?, ?, ?, ?) ";

        try (PreparedStatement psVenta = connection.prepareStatement(sqlVenta, Statement.RETURN_GENERATED_KEYS)) {
            psVenta.setTimestamp(1, Timestamp.valueOf(venta.getFecha()));
            psVenta.setBigDecimal(2, venta.getPrecioTotal());
            psVenta.setBigDecimal(3, venta.getDescuento());
            psVenta.setString(4, venta.getMetodoPago().name());
            psVenta.setInt(5, venta.getVendedor().getId());
            psVenta.executeUpdate();

            try (ResultSet generatedKeys = psVenta.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    venta.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo insertar la venta", e);
        }

        // Inserto cada detalle usando su clase, en vez de hacer aquí el sql de la tabla intermedia (venta_libro)
        DetalleVentaRepositoryImpl detalleVentaRepository = new DetalleVentaRepositoryImpl();

            // Uso for para recorrer cada detalle que contiene la venta (es un detalle por cada libro distinto dentro de la misma venta)
            for (DetalleVenta detalle : venta.getDetalles()) {
                detalle.setVenta(venta);
                detalleVentaRepository.insertar(detalle);
            }
    }

    @Override
    public void actualizar(Venta venta) {
        // La venta como tal no debería modificarse, pero puede que haya equivocación con el metodo de pago asi que eso es lo que se puede actualizar
        String sql = "UPDATE venta SET metodo_pago = ? WHERE id_venta = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, venta.getMetodoPago().name());
            ps.setInt(2, venta.getId());
            ps.executeUpdate();

        }  catch (SQLException e) {
            throw new RuntimeException("No se pudo actualizar el método de pago de la venta", e);
        }
    }

    @Override
    public void eliminar(Venta venta) {
        String sql = "DELETE FROM venta WHERE id_venta = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, venta.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("No se pudo eliminar la venta", e);
        }
    }

    @Override
    public Venta buscarPorId(int id) {
        String sql = "SELECT * FROM venta WHERE id_venta = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    return mapear(rs);
                }
                return null;
            }
        }  catch (SQLException e) {
            throw new RuntimeException("No se pudo buscar la venta por id", e);
        }
    }

    @Override
    public List<Venta> listarTodos() {
        String sql = "SELECT * FROM venta";
        List<Venta> ventas = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ventas.add(mapear(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("No se pudo listar todas las ventas", e);
        }
        return ventas;
    }

    @Override
    protected Venta mapear(ResultSet rs) throws SQLException {
        Usuario vendedor = new UsuarioRepositoryImpl().buscarPorId(rs.getInt("fk_id_vendedor"));
        int idVenta = rs.getInt("id_venta");
        List<DetalleVenta> detalleVentas = new DetalleVentaRepositoryImpl().listarPorVenta(idVenta);

        return new Venta(
                idVenta,
                rs.getTimestamp("fecha_venta").toLocalDateTime(),
                rs.getBigDecimal("precio_total"),
                rs.getBigDecimal("descuento"),
                MetodoPago.valueOf(rs.getString("metodo_pago")),
                vendedor,
                detalleVentas
        );
    }

}
