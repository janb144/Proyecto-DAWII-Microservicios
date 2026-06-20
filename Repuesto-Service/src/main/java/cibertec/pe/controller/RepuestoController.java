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

import cibertec.pe.entity.RepuestoRequest;
import cibertec.pe.model.Repuesto;
import cibertec.pe.service.RepuestoImplement;

@RestController
@RequestMapping("/api/repuesto")
public class RepuestoController {
	@Autowired
	private RepuestoImplement repu;

	@GetMapping("/listarRepuestos")
	public List<Repuesto> getAllRepuestos() {
		return repu.getAllRepuestos();
	}

	@PostMapping("/crearRepuesto")
	public Repuesto createRepuesto(@RequestBody RepuestoRequest request) {
		return repu.createRepuesto(request);
	}

	@GetMapping("/buscarRepuesto/{codigo}")
	public Optional<Repuesto> findRepuesto(@PathVariable int codigo) {
		return repu.findRepuesto(codigo);
	}

	@PutMapping("/editarRepuesto/{codigo}")
	public String updateRepuesto(@PathVariable int codigo, @RequestBody RepuestoRequest request) {
		return repu.updateRepuesto(codigo, request);
	}

	@DeleteMapping("/eliminarRepuesto/{codigo}")
	public void deleteRepuesto(@PathVariable int codigo) {
		repu.deleteRepuesto(codigo);
	}
	
	@PutMapping("/disminuirStock/{codigo}/{cantidad}")
	public void disminuirStock(@PathVariable int codigo, @PathVariable int cantidad) {
		repu.disminuirStock(codigo, cantidad);
	}
}
