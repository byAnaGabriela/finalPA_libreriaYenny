package bll;

import java.time.LocalDateTime;

public class Borrador {

    private int id;
    private int version;
    private String rutaArchivo;
    private LocalDateTime fechaSubida;
    private Propuesta propuesta;

    //Constructores☆゜・。。・゜゜・。。・゜★゜・。。・゜゜・。。・゜☆゜・。。・゜゜・。。・゜★
    public Borrador() {
    }

    public Borrador(int version, String rutaArchivo, LocalDateTime fechaSubida, Propuesta propuesta) {
        this.version = version;
        this.rutaArchivo = rutaArchivo;
        this.fechaSubida = fechaSubida;
        this.propuesta = propuesta;
    }

    public Borrador(int id, int version, String rutaArchivo, LocalDateTime fechaSubida, Propuesta propuesta) {
        this.id = id;
        this.version = version;
        this.rutaArchivo = rutaArchivo;
        this.fechaSubida = fechaSubida;
        this.propuesta = propuesta;
    }

    //Getters y setters☆゜・。。・゜゜・。。・゜★゜・。。・゜゜・。。・゜☆゜・。。・゜゜・。。・゜★
    public int getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getRutaArchivo() {
        return rutaArchivo;
    }

    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    public LocalDateTime getFechaSubida() {
        return fechaSubida;
    }

    public void setFechaSubida(LocalDateTime fechaSubida) {
        this.fechaSubida = fechaSubida;
    }

    public Propuesta getPropuesta() {
        return propuesta;
    }

    public void setPropuesta(Propuesta propuesta) {
        this.propuesta = propuesta;
    }

    //ToString☆゜・。。・゜゜・。。・゜★゜・。。・゜゜・。。・゜☆゜・。。・゜゜・。。・゜★
    @Override
    public String toString() {
        return "Borrador: " +
                "\nVersión: " + version +
                "\nRuta de archivo: " + rutaArchivo +
                "\nFecha de subida: " + fechaSubida +
                "\nPropuesta: " + propuesta.getTitulo();
    }

}