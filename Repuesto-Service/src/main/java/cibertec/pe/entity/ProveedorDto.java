package cibertec.pe.entity;

public class ProveedorDto {
	private int cod_Proveedor;
	private String ruc;
	private String nomRazSocial;
	private String telefono;
	private String direccion;
	private String correo;

	public ProveedorDto() {
	}

	public ProveedorDto(String ruc, String nomRazSocial, String telefono, String direccion, String correo) {
		this.ruc = ruc;
		this.nomRazSocial = nomRazSocial;
		this.telefono = telefono;
		this.direccion = direccion;
		this.correo = correo;
	}

	public ProveedorDto(int cod_Proveedor, String ruc, String nomRazSocial, String telefono, String direccion,
			String correo) {
		this.cod_Proveedor = cod_Proveedor;
		this.ruc = ruc;
		this.nomRazSocial = nomRazSocial;
		this.telefono = telefono;
		this.direccion = direccion;
		this.correo = correo;
	}

	public int getCod_Proveedor() {
		return cod_Proveedor;
	}

	public void setCod_Proveedor(int cod_Proveedor) {
		this.cod_Proveedor = cod_Proveedor;
	}

	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
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

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}
}
