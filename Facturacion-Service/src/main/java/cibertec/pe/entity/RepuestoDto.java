package cibertec.pe.entity;

public class RepuestoDto {
	private int cod_Repuesto;
	private String nom_Repuesto;
	private double precioUnitario;
	private int stock;

	public RepuestoDto() {
	}

	public RepuestoDto(String nom_Repuesto, double precioUnitario, int stock) {
		this.nom_Repuesto = nom_Repuesto;
		this.precioUnitario = precioUnitario;
		this.stock = stock;
	}

	public RepuestoDto(int cod_Repuesto, String nom_Repuesto, double precioUnitario, int stock) {
		this.cod_Repuesto = cod_Repuesto;
		this.nom_Repuesto = nom_Repuesto;
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
