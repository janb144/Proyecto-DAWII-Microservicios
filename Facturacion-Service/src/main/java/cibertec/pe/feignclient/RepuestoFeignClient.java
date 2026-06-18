package cibertec.pe.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cibertec.pe.entity.RepuestoDto;

@FeignClient(name="Repuesto-Service",url="http://localhost:9002")
public interface RepuestoFeignClient {
	
	@GetMapping("/api/repuesto/buscarRepuesto/{codigo}")
	public RepuestoDto obtenerRepuestoPorId(@PathVariable int codigo);

}
