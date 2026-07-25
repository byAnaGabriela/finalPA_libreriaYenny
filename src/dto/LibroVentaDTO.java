package dto;

import model.Libro;

import java.math.BigDecimal;

public class LibroVentaDTO {
    private Libro libro;
    private int cantidadVendida;
    private BigDecimal ingresoTotal;

    //Constructor☆゜・。。・゜゜・。。・゜★゜・。。・゜゜・。。・゜☆゜・。。・゜゜・。。・゜★
    public LibroVentaDTO(Libro libro, int cantidadVendida, BigDecimal ingresoTotal) {
        this.libro = libro;
        this.cantidadVendida = cantidadVendida;
        this.ingresoTotal = ingresoTotal;
    }

    //Getters y setters☆゜・。。・゜゜・。。・゜★゜・。。・゜゜・。。・゜☆゜・。。・゜゜・。。・゜★
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

    public BigDecimal getIngresoTotal() {
        return ingresoTotal;
    }

    public void setIngresoTotal(BigDecimal ingresoTotal) {
        this.ingresoTotal = ingresoTotal;
    }

    //ToString☆゜・。。・゜゜・。。・゜★゜・。。・゜゜・。。・゜☆゜・。。・゜゜・。。・゜★
    @Override
    public String toString() {
        return "Libro - Venta:" +
                "\nlibro: " + libro +
                "\nCantidad vendida: " + cantidadVendida +
                "\nIngreso total: " + ingresoTotal;
    }
}
