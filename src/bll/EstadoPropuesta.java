package bll;

public class EstadoPropuesta {

    private int id;
    private String nombre;

    //Constructores☆゜・。。・゜゜・。。・゜★゜・。。・゜゜・。。・゜☆゜・。。・゜゜・。。・゜★
    public EstadoPropuesta() {
    }

    public EstadoPropuesta(String nombre) {
        this.nombre = nombre;
    }

    public EstadoPropuesta(int id, String nombre) {
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
        return "Estado de propuesta: " +
                "\nNombre: " + nombre;
    }

}