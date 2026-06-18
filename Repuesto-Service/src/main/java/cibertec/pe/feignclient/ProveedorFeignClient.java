package cibertec.pe.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cibertec.pe.entity.ProveedorDto;

@FeignClient(name="Proveedor-Service", url="http://localhost:9005")
public interface ProveedorFeignClient {
	@GetMapping("api/proveedor/buscarProveedor/{codigo}")
	public ProveedorDto obtenerProveedorPorId(@PathVariable int codigo)  ;

}
