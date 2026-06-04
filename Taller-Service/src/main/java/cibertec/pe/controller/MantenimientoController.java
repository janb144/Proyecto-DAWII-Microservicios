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

import cibertec.pe.model.Mantenimiento;
import cibertec.pe.service.MantenimientoImplement;

@RestController
@RequestMapping("/api/mantenimiento")

public class MantenimientoController {
	@Autowired
	private MantenimientoImplement mant;
	
	@GetMapping("/listMantenimientos")
	public List<Mantenimiento> getAllMantenimientos() {
		return mant.getAllMantenimientos();
	}

	@PostMapping("/crearMantenimiento")
	public Mantenimiento crearMantenimiento(@RequestBody Mantenimiento mantenimiento) {
		return mant.createMantenimiento(mantenimiento);
	}

	@GetMapping("/buscarMantenimiento/{codigo}")
	public Optional<Mantenimiento> findMantenimiento(@PathVariable int codigo) {
		return mant.findMantenimiento(codigo);
	}

	@PutMapping("/editarMantenimiento/{codigo}")
	public String editMantenimiento(@PathVariable int codigo, @RequestBody Mantenimiento mantenimiento) {
		return mant.updateMantenimiento(codigo, mantenimiento);
	}

	@DeleteMapping("/borrarMantenimiento/{codigo}")
	public void deleteMantenimiento(@PathVariable int codigo) {
		mant.deleteMantenimiento(codigo);
		
	}
}
