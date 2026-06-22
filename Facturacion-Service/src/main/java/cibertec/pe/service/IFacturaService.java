package cibertec.pe.service;

import java.util.List;
import java.util.Optional;

import cibertec.pe.entity.Mantenimiento;
import cibertec.pe.modelo.Factura;

public interface IFacturaService {
	
	public List<Factura>				getAllFacturas();
	public Factura						createFactura(Factura factura);
	public Optional<Factura>			findFactura(int codigo);
	public String 						updateFactura(int codigo, Factura factura);
	public void							deleteFactura(int codigo);
	//empieza el consumo de otros servicios
	public List<Mantenimiento>			listMantenimientos();

}
