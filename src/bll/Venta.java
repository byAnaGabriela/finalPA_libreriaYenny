package bll;

import enums.MetodoPago;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Venta {

    private int id;
    private LocalDateTime fecha;
    private double precioTotal;
    private double descuento;
    private MetodoPago metodoPago;
    private Vendedor vendedor;
    private List<DetalleVenta> detalles;

    //Constructores☆゜・。。・゜゜・。。・゜★゜・。。・゜゜・。。・゜☆゜・。。・゜゜・。。・゜★
    public Venta() {
    }

    public Venta(LocalDateTime fecha, double precioTotal, double descuento, MetodoPago metodoPago, Vendedor vendedor, List<DetalleVenta> detalles) {
        this.fecha = fecha;
        this.precioTotal = precioTotal;
        this.descuento = descuento;
        this.metodoPago = metodoPago;
        this.vendedor = vendedor;
        this.detalles = new ArrayList<>(detalles);
    }

    public Venta(int id, LocalDateTime fecha, double precioTotal, double descuento, MetodoPago metodoPago, Vendedor vendedor, List<DetalleVenta> detalles) {
        this.id = id;
        this.fecha = fecha;
        this.precioTotal = precioTotal;
        this.descuento = descuento;
        this.metodoPago = metodoPago;
        this.vendedor = vendedor;
        this.detalles = new ArrayList<>(detalles);
    }

    //Getters y setters☆゜・。。・゜゜・。。・゜★゜・。。・゜゜・。。・゜☆゜・。。・゜゜・。。・゜★
    public int getId() {
        return id;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public List<DetalleVenta> getDetalleVentas() {
        return detalles;
    }

    public void setDetalleVentas(List<DetalleVenta> detalles) {
        this.detalles = detalles;
    }

    //ToString☆゜・。。・゜゜・。。・゜★゜・。。・゜゜・。。・゜☆゜・。。・゜゜・。。・゜★
    @Override
    public String toString() {
        return "Venta: " +
                "\nFecha: " + fecha +
                "\nPrecio total: " + precioTotal +
                "\nDescuento: " + descuento +
                "\nMetodo de pago: " + metodoPago +
                "\nVendedor: " + vendedor.getNombre() +
                "\nDetalle de ventas: " + detalles;
    }

}