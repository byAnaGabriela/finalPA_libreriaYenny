package bll;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Libro {

    private int id;
    private String isbn;
    private String titulo;
    private String sinopsis;
    private int cantidadPaginas;
    private double precio;
    private LocalDateTime fechaPublicacion;
    private Editorial editorial;
    private Categoria categoria;
    private Genero genero;
    private Idioma idioma;
    private List<Autor> autores;

    public Libro() {
    }

    public Libro(String isbn, String titulo, String sinopsis, int cantidadPaginas, double precio, LocalDateTime fechaPublicacion, Editorial editorial, Categoria categoria, Genero genero, Idioma idioma, List<Autor> autores) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.sinopsis = sinopsis;
        this.cantidadPaginas = cantidadPaginas;
        this.precio = precio;
        this.fechaPublicacion = fechaPublicacion;
        this.editorial = editorial;
        this.categoria = categoria;
        this.genero = genero;
        this.idioma = idioma;
        this.autores = new ArrayList<>();
    }

    public Libro(int id, String isbn, String titulo, String sinopsis, int cantidadPaginas, double precio, LocalDateTime fechaPublicacion, Editorial editorial, Categoria categoria, Genero genero, Idioma idioma, List<Autor> autores) {
        this.id = id;
        this.isbn = isbn;
        this.titulo = titulo;
        this.sinopsis = sinopsis;
        this.cantidadPaginas = cantidadPaginas;
        this.precio = precio;
        this.fechaPublicacion = fechaPublicacion;
        this.editorial = editorial;
        this.categoria = categoria;
        this.genero = genero;
        this.idioma = idioma;
        this.autores = new ArrayList<>();
    }

    public int getId() {
        return id;
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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public LocalDateTime getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDateTime fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
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

    @Override
    public String toString() {
        return "Libro: " +
                "\nIsbn: " + isbn +
                "\nTitulo: " + titulo +
                "\nSinopsis: " + sinopsis +
                "\nCantidad de páginas: " + cantidadPaginas +
                "\nPrecio: " + precio +
                "\nFecha de publicación: " + fechaPublicacion +
                "\nEditorial: " + editorial.getNombre() +
                "\nCategoría: " + categoria.getNombre() +
                "\nGénero: " + genero.getNombre() +
                "\nIdioma: " + idioma.getNombre() +
                "\nAutores: " + autores;
    }
}
