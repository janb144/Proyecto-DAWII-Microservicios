package cibertec.pe.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cibertec.pe.entity.ComprobanteRequest;
import cibertec.pe.modelo.Comprobante;
import cibertec.pe.service.IComprobanteService;

@RestController
@RequestMapping("/api/comprobante")

public class ComprobanteController {
	@Autowired
	private IComprobanteService comproservi;
	
	@PostMapping("/crearComprobante")
	public Comprobante crearComprobante(@RequestBody ComprobanteRequest request) {
		return comproservi.crearComprobante(request);
	}
	
	
	@GetMapping("/listarComprobantes")
	public List<Comprobante> listarComprobantes(){
		return comproservi.getAllComprobantes();
	}
	
	@GetMapping("/buscarComprobante/{codigo}")
	public Optional<Comprobante> buscarComprobante(@PathVariable("codigo") int codigo) {
		return comproservi.findComprobante(codigo);
	}

}
