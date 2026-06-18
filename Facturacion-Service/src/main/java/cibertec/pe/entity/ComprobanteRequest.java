package cibertec.pe.entity;

import java.util.List;

public class ComprobanteRequest {
	private int cod_Mantenimiento;
	private List<DetalleRequest> repuestos;

	public ComprobanteRequest() {
	}

	public ComprobanteRequest(int cod_Mantenimiento, List<DetalleRequest> repuestos) {
		this.cod_Mantenimiento = cod_Mantenimiento;
		this.repuestos = repuestos;
	}

	public int getCod_Mantenimiento() {
		return cod_Mantenimiento;
	}

	public void setCod_Mantenimiento(int cod_Mantenimiento) {
		this.cod_Mantenimiento = cod_Mantenimiento;
	}

	public List<DetalleRequest> getRepuestos() {
		return repuestos;
	}

	public void setRepuestos(List<DetalleRequest> repuestos) {
		this.repuestos = repuestos;
	}

}
