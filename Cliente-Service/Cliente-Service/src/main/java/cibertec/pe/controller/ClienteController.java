package cibertec.pe.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cibertec.pe.model.Cliente;
import cibertec.pe.service.ClienteImplement;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {
	@Autowired 
	private ClienteImplement clien;

	@GetMapping("/listarClientes")
	public List<Cliente> getAllClientes() {
		return clien.getAllClientes();
	}

	@PostMapping("/crearCliente")
	public Cliente createCliente(@RequestBody Cliente cliente) {
		return clien.createCliente(cliente);
	}

	@GetMapping("/buscarCliente/{codigo}")
	public Optional<Cliente> findCliente(@PathVariable int codigo) {
		return clien.findCliente(codigo);
	}

	@PutMapping("/editarCliente/{codigo}")
	public String updateCliente(@PathVariable int codigo,@RequestBody Cliente cliente) {
		return clien.updateCliente(codigo, cliente);
	}

	@DeleteMapping("eliminarCliente/{codigo}")
	public void deleteCliente(@PathVariable int codigo) {
		clien.deleteCliente(codigo);
		
	}
}
