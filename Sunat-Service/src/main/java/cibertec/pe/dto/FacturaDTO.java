package cibertec.pe.dto;


import java.time.LocalDate;

public class FacturaDTO {

    private int cod_Factura;
    private int cod_Mantenimiento;
    private String clienteDocumento;
    private String clienteNombre;
    private double costoManoObra;
    private double subTotal;
    private double igv;
    private double total;
    private LocalDate fechaEmision;

    public FacturaDTO() {
    }

    public int getCod_Factura() {
        return cod_Factura;
    }

    public void setCod_Factura(int cod_Factura) {
        this.cod_Factura = cod_Factura;
    }

    public int getCod_Mantenimiento() {
        return cod_Mantenimiento;
    }

    public void setCod_Mantenimiento(int cod_Mantenimiento) {
        this.cod_Mantenimiento = cod_Mantenimiento;
    }

    public String getClienteDocumento() {
        return clienteDocumento;
    }

    public void setClienteDocumento(String clienteDocumento) {
        this.clienteDocumento = clienteDocumento;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    public double getCostoManoObra() {
        return costoManoObra;
    }

    public void setCostoManoObra(double costoManoObra) {
        this.costoManoObra = costoManoObra;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public double getIgv() {
        return igv;
    }

    public void setIgv(double igv) {
        this.igv = igv;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

}