package cibertec.pe.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cibertec.pe.entity.ProveedorDto;
import cibertec.pe.entity.RepuestoRequest;
import cibertec.pe.feignclient.ProveedorFeignClient;
import cibertec.pe.model.Repuesto;
import cibertec.pe.repository.IRepuestoRepository;

@Service
public class RepuestoImplement implements IRepuestoService {
	@Autowired
	private IRepuestoRepository repu;

	@Autowired
	private ProveedorFeignClient provefeign;

	@Override
	public List<Repuesto> getAllRepuestos() {
		return repu.findAll();
	}

	@Override
	public Repuesto createRepuesto(RepuestoRequest request) {
		// Consumir Proveedor-Service usando FeignClient
		ProveedorDto prodto = provefeign.obtenerProveedorPorId(request.getCod_Proveedor());
		if (prodto != null) {
			Repuesto nuevoRepuesto = new Repuesto();

			nuevoRepuesto.setNom_Repuesto(request.getNom_Repuesto());
			nuevoRepuesto.setMarcaRep(request.getMarcaRep());
			nuevoRepuesto.setDescripRep(request.getDescripRep());
			nuevoRepuesto.setPrecioUnitario(request.getPrecioUnitario());
			nuevoRepuesto.setStock(request.getStock());
			nuevoRepuesto.setCod_Proveedor(prodto.getCod_Proveedor());

			return repu.save(nuevoRepuesto);
		} else {
			throw new RuntimeException("ERROR: El Proveedor no existe.");
		}

	}

	@Override
	public Optional<Repuesto> findRepuesto(int codigo) {
		return repu.findById(codigo);
	}

	@Override
	public String updateRepuesto(int codigo, RepuestoRequest request) {
		Repuesto repues = repu.findById(codigo).orElse(null);
		if (repues != null) {
			// Validamos que el proveedor del request exista
			ProveedorDto prodto = provefeign.obtenerProveedorPorId(request.getCod_Proveedor());

			if (prodto != null) {
				repues.setNom_Repuesto(request.getNom_Repuesto());
				repues.setMarcaRep(request.getMarcaRep());
				repues.setDescripRep(request.getDescripRep());
				repues.setPrecioUnitario(request.getPrecioUnitario());
				repues.setStock(request.getStock());
				repues.setCod_Proveedor(prodto.getCod_Proveedor());
				repu.save(repues);
				return "Repuesto Actualizado";
			} else
				return "ERROR: Proveedor no encontrado";
		} else return "ERROR";
	}

	@Override
	public void deleteRepuesto(int codigo) {
		repu.deleteById(codigo);
	}

	@Override
	public void disminuirStock(int codigo, int cantidad) {
		Repuesto repue = repu.findById(codigo).orElse(null);
		if(repue!=null) {
			int nuevoStock = repue.getStock()-cantidad;
			repue.setStock(nuevoStock);
			repu.save(repue);
		}
		
	}
}
