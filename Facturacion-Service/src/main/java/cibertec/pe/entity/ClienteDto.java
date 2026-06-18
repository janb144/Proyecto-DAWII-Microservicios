package cibertec.pe.entity;


public class ClienteDto {
	private int codCliente;
	private String numDocumento;
	private String nomRazSocial;
	private String tipoDocumento;

	public ClienteDto() {
	}

	public ClienteDto(String numDocumento, String nomRazSocial, String tipoDocumento) {
		this.numDocumento = numDocumento;
		this.nomRazSocial = nomRazSocial;
		this.tipoDocumento = tipoDocumento;
	}

	public ClienteDto(int codCliente, String numDocumento, String nomRazSocial, String tipoDocumento) {
		this.codCliente = codCliente;
		this.numDocumento = numDocumento;
		this.nomRazSocial = nomRazSocial;
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

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

}
