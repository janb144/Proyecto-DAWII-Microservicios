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

import cibertec.pe.model.Proveedor;
import cibertec.pe.service.ProveedorImplement;

@RestController
@RequestMapping("/api/proveedor")
public class ProveedorController {
	@Autowired
	private ProveedorImplement prove;

	@GetMapping("/listarProveedores")
	public List<Proveedor> getAllProveedores() {
		return prove.getAllProveedores();
	}

	@PostMapping("/crearProveedor")
	public Proveedor createProveedor(@RequestBody Proveedor proveedor) {
		return prove.createProveedor(proveedor);
	}

	@GetMapping("/buscarProveedor/{codigo}")
	public Optional<Proveedor> findProveedor(@PathVariable int codigo) {
		return prove.findProveedor(codigo);
	}

	@PutMapping("/editarProveedor/{codigo}")
	public String updateProveedor(@PathVariable int codigo,@RequestBody Proveedor proveedor) {
		return prove.updateProveedor(codigo, proveedor);
	}

	@DeleteMapping("/eliminarProveedor/{codigo}")
	public void deleteProveedor(@PathVariable int codigo) {
		prove.deleteProveedor(codigo);
	}

}
