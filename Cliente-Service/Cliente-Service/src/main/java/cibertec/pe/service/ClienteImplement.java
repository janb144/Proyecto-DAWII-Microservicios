package cibertec.pe.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cibertec.pe.model.Cliente;
import cibertec.pe.repository.IClienteRepository;

@Service
public class ClienteImplement implements IClienteService{
	@Autowired
	private IClienteRepository clien;

	@Override
	public List<Cliente> getAllClientes() {
		return clien.findAll();
	}

	@Override
	public Cliente createCliente(Cliente cliente) {
		return clien.save(cliente);
	}

	@Override
	public Optional<Cliente> findCliente(int codigo) {
		return clien.findById(codigo);
	}

	@Override
	public String updateCliente(int codigo, Cliente cliente) {
		Cliente nueclie = clien.findById(codigo).get();
		if(nueclie !=null) {
			nueclie.setTipoDocumento(cliente.getTipoDocumento());
			nueclie.setNomRazSocial(cliente.getNomRazSocial());
			nueclie.setNumDocumento(cliente.getNumDocumento());
			nueclie.setDireccion(cliente.getDireccion());
			nueclie.setTelefono(cliente.getTelefono());
			clien.save(nueclie);
			
			return "Cliente Actualizado";
		}else return "ERROR al Actualizar";
	}

	@Override
	public void deleteCliente(int codigo) {
		clien.deleteById(codigo);
	}

}
