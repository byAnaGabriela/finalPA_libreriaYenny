package dto;

import model.Autor;

import java.math.BigDecimal;
import java.util.List;

public class EscritorRendimientoDTO {
    private Autor autor;
    private int cantidadVendida;
    private BigDecimal gananciaTotal;
    private List<LibroVentaDTO> detallePorLibro;

    //Constructor☆゜・。。・゜゜・。。・゜★゜・。。・゜゜・。。・゜☆゜・。。・゜゜・。。・゜★
    public EscritorRendimientoDTO(Autor autor, int cantidadVendida, BigDecimal gananciaTotal, List<LibroVentaDTO> detallePorLibro) {
        this.autor = autor;
        this.cantidadVendida = cantidadVendida;
        this.gananciaTotal = gananciaTotal;
        this.detallePorLibro = detallePorLibro;
    }

    //Getters y setters☆゜・。。・゜゜・。。・゜★゜・。。・゜゜・。。・゜☆゜・。。・゜゜・。。・゜★
    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public int getCantidadVendida() {
        return cantidadVendida;
    }

    public void setCantidadVendida(int cantidadVendida) {
        this.cantidadVendida = cantidadVendida;
    }

    public BigDecimal getGananciaTotal() {
        return gananciaTotal;
    }

    public void setGananciaTotal(BigDecimal gananciaTotal) {
        this.gananciaTotal = gananciaTotal;
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
        return "Rendimiento de escritor: " +
                "\nAutor: " + autor +
                "\nCantidad vendida: " + cantidadVendida +
                "\nGanancia total: " + gananciaTotal +
                "\nDetalle por libro: " + detallePorLibro;
    }

}
