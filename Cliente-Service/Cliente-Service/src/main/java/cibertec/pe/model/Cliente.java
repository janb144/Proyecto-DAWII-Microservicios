package cibertec.pe.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Cliente {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int codCliente;
	private String numDocumento;
	private String nomRazSocial;
	private String telefono;
	private String direccion;
	@Enumerated (EnumType.STRING)
	private TipoDocumento tipoDocumento;
	
	public Cliente() {}
	
	public Cliente(String numDocumento, String nomRazSocial, String telefono, String direccion,
			TipoDocumento tipoDocumento) {
		this.numDocumento = numDocumento;
		this.nomRazSocial = nomRazSocial;
		this.telefono = telefono;
		this.direccion = direccion;
		this.tipoDocumento = tipoDocumento;
	}
	
	public Cliente(int codCliente, String numDocumento, String nomRazSocial, String telefono, String direccion,
			TipoDocumento tipoDocumento) {
		this.codCliente = codCliente;
		this.numDocumento = numDocumento;
		this.nomRazSocial = nomRazSocial;
		this.telefono = telefono;
		this.direccion = direccion;
		this.tipoDocumento = tipoDocumento;
	}
	
	public int getCodCliente() {
		return codCliente;
	}
	public void setCodCliente(int codCliente) {
		this.codCliente = codCliente;
	}
	public String getNumDocumento() {
		return numDocumento;
	}
	public void setNumDocumento(String numDocumento) {
		this.numDocumento = numDocumento;
	}
	public String getNomRazSocial() {
		return nomRazSocial;
	}
	public void setNomRazSocial(String nomRazSocial) {
		this.nomRazSocial = nomRazSocial;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
}
