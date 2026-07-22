package model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Libro {

    private int id;
    private String isbn;
    private String titulo;
    private String sinopsis;
    private int cantidadPaginas;
    private BigDecimal precio;
    private LocalDate fechaPublicacion;
    private int cantidadDisponible;
    private int stockMinimo;
    private Editorial editorial;
    private Categoria categoria;
    private Genero genero;
    private Idioma idioma;
    private List<Autor> autores;
    private Propuesta propuestaOrigen;

    //Constructores☆゜・。。・゜゜・。。・゜★゜・。。・゜゜・。。・゜☆゜・。。・゜゜・。。・゜★
    public Libro() {
    }

    public Libro(String isbn, String titulo, String sinopsis, int cantidadPaginas, BigDecimal precio, LocalDate fechaPublicacion, int cantidadDisponible, Editorial editorial, Categoria categoria, Genero genero, Idioma idioma, List<Autor> autores, Propuesta propuestaOrigen) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.sinopsis = sinopsis;
        this.cantidadPaginas = cantidadPaginas;
        this.precio = precio;
        this.fechaPublicacion = fechaPublicacion;
        this.cantidadDisponible = cantidadDisponible;
        this.stockMinimo = 5;
        this.editorial = editorial;
        this.categoria = categoria;
        this.genero = genero;
        this.idioma = idioma;
        this.autores = autores;
        this.propuestaOrigen = propuestaOrigen;
    }

    public Libro(int id, String isbn, String titulo, String sinopsis, int cantidadPaginas, BigDecimal precio, LocalDate fechaPublicacion, int cantidadDisponible, Editorial editorial, Categoria categoria, Genero genero, Idioma idioma, List<Autor> autores, Propuesta propuestaOrigen) {
        this.id = id;
        this.isbn = isbn;
        this.titulo = titulo;
        this.sinopsis = sinopsis;
        this.cantidadPaginas = cantidadPaginas;
        this.precio = precio;
        this.fechaPublicacion = fechaPublicacion;
        this.cantidadDisponible = cantidadDisponible;
        this.stockMinimo = 5;
        this.editorial = editorial;
        this.categoria = categoria;
        this.genero = genero;
        this.idioma = idioma;
        this.autores = autores;
        this.propuestaOrigen = propuestaOrigen;
    }

    //Getters y setters☆゜・。。・゜゜・。。・゜★゜・。。・゜゜・。。・゜☆゜・。。・゜゜・。。・゜★
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public int getCantidadPaginas() {
        return cantidadPaginas;
    }

    public void setCantidadPaginas(int cantidadPaginas) {
        this.cantidadPaginas = cantidadPaginas;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public LocalDate getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDate fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public int getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(int cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    public int getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(int stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public Editorial getEditorial() {
        return editorial;
    }

    public void setEditorial(Editorial editorial) {
        this.editorial = editorial;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    public Propuesta getPropuestaOrigen() {
        return propuestaOrigen;
    }

    public void setPropuestaOrigen(Propuesta propuestaOrigen) {
        this.propuestaOrigen = propuestaOrigen;
    }

    //Métodos☆゜・。。・゜゜・。。・゜★゜・。。・゜゜・。。・゜☆゜・。。・゜゜・。。・゜★
    public boolean estaDisponible() {
        return  cantidadDisponible > 0;
    }

    public boolean necesitaReposicion() {
        return cantidadDisponible <= stockMinimo;
    }

    //ToString☆゜・。。・゜゜・。。・゜★゜・。。・゜゜・。。・゜☆゜・。。・゜゜・。。・゜★
    @Override
    public String toString() {
        return "Libro: " +
                "\nIsbn: " + isbn +
                "\nTítulo: " + titulo +
                "\nSinopsis: " + sinopsis +
                "\nCantidad de páginas: " + cantidadPaginas +
                "\nPrecio: " + precio +
                "\nFecha publicación: " + fechaPublicacion +
                "\nCantidad disponible: " + cantidadDisponible +
                "\nEditorial: " + editorial +
                "\nCategoría: " + categoria +
                "\nGénero: " + genero +
                "\nIdioma: " + idioma +
                "\nAutores: " + autores +
                "\nPropuesta origen: " + propuestaOrigen;
    }

}
