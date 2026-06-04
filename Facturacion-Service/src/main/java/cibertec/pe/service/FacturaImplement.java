package cibertec.pe.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cibertec.pe.entity.Mantenimiento;
import cibertec.pe.feignclient.MantenimientoFeignClient;
import cibertec.pe.modelo.Factura;
import cibertec.pe.repository.IFacturaRepository;

@Service
public class FacturaImplement implements IFacturaService {
	@Autowired
	private IFacturaRepository fact;
	
	@Autowired
	private MantenimientoFeignClient mante;

	@Override
	public List<Factura> getAllFacturas() {
		return fact.findAll();
	}

	@Override
	public Factura createFactura(Factura factura) {
		return fact.save(factura);
	}

	@Override
	public Optional<Factura> findFactura(int codigo) {
		return fact.findById(codigo);
	}

	@Override
	public String updateFactura(int codigo, Factura factura) {
		Factura fctr = fact.findById(codigo).get();
		if(fctr!=null) {
			fctr.setClienteDocumento(factura.getClienteDocumento());
			fctr.setClienteNombre(factura.getClienteNombre());
			fctr.setCod_Mantenimiento(factura.getCod_Mantenimiento());
			
			fact.save(fctr);
			return "Factura Actualizada";
		}else return "ERRER";
	}

	@Override
	public void deleteFactura(int codigo) {
		fact.deleteById(codigo);

	}

	@Override
	public List<Mantenimiento> listMantenimientos() {
		return mante.listarMantenimientos();
	}

}
