package cibertec.pe.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_detalle_comprobante")
public class DetalleComprobante {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cod_Detalle;
	private int cod_Repuesto;
	private String nom_Repuesto;
	private double precioUnitario;
	private int cantidad;

	// Relación de muchos a uno: Muchos detalles pertenecen a un solo Comprobante
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_Factura")
	@JsonIgnore

	private Comprobante comprobante;

	public DetalleComprobante() {
	}

	public DetalleComprobante(int cod_Repuesto, String nom_Repuesto, double precioUnitario, int cantidad,
			Comprobante comprobante) {
		this.cod_Repuesto = cod_Repuesto;
		this.nom_Repuesto = nom_Repuesto;
		this.precioUnitario = precioUnitario;
		this.cantidad = cantidad;
		this.comprobante = comprobante;
	}

	public DetalleComprobante(int cod_Detalle, int cod_Repuesto, String nom_Repuesto, double precioUnitario,
			int cantidad, Comprobante comprobante) {
		this.cod_Detalle = cod_Detalle;
		this.cod_Repuesto = cod_Repuesto;
		this.nom_Repuesto = nom_Repuesto;
		this.precioUnitario = precioUnitario;
		this.cantidad = cantidad;
		this.comprobante = comprobante;
	}

	public int getCod_Detalle() {
		return cod_Detalle;
	}

	public void setCod_Detalle(int cod_Detalle) {
		this.cod_Detalle = cod_Detalle;
	}

	public int getCod_Repuesto() {
		return cod_Repuesto;
	}

	public void setCod_Repuesto(int cod_Repuesto) {
		this.cod_Repuesto = cod_Repuesto;
	}

	public String getNom_Repuesto() {
		return nom_Repuesto;
	}

	public void setNom_Repuesto(String nom_Repuesto) {
		this.nom_Repuesto = nom_Repuesto;
	}

	public double getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public Comprobante getComprobante() {
		return comprobante;
	}

	public void setComprobante(Comprobante comprobante) {
		this.comprobante = comprobante;
	}

}
