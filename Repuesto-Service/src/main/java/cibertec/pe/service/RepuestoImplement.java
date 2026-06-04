package cibertec.pe.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cibertec.pe.model.Repuesto;
import cibertec.pe.repository.IRepuestoRepository;

@Service
public class RepuestoImplement implements IRepuestoService {
	@Autowired
	private IRepuestoRepository repu;

	@Override
	public List<Repuesto> getAllRepuestos() {
		return repu.findAll();
	}

	@Override
	public Repuesto createRepuesto(Repuesto repuesto) {
		return repu.save(repuesto);
	}

	@Override
	public Optional<Repuesto> findRepuesto(int codigo) {
		return repu.findById(codigo);
	}

	@Override
	public String updateRepuesto(int codigo, Repuesto repuesto) {
		Repuesto repues = repu.findById(codigo).get();
		if (repues != null) {
			repues.setNom_Repuesto(repuesto.getNom_Repuesto());
			repues.setMarcaRep(repuesto.getMarcaRep());
			repues.setDescripRep(repuesto.getDescripRep());
			repues.setPrecioUnitario(repuesto.getPrecioUnitario());
			repues.setStock(repuesto.getStock());
			repu.save(repues);

			return "Repuesto Actualizado";
		} else
			return "ERROR";
	}

	@Override
	public void deleteRepuesto(int codigo) {
		repu.deleteById(codigo);
	}

}
