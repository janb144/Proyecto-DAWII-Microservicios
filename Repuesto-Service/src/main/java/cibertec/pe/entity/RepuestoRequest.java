package cibertec.pe.entity;

public class RepuestoRequest {
	private int cod_Proveedor;
	private String nom_Repuesto;
	private String marcaRep;
	private String descripRep;
	private double precioUnitario;
	private int stock;

	public RepuestoRequest() {
	}

	public RepuestoRequest(int cod_Proveedor, String nom_Repuesto, String marcaRep, String descripRep,
			double precioUnitario, int stock) {

		this.cod_Proveedor = cod_Proveedor;
		this.nom_Repuesto = nom_Repuesto;
		this.marcaRep = marcaRep;
		this.descripRep = descripRep;
		this.precioUnitario = precioUnitario;
		this.stock = stock;
	}

	public int getCod_Proveedor() {
		return cod_Proveedor;
	}

	public void setCod_Proveedor(int cod_Proveedor) {
		this.cod_Proveedor = cod_Proveedor;
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
