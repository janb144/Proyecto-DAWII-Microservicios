package cibertec.pe.entity;

public class ClienteDto {
	private int codCliente;
	private String tipDocumento;
	private String numDocumento;
	private String nomRazSocial;
	private String telefono;
	private String direccion;
	
	public ClienteDto() {}
	
	public ClienteDto(String tipDocumento, String numDocumento, String nomRazSocial, String telefono,
			String direccion) {
		this.tipDocumento = tipDocumento;
		this.numDocumento = numDocumento;
		this.nomRazSocial = nomRazSocial;
		this.telefono = telefono;
		this.direccion = direccion;
	}
	
	public ClienteDto(int codCliente, String tipDocumento, String numDocumento, String nomRazSocial, String telefono,
			String direccion) {
		this.codCliente = codCliente;
		this.tipDocumento = tipDocumento;
		this.numDocumento = numDocumento;
		this.nomRazSocial = nomRazSocial;
		this.telefono = telefono;
		this.direccion = direccion;
	}
	
	public int getCodCliente() {
		return codCliente;
	}
	public void setCodCliente(int codCliente) {
		this.codCliente = codCliente;
	}
	public String getTipDocumento() {
		return tipDocumento;
	}
	public void setTipDocumento(String tipDocumento) {
		this.tipDocumento = tipDocumento;
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
}
