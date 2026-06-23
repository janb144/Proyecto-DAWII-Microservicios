package cibertec.pe.dto;

public class DetalleComprobanteDTO {
    private int cod_Detalle;
    private int cod_Repuesto;
    private String nom_Repuesto;
    private double precioUnitario;
    private int cantidad;

    public DetalleComprobanteDTO() {
    }

    public int getCod_Detalle() { return cod_Detalle; }
    public void setCod_Detalle(int cod_Detalle) { this.cod_Detalle = cod_Detalle; }

    public int getCod_Repuesto() { return cod_Repuesto; }
    public void setCod_Repuesto(int cod_Repuesto) { this.cod_Repuesto = cod_Repuesto; }

    public String getNom_Repuesto() { return nom_Repuesto; }
    public void setNom_Repuesto(String nom_Repuesto) { this.nom_Repuesto = nom_Repuesto; }

    public double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(double precioUnitario) { this.precioUnitario = precioUnitario; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
}