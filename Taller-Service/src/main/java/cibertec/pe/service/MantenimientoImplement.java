package cibertec.pe.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cibertec.pe.model.Mantenimiento;
import cibertec.pe.repository.IMantenimientoRepository;

@Service
public class MantenimientoImplement implements IMantenimientoService{
	@Autowired
	private IMantenimientoRepository mant;

	@Override
	public List<Mantenimiento> getAllMantenimientos() {
		return mant.findAll();
	}

	@Override
	public Mantenimiento createMantenimiento(Mantenimiento mantenimiento) {
		return mant.save(mantenimiento);
	}

	@Override
	public Optional<Mantenimiento> findMantenimiento(int codigo) {
		return mant.findById(codigo);
	}

	@Override
	public String updateMantenimiento(int codigo, Mantenimiento mantenimiento) {
		Mantenimiento manteni = mant.findById(codigo).get();
		if(manteni !=null) {
			manteni.setClienteDocumento(mantenimiento.getClienteDocumento());
			manteni.setClienteNombre(mantenimiento.getClienteNombre());
			manteni.setMotoPlaca(mantenimiento.getMotoPlaca());
			manteni.setMotoModelo(mantenimiento.getMotoModelo());
			manteni.setDescripcionAveria(mantenimiento.getDescripcionAveria());
			manteni.setCostoManoObra(mantenimiento.getCostoManoObra());
			manteni.setEstado(mantenimiento.getEstado());
			manteni.setFechaIngreso(mantenimiento.getFechaIngreso());
			mant.save(manteni);
			return "Mantenimiento Actualizado";
		}
		
		else return "ERROR";
	}

	@Override
	public void deleteMantenimiento(int codigo) {
		mant.deleteById(codigo);
		
	}

}
