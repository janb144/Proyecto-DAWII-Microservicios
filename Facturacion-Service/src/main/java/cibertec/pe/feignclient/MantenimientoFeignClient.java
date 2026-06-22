package cibertec.pe.feignclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cibertec.pe.entity.MantenimientoDto;

@FeignClient(name="Mantenimiento-Service", url="http://localhost:9001")
public interface MantenimientoFeignClient {
	
	@GetMapping("/api/mantenimiento/buscarMantenimiento/{codigo}")
	public MantenimientoDto obtenerMantenimientoPorId(@PathVariable int codigo);
	
	@GetMapping("/api/mantenimiento/listMantenimientos")
	public List<MantenimientoDto> listarMantenimientos();

}
