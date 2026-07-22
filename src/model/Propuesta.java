package model;

import model.enums.EstadoPropuesta;

import java.time.LocalDateTime;

public class Propuesta {

    private int id;
    private String titulo;
    private String descripcion;
    private LocalDateTime fechaCreacion;
    private Usuario escritor;
    private Usuario editor;
    private EstadoPropuesta estado;

    //Constructores☆゜・。。・゜゜・。。・゜★゜・。。・゜゜・。。・゜☆゜・。。・゜゜・。。・゜★
    public Propuesta() {
    }

    public Propuesta(String titulo, String descripcion, LocalDateTime fechaCreacion, Usuario escritor, Usuario editor, EstadoPropuesta estado) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;
        this.escritor = escritor;
        this.editor = editor;
        this.estado = estado;
    }

    public Propuesta(int id, String titulo, String descripcion, LocalDateTime fechaCreacion, Usuario escritor, Usuario editor, EstadoPropuesta estado) {
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

    public void setId(int id) {
        this.id = id;
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

    public Usuario getEscritor() {
        return escritor;
    }

    public void setEscritor(Usuario escritor) {
        this.escritor = escritor;
    }

    public Usuario getEditor() {
        return editor;
    }

    public void setEditor(Usuario editor) {
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
                "\nEstado: " + estado;
    }

}
