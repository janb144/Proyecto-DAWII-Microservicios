package cibertec.pe.service;

import java.util.List;
import java.util.Optional;

import cibertec.pe.model.Proveedor;

public interface IProveedorService {
	public List<Proveedor>			getAllProveedores();
	public Proveedor				createProveedor(Proveedor proveedor);
	public Optional<Proveedor>		findProveedor(int codigo);
	public String					updateProveedor(int codigo, Proveedor proveedor);
	public void						deleteProveedor(int codigo);
}
