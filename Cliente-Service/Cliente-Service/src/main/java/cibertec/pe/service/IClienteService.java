package cibertec.pe.service;

import java.util.List;
import java.util.Optional;

import cibertec.pe.model.Cliente;

public interface IClienteService {
	public List<Cliente>		getAllClientes();
	public Cliente				createCliente(Cliente cliente);
	public Optional<Cliente>	findCliente(int codigo);	
	public String				updateCliente(int codigo, Cliente cliente);
	public void 				deleteCliente(int codigo);
}
