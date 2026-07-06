package bll;

public class Idioma {

    private int id;
    private String nombre;

    //Constructores☆゜・。。・゜゜・。。・゜★゜・。。・゜゜・。。・゜☆゜・。。・゜゜・。。・゜★
    public Idioma() {
    }

    public Idioma(String nombre) {
        this.nombre = nombre;
    }

    public Idioma(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    //Getters y setters☆゜・。。・゜゜・。。・゜★゜・。。・゜゜・。。・゜☆゜・。。・゜゜・。。・゜★
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    //ToString☆゜・。。・゜゜・。。・゜★゜・。。・゜゜・。。・゜☆゜・。。・゜゜・。。・゜★
    @Override
    public String toString() {
        return "Idioma: " +
                "\nNombre: " + nombre;
    }

}