package cibertec.pe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cibertec.pe.entity.Mantenimiento;
import cibertec.pe.feignclient.MantenimientoFeignClient;

@RestController
@RequestMapping("/api/factura")

public class FacturaController {
	
	@Autowired
	private MantenimientoFeignClient manten;
	
	@GetMapping("/listMantenimi")
	public List<Mantenimiento>getAllMantenimientos(){
		return manten.listarMantenimientos();
	}

}
