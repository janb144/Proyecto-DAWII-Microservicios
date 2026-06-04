package cibertec.pe.service;

import java.util.List;
import java.util.Optional;

import cibertec.pe.model.Mantenimiento;

public interface IMantenimientoService {
	public List<Mantenimiento>				getAllMantenimientos();
	public Mantenimiento					createMantenimiento(Mantenimiento mantenimiento);
	public Optional<Mantenimiento>			findMantenimiento(int codigo);
	public String							updateMantenimiento(int codigo, Mantenimiento mantenimiento);
	public void 							deleteMantenimiento(int codigo);
}
