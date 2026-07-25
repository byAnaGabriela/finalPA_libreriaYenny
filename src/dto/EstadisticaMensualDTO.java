package dto;

import java.math.BigDecimal;
import java.util.List;

public class EstadisticaMensualDTO {
    private int anio;
    private int mes;
    private int cantidadVentas;
    private int cantidadLibrosVendidos;
    private BigDecimal montoTotal;
    private List<LibroVentaDTO> detallePorLibro;

    //Constructor☆゜・。。・゜゜・。。・゜★゜・。。・゜゜・。。・゜☆゜・。。・゜゜・。。・゜★
    public EstadisticaMensualDTO(int anio, int mes, int cantidadVentas, int cantidadLibrosVendidos, BigDecimal montoTotal, List<LibroVentaDTO> detallePorLibro) {
        this.anio = anio;
        this.mes = mes;
        this.cantidadVentas = cantidadVentas;
        this.cantidadLibrosVendidos = cantidadLibrosVendidos;
        this.montoTotal = montoTotal;
        this.detallePorLibro = detallePorLibro;
    }

    //Getters y setters☆゜・。。・゜゜・。。・゜★゜・。。・゜゜・。。・゜☆゜・。。・゜゜・。。・゜★
    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getCantidadVentas() {
        return cantidadVentas;
    }

    public void setCantidadVentas(int cantidadVentas) {
        this.cantidadVentas = cantidadVentas;
    }

    public int getCantidadLibrosVendidos() {
        return cantidadLibrosVendidos;
    }

    public void setCantidadLibrosVendidos(int cantidadLibrosVendidos) {
        this.cantidadLibrosVendidos = cantidadLibrosVendidos;
    }

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }

    public List<LibroVentaDTO> getDetallePorLibro() {
        return detallePorLibro;
    }

    public void setDetallePorLibro(List<LibroVentaDTO> detallePorLibro) {
        this.detallePorLibro = detallePorLibro;
    }

    //ToString☆゜・。。・゜゜・。。・゜★゜・。。・゜゜・。。・゜☆゜・。。・゜゜・。。・゜★
    @Override
    public String toString() {
        return "Estadistica mensual: " +
                "\nAño: " + anio +
                "\nMes: " + mes +
                "\nCantidad de ventas: " + cantidadVentas +
                "\nCantidad de libros vendidos: " + cantidadLibrosVendidos +
                "\nMonto total: " + montoTotal +
                "\nDetalle por libro: " + detallePorLibro;
    }

}
