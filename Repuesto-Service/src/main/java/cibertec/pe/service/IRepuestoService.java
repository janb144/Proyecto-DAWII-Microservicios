package cibertec.pe.service;

import java.util.List;
import java.util.Optional;

import cibertec.pe.entity.RepuestoRequest;
import cibertec.pe.model.Repuesto;

public interface IRepuestoService {
	public List<Repuesto>					getAllRepuestos();
	public Repuesto							createRepuesto(RepuestoRequest request);
	public Optional<Repuesto>				findRepuesto(int codigo);
	public String							updateRepuesto(int codigo, RepuestoRequest request);
	public void 							deleteRepuesto(int codigo);
}
