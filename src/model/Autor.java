package model;

public class Autor {

    private int id;
    private String nombre;
    private String apellido;
    private Usuario escritorVinculado;

    //Constructores☆゜・。。・゜゜・。。・゜★゜・。。・゜゜・。。・゜☆゜・。。・゜゜・。。・゜★
    public Autor() {
    }

    public Autor(String nombre, String apellido, Usuario escritorVinculado) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.escritorVinculado = escritorVinculado;
    }

    public Autor(int id, String nombre, String apellido, Usuario escritorVinculado) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.escritorVinculado = escritorVinculado;
    }

    //Getters y setters☆゜・。。・゜゜・。。・゜★゜・。。・゜゜・。。・゜☆゜・。。・゜゜・。。・゜★
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Usuario getEscritorVinculado() {
        return escritorVinculado;
    }

    public void setEscritorVinculado(Usuario escritorVinculado) {
        this.escritorVinculado = escritorVinculado;
    }

    //ToString☆゜・。。・゜゜・。。・゜★゜・。。・゜゜・。。・゜☆゜・。。・゜゜・。。・゜★
    @Override
    public String toString() {
        return "Autor: " +
                "\nNombre: " + nombre +
                "\nApellido: " + apellido +
                "\nEscritor Vinculado: " + escritorVinculado.getNombreUsuario();
    }

}
