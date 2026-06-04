package cibertec.pe.entity;

import java.time.LocalDate;

public class Mantenimiento {
	private int cod_Mantenimiento;
	private String clienteDocumento;
	private String clienteNombre;
	private String motoPlaca;
	private String motoModelo;
	private String descripcionAveria;
	private double costoManoObra;
	private String estado = "Pendiente";
	private LocalDate fechaIngreso = LocalDate.now();
	
	public Mantenimiento() {}
	
	public Mantenimiento(String clienteDocumento, String clienteNombre, String motoPlaca, String motoModelo,
			String descripcionAveria, double costoManoObra, String estado, LocalDate fechaIngreso) {
		super();
		this.clienteDocumento = clienteDocumento;
		this.clienteNombre = clienteNombre;
		this.motoPlaca = motoPlaca;
		this.motoModelo = motoModelo;
		this.descripcionAveria = descripcionAveria;
		this.costoManoObra = costoManoObra;
		this.estado = estado;
		this.fechaIngreso = fechaIngreso;
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
	public String getMotoPlaca() {
		return motoPlaca;
	}
	public void setMotoPlaca(String motoPlaca) {
		this.motoPlaca = motoPlaca;
	}
	public String getMotoModelo() {
		return motoModelo;
	}
	public void setMotoModelo(String motoModelo) {
		this.motoModelo = motoModelo;
	}
	public String getDescripcionAveria() {
		return descripcionAveria;
	}
	public void setDescripcionAveria(String descripcionAveria) {
		this.descripcionAveria = descripcionAveria;
	}
	public double getCostoManoObra() {
		return costoManoObra;
	}
	public void setCostoManoObra(double costoManoObra) {
		this.costoManoObra = costoManoObra;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public LocalDate getFechaIngreso() {
		return fechaIngreso;
	}
	public void setFechaIngreso(LocalDate fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}
}
