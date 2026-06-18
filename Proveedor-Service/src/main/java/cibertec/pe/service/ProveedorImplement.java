package cibertec.pe.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cibertec.pe.model.Proveedor;
import cibertec.pe.repository.IProveedorRepository;

@Service
public class ProveedorImplement implements IProveedorService {
	@Autowired
	private IProveedorRepository prove;

	@Override
	public List<Proveedor> getAllProveedores() {
		return prove.findAll();
	}

	@Override
	public Proveedor createProveedor(Proveedor proveedor) {
		return prove.save(proveedor);
	}

	@Override
	public Optional<Proveedor> findProveedor(int codigo) {
		return prove.findById(codigo);
	}

	@Override
	public String updateProveedor(int codigo, Proveedor proveedor) {
		Proveedor nueprove = prove.findById(codigo).get();
		if (nueprove != null) {
			nueprove.setRuc(proveedor.getRuc());
			nueprove.setNomRazSocial(proveedor.getNomRazSocial());
			nueprove.setTelefono(proveedor.getTelefono());
			nueprove.setDireccion(proveedor.getDireccion());
			nueprove.setCorreo(proveedor.getCorreo());
			prove.save(nueprove);

			return "Proveedor Actualizado";
		} else
			return "ERROR";
	}

	@Override
	public void deleteProveedor(int codigo) {
		prove.deleteById(codigo);
	}

}
