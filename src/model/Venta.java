package model;

import model.enums.MetodoPago;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Venta {

    private int id;
    private LocalDateTime fecha;
    private BigDecimal precioTotal;
    private BigDecimal descuento;
    private MetodoPago metodoPago;
    private Usuario vendedor;
    private List<DetalleVenta> detalles;

    //Constructores☆゜・。。・゜゜・。。・゜★゜・。。・゜゜・。。・゜☆゜・。。・゜゜・。。・゜★
    public Venta() {
    }

    public Venta(LocalDateTime fecha, BigDecimal precioTotal, BigDecimal descuento, MetodoPago metodoPago, Usuario vendedor, List<DetalleVenta> detalles) {
        this.fecha = fecha;
        this.precioTotal = precioTotal;
        this.descuento = descuento;
        this.metodoPago = metodoPago;
        this.vendedor = vendedor;
        this.detalles = detalles;
    }

    public Venta(int id, LocalDateTime fecha, BigDecimal precioTotal, BigDecimal descuento, MetodoPago metodoPago, Usuario vendedor, List<DetalleVenta> detalles) {
        this.id = id;
        this.fecha = fecha;
        this.precioTotal = precioTotal;
        this.descuento = descuento;
        this.metodoPago = metodoPago;
        this.vendedor = vendedor;
        this.detalles = detalles;
    }

    //Getters y setters☆゜・。。・゜゜・。。・゜★゜・。。・゜゜・。。・゜☆゜・。。・゜゜・。。・゜★
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(BigDecimal precioTotal) {
        this.precioTotal = precioTotal;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public Usuario getVendedor() {
        return vendedor;
    }

    public void setVendedor(Usuario vendedor) {
        this.vendedor = vendedor;
    }

    public List<DetalleVenta> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleVenta> detalles) {
        this.detalles = detalles;
    }

    //Métodos☆゜・。。・゜゜・。。・゜★゜・。。・゜゜・。。・゜☆゜・。。・゜゜・。。・゜★
    public BigDecimal calcularTotal() {
        BigDecimal total = BigDecimal.ZERO;
        int cantidadVendida = 0;

        for (DetalleVenta detalle : detalles) {
            //Subtotal -> cantidad * precio unitario
            BigDecimal subtotal = detalle.getPrecioUnitario().multiply(BigDecimal.valueOf(detalle.getCantidadVendida()));
            total = total.add(subtotal);

            //Suma de los ejemplares comprados
            cantidadVendida += detalle.getCantidadVendida();

            //Si se compran 5 o más libros se aplica un descuento del 10%
            if (cantidadVendida >= 5){
                BigDecimal descuentoCalculado = total.multiply(BigDecimal.valueOf(0.10));
                this.descuento = descuentoCalculado;
                total = total.subtract(descuentoCalculado);

                //Si no aplica el descuento, se queda en 0
            } else {
                this.descuento = BigDecimal.ZERO;
            }
            this.precioTotal = total;

        }
            return total;
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
