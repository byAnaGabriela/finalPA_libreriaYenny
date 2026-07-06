package bll;

import java.time.LocalDateTime;

public class Propuesta {

    private int id;
    private String titulo;
    private String descripcion;
    private LocalDateTime fechaCreacion;
    private Escritor escritor;
    private Editor editor;
    private EstadoPropuesta estado;

    //Constructores☆゜・。。・゜゜・。。・゜★゜・。。・゜゜・。。・゜☆゜・。。・゜゜・。。・゜★
    public Propuesta() {
    }

    public Propuesta(String titulo, String descripcion, LocalDateTime fechaCreacion, Escritor escritor, Editor editor, EstadoPropuesta estado) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;
        this.escritor = escritor;
        this.editor = editor;
        this.estado = estado;
    }

    public Propuesta(int id, String titulo, String descripcion, LocalDateTime fechaCreacion, Escritor escritor, Editor editor, EstadoPropuesta estado) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;
        this.escritor = escritor;
        this.editor = editor;
        this.estado = estado;
    }

    //Getters y setters☆゜・。。・゜゜・。。・゜★゜・。。・゜゜・。。・゜☆゜・。。・゜゜・。。・゜★
    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Escritor getEscritor() {
        return escritor;
    }

    public void setEscritor(Escritor escritor) {
        this.escritor = escritor;
    }

    public Editor getEditor() {
        return editor;
    }

    public void setEditor(Editor editor) {
        this.editor = editor;
    }

    public EstadoPropuesta getEstado() {
        return estado;
    }

    public void setEstado(EstadoPropuesta estado) {
        this.estado = estado;
    }

    //ToString☆゜・。。・゜゜・。。・゜★゜・。。・゜゜・。。・゜☆゜・。。・゜゜・。。・゜★
    @Override
    public String toString() {
        return "Propuesta: " +
                "\nTítulo: " + titulo +
                "\nDescripción: " + descripcion +
                "\nFecha de creación: " + fechaCreacion +
                "\nEscritor: " + escritor.getNombre() +
                "\nEditor asignado: " + editor.getNombre() +
                "\nEstado: " + estado.getNombre();
    }

}