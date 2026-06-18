package cibertec.pe.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Mantenimiento {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cod_Mantenimiento;
	private int cod_Cliente;
	private String motoPlaca;
	private String motoModelo;
	private String descripcionAveria;
	private double costoManoObra;
	private String estado = "Pendiente";
	private LocalDate fechaIngreso = LocalDate.now();

	public Mantenimiento() {
	}

	public Mantenimiento(int cod_Cliente, String motoPlaca, String motoModelo, String descripcionAveria,
			double costoManoObra, String estado, LocalDate fechaIngreso) {
		this.cod_Cliente = cod_Cliente;
		this.motoPlaca = motoPlaca;
		this.motoModelo = motoModelo;
		this.descripcionAveria = descripcionAveria;
		this.costoManoObra = costoManoObra;
		this.estado = estado;
		this.fechaIngreso = fechaIngreso;
	}

	public Mantenimiento(int cod_Mantenimiento, int cod_Cliente, String motoPlaca, String motoModelo,
			String descripcionAveria, double costoManoObra, String estado, LocalDate fechaIngreso) {
		this.cod_Mantenimiento = cod_Mantenimiento;
		this.cod_Cliente = cod_Cliente;
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

	public int getCod_Cliente() {
		return cod_Cliente;
	}

	public void setCod_Cliente(int cod_Cliente) {
		this.cod_Cliente = cod_Cliente;
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