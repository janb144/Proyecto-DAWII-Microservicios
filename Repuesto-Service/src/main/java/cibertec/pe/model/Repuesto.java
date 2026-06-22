package cibertec.pe.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Repuesto {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int cod_Repuesto;
	private String nom_Repuesto;
	private String marcaRep;
	private String descripRep;
	private double precioUnitario;
	private int stock;
	
	public Repuesto() {}

	public Repuesto(int cod_Repuesto, String nom_Repuesto, String marcaRep, String descripRep, double precioUnitario,
			int stock) {
		this.cod_Repuesto = cod_Repuesto;
		this.nom_Repuesto = nom_Repuesto;
		this.marcaRep = marcaRep;
		this.descripRep = descripRep;
		this.precioUnitario = precioUnitario;
		this.stock = stock;
	}

	public Repuesto(String nom_Repuesto, String marcaRep, String descripRep, double precioUnitario, int stock) {
		this.nom_Repuesto = nom_Repuesto;
		this.marcaRep = marcaRep;
		this.descripRep = descripRep;
		this.precioUnitario = precioUnitario;
		this.stock = stock;
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

	public String getMarcaRep() {
		return marcaRep;
	}

	public void setMarcaRep(String marcaRep) {
		this.marcaRep = marcaRep;
	}

	public String getDescripRep() {
		return descripRep;
	}

	public void setDescripRep(String descripRep) {
		this.descripRep = descripRep;
	}

	public double getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}
}
