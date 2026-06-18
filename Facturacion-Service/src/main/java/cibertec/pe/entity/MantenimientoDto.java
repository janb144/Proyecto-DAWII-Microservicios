package cibertec.pe.entity;

public class MantenimientoDto {
	private int cod_Mantenimiento;
	private int cod_Cliente;
	private String motoPlaca;
	private String motoModelo;
	private double costoManoObra;
	private String estado = "Pendiente";

	public MantenimientoDto() {
	}

	public MantenimientoDto(int cod_Cliente, String motoPlaca, String motoModelo, double costoManoObra, String estado) {
		this.cod_Cliente = cod_Cliente;
		this.motoPlaca = motoPlaca;
		this.motoModelo = motoModelo;
		this.costoManoObra = costoManoObra;
		this.estado = estado;
	}

	public MantenimientoDto(int cod_Mantenimiento, int cod_Cliente, String motoPlaca, String motoModelo,
			double costoManoObra, String estado) {
		this.cod_Mantenimiento = cod_Mantenimiento;
		this.cod_Cliente = cod_Cliente;
		this.motoPlaca = motoPlaca;
		this.motoModelo = motoModelo;
		this.costoManoObra = costoManoObra;
		this.estado = estado;
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

}
