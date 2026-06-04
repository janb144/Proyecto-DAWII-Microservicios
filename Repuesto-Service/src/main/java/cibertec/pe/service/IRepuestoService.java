package cibertec.pe.service;

import java.util.List;
import java.util.Optional;

import cibertec.pe.model.Repuesto;

public interface IRepuestoService {
	public List<Repuesto>					getAllRepuestos();
	public Repuesto							createRepuesto(Repuesto repuesto);
	public Optional<Repuesto>				findRepuesto(int codigo);
	public String							updateRepuesto(int codigo, Repuesto respuesto);
	public void 							deleteRepuesto(int codigo);
}
