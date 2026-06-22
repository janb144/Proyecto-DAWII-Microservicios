package cibertec.pe.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_comprobantes")
public class Comprobante {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cod_Comprobante;
	private String tipoComprobante;
	private String nroSerie;
	private int correlativo;
	private int cod_Mantenimiento;
	private String clienteNombre;
	private String tipoDocumento;
	private String clienteDocumento;
	private String motoPlaca;
	private double costoManoObra;
	private double subTotal;
	private double igv;
	private double total;
	private LocalDate fechaEmision = LocalDate.now();
	// Relación uno a muchos: Un comprobante tiene muchos detalles
	// CascadeType.ALL permite que al guardar el comprobante se guarden
	// automáticamente sus detalles
	@OneToMany(mappedBy = "comprobante", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<DetalleComprobante> detalles = new ArrayList<>();

	public Comprobante() {
	}

	public Comprobante(String tipoComprobante, String nroSerie, int correlativo, int cod_Mantenimiento,
			String clienteNombre, String tipoDocumento, String clienteDocumento, String motoPlaca, double costoManoObra,
			double subTotal, double igv, double total, LocalDate fechaEmision, List<DetalleComprobante> detalles) {
		super();
		this.tipoComprobante = tipoComprobante;
		this.nroSerie = nroSerie;
		this.correlativo = correlativo;
		this.cod_Mantenimiento = cod_Mantenimiento;
		this.clienteNombre = clienteNombre;
		this.tipoDocumento = tipoDocumento;
		this.clienteDocumento = clienteDocumento;
		this.motoPlaca = motoPlaca;
		this.costoManoObra = costoManoObra;
		this.subTotal = subTotal;
		this.igv = igv;
		this.total = total;
		this.fechaEmision = fechaEmision;
		this.detalles = detalles;
	}

	public Comprobante(int cod_Comprobante, String tipoComprobante, String nroSerie, int correlativo,
			int cod_Mantenimiento, String clienteNombre, String tipoDocumento, String clienteDocumento,
			String motoPlaca, double costoManoObra, double subTotal, double igv, double total, LocalDate fechaEmision,
			List<DetalleComprobante> detalles) {
		super();
		this.cod_Comprobante = cod_Comprobante;
		this.tipoComprobante = tipoComprobante;
		this.nroSerie = nroSerie;
		this.correlativo = correlativo;
		this.cod_Mantenimiento = cod_Mantenimiento;
		this.clienteNombre = clienteNombre;
		this.tipoDocumento = tipoDocumento;
		this.clienteDocumento = clienteDocumento;
		this.motoPlaca = motoPlaca;
		this.costoManoObra = costoManoObra;
		this.subTotal = subTotal;
		this.igv = igv;
		this.total = total;
		this.fechaEmision = fechaEmision;
		this.detalles = detalles;
	}

	public int getCod_Comprobante() {
		return cod_Comprobante;
	}

	public void setCod_Comprobante(int cod_Comprobante) {
		this.cod_Comprobante = cod_Comprobante;
	}

	public String getTipoComprobante() {
		return tipoComprobante;
	}

	public void setTipoComprobante(String tipoComprobante) {
		this.tipoComprobante = tipoComprobante;
	}

	public String getNroSerie() {
		return nroSerie;
	}

	public void setNroSerie(String nroSerie) {
		this.nroSerie = nroSerie;
	}

	public int getCorrelativo() {
		return correlativo;
	}

	public void setCorrelativo(int correlativo) {
		this.correlativo = correlativo;
	}

	public int getCod_Mantenimiento() {
		return cod_Mantenimiento;
	}

	public void setCod_Mantenimiento(int cod_Mantenimiento) {
		this.cod_Mantenimiento = cod_Mantenimiento;
	}

	public String getClienteNombre() {
		return clienteNombre;
	}

	public void setClienteNombre(String clienteNombre) {
		this.clienteNombre = clienteNombre;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getClienteDocumento() {
		return clienteDocumento;
	}

	public void setClienteDocumento(String clienteDocumento) {
		this.clienteDocumento = clienteDocumento;
	}

	public String getMotoPlaca() {
		return motoPlaca;
	}

	public void setMotoPlaca(String motoPlaca) {
		this.motoPlaca = motoPlaca;
	}

	public double getCostoManoObra() {
		return costoManoObra;
	}

	public void setCostoManoObra(double costoManoObra) {
		this.costoManoObra = costoManoObra;
	}

	public double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}

	public double getIgv() {
		return igv;
	}

	public void setIgv(double igv) {
		this.igv = igv;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public LocalDate getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(LocalDate fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public List<DetalleComprobante> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<DetalleComprobante> detalles) {
		this.detalles = detalles;
	}

}
