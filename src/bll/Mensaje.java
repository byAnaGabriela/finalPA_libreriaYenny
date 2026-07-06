package bll;

import java.time.LocalDateTime;

public class Mensaje {

    private int id;
    private String texto;
    private LocalDateTime fechaEnvio;
    private Propuesta propuesta;
    private Usuario usuario;
    private Mensaje mensajePadre;

    //Constructores☆゜・。。・゜゜・。。・゜★゜・。。・゜゜・。。・゜☆゜・。。・゜゜・。。・゜★
    public Mensaje() {
    }

    public Mensaje(String texto, LocalDateTime fechaEnvio, Usuario usuario, Propuesta propuesta, Mensaje mensajePadre) {
        this.texto = texto;
        this.fechaEnvio = fechaEnvio;
        this.usuario = usuario;
        this.propuesta = propuesta;
        this.mensajePadre = mensajePadre;
    }

    public Mensaje(int id, String texto, LocalDateTime fechaEnvio, Usuario usuario, Propuesta propuesta, Mensaje mensajePadre) {
        this.id = id;
        this.texto = texto;
        this.fechaEnvio = fechaEnvio;
        this.usuario = usuario;
        this.propuesta = propuesta;
        this.mensajePadre = mensajePadre;
    }

    //Getters y setters☆゜・。。・゜゜・。。・゜★゜・。。・゜゜・。。・゜☆゜・。。・゜゜・。。・゜★
    public int getId() {
        return id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public LocalDateTime getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(LocalDateTime fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Propuesta getPropuesta() {
        return propuesta;
    }

    public void setPropuesta(Propuesta propuesta) {
        this.propuesta = propuesta;
    }

    public Mensaje getMensajePadre() {
        return mensajePadre;
    }

    public void setMensajePadre(Mensaje mensajePadre) {
        this.mensajePadre = mensajePadre;
    }

    //ToString☆゜・。。・゜゜・。。・゜★゜・。。・゜゜・。。・゜☆゜・。。・゜゜・。。・゜★
    @Override
    public String toString() {
        return "Mensaje: " +
                "\nTexto: " + texto +
                "\nFecha de envío: " + fechaEnvio +
                "\nEnviado por: " + usuario.getNombre() +
                "\nPropuesta: " + propuesta.getTitulo() +
                "\nMensaje padre: " + mensajePadre;
    }

}