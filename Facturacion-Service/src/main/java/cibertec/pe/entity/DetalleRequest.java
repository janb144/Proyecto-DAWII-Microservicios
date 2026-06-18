package cibertec.pe.entity;

public class DetalleRequest {
	private int cod_Repuesto;
	private int cantidad;
	
	public DetalleRequest() {}
	
	public DetalleRequest(int cod_Repuesto, int cantidad) {
		this.cod_Repuesto = cod_Repuesto;
		this.cantidad = cantidad;
	}
	
	public int getCod_Repuesto() {
		return cod_Repuesto;
	}
	public void setCod_Repuesto(int cod_Repuesto) {
		this.cod_Repuesto = cod_Repuesto;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
}
