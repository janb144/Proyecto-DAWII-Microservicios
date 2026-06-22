package cibertec.pe.feignclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import cibertec.pe.entity.Mantenimiento;

@FeignClient(name ="REST-Mantenimiento-Service",url ="http://localhost:9001/")
public interface MantenimientoFeignClient {
	
	@GetMapping("/api/mantenimiento/listMantenimientos")
	public List<Mantenimiento> listarMantenimientos();

}
