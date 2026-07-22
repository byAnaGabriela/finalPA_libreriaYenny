package model;

import java.math.BigDecimal;

public class DetalleVenta {

    private Venta venta;
    private Libro libro;
    private int cantidadVendida;
    private BigDecimal precioUnitario;

    //Constructores☆゜・。。・゜゜・。。・゜★゜・。。・゜゜・。。・゜☆゜・。。・゜゜・。。・゜★
    public DetalleVenta() {
    }

    public DetalleVenta(Venta venta, Libro libro, int cantidadVendida, BigDecimal precioUnitario) {
        this.venta = venta;
        this.libro = libro;
        this.cantidadVendida = cantidadVendida;
        this.precioUnitario = precioUnitario;
    }

    //Getters y setters☆゜・。。・゜゜・。。・゜★゜・。。・゜゜・。。・゜☆゜・。。・゜゜・。。・゜★
    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public Libro getLibro() {
        return libro;
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

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
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
