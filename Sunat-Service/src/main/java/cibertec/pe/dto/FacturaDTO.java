package cibertec.pe.dto;

import java.time.LocalDate;
import java.util.List;

public class FacturaDTO {

    private int cod_Comprobante;
    private String tipoComprobante;
    private String nroSerie;
    private int correlativo;
    private int cod_Mantenimiento;
    private String clienteDocumento;
    private String clienteNombre;
    private double costoManoObra;
    private double subTotal;
    private double igv;
    private double total;
    private LocalDate fechaEmision;
    private List<DetalleComprobanteDTO> detalles;

    public FacturaDTO() {
    }

    public int getCod_Comprobante() { return cod_Comprobante; }
    public void setCod_Comprobante(int cod_Comprobante) { this.cod_Comprobante = cod_Comprobante; }

    public String getTipoComprobante() { return tipoComprobante; }
    public void setTipoComprobante(String tipoComprobante) { this.tipoComprobante = tipoComprobante; }

    public String getNroSerie() { return nroSerie; }
    public void setNroSerie(String nroSerie) { this.nroSerie = nroSerie; }

    public int getCorrelativo() { return correlativo; }
    public void setCorrelativo(int correlativo) { this.correlativo = correlativo; }

    public int getCod_Mantenimiento() { return cod_Mantenimiento; }
    public void setCod_Mantenimiento(int cod_Mantenimiento) { this.cod_Mantenimiento = cod_Mantenimiento; }

    public String getClienteDocumento() { return clienteDocumento; }
    public void setClienteDocumento(String clienteDocumento) { this.clienteDocumento = clienteDocumento; }

    public String getClienteNombre() { return clienteNombre; }
    public void setClienteNombre(String clienteNombre) { this.clienteNombre = clienteNombre; }

    public double getCostoManoObra() { return costoManoObra; }
    public void setCostoManoObra(double costoManoObra) { this.costoManoObra = costoManoObra; }

    public double getSubTotal() { return subTotal; }
    public void setSubTotal(double subTotal) { this.subTotal = subTotal; }

    public double getIgv() { return igv; }
    public void setIgv(double igv) { this.igv = igv; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public LocalDate getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(LocalDate fechaEmision) { this.fechaEmision = fechaEmision; }

    public List<DetalleComprobanteDTO> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleComprobanteDTO> detalles) { this.detalles = detalles; }
}