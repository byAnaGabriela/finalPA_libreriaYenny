package bll;

public class DetalleVenta {

    private Venta venta;
    private Libro libro;
    private int cantidadVendida;
    private double precioUnitario;

    //Constructores☆゜・。。・゜゜・。。・゜★゜・。。・゜゜・。。・゜☆゜・。。・゜゜・。。・゜★
    public DetalleVenta() {
    }

    public DetalleVenta(Venta venta, Libro libro, int cantidadVendida, double precioUnitario) {
        this.venta = venta;
        this.libro = libro;
        this.cantidadVendida = cantidadVendida;
        this.precioUnitario = precioUnitario;
    }

    //Constructores☆゜・。。・゜゜・。。・゜★゜・。。・゜゜・。。・゜☆゜・。。・゜゜・。。・゜★
    public int getIdVenta() {
        return venta.getId();
    }

    public int getIdLibro() {
        return libro.getId();
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public int getCantidadVendida() {
        return cantidadVendida;
    }

    public void setCantidadVendida(int cantidadVendida) {
        this.cantidadVendida = cantidadVendida;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    //ToString☆゜・。。・゜゜・。。・゜★゜・。。・゜゜・。。・゜☆゜・。。・゜゜・。。・゜★
    @Override
    public String toString() {
        return "Detalle de venta: " +
                "\nN° de venta: " + venta.getId() +
                "\nlibro: " + libro.getTitulo() +
                "\nCantidad vendida: " + cantidadVendida +
                "\nPrecio unitario: " + precioUnitario;
    }

}